package com.ph.lease.dao;

import com.ph.lease.entity.Admin;

/**
 * @author PengHao
 */
public interface AdminMapper {
    /**
     * 查询管理员
     *
     * @param admin
     * @return
     */
    Admin select(Admin admin);
    
    /**
     * 修改密码
     *
     * @param admin
     */
    int updatePassword(Admin admin);
}