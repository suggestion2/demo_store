package com.store.demo.controller.api;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Customer;
import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.service.OrderItemService;
import com.store.demo.service.OrderService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.SuccessView;
import com.store.demo.domain.Comment;
import com.store.demo.service.CommentService;
import com.store.demo.request.CommentCreateForm;
import com.store.demo.request.CommentListForm;
import com.store.demo.response.CommentListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;

@RestController("commentApiController")
@RequestMapping(value = "/api/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public CommentListView list(@Valid @RequestBody CommentListForm form){
        return new CommentListView(commentService.selectList(form.getQueryMap()),commentService.selectCount(form.getQueryMap()));
    }

    @RequestMapping(value = CREATE,method = RequestMethod.POST)
    @CustomerLoginRequired
    public ResponseView create(@Valid @RequestBody CommentCreateForm form){
        Customer customer = sessionContext.getCustomer();

        OrderItem orderItem = orderItemService.getById(form.getOrderItemId());
        if(Objects.isNull(orderItem)){
            throw new ResourceNotFoundException("订单不存在");
        }
        if(orderItem.getComment().equals(1)){
            throw new InvalidRequestException("订单已经评价过","订单已经评价过");
        }
        Order order = orderService.getById(orderItem.getOrderId());
        if(Objects.isNull(order) || !order.getCustomerId().equals(customer.getId())){
            throw new ResourceNotFoundException("订单不存在");
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(form,comment);
        comment.setGoodsId(orderItem.getGoodsId());
        comment.setCustomerId(customer.getId());
        comment.setCustomerName(customer.getName());
        commentService.create(comment);

        orderItemService.comment(orderItem);
        if(orderItemService.selectUncommentCount(order.getId()) == 0){
            orderService.comment(order);
        }

        return new ResponseView();
    }
}
