package com.store.demo.response;

import com.sug.core.platform.web.pagination.PaginationView;
import org.springframework.beans.factory.annotation.Autowired;
import com.store.demo.domain.Comment;

import java.util.List;

public class CommentListView extends PaginationView<Comment>{

        @Autowired
        public CommentListView() {
        }

        public CommentListView(List<Comment> list,int count) {
            this.setList(list);
            this.setCount(count);
        }

        public CommentListView(List<Comment> list) {
            this.setList(list);
        }
}
