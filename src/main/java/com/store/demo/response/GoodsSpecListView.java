package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.GoodsSpec;

import java.util.List;

public class GoodsSpecListView extends PaginationView<GoodsSpec>{

        @Autowired
        public GoodsSpecListView() {
        }

        public GoodsSpecListView(List<GoodsSpec> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public GoodsSpecListView(List<GoodsSpec> list) {
            this.setList(list);
        }
}
