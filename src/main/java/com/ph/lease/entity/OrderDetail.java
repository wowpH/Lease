package com.ph.lease.entity;

import com.ph.lease.util.DateUtil;

import java.util.Date;

/**
 * @author PengHao
 */
public class OrderDetail {
    private String id;
    private String oid;
    private Integer mid;
    private Integer amount;
    private Date creationDate;
    private char valid;
    private Order order;
    private Material material;
    
    public OrderDetail() {
    }
    
    public OrderDetail(String oid) {
        this.oid = oid;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getOid() {
        return oid;
    }
    
    public void setOid(String oid) {
        this.oid = oid;
    }
    
    public Integer getMid() {
        return mid;
    }
    
    public void setMid(Integer mid) {
        this.mid = mid;
    }
    
    public Integer getAmount() {
        return amount;
    }
    
    public void setAmount(Integer amount) {
        this.amount = amount;
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
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    
    @Override
    public String toString() {
        // Log.log("OrderDetail.toString()");
        StringBuilder result = new StringBuilder();
        result.append("OrderDetail{");
        if (id != null) {
            result.append("id='" + id + '\'');
        } else {
            result.append("id=null");
        }
        result.append(", oid='" + oid + '\'');
        result.append(", mid=" + mid);
        result.append(", amount=" + amount);
        if (creationDate != null) {
            result.append(", creationDate=" + DateUtil.format(creationDate));
        } else {
            result.append(", creationDate=null");
        }
        result.append(", valid=" + valid);
        // result.append(", order=" + order);
        // result.append(", material=" + material);
        result.append('}');
        // Log.log(result.toString());
        return result.toString();
    }
}