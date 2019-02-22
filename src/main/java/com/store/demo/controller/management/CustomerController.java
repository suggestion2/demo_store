package com.store.demo.controller.management;

import com.store.demo.interceptor.UserLoginRequired;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.rest.view.SuccessView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;


@RestController
@RequestMapping(value = "/management/customer")
@UserLoginRequired
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    /*@Autowired
    private CustomerService customerService;

    @Autowired
    private ScoreRecordService scoreRecordService;

    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = LIST, method = RequestMethod.POST)
    public CustomerListView list(@Valid @RequestBody CustomerListForm form) {
        return new CustomerListView(customerService.selectList(form.getQueryMap()),customerService.selectCount(form.getQueryMap()));
    }

    @RequestMapping(value = UPDATE, method = RequestMethod.PUT)
    public SuccessView update(@Valid @RequestBody CustomerUpdateForm form) {
        Customer customer = customerService.getById(form.getId());
        if (Objects.isNull(customer)) {
            throw new ResourceNotFoundException("customer not exists");
        }
        BeanUtils.copyProperties(form, customer);
        customer.setUpdateBy(sessionContext.getUser().getId());
        customerService.update(customer);
        return new SuccessView();
    }

    @RequestMapping(value = "/cleanScore", method = RequestMethod.PUT)
    public SuccessView cleanScore(@Valid @RequestBody CustomerUpdateForm form) {
        Customer customer = customerService.getById(form.getId());
        if (Objects.isNull(customer)) {
            throw new ResourceNotFoundException("customer not exists");
        }
        customer.setScore(0);
        customerService.update(customer);
        scoreRecordService.deleteByCustomerId(customer.getId());
        return new SuccessView();
    }*/
}
