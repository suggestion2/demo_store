package com.store.demo.response;

import com.store.demo.domain.Order;
import com.sug.core.platform.web.pagination.PaginationView;

import java.util.List;

public class OrderListShowView extends PaginationView<Order>{

//        @Autowired
        public OrderListShowView() {
        }

        public OrderListShowView(List<Order> list, int count) {
            this.setList(list);
            this.setCount(count);
        }

        public OrderListShowView(List<Order> list) {
            this.setList(list);
        }
}
