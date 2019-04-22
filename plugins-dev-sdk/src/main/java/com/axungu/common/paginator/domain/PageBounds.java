package com.axungu.common.paginator.domain;

import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  分页查询对象
 *  @author badqiu
 *  @author hunhun
 *  @author miemiedev
 */
public class PageBounds extends RowBounds implements Serializable {

    private static final long serialVersionUID = 5985713860175930857L;

    public final static int NO_PAGE = 1;

    /** 页号 */
    protected int page = NO_PAGE;

    /** 分页大小 */
    protected int plimit = NO_ROW_LIMIT;

    /** 分页排序信息 */
    protected List<Order> orders = new ArrayList<Order>();

    /** 结果集是否包含TotalCount */
    protected boolean containsTotalCount = true;

    protected Boolean asyncTotalCount;

    public PageBounds() {
    }

    public PageBounds(RowBounds rowBounds) {
        if (rowBounds instanceof PageBounds) {
            PageBounds pageBounds = (PageBounds) rowBounds;
            this.page = pageBounds.page;
            this.plimit = pageBounds.plimit;
            this.orders = pageBounds.orders;
            this.containsTotalCount = pageBounds.containsTotalCount;
            this.asyncTotalCount = pageBounds.asyncTotalCount;
        } else {
            this.page = (rowBounds.getOffset() / rowBounds.getLimit()) + 1;
            this.plimit = rowBounds.getLimit();
        }

    }

    /**
     * Query TOP N, default containsTotalCount = false
     * @param limit
     */
    public PageBounds(int limit) {
        this.plimit = limit;
        this.containsTotalCount = false;
    }

    public PageBounds(int page, int limit) {
        this(page, limit, new ArrayList<Order>(), true);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * @param orders
     */
    public PageBounds(List<Order> orders) {
        this(NO_PAGE, NO_ROW_LIMIT, orders, false);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * @param order
     */
    public PageBounds(Order... order) {
        this(NO_PAGE, NO_ROW_LIMIT, order);
        this.containsTotalCount = false;
    }

    public PageBounds(int page, int limit, Order... order) {
        this(page, limit, Arrays.asList(order), true);
    }

    public PageBounds(int page, int limit, List<Order> orders) {
        this(page, limit, orders, true);
    }

    public PageBounds(int page, int limit, List<Order> orders, boolean containsTotalCount) {
        this.page = page;
        this.plimit = limit;
        this.orders = orders;
        this.containsTotalCount = containsTotalCount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPlimit() {
        return plimit;
    }

    public void setPlimit(int plimit) {
        this.plimit = plimit;
    }

    public boolean isContainsTotalCount() {
        return containsTotalCount;
    }

    public void setContainsTotalCount(boolean containsTotalCount) {
        this.containsTotalCount = containsTotalCount;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public Boolean getAsyncTotalCount() {
        return asyncTotalCount;
    }

    public void setAsyncTotalCount(Boolean asyncTotalCount) {
        this.asyncTotalCount = asyncTotalCount;
    }

    @Override
    public int getOffset() {
        if (page >= 1) {
            return (page - 1) * this.plimit;
        }
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PageBounds{");
        sb.append("page=").append(page);
        sb.append(", limit=").append(this.plimit);
        sb.append(", orders=").append(orders);
        sb.append(", containsTotalCount=").append(containsTotalCount);
        sb.append(", asyncTotalCount=").append(asyncTotalCount);
        sb.append('}');
        return sb.toString();
    }
}