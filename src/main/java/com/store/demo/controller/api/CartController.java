package com.store.demo.controller.api;

import com.store.demo.domain.CartItem;
import com.store.demo.domain.Goods;
import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.request.CartItemCreateForm;
import com.store.demo.response.CartDetailView;
import com.store.demo.response.CartView;
import com.store.demo.service.CartItemService;
import com.store.demo.service.GoodsService;
import com.store.demo.service.GoodsSpecUnitService;
import com.store.demo.service.stock.GoodsStocks;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.SuccessView;
import com.store.demo.domain.Cart;
import com.store.demo.service.CartService;
import com.store.demo.request.CartCreateForm;
import com.store.demo.request.CartUpdateForm;
import com.store.demo.request.CartListForm;
import com.store.demo.response.CartListView;
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
import static com.store.demo.constants.CartConstants.MAX_GOODS_COUNT;
import static com.store.demo.constants.CommonConstants.*;

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
    private GoodsSpecUnitService goodsSpecUnitService;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public CartListView list(@Valid @RequestBody CartListForm form){
        return new CartListView(cartService.selectList(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL,method = RequestMethod.GET)
    public Cart detail(@PathVariable Integer id){
        return cartService.getById(id);
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

//    @RequestMapping(value = CREATE,method = RequestMethod.POST)
//    public SuccessView create(@Valid @RequestBody CartCreateForm form){
//        Cart cart = new Cart();
//        BeanUtils.copyProperties(form,cart);
//        cartService.create(cart);
//        return new SuccessView();
//    }
//
//    @RequestMapping(value = UPDATE,method = RequestMethod.PUT)
//    public SuccessView update(@Valid @RequestBody CartUpdateForm form){
//        Cart cart = cartService.getById(form.getId());
//        if(Objects.isNull(cart)){
//            throw new ResourceNotFoundException("cart not exists");
//        }
//        BeanUtils.copyProperties(form,cart);
//        cartService.update(cart);
//        return new SuccessView();
//    }
}
