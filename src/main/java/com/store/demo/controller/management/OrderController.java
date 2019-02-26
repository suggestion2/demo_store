package com.store.demo.controller.management;

import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.rest.view.SuccessView;
import com.store.demo.domain.Order;
import com.store.demo.service.OrderService;
import com.store.demo.request.OrderCreateForm;
import com.store.demo.request.OrderUpdateForm;
import com.store.demo.request.OrderListForm;
import com.store.demo.response.OrderListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public OrderListView list(@Valid @RequestBody OrderListForm form){
        return new OrderListView(orderService.selectList(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL,method = RequestMethod.GET)
    public Order detail(@PathVariable Integer id){
        return orderService.getById(id);
    }
}
