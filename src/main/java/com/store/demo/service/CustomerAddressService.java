package com.store.demo.service;

import com.store.demo.domain.CustomerAddress;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface CustomerAddressService {
    CustomerAddress getById(Integer id);

    CustomerAddress select(Map<String, Object> map);

    List<CustomerAddress> selectList(Map<String, Object> map);

    int setPrimary(CustomerAddress customerAddress);

    int selectCount(Map<String, Object> map);

    int create(CustomerAddress customerAddress);

    int update(CustomerAddress customerAddress);

    int cleanPrimary(Integer id);

    int deleteById(Integer id);

    CustomerAddress getPrimaryByCustomerId(Integer id);

}