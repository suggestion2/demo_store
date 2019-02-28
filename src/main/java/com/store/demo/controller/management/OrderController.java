package com.store.demo.controller.management;

import com.store.demo.constants.CommonConstants;
import com.store.demo.constants.OrderConstants;
import com.store.demo.context.SessionContext;
import com.store.demo.domain.AfterSale;
import com.store.demo.domain.Order;
import com.store.demo.domain.OrderItem;
import com.store.demo.domain.Payment;
import com.store.demo.interceptor.UserLoginRequired;
import com.store.demo.request.OrderCancelForm;
import com.store.demo.request.OrderDispatchForm;
import com.store.demo.request.OrderListForm;
import com.store.demo.request.OrderManagementCancelForm;
import com.store.demo.response.*;
import com.store.demo.service.OrderItemService;
import com.store.demo.service.OrderService;
import com.store.demo.service.PaymentService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.SuccessView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.DETAIL;
import static com.store.demo.constants.CommonConstants.LIST;
import static com.store.demo.constants.OrderConstants.STORE_CANCEL;


@RestController(value = "managementOrderApiController")
@RequestMapping(value = "/management/order")
@UserLoginRequired
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private PaymentService paymentService;


    @Autowired
    private SessionContext sessionContext;

//    @Autowired
//    private DispatchParamService dispatchParamService;
//
//    @Autowired
//    private DispatchCompanyService dispatchCompanyService;



    @RequestMapping(value = LIST, method = RequestMethod.POST)
    public OrderListShowView list(@Valid @RequestBody OrderListForm form) {
        return new OrderListShowView(orderService.selectList(form.getQueryMap()),orderService.selectCount(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL, method = RequestMethod.GET)
    public OrderDetailView detail(@PathVariable Integer id) {
        Order order = orderService.getById(id);
        if(Objects.isNull(order)){
            throw new ResourceNotFoundException("order not found");
        }
        OrderView orderView = new OrderView();
        BeanUtils.copyProperties(order, orderView);
        List<OrderItemView > list = orderItemService.getListByOrderId(order.getId());
        Payment payment = paymentService.getByOrderId(order.getId());
        return new OrderDetailView(orderView,list,payment.getNumber());
    }
//
    @RequestMapping(value = "/dispatch", method = RequestMethod.PUT)
    @Transactional
    public ResponseView update(@Valid @RequestBody OrderDispatchForm form) {
        Order order = orderService.getById(form.getId());
        if (Objects.isNull(order)) {
            throw new ResourceNotFoundException("order not exists");
        }
        if(!order.getStatus().equals(OrderConstants.PAID)){
            throw new InvalidRequestException("invalid order status");
        }
        order.setStatus(OrderConstants.DISPATCH);

        order.setDispatchCompany(form.getDispatchCompany());
        order.setDispatchNumber(form.getDispatchNumber());
        order.setUpdateBy(sessionContext.getUser().getId());

        orderService.updateDispatch(order);
        return new ResponseView();
    }
//
    @RequestMapping(value = CommonConstants.CANCEL, method = RequestMethod.PUT)
    public ResponseView update(@Valid @RequestBody OrderManagementCancelForm form) {
        Order order = orderService.getById(form.getId());
        if (Objects.isNull(order)) {
            throw new ResourceNotFoundException("order not exists");
        }
        order.setStatus(OrderConstants.CANCEL);
        order.setCancelType(STORE_CANCEL);
        order.setCancelReason(form.getReason());
        order.setUpdateBy(sessionContext.getUser().getId());
        orderService.userCancel(order);
        return new ResponseView();
    }
}
