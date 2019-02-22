package com.store.demo.request;

import java.util.List;

public class SpecEditListForm {
    private SpecEditForm form;
    private List<SpecEditForm> subList;

    public SpecEditForm getForm() {
        return form;
    }

    public void setForm(SpecEditForm form) {
        this.form = form;
    }

    public List<SpecEditForm> getSubList() {
        return subList;
    }

    public void setSubList(List<SpecEditForm> subList) {
        this.subList = subList;
    }
}
