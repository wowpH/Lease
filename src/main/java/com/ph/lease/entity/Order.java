package com.ph.lease.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.lease.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author PengHao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
