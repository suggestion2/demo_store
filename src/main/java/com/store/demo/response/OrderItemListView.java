package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.OrderItem;

import java.util.List;

public class OrderItemListView extends PaginationView<OrderItem>{

        @Autowired
        public OrderItemListView() {
        }

        public OrderItemListView(List<OrderItem> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public OrderItemListView(List<OrderItem> list) {
            this.setList(list);
        }
}
