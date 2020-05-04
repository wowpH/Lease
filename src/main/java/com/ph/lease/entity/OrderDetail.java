package com.ph.lease.entity;

import com.ph.lease.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author PengHao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    private String id;
    private String oid;
    private Integer mid;
    private Integer amount;
    private Date creationDate;
    private char valid;

    private Order order;
    private Material material;

    public OrderDetail(String oid) {
        this.oid = oid;
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
