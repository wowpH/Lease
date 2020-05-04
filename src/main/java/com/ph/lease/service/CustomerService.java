package com.ph.lease.service;

import com.ph.lease.entity.Customer;

import java.util.List;

/**
 * @author PengHao
 */
public interface CustomerService {

    /**
     * 添加客户
     *
     * @param customer
     * @return
     */
    int insert(Customer customer);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<Customer> selectAll();

    /**
     * 分页查询
     *
     * @param offset
     * @param rowCount
     * @return
     */
    List<Customer> selectByPage(int offset, int rowCount);

    /**
     * 根据手机号查询用户
     *
     * @param telephone
     * @return
     */
    Customer selectByTelephone(String telephone);

    /**
     * 查询所有用户，创建时间降序排序
     *
     * @return
     */
    List<Customer> selectAllCreateDateDesc();

    /**
     * 模糊查询姓名和手机号，按照ID降序排序
     *
     * @param customer
     * @return
     */
    List<Customer> selectFuzzyByNameAndTelephoneOrderByIDDesc(Customer customer);

    /**
     * 根据手机号查询记录数
     *
     * @param customer
     * @return
     */
    int selectCountByTelephone(Customer customer);

    /**
     * 查询所有客户，按照订单数降序排序
     *
     * @return
     */
    List<Customer> selectOrderByOrderCountDesc();

    /**
     * 根据手机号和密码查询客户
     *
     * @param customer
     * @return
     */
    Customer selectByTelephoneAndPassword(Customer customer);

    /**
     * 更新信息
     * 可以更新姓名，电话，密码，地址
     *
     * @param customer
     * @return 更新记录数
     */
    int update(Customer customer);

    /**
     * 删除客户
     *
     * @param customer
     * @return 删除的客户数
     */
    int delete(Customer customer);

}
