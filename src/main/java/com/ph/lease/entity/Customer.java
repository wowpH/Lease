package com.ph.lease.entity;

import java.sql.Date;

/**
 * @author PengHao
 */
public class Customer {
    private Integer id;
    private String name;
    private String telephone;
    private String password;
    private String address;
    private Date create_date;
    
    public Customer() {
    }
    
    public Customer(String telephone, String password) {
        this.telephone = telephone;
        this.password = password;
    }
    
    public Customer(String name, String telephone, String password, String address, Date create_date) {
        this(null, name, telephone, password, address, create_date);
    }
    
    public Customer(Integer id, String name, String telephone, String password, String address, Date create_date) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.password = password;
        this.address = address;
        this.create_date = create_date;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Date getCreate_date() {
        return create_date;
    }
    
    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}