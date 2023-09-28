package com.connexion.cps.controller.common;

import java.util.List;

public class ListView<T> extends ResponseView {

    protected List<T> data;

    public ListView<T> fromList(List<T> lst){
        this.data = lst;
        this.success();
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
