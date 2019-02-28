package com.store.demo.controller.api;

import com.store.demo.context.SessionContext;
import com.store.demo.interceptor.CustomerLoginRequired;
import com.sug.core.platform.exception.ResourceNotFoundException;
import com.sug.core.platform.web.rest.exception.InvalidRequestException;
import com.sug.core.rest.view.ResponseView;
import com.sug.core.rest.view.ResponseView;
import com.store.demo.domain.CustomerAddress;
import com.store.demo.service.CustomerAddressService;
import com.store.demo.request.CustomerAddressCreateForm;
import com.store.demo.request.CustomerAddressUpdateForm;
import com.store.demo.response.CustomerAddressListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.store.demo.constants.CommonConstants.*;

@RestController
@RequestMapping(value = "/api/customerAddress")
@CustomerLoginRequired
public class CustomerAddressController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerAddressController.class);

    @Autowired
    private CustomerAddressService customerAddressService;

    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = LIST,method = RequestMethod.GET)
    public CustomerAddressListView list(){
        Map<String,Object> query = new HashMap<>();
        query.put("customerId",sessionContext.getCustomer().getId());
        return new CustomerAddressListView(customerAddressService.selectList(query));
    }

    @RequestMapping(value = DETAIL,method = RequestMethod.GET)
    public CustomerAddress detail(@PathVariable Integer id){
        CustomerAddress address = customerAddressService.getById(id);
        if(Objects.isNull(address) || !address.getCustomerId().equals(sessionContext.getCustomer().getId())){
            throw new ResourceNotFoundException("address not found");
        }
        return address;
    }

    @RequestMapping(value = CREATE,method = RequestMethod.POST)
    public CustomerAddress create(@Valid @RequestBody CustomerAddressCreateForm form){
        CustomerAddress customerAddress = new CustomerAddress();
        BeanUtils.copyProperties(form,customerAddress);
        customerAddress.setCustomerId(sessionContext.getCustomer().getId());
        if(form.getPrimary().equals(1)){
            customerAddressService.cleanPrimary(sessionContext.getCustomer().getId());
        }
        customerAddressService.create(customerAddress);
        return customerAddress;
    }

    @RequestMapping(value = UPDATE,method = RequestMethod.PUT)
    public ResponseView update(@Valid @RequestBody CustomerAddressUpdateForm form){
        CustomerAddress customerAddress = customerAddressService.getById(form.getId());
        if(Objects.isNull(customerAddress) || !customerAddress.getCustomerId().equals(sessionContext.getCustomer().getId())){
            throw new ResourceNotFoundException("customerAddress not exists");
        }
        BeanUtils.copyProperties(form,customerAddress);
        if(form.getPrimary().equals(1)){
            customerAddressService.cleanPrimary(sessionContext.getCustomer().getId());
        }
        customerAddressService.update(customerAddress);
        return new ResponseView();
    }

    @RequestMapping(value = DELETE_BY_ID,method = RequestMethod.DELETE)
    public ResponseView delete(@PathVariable Integer id){
        CustomerAddress customerAddress = customerAddressService.getById(id);
        if(Objects.isNull(customerAddress) || !customerAddress.getCustomerId().equals(sessionContext.getCustomer().getId())){
            throw new ResourceNotFoundException("customerAddress not exists");
        }
        if(customerAddress.getPrimary().equals(1)){
            throw new InvalidRequestException("invalidPrimaryAddress","can not delete primary address");
        }
        customerAddressService.deleteById(id);
        return new ResponseView();
    }
}
