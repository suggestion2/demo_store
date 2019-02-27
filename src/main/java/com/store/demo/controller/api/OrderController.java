package com.store.demo.controller.api;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.*;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.request.OrderBuyForm;
import com.store.demo.request.OrderCreateForm;
import com.store.demo.request.OrderListForm;
import com.store.demo.response.OrderCreateView;
import com.store.demo.response.OrderItemView;
import com.store.demo.response.OrderListView;
import com.store.demo.response.OrderPrepareView;
import com.store.demo.service.*;
import com.store.demo.service.oss.OssService;
import com.store.demo.service.stock.GoodsStocks;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.SuccessView;
import com.sug.core.util.BigDecimalUtils;
import com.sug.core.util.SequenceNumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.store.demo.constants.CommonConstants.*;
import static com.store.demo.constants.OrderConstants.*;
import static com.store.demo.constants.PaymentConstants.ORDER;
import static com.store.demo.service.oss.ImageConstants.GOODS;

@RestController("apiOrderController")
@RequestMapping(value = "api/order")
@CustomerLoginRequired
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSpecUnitService goodsSpecUnitService;

    @Autowired
    private SessionContext sessionContext;

    @Autowired
    private OssService ossService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private PaymentService paymentService;

    @Value("${spring.profiles.active}")
    private String env;

    @RequestMapping(value = LIST, method = RequestMethod.POST)
    public OrderListView list(@Valid @RequestBody OrderListForm form) {
        Map<String, Object> map = form.getQueryMap();
        map.put("customerId", customerService.getCurrentCustomer().getId());
        return new OrderListView(orderService.selectViewList(map), orderService.selectCount(map));
    }

    //立即购买
    @RequestMapping(value = "/immediate", method = RequestMethod.POST)
    public ResponseView immediate(@Valid @RequestBody OrderBuyForm form) {
        Goods goods = goodsService.getById(form.getGoodsId());
        if (Objects.isNull(goods)) {
            throw new ResourceNotFoundException("商品不存在或已下架");
        }
        GoodsSpecUnit unit = null;
        //判断该商品是否有尺码规格
        if (Objects.isNull(form.getUnitId())) {
            throw new InvalidRequestException("请选择商品规格");
        }
        //判断该规格商品是否存在
        if (Objects.nonNull(form.getUnitId())) {
            unit = goodsSpecUnitService.getById(form.getUnitId());
            if (Objects.isNull(unit) || !unit.getGoodsId().equals(goods.getId())) {
                throw new ResourceNotFoundException("单品不存在或已下架");
            }
        }
        //判断商品库存是否足够
        GoodsStocks goodsStocks = goodsSpecUnitService.getStocks( form.getUnitId());
        if (goodsStocks.getStocks() < form.getCount()) {
            throw new InvalidRequestException("商品库存不足");
        }
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(form, orderItem);
        orderItem.setGoodsId(goods.getId());
        orderItem.setCount(form.getCount());
        orderItem.setCartItemId(IMMEDIATE_CARTITEM_ID);
        //判断该商品是否有尺码规格，有尺码规格按不同的尺码规格设置不同的价格还有商品名字等
        orderItem.setGoodsName(goods.getName() + "-" + unit.getTitle());
        orderItem.setUnitName(unit.getTitle());
        orderItem.setPrice(unit.getPrice());
        orderItem.setShippingCost(unit.getShippingCost());
        orderItem.setUnitId(unit.getId());
        orderItem.setBannerUrl(unit.getImageUrl());
        orderItem.setAmount(BigDecimalUtils.multiply(orderItem.getPrice(), orderItem.getCount()));
        sessionContext.setCurrentOrderItem(orderItem);
        return new ResponseView();
    }

    @RequestMapping(value = "/immediate/view", method = RequestMethod.GET)
    public OrderPrepareView immediateView() {
        OrderItem orderItem = sessionContext.getCurrentOrderItem();
        if(Objects.isNull(orderItem)){
            throw new ResourceNotFoundException("orderItem not found");
        }
        OrderPrepareView orderPrepareView = new OrderPrepareView();

        OrderItemView orderItemView = new OrderItemView();
        BeanUtils.copyProperties(orderItem, orderItemView);
        orderItemView.setPrice(orderItem.getPrice().doubleValue());
        orderItemView.setShippingCost(orderItem.getShippingCost().doubleValue());
        orderItemView.setAmount(orderItem.getAmount().doubleValue());
        orderItemView.setBannerUrl(ossService.getBucket(GOODS) + orderItemView.getBannerUrl());

        List<OrderItemView> orderItemViewList = new ArrayList<>();
        orderItemViewList.add(orderItemView);

        orderPrepareView.setShippingCostAmount(orderItem.getShippingCost().doubleValue());
        orderPrepareView.setGoodsAmount(orderItem.getAmount().doubleValue());
        orderPrepareView.setTotalAmount(new BigDecimal(orderPrepareView.getShippingCostAmount() + orderPrepareView.getGoodsAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        orderPrepareView.setCount(orderItem.getCount());

        orderPrepareView.setList(orderItemViewList);

        sessionContext.setOrderType(IMMEDIATE);
        return orderPrepareView;
    }

    @Transactional
    @RequestMapping(value = CREATE, method = RequestMethod.POST)
    public OrderCreateView create(@Valid @RequestBody OrderCreateForm form) {
        Customer customer = customerService.getCurrentCustomer();
        Integer type = sessionContext.getOrderType();
        if (Objects.isNull(type)) {
            throw new InvalidRequestException("order overdue");
        }
        //获得当前订单，不是购物车的订单
        OrderItem orderCurrentItem = sessionContext.getCurrentOrderItem();
        List<OrderItem> orderItemList = new ArrayList<>();
        List<CartItem> validCartItemList = new ArrayList<>();
        Cart cart = cartService.getCurrentCart();
        //判断是购物车的商品购买还是当前直接购买
        if (type.equals(IMMEDIATE)) {
            if (Objects.isNull(orderCurrentItem)) {
                throw new RuntimeException("orderCurrentItem not found");
            }
            orderItemList.add(orderCurrentItem);
        } else {
            if (!form.getPaymentForm().equals(WECHAT_PAY) && !form.getPaymentForm().equals(ALIPAY)) {
                throw new InvalidRequestException("invalid payment form");
            }
            if (Objects.isNull(cart)) {
                throw new ResourceNotFoundException("没有购物车");
            }
            List<CartItem> cartItemList = cartItemService.getListByCartId(form.getList(),cart.getId());
            if (Objects.isNull(cartItemList) || cartItemList.size() == 0) {
                throw new InvalidRequestException("购物车部分商品下架,请返回购物车查看");
            }
            List<GoodsStocks> stocksCheckList = new ArrayList<>();
            cartItemList.forEach(c -> {
                GoodsStocks goodsStocks = new GoodsStocks();
                goodsStocks.setId(c.getGoodsId());
                goodsStocks.setUnitId(c.getUnitId());
                goodsStocks.setStocks(c.getCount());
                stocksCheckList.add(goodsStocks);
            });

            Map<String, Integer> stocksMap = goodsService.getStocks(stocksCheckList);
            //判断库存是否大于订单量
            cartItemList.forEach(c -> {
                if (c.getCount() <= stocksMap.get(c.getGoodsId() + ":" + c.getUnitId())) {
                    OrderItem orderItem = new OrderItem();
                    BeanUtils.copyProperties(c, orderItem);
                    orderItem.setCartItemId(c.getId());
                    orderItemList.add(orderItem);
                    validCartItemList.add(c);
                }
            });

        }
        CustomerAddress customerAddress = customerAddressService.getById(form.getCustomerAddressId());
        if (Objects.isNull(customerAddress) || !customerAddress.getCustomerId().equals(customer.getId())) {
            throw new ResourceNotFoundException("用户地址不存在");
        }
        Order order = new Order();
        order.setNumber("O" + SequenceNumUtils.generateNum());
        order.setGoodsName(orderItemList.get(0).getGoodsName());
        order.setCustomerId(customer.getId());
        order.setCustomerName(customer.getName());
        order.setCustomerPhone(customer.getPhone());
        order.setCustomerAddressId(customerAddress.getId());
        order.setCustomerAddress(customerAddress.getProvince() + customerAddress.getCity() + customerAddress.getCounty() + customerAddress.getAddress());
        order.setGoodsAmount(orderItemList.stream().map(OrderItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setCount(orderItemList.stream().mapToInt(OrderItem::getCount).sum());
        order.setShippingCostAmount(orderItemList.stream().map(o -> BigDecimalUtils.multiply(o.getShippingCost(), o.getCount())).reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setRemarks(form.getRemarks());
        order.setTotalAmount(BigDecimalUtils.add(order.getGoodsAmount(), order.getShippingCostAmount()));
        orderService.create(order, orderItemList);

        Payment payment = new Payment();
        payment.setNumber("P" + SequenceNumUtils.generateNum());
        payment.setCustomerId(customer.getId());
        payment.setCustomerPhone(customer.getPhone());
        payment.setOrderId(order.getId());
        payment.setType(ORDER);
        payment.setAmount("prod".equalsIgnoreCase(env) ? order.getTotalAmount() : BigDecimal.valueOf(0.01d));
        payment.setCreateBy(CREATE_BY_SERVER);

        paymentService.create(payment);
        //判断是购物车的商品购买还是当前直接购买
        if (type.equals(BY_CART)) {
            //把购买过的cartItem在数据库valid设置成购买过
            cartItemService.completeByIdList(validCartItemList.stream().map(CartItem::getId).collect(Collectors.toList()));
            cart.setAmount(BigDecimalUtils.subtract(cart.getAmount(), validCartItemList.stream().map(CartItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)));
            cart.setCount(cart.getCount() - validCartItemList.stream().mapToInt(CartItem::getCount).sum());
            cartService.update(cart);
        } else {
            sessionContext.removeCurrentOrderItem();
        }
        sessionContext.removeOrderType();
        return new OrderCreateView(order.getNumber(), payment.getNumber());
    }


}
