package com.store.demo.service.Impl;

import com.store.demo.domain.CartItem;
import com.store.demo.response.CartItemView;
import com.store.demo.service.CartItemService;
import com.store.demo.mapper.CartItemMapper;
import com.store.demo.service.oss.OssService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.store.demo.service.oss.ImageConstants.GOODS;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private OssService ossService;

    @Override
    public CartItem getById(Integer id){
        return cartItemMapper.selectById(id);
    }
    @Override
    public CartItem select(Map<String, Object> map){
        return cartItemMapper.select(map);
    }

    @Override
    public List<CartItem> selectList(Map<String, Object> map){
        return cartItemMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return cartItemMapper.selectCount(map);
    }

    @Override
    public int create(CartItem cartItem){
        return cartItemMapper.insert(cartItem);
    }

    @Override
    public int update(CartItem cartItem){
        return cartItemMapper.update(cartItem);
    }

    @Override
    public int deleteById(Integer id){
        return cartItemMapper.deleteById(id);
    }

    @Override
    public List<CartItemView> getShortListByCartId(Integer cartId, Integer validType) {
        Map<String,Object> map = new HashMap<>();
        map.put("cartId",cartId);
        map.put("valid",validType);
        List<CartItemView> list = cartItemMapper.selectShortList(map);
        //用图片名称去获取图片
        list.forEach(v->v.setBannerUrl(getImage(v.getBannerUrl())));
        return list;
    }

    private String getImage(String imageUrl){
        return ossService.getBucket(GOODS) + imageUrl;
    }

    @Override
    public int discontinuedById(Integer id) {
        return cartItemMapper.discontinuedById(id);
    }
    @Override
    public CartItem getByGoodsId(Integer goodsId, Integer cartId) {
        Map<String,Object> map = new HashMap<>();
        map.put("cartId",cartId);
        map.put("goodsId",goodsId);
        return cartItemMapper.select(map);
    }

    @Override
    public CartItem getByUnitId(Integer unitId, Integer cartId) {
        Map<String,Object> map = new HashMap<>();
        map.put("cartId",cartId);
        map.put("unitId",unitId);
        return cartItemMapper.select(map);
    }
}