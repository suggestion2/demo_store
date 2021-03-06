package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.Goods;

import java.util.List;

public class GoodsListView extends PaginationView<Goods>{

        @Autowired
        public GoodsListView() {
        }

        public GoodsListView(List<Goods> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public GoodsListView(List<Goods> list) {
            this.setList(list);
        }
}
