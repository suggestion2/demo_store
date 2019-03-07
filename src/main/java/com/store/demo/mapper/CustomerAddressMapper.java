package com.store.demo.mapper;

import com.store.demo.domain.CustomerAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomerAddressMapper {

    CustomerAddress selectById(Integer id);

    CustomerAddress select(Map<String, Object> map);

    List<CustomerAddress> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int insert(CustomerAddress customerAddress);

    int update(CustomerAddress customerAddress);

    int cleanPrimary(Integer id);

    int setPrimary(CustomerAddress customerAddress);

    int deleteById(Integer id);
}