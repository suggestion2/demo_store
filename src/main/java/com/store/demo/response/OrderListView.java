package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.Order;

import java.util.List;

public class OrderListView extends PaginationView<OrderShortView>{

//        @Autowired
        public OrderListView() {
        }

        public OrderListView(List<OrderShortView> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public OrderListView(List<OrderShortView> list) {
            this.setList(list);
        }
}
