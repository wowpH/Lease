package com.ph.lease.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author PengHao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearch {

    private String name;
    private String start;
    private String end;

}
