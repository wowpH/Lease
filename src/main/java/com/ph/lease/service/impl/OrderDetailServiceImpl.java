package com.ph.lease.service.impl;

import com.ph.lease.dao.OrderDetailMapper;
import com.ph.lease.entity.Message;
import com.ph.lease.entity.OrderDetail;
import com.ph.lease.service.OrderDetailService;
import com.ph.lease.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author PengHao
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Message insert(List<OrderDetail> orderDetails) {
        Message message = new Message();
        for (int i = 0; i < orderDetails.size(); ++i) {
            OrderDetail orderDetail = orderDetails.get(i);
            String id = createOrderDetailID();
            orderDetail.setId(id);
            orderDetail.setCreationDate(DateUtil.parse(id.substring(0, 14), "yyyyMMddHHmmss"));
            int count = orderDetailMapper.insert(orderDetail);
            if (count == 0) {
                deleteByOrderID(orderDetails.subList(0, i));
                message.setMsg("订单明细添加失败！");
                return message;
            }
        }
        message.setMsg("订单明细添加成功！");
        message.setMsgCode(Message.SF_SUCCESS);
        return message;
    }

    @Override
    public List<OrderDetail> selectByOrderID(OrderDetail orderDetail) {
        return orderDetailMapper.selectByOrderID(orderDetail);
    }

    @Override
    public String createOrderDetailID() {
        StringBuilder orderDetailID = new StringBuilder();

        // 当前日期，格式："yyyyMMddHHmmss"
        String currentDate = DateUtil.getCurrentDate();
        // 订单明细ID的[0,13]位是日期
        orderDetailID.append(currentDate);

        // 最近的订单明细ID
        String latestOrderDetailID = orderDetailMapper.selectLatestOrderID();
        String newRecord = "001";
        if (latestOrderDetailID != null) {
            // 当前日期，格式："yyyyMMdd"
            currentDate = currentDate.substring(0, 8);
            // 最近订单明细的日期，格式："yyyyMMdd"
            String latestOrderDetailDate = latestOrderDetailID.substring(0, 8);
            if (currentDate.compareTo(latestOrderDetailDate) == 0) {
                // 今天已有订单明细，最后三位的记录数增加，后三位的范围是[001,999]
                int count = Integer.parseInt(latestOrderDetailID.substring(14)) % 999 + 1;
                newRecord = String.format("%03d", count);
            }
        }
        // 订单明细ID的[14,16]位是当天的订单明细数+1
        orderDetailID.append(newRecord);

        return orderDetailID.toString();
    }

    @Override
    public int deleteByOrderID(List<OrderDetail> orderDetails) {
        for (int i = 0; i < orderDetails.size(); ++i) {
            int count = orderDetailMapper.deleteByOrderID(orderDetails.get(i));
        }
        return orderDetails.size();
    }

    @Override
    public int deleteLogicallyByOrderID(String orderId) {
        return orderDetailMapper.deleteLogicallyByOrderID(orderId);
    }

}
