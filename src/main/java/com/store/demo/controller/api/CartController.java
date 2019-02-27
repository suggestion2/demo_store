package com.store.demo.controller.api;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.*;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.request.*;
import com.store.demo.response.*;
import com.store.demo.service.CartItemService;
import com.store.demo.service.GoodsService;
import com.store.demo.service.GoodsSpecUnitService;
import com.store.demo.service.oss.OssService;
import com.store.demo.service.stock.GoodsStocks;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.SuccessView;
import com.store.demo.service.CartService;
import com.sug.core.util.BigDecimalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.store.demo.constants.CartConstants.ADD;
import static com.store.demo.constants.CartConstants.EDIT;
import static com.store.demo.constants.CartConstants.MAX_GOODS_COUNT;
import static com.store.demo.constants.CartItemConstants.DISCONTINUED_COUNT;
import static com.store.demo.constants.CartItemConstants.DISCONTINUED_STATUS;
import static com.store.demo.constants.CartItemConstants.VALID_STATUS;
import static com.store.demo.constants.CommonConstants.*;
import static com.store.demo.constants.OrderConstants.BY_CART;
import static com.store.demo.service.oss.ImageConstants.GOODS;

@RestController("apiCartController")
@RequestMapping(value = "api/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OssService ossService;

    @Autowired
    private GoodsSpecUnitService goodsSpecUnitService;

    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public CartListView list(@Valid @RequestBody CartListForm form){
        return new CartListView(cartService.selectList(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL,method = RequestMethod.GET)
    public Cart detail(@PathVariable Integer id){
        return cartService.getById(id);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CartView info() {
        Cart cart = cartService.getCurrentCart();
        return Objects.isNull(cart) ? new CartView() : new CartView(cart);
    }


    @RequestMapping(value = EDIT, method = RequestMethod.PUT)
    public CartEditView editItem(@Valid @RequestBody CartItemUpdateForm form) {
        int maxCount = 0;
        //判断最大数量是否大于修改商品的数量
        if (MAX_GOODS_COUNT < form.getCount()) {
            form.setCount(MAX_GOODS_COUNT);
            maxCount = 1;
        }

        Cart cart = cartService.getCurrentCart();
        if (Objects.isNull(cart)) {
            throw new ResourceNotFoundException("没有购物车");
        }
        //得到单个商品的购物车详情
        CartItem cartItem = cartItemService.getById(form.getId());
        if (Objects.isNull(cartItem) || !cartItem.getCartId().equals(cart.getId())) {
            throw new ResourceNotFoundException("商品不存在或已下架,请刷新购物车");
        }

        if (cartItem.getCount().equals(form.getCount())) {
            return new CartEditView(new CartView(cart), new CartItemView(cartItem));
        }

        Goods goods = goodsService.getById(cartItem.getGoodsId());

        GoodsSpecUnit unit =goodsSpecUnitService.getById(cartItem.getUnitId());
        //如果商品不存在了
        if (Objects.isNull(goods) || Objects.isNull(unit)) {
            cart.setAmount(BigDecimalUtils.subtract(cart.getAmount(), cartItem.getAmount()));
            cart.setCount(cart.getCount() - cartItem.getCount());
            cartItem.setCount(DISCONTINUED_COUNT);
            cartService.update(cart, cartItem);
            throw new ResourceNotFoundException("商品不存在或已下架,请刷新购物车");
        }
        //判断库存是否足够
        int outOfStock = 0;
        GoodsStocks goodsStocks = goodsSpecUnitService.getStocks(cartItem.getUnitId());
        if (goodsStocks.getStocks() < form.getCount()) {
            //不够把当前添加过来的数量设置为当前库存
            form.setCount(goodsStocks.getStocks());
            outOfStock = 1;
        }
        //当商品数量和数据库不一致时
        if (!cartItem.getCount().equals(form.getCount())) {
            //传过来的数量减去原总数
            int addCount = form.getCount() - cartItem.getCount();
            //把数量设置成传过来修改的数量
            cartItem.setCount(form.getCount());

            BigDecimal addAmount = cartItem.getAmount();
            //重新计算一下总价
            cartItem.setAmount(BigDecimalUtils.multiply(cartItem.getPrice(), cartItem.getCount()));
            //计算后的总价减去原总价算出要增加/减少的
            addAmount = BigDecimalUtils.subtract(cartItem.getAmount(), addAmount);
            //进行总价总数的增加或者减少
            cart.setAmount(BigDecimalUtils.add(cart.getAmount(), addAmount));
            cart.setCount(cart.getCount() + addCount);

            cartService.update(cart, cartItem);
        }
        //修改完商品数量后重新展示一下购物车
        return new CartEditView(new CartView(cart), new CartItemView(cartItem), maxCount, outOfStock);
    }

    @RequestMapping(value = ADD, method = RequestMethod.POST)
    public CartView addItem(@Valid @RequestBody CartItemCreateForm form) {
        Goods goods = goodsService.getById(form.getId());
        if (Objects.isNull(goods)) {
            throw new ResourceNotFoundException("商品不存在或已下架");
        }
        GoodsSpecUnit unit = null;
        //判断该规格商品是否存在
        if (Objects.nonNull(form.getUnitId())) {
            unit = goodsSpecUnitService.getById(form.getUnitId());
            if (Objects.isNull(unit) || !unit.getGoodsId().equals(goods.getId())) {
                throw new ResourceNotFoundException("单品不存在或已下架");
            }
        }
        //判断商品库存是否足够
        GoodsStocks goodsStocks = goodsSpecUnitService.getStocks(form.getUnitId());
        if (goodsStocks.getStocks() < form.getCount()) {
            throw new InvalidRequestException("商品库存不足");
        }
        //得到当前购物车
        Cart cart = cartService.getCurrentCart();

        CartItem cartItem = null;
        //判断购物车是否为空
        if (Objects.isNull(cart)) {
            cart = new Cart();
            cart.setAmount(BigDecimal.ZERO);
            cart.setCount(0);
        } else {
            //如果购物车不为空，判断该购物车的详情里面的商品有没有尺码规格，得到不同的购物车详情
            cartItem = cartItemService.getByUnitId(form.getUnitId(), cart.getId());
        }
        //如果购物车该商品详情为空，设置一下购物车详情
        if (Objects.isNull(cartItem)) {
            cartItem = new CartItem();
            cartItem.setGoodsId(goods.getId());
            cartItem.setGoodsNumber(goods.getNumber());
            cartItem.setCount(form.getCount());
            //判断该商品是否有尺码规格，有尺码规格按不同的尺码规格设置不同的价格还有商品名字等
            cartItem.setGoodsName(goods.getName() + "-" + unit.getTitle());
            cartItem.setUnitName(unit.getTitle());
            cartItem.setPrice(unit.getPrice());
            cartItem.setShippingCost(unit.getShippingCost());
            cartItem.setUnitId(unit.getId());
            cartItem.setBannerUrl(unit.getImageUrl());
            //计算
            cartItem.setAmount(BigDecimalUtils.multiply(cartItem.getPrice(), cartItem.getCount()));
            //购物车该商品详情不为空，判断一下新添加的数量，库存够不够
        } else {
            if (MAX_GOODS_COUNT < form.getCount() + cartItem.getCount()) {
                throw new InvalidRequestException(String.format("最多购买%d件", MAX_GOODS_COUNT));
            }
            if (goodsStocks.getStocks().compareTo(cartItem.getCount() + form.getCount()) < 0) {
                throw new InvalidRequestException("商品库存不足");
            }
            //重新计算一下该单个商品总数和总价
            cartItem.setCount(cartItem.getCount() + form.getCount());
            cartItem.setAmount(BigDecimalUtils.add(BigDecimalUtils.multiply(cartItem.getPrice(), form.getCount()), cartItem.getAmount()));
        }
        //计算整个购物车的总数和总价
        cart.setAmount(BigDecimalUtils.add(cart.getAmount(), BigDecimalUtils.multiply(cartItem.getPrice(), form.getCount())));
        cart.setCount(cart.getCount() + form.getCount());
        //购物车存在就覆盖不存在添加
        if (Objects.isNull(cart.getId())) {
            cartService.create(cart, cartItem);
        } else {
            cartService.update(cart, cartItem);
        }
        return new CartView(cart);
    }

    @RequestMapping(value = CURRENT, method = RequestMethod.GET)
    public CartDetailView current() {
        //去数据库或者缓存里面取购物车
        Cart cart = cartService.getCurrentCart();
        if (Objects.isNull(cart)) {
            return new CartDetailView();
        }
        //把取到的购物车的总数量和总价格拿出来
        BigDecimal originAmount = cart.getAmount();
        Integer originCount = cart.getCount();
        cart.setAmount(BigDecimal.ZERO);
        cart.setCount(0);
        //得到当前购物车详情
        List<CartItemView> validList = cartItemService.getShortListByCartId(cart.getId(), VALID_STATUS);
        if (Objects.nonNull(validList) && validList.size() > 0) {
            List<GoodsStocks> stocksList = new ArrayList<>();
            //计算整个购物车的总数和总价
            validList.forEach(v -> {
                cart.setAmount(BigDecimalUtils.add(cart.getAmount(), new BigDecimal(v.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP)));
                cart.setCount(cart.getCount() + v.getCount());
                //得到购物车里面的商品信息，拿商品信息，接着getStocks去查库存
                GoodsStocks goodsStocks = new GoodsStocks();
                goodsStocks.setId(v.getGoodsId());
                goodsStocks.setUnitId(v.getUnitId());
                stocksList.add(goodsStocks);
            });
            //得到库存
            Map<String, Integer> stocksMap = goodsService.getStocks(stocksList);
            //保存数据库拿出来的库存
            //判断商品库存是否足够的购物车详情里面提交商品的数量
            validList.forEach(v -> {
                v.setStocks(stocksMap.get(v.getGoodsId() + ":" + v.getUnitId()));
                v.setOutOfStock(v.getCount() > v.getStocks() ? 1 : 0);
            });
        }
        int change = 0;
        //如果重新计算后的购物车的总价总数和数据库的不一样那就进行覆盖
        if (!originAmount.equals(cart.getAmount()) || !originCount.equals(cart.getCount())) {
            cartService.update(cart);
            change = 1;
        }
        //这个是为了如果客户加入购物车的商品很长时间没有购买，商品被下架了，那么返回到页面展示商品失效了这种状态的页面
        List<CartItemView> discontinuedList= cartItemService.getShortListByCartId(cart.getId(), DISCONTINUED_STATUS);
        return new CartDetailView(change, new CartView(cart), new CartItemListView(validList), new CartItemListView(discontinuedList));
    }

    @RequestMapping(value = DELETE_BY_ID, method = RequestMethod.DELETE)
    public CartView deleteItem(@PathVariable Integer id) {
        Cart cart = cartService.getCurrentCart();
        if (Objects.isNull(cart)) {
            throw new ResourceNotFoundException("没有购物车");
        }

        CartItem deleteItem = cartItemService.getById(id);

        if (Objects.isNull(deleteItem) || !deleteItem.getCartId().equals(cart.getId())) {
            throw new ResourceNotFoundException("cartItem not found");
        }
        //修改购物车商品数量
        cart.setCount(cart.getCount() - deleteItem.getCount());
        cart.setAmount(BigDecimalUtils.subtract(cart.getAmount(), deleteItem.getAmount()));
        deleteItem.setCount(0);

        cartService.update(cart, deleteItem);

        return new CartView(cart);
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    @CustomerLoginRequired
    public OrderPrepareView checkout(@Valid @RequestBody CartItemCheckOutForm form) {
        Cart cart = cartService.getCurrentCart();
        if (Objects.isNull(cart)) {
            throw new ResourceNotFoundException("没有购物车");
        }

        List<CartItem> cartItemList = cartItemService.getListByCartId(form.getList(),cart.getId());
        if (Objects.isNull(cartItemList) || cartItemList.size() == 0) {
            throw new InvalidRequestException("购物车部分商品下架,请返回购物车查看");
        }

        OrderPrepareView orderPrepareView = new OrderPrepareView();

        List<OrderItemView> list = new ArrayList<>();

        List<GoodsStocks> stocksCheckList = new ArrayList<>();
        //保存订单商品数量信息
        cartItemList.forEach(c -> {
            GoodsStocks goodsStocks = new GoodsStocks();
            goodsStocks.setId(c.getGoodsId());
            goodsStocks.setUnitId(c.getUnitId());
            goodsStocks.setStocks(c.getCount());
            stocksCheckList.add(goodsStocks);
        });
        //查找库存
        Map<String, Integer> stocksMap = goodsService.getStocks(stocksCheckList);
        List<CartItem> validList = new ArrayList<>();
        cartItemList.forEach(c -> {
            if (c.getCount() <= stocksMap.get(c.getGoodsId() + ":" + c.getUnitId())) {
                OrderItemView orderItemView= new OrderItemView();
                BeanUtils.copyProperties(c, orderItemView);
                orderItemView.setBannerUrl(ossService.getBucket(GOODS) + orderItemView.getBannerUrl());
                orderItemView.setPrice(c.getPrice().doubleValue());
                orderItemView.setShippingCost(c.getShippingCost().doubleValue());
                orderItemView.setAmount(c.getAmount().doubleValue());
                list.add(orderItemView);
                validList.add(c);
            }
        });
        //计算运费
        orderPrepareView.setShippingCostAmount(validList.stream().map(c -> BigDecimalUtils.multiply(c.getShippingCost(), c.getCount())).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
        //计算商品总价
        orderPrepareView.setGoodsAmount(validList.stream().map(CartItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue());
        //计算总价
        orderPrepareView.setTotalAmount(new BigDecimal(orderPrepareView.getShippingCostAmount() + orderPrepareView.getGoodsAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        //计算总数
        orderPrepareView.setCount(validList.stream().mapToInt(CartItem::getCount).sum());
        orderPrepareView.setList(list);

        sessionContext.setOrderType(BY_CART);

        return orderPrepareView;
    }
}
