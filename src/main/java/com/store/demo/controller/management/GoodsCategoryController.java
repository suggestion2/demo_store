package com.store.demo.controller.management;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.GoodsCategory;
import com.store.demo.interceptor.UserLoginRequired;
import com.store.demo.request.GoodsCategoryCreateForm;
import com.store.demo.request.GoodsCategoryStatusForm;
import com.store.demo.request.GoodsCategoryUpdateForm;
import com.store.demo.response.GoodsCategoryListView;
import com.store.demo.service.GoodsCategoryService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.ResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;

@RestController("managementGoodsCategoryController")
@RequestMapping(value = "/management/goodsCategory")
@UserLoginRequired
public class GoodsCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsCategoryController.class);

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = LIST,method = RequestMethod.GET)
    public GoodsCategoryListView list(){
        return new GoodsCategoryListView(goodsCategoryService.selectViewList(null));
    }

    @RequestMapping(value = DETAIL,method = RequestMethod.GET)
    public GoodsCategory detail(@PathVariable Integer id){
        return goodsCategoryService.getById(id);
    }

    @RequestMapping(value = CREATE,method = RequestMethod.POST)
    public ResponseView create(@Valid @RequestBody GoodsCategoryCreateForm form){
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtils.copyProperties(form,goodsCategory);
        goodsCategory.setCreateBy(sessionContext.getUser().getId());
        goodsCategoryService.create(goodsCategory);
        return new ResponseView();
    }

    @RequestMapping(value = UPDATE,method = RequestMethod.PUT)
    public ResponseView update(@Valid @RequestBody GoodsCategoryUpdateForm form){
        GoodsCategory goodsCategory = goodsCategoryService.getById(form.getId());
        if(Objects.isNull(goodsCategory)){
            throw new ResourceNotFoundException("商品品类不存在");
        }
        BeanUtils.copyProperties(form,goodsCategory);
        goodsCategory.setUpdateBy(sessionContext.getUser().getId());
        goodsCategoryService.update(goodsCategory);
        return new ResponseView();
    }

    @RequestMapping(value = "/resetStatus",method = RequestMethod.PUT)
    public ResponseView resetStatus(@Valid @RequestBody GoodsCategoryStatusForm form){
        GoodsCategory goodsCategory = goodsCategoryService.getById(form.getId());
        if(Objects.isNull(goodsCategory)){
            throw new ResourceNotFoundException("商品品类不存在");
        }
        BeanUtils.copyProperties(form,goodsCategory);
//        goodsCategory.setUpdateBy(sessionContext.getUser().getId());
        goodsCategoryService.updateStatus(goodsCategory);
        return new ResponseView();
    }

    @RequestMapping(value = DELETE_BY_ID,method = RequestMethod.DELETE)
    public ResponseView deleteById(@PathVariable Integer id){
        GoodsCategory goodsCategory = goodsCategoryService.getById(id);
        if(Objects.isNull(goodsCategory)){
            throw new ResourceNotFoundException("商品品类不存在");
        }
        goodsCategoryService.deleteById(id);
        return new ResponseView();
    }
}
