package com.store.demo.controller.controller;

import com.store.demo.service.oss.OssService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.rest.view.SuccessView;
import com.store.demo.domain.Goods;
import com.store.demo.service.GoodsService;
import com.store.demo.request.GoodsCreateForm;
import com.store.demo.request.GoodsUpdateForm;
import com.store.demo.request.GoodsListForm;
import com.store.demo.response.GoodsListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;
import static com.store.demo.constants.ImageConstants.GOODS;

@RestController
@RequestMapping(value = "/management/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OssService ossService;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public GoodsListView list(@Valid @RequestBody GoodsListForm form){
        List<Goods> list = goodsService.selectList(form.getQueryMap());
        list.forEach(g -> g.setBannerUrl(getImage(g.getBannerUrl())));
        return new GoodsListView(list, goodsService.selectCount(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL,method = RequestMethod.GET)
    public Goods detail(@PathVariable Integer id){
        return goodsService.getById(id);
    }

    @RequestMapping(value = CREATE,method = RequestMethod.POST)
    public SuccessView create(@Valid @RequestBody GoodsCreateForm form){
        Goods goods = new Goods();
        BeanUtils.copyProperties(form,goods);
        goodsService.create(goods);
        return new SuccessView();
    }

    @RequestMapping(value = UPDATE,method = RequestMethod.PUT)
    public SuccessView update(@Valid @RequestBody GoodsUpdateForm form){
        Goods goods = goodsService.getById(form.getId());
        if(Objects.isNull(goods)){
            throw new ResourceNotFoundException("goods not exists");
        }
        BeanUtils.copyProperties(form,goods);
        goodsService.update(goods);
        return new SuccessView();
    }

    //拼接获取服务器图片的地址
    private String getImage(String imageUrl) {
        return ossService.getPublicObject(GOODS + imageUrl);
    }
}
