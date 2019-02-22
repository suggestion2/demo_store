package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.GoodsSpecUnit;

import java.util.List;

public class GoodsSpecUnitListView extends PaginationView<GoodsSpecUnit>{

        @Autowired
        public GoodsSpecUnitListView() {
        }

        public GoodsSpecUnitListView(List<GoodsSpecUnit> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public GoodsSpecUnitListView(List<GoodsSpecUnit> list) {
            this.setList(list);
        }
}
