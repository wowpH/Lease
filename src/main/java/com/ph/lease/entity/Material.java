package com.ph.lease.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 材料类
 * 对应数据库的tbl_material
 *
 * @author PengHao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
