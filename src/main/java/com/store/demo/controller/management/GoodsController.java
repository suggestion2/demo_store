package com.store.demo.controller.management;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.*;
import com.store.demo.interceptor.UserLoginRequired;
import com.store.demo.mapper.sku.GoodsSpecUnitUpdateParams;
import com.store.demo.mapper.sku.GoodsSpecUpdateParams;
import com.store.demo.request.*;
import com.store.demo.response.GoodsSpecAndUnitView;
import com.store.demo.response.SpecView;
import com.store.demo.service.GoodsCategoryService;
import com.store.demo.service.GoodsSpecService;
import com.store.demo.service.GoodsSpecUnitService;
import com.store.demo.service.oss.OssService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.SuccessView;
import com.store.demo.service.GoodsService;
import com.store.demo.response.GoodsListView;
import com.sug.core.util.JUGUUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

import static com.store.demo.constants.CommonConstants.*;
import static com.store.demo.constants.GoodsConstants.FOR_SALE;
import static com.store.demo.constants.GoodsConstants.STOCK;
import static com.store.demo.service.oss.ImageConstants.GOODS;

@RestController("managementGoodsController")
@RequestMapping(value = "/management/goods")
@UserLoginRequired
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

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public GoodsListView list(@Valid @RequestBody GoodsListForm form){
        List<Goods> list = goodsService.selectList(form.getQueryMap());
        list.forEach(g -> g.setBannerUrl(getImage(g.getBannerUrl())));
        return new GoodsListView(list, goodsService.selectCount(form.getQueryMap()));
    }

    @RequestMapping(value = DELETE_BY_ID,method = RequestMethod.DELETE)
    public ResponseView deleteById(@PathVariable Integer id){
        Goods goods = goodsService.getById(id);
        if(Objects.isNull(goods)){
            throw new ResourceNotFoundException("goods not exists");
        }
        if(!Objects.equals(goods.getStatus(),STOCK)){
            throw new ResourceNotFoundException("必须是下架的商品");
        }
        goodsService.deleteById(id);
        return new ResponseView();
    }

    @RequestMapping(value = CREATE,method = RequestMethod.POST)
    @Transactional
    public ResponseView create(@Valid @RequestBody GoodsCreateForm form){
        User currentUser = sessionContext.getUser();
        Goods goods = new Goods();
        BeanUtils.copyProperties(form, goods);
        goods.setNumber("G" + JUGUUIDGenerator.generateShort(24));
        goods.setCreateBy(currentUser.getId());
        goods.setUpdateBy(currentUser.getId());
        goodsService.create(goods);
        List<SpecEditListForm> specList = form.getSpecList();
        if (Objects.isNull(specList) || specList.size() == 0) {
            throw new ResourceNotFoundException("sku 没有填写");
        }
        //记录前台传过来的SpecEditListForm对象的form属性集合,也就是商品规格，如颜色
        List<SpecEditForm> parentList = specList.stream().map(SpecEditListForm::getForm).collect(Collectors.toList());
        //记录前台传过来的SpecEditListForm对象的subList集合属性集合，商品规格详情，如红色，集合里面的集合
        List<SpecEditForm> subList = specList.stream().flatMap(s -> s.getSubList().stream()).collect(Collectors.toList());

        parentList.forEach(f -> {
            f.setPrimary(0);
            f.setGoodsId(goods.getId());
            f.setParentId(0);
            f.setUpdateBy(currentUser.getId());
        });

        Map<String, Integer> tempParentIdMap = new HashMap<>();
        Map<String, Integer> tempIdMap = new HashMap<>();
        //创建商品规格表示这些规格的总称比如颜色，或者尺码，然后下面进行如颜色的添加，红色，蓝色，所以parentId=0
        goodsSpecService.batchCreate(parentList);
        //把页面传过来的临时goodsSpec的id作为key，利用mybatis的插入goodsSpec成功返回的subList的goodsSpec的id作为值保存下来
        //也就是用传过来的goodsSpec临时id对应到插入数据库的这个goodsSpec的id的值，这样定位到每个前面插入数据库的goodsSpec的id
        parentList.forEach(f -> tempParentIdMap.put(f.getTempId(), f.getId()));
        //拿到上面添加进数据库的规格的id作为parentId进行关联添加，用临时父类id去取对应的id
        subList.forEach(f -> {
            f.setParentId(tempParentIdMap.get(f.getTempParentId()));
            f.setGoodsId(goods.getId());
            f.setUpdateBy(currentUser.getId());
        });
        //添加如红色，蓝色，或者尺码35，36，并拿到上面添加的规格的id作为parentId进行关联添加
        goodsSpecService.batchCreate(subList);
        //把页面传过来的临时goodsSpec的id作为key，利用mybatis的插入goodsSpec成功返回的subList的goodsSpec的id作为值保存下来
        //也就是用传过来的goodsSpec临时id对应到插入数据库的这个goodsSpec的id的值，这样定位到每个前面插入数据库的goodsSpec的id
        subList.forEach(f -> tempIdMap.put(f.getTempId(), f.getId()));

        List<SpecUnitEditForm> unitList = form.getUnitList();
        if (Objects.isNull(unitList) || unitList.size() == 0) {
            throw new ResourceNotFoundException("unit not found");
        }
        //把unitList的specId传过来的字符串如2,5变成字符数组,然后用这个当key去取数据库对应的spec所对应的id
        unitList.forEach(f -> {
            List<String> tempSpecIdList = Arrays.stream(f.getSpecIds().split(",")).collect(Collectors.toList());
            StringBuilder specIds = new StringBuilder();
            //判断该字符数组有没有为空
            tempSpecIdList.forEach(s -> {
                if (Objects.isNull(tempIdMap.get(s))) {
                    throw new ResourceNotFoundException("specId not found in specUnit");
                }
                //用临时的id key去取刚刚插入数据库对应的spec所对应的id
                specIds.append(tempIdMap.get(s));
                specIds.append(",");
            });
            specIds.deleteCharAt(specIds.length() - 1);
            f.setSpecIds(specIds.toString());
            f.setGoodsId(goods.getId());
            f.setUpdateBy(currentUser.getId());
        });
        //保存商品unit
        goodsSpecUnitService.batchCreate(unitList);
        return new ResponseView();
    }

    @RequestMapping(value = DETAIL, method = RequestMethod.GET)
    public GoodsSpecAndUnitView detail(@PathVariable Integer id) {
        Goods goods = goodsService.getById(id);
        if(Objects.isNull(goods)){
            throw new ResourceNotFoundException("商品不存在");
        }

        List<GoodsSpec> specList = goodsSpecService.selectListByGoodsId(goods.getId());

        Map<Integer,List<GoodsSpec>> specMap = new HashMap<>();
        //一开始判断specMap有没有parentId对应的list，一开始new的map，没有新建一个list放入，保存成key是goodsSpec的parentId，value是goodsSpec
        specList.forEach(s->{
            List<GoodsSpec> list = specMap.get(s.getParentId());
            if(Objects.isNull(list)){
                list = new ArrayList<>();
            }
            list.add(s);
            specMap.put(s.getParentId(),list);
        });
        //拿出goodsSpec的父级，parentId为0的时候就是父级
        List<GoodsSpec> parentList = specMap.get(0);

        List<SpecView> specViewList = new ArrayList<>();
        //把specMap添加的goodsSpec的父级添加进SpecView的GoodsSpec goodsSpec，子级添加进SpecView的GoodsSpec goodsSpecList<GoodsSpec> list
        parentList.forEach(s->specViewList.add(new SpecView(s,specMap.get(s.getId()))));

        List<GoodsSpecUnit> unitList = goodsSpecUnitService.selectListByGoodsId(goods.getId());
        return new GoodsSpecAndUnitView(goods,specViewList,unitList);
    }

    @RequestMapping(value = UPDATE, method = RequestMethod.PUT)
    @Transactional
    public ResponseView update(@Valid @RequestBody GoodsUpdateForm form) {
        Goods goods = goodsService.getById(form.getId());
        if (Objects.isNull(goods)) {
            throw new ResourceNotFoundException("商品不存在");
        }

        User currentUser = sessionContext.getUser();

        if (!goods.getStatus().equals(STOCK)) {
            throw new InvalidRequestException("非待售商品");
        }

        BeanUtils.copyProperties(form, goods);
        goods.setUpdateBy(currentUser.getId());
        goodsService.update(goods);

        List<SpecEditListForm> specList = form.getSpecList();
        if (Objects.isNull(specList) || specList.size() == 0) {
            throw new ResourceNotFoundException("sku 没有填写");
        }

        //记录前台传过来的SpecEditListForm对象的form属性集合
        List<SpecEditForm> parentList = specList.stream().map(SpecEditListForm::getForm).collect(Collectors.toList());
        //记录前台传过来的SpecEditListForm对象的subList属性集合
        List<SpecEditForm> subList = specList.stream().flatMap(s -> s.getSubList().stream()).collect(Collectors.toList());
        //判断是新加的规格，还是本来就有规格，用nonNull和isNull判断是否有传过来id，有id说明要对这个id进行修改，没有就是新加的
        List<SpecEditForm> parentUpdateList = parentList.stream().filter(f -> Objects.nonNull(f.getId())).collect(Collectors.toList());
        List<SpecEditForm> parentInsertList = parentList.stream().filter(f -> Objects.isNull(f.getId())).collect(Collectors.toList());
        //子级
        List<SpecEditForm> subUpdateList = subList.stream().filter(f -> Objects.nonNull(f.getId())).collect(Collectors.toList());
        List<SpecEditForm> subInsertList = subList.stream().filter(f -> Objects.isNull(f.getId())).collect(Collectors.toList());

        Map<String, Integer> tempParentIdMap = new HashMap<>();
        Map<String, Integer> tempIdMap = new HashMap<>();

        List<SpecEditForm> updateList = new ArrayList<>();
        //判断商品是本来就有规格然后要进行规格修改，还是没有规格，进行规格添加，updateList>    0就是原来有规格现在要进行修改
        if (parentUpdateList.size() > 0) {
            parentUpdateList.forEach(f -> f.setPrimary(0));
            updateList.addAll(parentUpdateList);
        }

        if (subUpdateList.size() > 0) {
            //把id存入临时id集合map中
            subUpdateList.forEach(f -> tempIdMap.put(String.valueOf(f.getId()), f.getId()));
            updateList.addAll(subUpdateList);
        }

        if (updateList.size() > 0) {
            goodsSpecService.batchUpdate(new GoodsSpecUpdateParams(goods.getId(), updateList, currentUser.getId()));
        //如果修改的集合list不大于0，说明之前的规格已经被删除了
        } else {
            goodsSpecService.deleteByGoodsId(goods.getId());
        }
        //进行新的规格添加
        if (parentInsertList.size() > 0) {
            parentInsertList.forEach(f -> {
                f.setPrimary(0);
                f.setGoodsId(goods.getId());
                f.setParentId(0);
                f.setUpdateBy(currentUser.getId());
            });

            goodsSpecService.batchCreate(parentInsertList);
            //记录下新插入数据库的goodsSpec的父级的id，用临时id当key，数据库生成的id当值
            parentInsertList.forEach(f -> tempParentIdMap.put(f.getTempId(), f.getId()));
        }
        //进行新的规格下的尺码或者颜色添加
        if (subInsertList.size() > 0) {
            subInsertList.forEach(f -> {
                if (Objects.isNull(f.getParentId())) {
                    //用临时temParentId去取出map上面存入的fu'ji
                    f.setParentId(tempParentIdMap.get(f.getTempParentId()));
                }
                f.setUpdateBy(currentUser.getId());
                f.setGoodsId(goods.getId());
            });

            goodsSpecService.batchCreate(subInsertList);
            //记录下插入数据库的goodsSpec的id
            subInsertList.forEach(f -> tempIdMap.put(f.getTempId(), f.getId()));
        }

        List<SpecUnitEditForm> unitList = form.getUnitList();
        if (Objects.isNull(unitList) || unitList.size() == 0) {
            throw new ResourceNotFoundException("unit not found");
        }
        //记录前台传过来的SpecUnitEditForm对象的集合,用是否有id判断是新加的还是原来有的
        List<SpecUnitEditForm> unitUpdateList = unitList.stream().filter(f -> Objects.nonNull(f.getId())).collect(Collectors.toList());
        List<SpecUnitEditForm> unitInsertList = unitList.stream().filter(f -> Objects.isNull(f.getId())).collect(Collectors.toList());

        if (unitUpdateList.size() > 0) {
           // 如果修改之后这个商品没有规格，就把那些规格删了
            goodsSpecUnitService.batchUpdate(new GoodsSpecUnitUpdateParams(goods.getId(), unitUpdateList, currentUser.getId()));
        } else {
            goodsSpecUnitService.deleteByGoodsId(goods.getId());
        }
        //把unitList的specId传过来的字符串如2,5变成字符数组,然后用这个当key去取数据库对应的spec所对应的id
        if (unitInsertList.size() > 0) {
            for (SpecUnitEditForm f : unitInsertList) {
                List<String> tempSpecIdList = Arrays.stream(f.getSpecIds().split(",")).collect(Collectors.toList());
                StringBuilder specIds = new StringBuilder();
                tempSpecIdList.forEach(s -> {
                    //用临时的id key去取刚刚插入数据库对应的spec所对应的id
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
            }
            goodsSpecUnitService.batchCreate(unitInsertList);
        }

        return new ResponseView();
    }

    //上架
    @RequestMapping(value = "/resetStatus", method = RequestMethod.PUT)
    public SuccessView resetStatus(@Valid @RequestBody GoodsStatusForm form) {
        Goods goods = goodsService.getById(form.getId());
        if (Objects.isNull(goods)) {
            throw new ResourceNotFoundException("goods not exists");
        }
        if (!Objects.equals(form.getStatus(),STOCK)&&!Objects.equals(form.getStatus(),FOR_SALE)) {
            throw new InvalidRequestException("修改商品状态错误");
        }
        if(Objects.equals(form.getStatus(),STOCK)){
            List<Integer> categoryIdList = new ArrayList<>();
            categoryIdList.add(goods.getCategoryId1());
            categoryIdList.add(goods.getCategoryId2());
            if (goodsCategoryService.checkInvalidCategory(categoryIdList)) {
                throw new InvalidRequestException("商品所属品类已下架,请重新选择后再上架商品");
            }
        }
        goods.setStatus(form.getStatus());
        goods.setUpdateBy(sessionContext.getUser().getId());
        goodsService.resetStatus(goods);
        return new SuccessView();
    }



    //拼接获取服务器图片的地址
    private String getImage(String imageUrl) {
        return ossService.getPublicObject(GOODS + imageUrl);
    }
}
