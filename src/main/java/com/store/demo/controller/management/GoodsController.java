package com.store.demo.controller.management;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.GoodsSpecUnit;
import com.store.demo.domain.User;
import com.store.demo.request.*;
import com.store.demo.service.GoodsSpecService;
import com.store.demo.service.GoodsSpecUnitService;
import com.store.demo.service.oss.OssService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.rest.view.SuccessView;
import com.store.demo.domain.Goods;
import com.store.demo.service.GoodsService;
import com.store.demo.response.GoodsListView;
import com.sug.core.util.JUGUUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

import static com.store.demo.constants.CommonConstants.*;
import static com.store.demo.constants.ImageConstants.GOODS;

@RestController
@RequestMapping(value = "/management/goods")
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
    private SessionContext sessionContext;

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
        User currentUser = sessionContext.getUser();
        //TODO 获取user
        Goods goods = new Goods();
        BeanUtils.copyProperties(form, goods);
        goods.setNumber("G" + JUGUUIDGenerator.generateShort(24));
//        goods.setCreateBy(currentUser.getId());
//        goods.setUpdateBy(currentUser.getId());
        goodsService.create(goods);
        List<SpecEditListForm> specList = form.getSpecList();
        if (Objects.isNull(specList) || specList.size() == 0) {
            throw new ResourceNotFoundException("sku not found");
        }
        //记录前台传过来的SpecEditListForm对象的form属性集合,也就是商品规格，如颜色
        List<SpecEditForm> parentList = specList.stream().map(SpecEditListForm::getForm).collect(Collectors.toList());
        //记录前台传过来的SpecEditListForm对象的subList集合属性集合，商品规格详情，如红色，集合里面的集合
        List<SpecEditForm> subList = specList.stream().flatMap(s -> s.getSubList().stream()).collect(Collectors.toList());

        parentList.forEach(f -> {
            //父类默认没有首选
            f.setPrimary(0);
            f.setGoodsId(goods.getId());
            f.setParentId(0);
//            f.setUpdateBy(currentUser.getId());
        });

        Map<String, Integer> tempParentIdMap = new HashMap<>();
        Map<String, Integer> tempIdMap = new HashMap<>();
        //创建商品规格表示这些规格的总称比如颜色，或者尺码，然后下面进行如颜色的添加，红色，蓝色，所以parentId=0
        goodsSpecService.batchCreate(parentList);
        //拿到上面添加的规格的id作为parentId进行关联添加
        parentList.forEach(f -> tempParentIdMap.put(f.getTempId(), f.getId()));

        subList.forEach(f -> {
            f.setParentId(tempParentIdMap.get(f.getTempParentId()));
            f.setGoodsId(goods.getId());
//            f.setUpdateBy(currentUser.getId());
        });
        //添加如红色，蓝色，或者尺码35，36，并拿到上面添加的规格的id作为parentId进行关联添加
        goodsSpecService.batchCreate(subList);
        //把当前的临时goodsSpec的id保存下来
        subList.forEach(f -> tempIdMap.put(f.getTempId(), f.getId()));

        List<SpecUnitEditForm> unitList = form.getUnitList();
        if (Objects.isNull(unitList) || unitList.size() == 0) {
            throw new ResourceNotFoundException("unit not found");
        }
        //把上面记录的临时goodsSpec的id和创建goodsSpecUnit页面传过来的specIds进行比较，看看是否是上面有创建的goodsSpec的id，如果有，才能根据goodsSpec的id去创建goodsSpecUnit的specIds
        unitList.forEach(f -> {
            List<String> tempSpecIdList = Arrays.stream(f.getSpecIds().split(",")).collect(Collectors.toList());
            StringBuilder specIds = new StringBuilder();
            tempSpecIdList.forEach(s -> {
                if (Objects.isNull(tempIdMap.get(s))) {
                    throw new ResourceNotFoundException("specId not found in specUnit");
                }
                specIds.append(tempIdMap.get(s));
                specIds.append(",");
            });
            specIds.deleteCharAt(specIds.length() - 1);
            f.setSpecIds(specIds.toString());
            f.setGoodsId(goods.getId());
            f.setUpdateBy(currentUser.getId());
        });

        goodsSpecUnitService.batchCreate(unitList);
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
