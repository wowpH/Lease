package com.ph.lease.entity;

/**
 * 材料类
 * 对应数据库的tbl_material
 *
 * @author PengHao
 */
public class Material {
    private Integer id;
    private String name;
    private String model;
    private Double specification;
    private String unit;
    private Double price;
    private Integer stocks;
    private Integer total;
    private String description;
    private Double cost;
    private char valid;
    
    public Material() {
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
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public Double getSpecification() {
        return specification;
    }
    
    public void setSpecification(Double specification) {
        this.specification = specification;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getStocks() {
        return stocks;
    }
    
    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }
    
    public Integer getTotal() {
        return total;
    }
    
    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Double getCost() {
        return cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public char getValid() {
        return valid;
    }
    
    public void setValid(char valid) {
        this.valid = valid;
    }
}