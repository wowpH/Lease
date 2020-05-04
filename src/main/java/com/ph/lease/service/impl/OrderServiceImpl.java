package com.ph.lease.service.impl;

import com.ph.lease.dao.OrderMapper;
import com.ph.lease.entity.Customer;
import com.ph.lease.entity.Order;
import com.ph.lease.entity.OrderSearch;
import com.ph.lease.service.OrderService;
import com.ph.lease.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PengHao
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int insert(Order order) {
        return orderMapper.insert(order);
    }

    @Override
    public List<Order> selectFuzzyOrderByDateDesc(OrderSearch orderSearch) {
        return orderMapper.selectFuzzyOrderByDateDesc(orderSearch);
    }

    @Override
    public List<Order> selectOrderByCustomerID(Customer customer) {
        return orderMapper.selectOrderByCustomerID(customer);
    }

    @Override
    public String createOrderID() {
        StringBuilder orderID = new StringBuilder();

        // 当前日期，格式："yyyyMMddHHmmss"
        String currentDate = DateUtil.getCurrentDate();
        // 订单ID的[0,13]位是日期
        orderID.append(currentDate);

        // 最近的订单ID
        String latestOrderID = orderMapper.selectLatestOrderID();
        // Log.log(latestOrderID);
        String newRecord = "001";
        if (latestOrderID != null) {
            // 当前日期，格式："yyyyMMdd"
            currentDate = currentDate.substring(0, 8);
            // 最近的订单ID的日期，格式："yyyyMMdd"
            String latestOrderDate = latestOrderID.substring(0, 8);
            if (currentDate.compareTo(latestOrderDate) == 0) {
                // 今天已有订单，取最后三位加1，后三位的范围是[001,999]
                newRecord = String.format("%03d", Integer.parseInt(latestOrderID.substring(14)) % 999 + 1);
                // Log.log(newRecord);
            }
        }
        // 订单ID的[14,16]位是当天的订单明细数+1
        orderID.append(newRecord);

        return orderID.toString();
    }

    @Override
    public int delete(Order order) {
        return orderMapper.delete(order);
    }

}
