package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.Payment;

import java.util.List;

public class PaymentListView extends PaginationView<Payment>{

        @Autowired
        public PaymentListView() {
        }

        public PaymentListView(List<Payment> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public PaymentListView(List<Payment> list) {
            this.setList(list);
        }
}
