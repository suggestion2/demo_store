package com.store.demo.controller.api;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Customer;
import com.store.demo.domain.CustomerAddress;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.store.demo.request.CustomerAddressPrimaryForm;
import com.store.demo.request.CustomerPasswordForm;
import com.store.demo.request.CustomerUpdateForm;
import com.store.demo.service.CustomerAddressService;
import com.store.demo.service.CustomerService;
import com.sug.core.platform.crypto.MD5;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.SuccessView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Objects;

import static com.store.demo.constants.CommonConstants.MD5_SALT;
import static com.store.demo.constants.CommonConstants.UPDATE;

@RestController(value = "customerApiController")
@RequestMapping(value = "/api/customer")
@CustomerLoginRequired
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
    public ResponseView update(@RequestBody @Valid CustomerPasswordForm form) {
        Customer customer = customerService.getById(sessionContext.getCustomer().getId());
        if(!customer.getPassword().equalsIgnoreCase(MD5.encrypt(form.getOriginPassword() + MD5_SALT))){
            throw new InvalidRequestException("修改失败","原密码错误");
        }
        customer.setPassword(MD5.encrypt(form.getNewPassword() + MD5_SALT));
        customerService.update(customer);

        sessionContext.setCustomer(customer);

        return new ResponseView();
    }
    @RequestMapping(value = "/primary", method = RequestMethod.PUT)
    public SuccessView setPrimary(@Valid @RequestBody CustomerAddressPrimaryForm form) {
        CustomerAddress customerAddress = customerAddressService.getById(form.getId());
        if (Objects.isNull(customerAddress) || !customerAddress.getCustomerId().equals(customerService.getCurrentCustomer().getId())) {
            throw new ResourceNotFoundException("customerAddress not exists");
        }
        customerAddressService.setPrimary(customerAddress);
        return new SuccessView();
    }
    @RequestMapping(value = UPDATE, method = RequestMethod.PUT)
    public ResponseView update(@RequestBody @Valid CustomerUpdateForm form) {
        Customer customer = customerService.getById(sessionContext.getCustomer().getId());
        customer.setName(StringUtils.hasText(form.getName()) ? form.getName() : customer.getName());
        customer.setUpdateBy(0);
        customerService.update(customer);

        sessionContext.setCustomer(customer);

        return new ResponseView();
    }
}
