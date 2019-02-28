package com.store.demo.controller.management;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Comment;
import com.store.demo.domain.Customer;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.interceptor.UserLoginRequired;
import com.store.demo.request.CommentCreateForm;
import com.store.demo.request.CommentListForm;
import com.store.demo.response.CommentListView;
import com.store.demo.service.CommentService;
import com.sug.core.rest.view.ResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.store.demo.constants.CommonConstants.CREATE;
import static com.store.demo.constants.CommonConstants.LIST;

@RestController
@RequestMapping(value = "/management/comment")
@UserLoginRequired
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public CommentListView list(@Valid @RequestBody CommentListForm form){
        return new CommentListView(commentService.selectList(form.getQueryMap()),commentService.selectCount(form.getQueryMap()));
    }

}
