package com.ph.lease.service.impl;

import com.ph.lease.entity.Admin;
import com.ph.lease.service.AdminService;
import com.ph.lease.dao.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PengHao
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin select(Admin admin) {
        return adminMapper.select(admin);
    }

    @Override
    public int updatePassword(Admin admin) {
        return adminMapper.updatePassword(admin);
    }

}
