package com.ph.lease.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员实体类
 *
 * @author PengHao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer id;
    private String username;
    private String password;

    public Admin(String username, String password) {
        this(-1, username, password);
    }
}
