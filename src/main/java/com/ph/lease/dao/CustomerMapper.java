package com.ph.lease.dao;

import com.ph.lease.entity.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author PengHao
 */
public interface CustomerMapper {

    /**
     * 添加客户
     *
     * @param customer
     * @return
     */
    int insert(Customer customer);

    /**
     * 查询用户
     *
     * @param customer
     * @return
     */
    Customer select(Customer customer);

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
    List<Customer> selectByPage(@Param("offset") int offset, @Param("row_count") int rowCount);

    /**
     * 根据手机号查询客户
     *
     * @param telephone
     * @return
     */
    Customer selectByTelephone(@Param("telephone") String telephone);

    /**
     * 查询所有用户，按照创建时间降序排序
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
