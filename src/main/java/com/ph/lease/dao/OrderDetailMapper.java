package com.ph.lease.dao;

import com.ph.lease.entity.OrderDetail;

import java.util.List;

/**
 * @author PengHao
 */
public interface OrderDetailMapper {

    /**
     * 插入订单明细
     *
     * @param orderDetail
     * @return
     */
    int insert(OrderDetail orderDetail);

    List<OrderDetail> selectByOrderID(OrderDetail orderDetail);

    /**
     * 查询最近的订单明细ID
     *
     * @return
     */
    String selectLatestOrderID();

    /**
     * 根据订单ID，删除订单明细，物理删除
     *
     * @param orderDetail
     * @return
     */
    int deleteByOrderID(OrderDetail orderDetail);

    /**
     * 根据订单ID逻辑删除订单明细
     *
     * @param oid
     * @return
     */
    int deleteLogicallyByOrderID(String oid);

}
