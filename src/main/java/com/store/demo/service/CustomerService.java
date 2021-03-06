package com.store.demo.service;

import com.store.demo.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface CustomerService {
    Customer getById(Integer id);

    Customer select(Map<String, Object> map);

    List<Customer> selectList(Map<String, Object> map);

    int selectCount(Map<String, Object> map);

    int create(Customer customer);

    int update(Customer customer);

    int deleteById(Integer id);

    Customer getByPhone(String phone);

    Customer getCurrentCustomer();

}