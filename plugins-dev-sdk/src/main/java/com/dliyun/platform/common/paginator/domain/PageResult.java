package com.dliyun.platform.common.paginator.domain;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -2857504782717596783L;

    private List<T> data;

    private Paginator paginator;

    public PageResult() {
    }

    public PageResult(PageList<T> pageList) {
        this.setData(pageList);
        this.setPaginator(pageList.getPaginator());
    }

    public PageList<T> getPageList() {
        PageList<T> pageList = new PageList<T>(this.data, this.paginator);
        return pageList;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

}
