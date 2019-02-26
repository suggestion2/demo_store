package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.CartItem;

import java.util.List;

public class CartItemListView extends PaginationView<CartItem>{

        @Autowired
        public CartItemListView() {
        }

        public CartItemListView(List<CartItem> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public CartItemListView(List<CartItem> list) {
            this.setList(list);
        }
}
