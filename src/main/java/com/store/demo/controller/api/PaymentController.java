package com.store.demo.controller.api;

import com.store.demo.domain.Customer;
import com.store.demo.domain.Payment;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.request.PaymentCreateForm;
import com.store.demo.request.PaymentListForm;
import com.store.demo.request.PaymentUpdateForm;
import com.store.demo.response.PaymentListView;
import com.store.demo.response.PaymentStatusView;
import com.store.demo.service.CustomerService;
import com.store.demo.service.PaymentService;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.rest.view.ResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;

@RestController("apiPaymentController")
@RequestMapping(value = "api/payment")
@CustomerLoginRequired
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = LIST,method = RequestMethod.POST)
    public PaymentListView list(@Valid @RequestBody PaymentListForm form){
        return new PaymentListView(paymentService.selectList(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL,method = RequestMethod.GET)
    public Payment detail(@PathVariable Integer id){
        return paymentService.getById(id);
    }

    @RequestMapping(value = CREATE,method = RequestMethod.POST)
    public ResponseView create(@Valid @RequestBody PaymentCreateForm form){
        Payment payment = new Payment();
        BeanUtils.copyProperties(form,payment);
        paymentService.create(payment);
        return new ResponseView();
    }

    @RequestMapping(value = "/status/{number}", method = RequestMethod.GET)
    public PaymentStatusView paymentStatus(@PathVariable String number) {
        Payment payment = paymentService.getByNumber(number);
        Customer customer = customerService.getCurrentCustomer();

        if(Objects.isNull(payment) || !payment.getCustomerId().equals(customer.getId())){
            throw new ResourceNotFoundException("payment not found");
        }

        PaymentStatusView view = new PaymentStatusView();
        view.setStatus(payment.getStatus());

        return view;
    }

    @RequestMapping(value = UPDATE,method = RequestMethod.PUT)
    public ResponseView update(@Valid @RequestBody PaymentUpdateForm form){
        Payment payment = paymentService.getById(form.getId());
        if(Objects.isNull(payment)){
            throw new ResourceNotFoundException("支付订单不存在");
        }
        BeanUtils.copyProperties(form,payment);
        paymentService.update(payment);
        return new ResponseView();
    }
}
