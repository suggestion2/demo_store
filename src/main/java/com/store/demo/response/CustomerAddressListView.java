package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.CustomerAddress;

import java.util.List;

public class CustomerAddressListView extends PaginationView<CustomerAddress>{

        @Autowired
        public CustomerAddressListView() {
        }

        public CustomerAddressListView(List<CustomerAddress> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public CustomerAddressListView(List<CustomerAddress> list) {
            this.setList(list);
        }
}
