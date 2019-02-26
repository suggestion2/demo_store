package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartItemListView extends PaginationView<CartItemView>{
    public CartItemListView(List<CartItemView> list) {
        this.setList(list);
    }

    @Autowired
    public CartItemListView() {
    }
}
