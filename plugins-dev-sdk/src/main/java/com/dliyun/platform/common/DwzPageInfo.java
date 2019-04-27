package com.dliyun.platform.common;


import com.dliyun.platform.common.paginator.domain.Order;
import com.dliyun.platform.common.paginator.domain.PageBounds;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


public class DwzPageInfo implements Serializable {

    public final static int PAGE_LIMIT = 20;
    private static final long serialVersionUID = 9102461184055527982L;
    private Integer dwz_p_ = 1;

    private Integer dwz_page_size_ = DwzPageInfo.PAGE_LIMIT;

    private Integer dwz_total_ = 0;

    private String dwz_order_field_;

    private String dwz_order_direction_;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getDwz_p_() {
        if (dwz_p_ == null || dwz_p_ < 1) {
            dwz_p_ = 1;
        }
        return dwz_p_;
    }

    public void setDwz_p_(Integer dwz_p_) {
        this.dwz_p_ = dwz_p_;
    }

    public Integer getDwz_page_size_() {
        return dwz_page_size_;
    }

    public void setDwz_page_size_(Integer dwz_page_size_) {
        if (dwz_page_size_ == null || dwz_page_size_ < 1) {
            dwz_page_size_ = DwzPageInfo.PAGE_LIMIT;
        }
        this.dwz_page_size_ = dwz_page_size_;
    }

    public Integer getDwz_total_() {
        return dwz_total_;
    }

    public void setDwz_total_(Integer dwz_total_) {
        this.dwz_total_ = dwz_total_;
    }

    public String getDwz_order_field_() {
        return dwz_order_field_;
    }

    public void setDwz_order_field_(String dwz_order_field_) {
        this.dwz_order_field_ = dwz_order_field_;
    }

    public String getDwz_order_direction_() {
        return dwz_order_direction_;
    }

    public void setDwz_order_direction_(String dwz_order_direction_) {
        this.dwz_order_direction_ = dwz_order_direction_;
    }

    @Deprecated
    public Order getOrder() {
        return buildOrder();
    }

    @Deprecated
    public PageBounds getPageBounds() {
        return buildPageBounds();
    }

    public Order buildOrder() {
        if (StringUtils.isNotBlank(dwz_order_direction_) && !StringUtils.isNotBlank(dwz_order_field_)) {
            return Order.create(this.dwz_order_field_, this.dwz_order_direction_);
        } else {
            return null;
        }
    }

    public PageBounds buildPageBounds() {
        PageBounds bounds = new PageBounds(this.dwz_p_, this.dwz_page_size_);
        if (this.buildOrder() != null) {
            bounds.addOrder(this.buildOrder());
        }
        return bounds;
    }
}