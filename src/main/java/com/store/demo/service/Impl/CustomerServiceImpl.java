package com.store.demo.service.Impl;

import com.store.demo.context.SessionContext;
import com.store.demo.domain.Customer;
import com.store.demo.service.CustomerService;
import com.store.demo.mapper.CustomerMapper;
import com.sug.core.platform.exception.LoginRequiredException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SessionContext sessionContext;
    @Override
    public Customer getByPhone(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        return customerMapper.select(map);
    }

    @Override
    public Customer getCurrentCustomer() {
        Customer customer = sessionContext.getCustomer();
        if(Objects.isNull(customer)){
            throw new LoginRequiredException("请重新登录");
        }
        return customer;
    }
    @Override
    public Customer getById(Integer id){
        return customerMapper.selectById(id);
    }
    @Override
    public Customer select(Map<String, Object> map){
        return customerMapper.select(map);
    }

    @Override
    public List<Customer> selectList(Map<String, Object> map){
        return customerMapper.selectList(map);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return customerMapper.selectCount(map);
    }

    @Override
    public int create(Customer customer){
        return customerMapper.insert(customer);
    }

    @Override
    public int update(Customer customer){
        return customerMapper.update(customer);
    }

    @Override
    public int deleteById(Integer id){
        return customerMapper.deleteById(id);
    }
}