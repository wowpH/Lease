package com.ph.lease.service.impl;

import com.ph.lease.dao.CustomerMapper;
import com.ph.lease.entity.Customer;
import com.ph.lease.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PengHao
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public int insert(Customer customer) {
        return customerMapper.insert(customer);
    }

    @Override
    public List<Customer> selectAll() {
        return customerMapper.selectAll();
    }

    @Override
    public List<Customer> selectByPage(int offset, int rowCount) {
        return customerMapper.selectByPage(offset, rowCount);
    }

    @Override
    public Customer selectByTelephone(String telephone) {
        return customerMapper.selectByTelephone(telephone);
    }

    @Override
    public List<Customer> selectAllCreateDateDesc() {
        return customerMapper.selectAllCreateDateDesc();
    }

    @Override
    public List<Customer> selectFuzzyByNameAndTelephoneOrderByIDDesc(Customer customer) {
        return customerMapper.selectFuzzyByNameAndTelephoneOrderByIDDesc(customer);
    }

    @Override
    public int selectCountByTelephone(Customer customer) {
        return customerMapper.selectCountByTelephone(customer);
    }

    @Override
    public List<Customer> selectOrderByOrderCountDesc() {
        return customerMapper.selectOrderByOrderCountDesc();
    }

    @Override
    public Customer selectByTelephoneAndPassword(Customer customer) {
        return customerMapper.selectByTelephoneAndPassword(customer);
    }

    @Override
    public int update(Customer customer) {
        return customerMapper.update(customer);
    }

    @Override
    public int delete(Customer customer) {
        return customerMapper.delete(customer);
    }

}
