package com.ph.lease.service;

import com.ph.lease.entity.Message;
import com.ph.lease.entity.OrderDetail;

import java.util.List;

/**
 * @author PengHao
 */
public interface OrderDetailService {

    /**
     * 添加订单明细
     *
     * @param orderDetails
     * @return
     */
    Message insert(List<OrderDetail> orderDetails);

    List<OrderDetail> selectByOrderID(OrderDetail orderDetail);

    /**
     * 创建订单明细ID
     *
     * @return
     */
    String createOrderDetailID();

    /**
     * 根据订单ID，删除订单明细
     *
     * @param orderDetail
     * @return 删除的数量
     */
    int deleteByOrderID(List<OrderDetail> orderDetail);

    /**
     * 根据订单ID逻辑删除订单明细
     *
     * @param orderId
     * @return
     */
    int deleteLogicallyByOrderID(String orderId);

}
