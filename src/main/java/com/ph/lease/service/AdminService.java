package com.ph.lease.service;

import com.ph.lease.entity.Admin;

/**
 * @author PengHao
 */
public interface AdminService {
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
     * @return
     */
    int updatePassword(Admin admin);

}
