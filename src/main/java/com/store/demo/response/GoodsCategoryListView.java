package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.GoodsCategory;

import java.util.List;

public class GoodsCategoryListView extends PaginationView<GoodsCategoryView>{

        @Autowired
        public GoodsCategoryListView() {
        }

        public GoodsCategoryListView(List<GoodsCategoryView> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public GoodsCategoryListView(List<GoodsCategoryView> list) {
            this.setList(list);
        }
}
