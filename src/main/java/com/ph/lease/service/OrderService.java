package com.ph.lease.service;

import com.ph.lease.entity.Customer;
import com.ph.lease.entity.Order;
import com.ph.lease.entity.OrderSearch;

import java.util.List;

/**
 * @author PengHao
 */
public interface OrderService {

    /**
     * 添加订单
     *
     * @param order
     * @return 插入的记录数
     */
    int insert(Order order);

    List<Order> selectFuzzyOrderByDateDesc(OrderSearch orderSearch);

    /**
     * 根据客户编号查询客户订单
     *
     * @param customer
     * @return
     */
    List<Order> selectOrderByCustomerID(Customer customer);

    String createOrderID();

    /**
     * 删除订单
     *
     * @param order
     * @return
     */
    int delete(Order order);

}
