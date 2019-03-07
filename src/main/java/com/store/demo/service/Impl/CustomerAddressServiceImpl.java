package com.store.demo.service.Impl;

import com.store.demo.domain.CustomerAddress;
import com.store.demo.service.CustomerAddressService;
import com.store.demo.mapper.CustomerAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerAddressServiceImpl implements CustomerAddressService{

    @Autowired
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public CustomerAddress getById(Integer id){
        return customerAddressMapper.selectById(id);
    }
    @Override
    public CustomerAddress select(Map<String, Object> map){
        return customerAddressMapper.select(map);
    }

    @Override
    public List<CustomerAddress> selectList(Map<String, Object> map){
        return customerAddressMapper.selectList(map);
    }
    @Override
    public int setPrimary(CustomerAddress customerAddress) {
        return customerAddressMapper.setPrimary(customerAddress);
    }

    @Override
    public int selectCount(Map<String, Object> map){
        return customerAddressMapper.selectCount(map);
    }

    @Override
    public int create(CustomerAddress customerAddress){
        return customerAddressMapper.insert(customerAddress);
    }

    @Override
    public int update(CustomerAddress customerAddress){
        return customerAddressMapper.update(customerAddress);
    }

    @Override
    public int cleanPrimary(Integer id) {
        return customerAddressMapper.cleanPrimary(id);
    }

    @Override
    public int deleteById(Integer id){
        return customerAddressMapper.deleteById(id);
    }
}