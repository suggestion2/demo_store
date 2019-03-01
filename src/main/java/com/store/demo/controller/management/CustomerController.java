package com.store.demo.controller.management;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Customer;
import com.store.demo.interceptor.UserLoginRequired;
import com.store.demo.request.CustomerListForm;
import com.store.demo.response.CustomerListView;
import com.store.demo.service.CustomerService;
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


@RestController
@RequestMapping(value = "/management/customer")
@UserLoginRequired
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = LIST, method = RequestMethod.POST)
    public CustomerListView list(@Valid @RequestBody CustomerListForm form) {
        return new CustomerListView(customerService.selectList(form.getQueryMap()),customerService.selectCount(form.getQueryMap()));
    }

    @RequestMapping(value = DETAIL, method = RequestMethod.GET)
    public Customer update(@PathVariable Integer id) {
        Customer customer = customerService.getById(id);
        if(Objects.isNull(customer)){
            throw new ResourceNotFoundException("用户不存在");
        }
        return customer;
    }
}
