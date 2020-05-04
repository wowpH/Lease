package com.ph.lease.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author PengHao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private Integer id;
    private String name;
    private String telephone;
    private String password;
    private String address;
    private Date create_date;

    public Customer(String telephone, String password) {
        this.telephone = telephone;
        this.password = password;
    }

    public Customer(String name, String telephone, String password, String address, Date create_date) {
        this(null, name, telephone, password, address, create_date);
    }

}
