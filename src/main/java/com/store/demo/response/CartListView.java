package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.Cart;

import java.util.List;

public class CartListView extends PaginationView<Cart>{

        @Autowired
        public CartListView() {
        }

        public CartListView(List<Cart> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public CartListView(List<Cart> list) {
            this.setList(list);
        }
}
