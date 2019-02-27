package com.store.demo;

import com.store.demo.domain.CartItem;
import com.store.demo.mapper.CartItemMapper;
import com.store.demo.request.sms.CartItemIdForm;
import com.store.demo.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	private CartItemMapper cartItemMapper;

	@Test
	public void contextLoads() {
		Map<String,Object> map = new HashMap<>();
		List<CartItemIdForm> list = new ArrayList<>();
		CartItemIdForm cartItemIdForm1= new CartItemIdForm();
		cartItemIdForm1.setId(14);
		CartItemIdForm cartItemIdForm2= new CartItemIdForm();
		cartItemIdForm2.setId(19);
		list.add(cartItemIdForm1);
		list.add(cartItemIdForm2);
		map.put("list",list);
		map.put("cartId",14);
		List<CartItem> a = cartItemMapper.selectList(map);
		System.out.println(a);
	}

}
