package com.ph.lease.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.lease.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * @author PengHao
 */
public class Order {
    private String id;
    private Integer cid;
    private String type;
    // 注解貌似无效
    // 通过注解转换后，可以直接在前端展示
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;
    private char valid;
    private Customer customer;
    private List<OrderDetail> orderDetails;
    
    public Order() {
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Integer getCid() {
        return cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public char getValid() {
        return valid;
    }
    
    public void setValid(char valid) {
        this.valid = valid;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Order{");
        if (id != null) {
            result.append("id='" + id + '\'');
        } else {
            result.append("id=null");
        }
        result.append(", cid=" + cid);
        if (type != null) {
            result.append(", type='" + type + '\'');
        } else {
            result.append(", type=null");
        }
        if (creationDate != null) {
            result.append(", creationDate=" + DateUtil.format(creationDate));
        } else {
            result.append(", creationDate=null");
        }
        result.append(", valid=" + valid);
        if (customer != null) {
            result.append(", " + customer);
        } else {
            result.append(", customer=null");
        }
        result.append(", orderDetails" + orderDetails);
        result.append('}');
        return result.toString();
    }
}