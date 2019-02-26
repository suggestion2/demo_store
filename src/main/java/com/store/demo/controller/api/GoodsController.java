package com.store.demo.controller.api;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Goods;
import com.store.demo.domain.GoodsSpec;
import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.domain.User;
import com.store.demo.interceptor.UserLoginRequired;
import com.store.demo.mapper.sku.GoodsSpecUnitUpdateParams;
import com.store.demo.mapper.sku.GoodsSpecUpdateParams;
import com.store.demo.request.*;
import com.store.demo.response.GoodsCategoryListView;
import com.store.demo.response.GoodsListView;
import com.store.demo.response.GoodsSpecAndUnitView;
import com.store.demo.response.SpecView;
import com.store.demo.service.GoodsCategoryService;
import com.store.demo.service.GoodsService;
import com.store.demo.service.GoodsSpecService;
import com.store.demo.service.GoodsSpecUnitService;
import com.store.demo.service.oss.OssService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.SuccessView;
import com.sug.core.util.JUGUUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static com.store.demo.constants.CommonConstants.*;
import static com.store.demo.constants.GoodsConstants.FOR_SALE;
import static com.store.demo.constants.GoodsConstants.STOCK;
import static com.store.demo.constants.ImageConstants.GOODS;

@RestController
@RequestMapping(value = "/api/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsSpecService goodsSpecService;

    @Autowired
    private GoodsSpecUnitService goodsSpecUnitService;

    @Autowired
    private OssService ossService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public GoodsCategoryListView list(){
        return new GoodsCategoryListView(goodsCategoryService.selectViewList(null));
    }

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public GoodsListView list(@Valid @RequestBody GoodsListForm form){
        form.setStatus(1);
        List<Goods> list = goodsService.selectList(form.getQueryMap());
        list.forEach(g -> g.setBannerUrl(getImage(g.getBannerUrl())));
        return new GoodsListView(list, goodsService.selectCount(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL, method = RequestMethod.GET)
    public GoodsSpecAndUnitView detail(@PathVariable Integer id) {
        Goods goods = goodsService.getById(id);
        if(Objects.isNull(goods)){
            throw new ResourceNotFoundException("商品不存在");
        }

        List<GoodsSpec> specList = goodsSpecService.selectListByGoodsId(goods.getId());

        Map<Integer,List<GoodsSpec>> specMap = new HashMap<>();
        specList.forEach(s->{
            List<GoodsSpec> list = specMap.get(s.getParentId());
            if(Objects.isNull(list)){
                list = new ArrayList<>();
            }
            list.add(s);
            specMap.put(s.getParentId(),list);
        });
        List<GoodsSpec> parentList = specMap.get(0);

        List<SpecView> specViewList = new ArrayList<>();
        parentList.forEach(s->specViewList.add(new SpecView(s,specMap.get(s.getId()))));

        List<GoodsSpecUnit> unitList = goodsSpecUnitService.selectListByGoodsId(goods.getId());
        return new GoodsSpecAndUnitView(goods,specViewList,unitList);
    }

    //拼接获取服务器图片的地址
    private String getImage(String imageUrl) {
        return ossService.getPublicObject(GOODS + imageUrl);
    }
}
