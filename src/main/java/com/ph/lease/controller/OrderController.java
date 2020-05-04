package com.ph.lease.controller;

import com.ph.lease.entity.Message;
import com.ph.lease.entity.Order;
import com.ph.lease.entity.OrderDetail;
import com.ph.lease.entity.OrderSearch;
import com.ph.lease.service.MaterialService;
import com.ph.lease.service.OrderDetailService;
import com.ph.lease.service.OrderService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author PengHao
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping("/create-order-id.do")
    @ResponseBody
    public Message doCreateOrderID() {
        // Log.log("OrderController.doCreateOrderID");
        String orderID = orderService.createOrderID();
        Message message = new Message(Message.SF_SUCCESS, orderID);
        // Log.log(message.toString());
        return message;
    }

    /**
     * @param page
     * @param limit
     * @param orderSearch 查询参数
     * @return
     */
    @RequestMapping("/list-order.do")
    @ResponseBody
    public String doListOrders(Integer page, Integer limit, OrderSearch orderSearch) {
        // Log.log("AdminController 类 doListOrders 方法", "获取订单信息");
        // Log.log(orderSearch.toString());
        // Log.log("page", page);
        // Log.log("limit", limit);
        // Log.log("待查询的材料", order.toString());

        // 按照创建时间降序排序
        List<Order> allOrders = orderService.selectFuzzyOrderByDateDesc(orderSearch);

        // Log.log("查询到的订单");
        // for (Order o : allOrders) {
        //     Log.log(o.toString());
        // }
        // Log.logLn();

        int fromIndex = (page - 1) * limit;
        int toIndex = Math.min(fromIndex + limit, allOrders.size());

        List<Order> currentPageOrders = allOrders.subList(fromIndex, toIndex);

        // Log.log("第" + page + "页的" + limit + "个订单是");
        // for (Order o : currentPageOrders) {
        //     Log.log(o.toString());
        // }
        // Log.logLn();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "订单信息");
        jsonObject.put("data", currentPageOrders);
        jsonObject.put("count", allOrders.size());

        // Log.log("返回给前端的数据是", jsonObject.toString());

        return jsonObject.toString();
    }

    @RequestMapping("/add-order.do")
    @ResponseBody
    public Message doAddOrder(Order order) {
        // Log.log("OrderController.doAddOrder");
        // Log.log(order.toString());

        Message message = new Message();

        List<OrderDetail> orderDetails = order.getOrderDetails();
        // Log.log(orderDetails.toString());

        if (order.getType().equals("租")) {
            // 减少库存量
            message = materialService.reduceStocks(orderDetails);
            // Log.log(message.toString());
        } else if (order.getType().equals("还")) {
            message = materialService.increaseStocks(orderDetails);
        }

        if (message.getMsgCode() == Message.SF_FAILURE) {
            return message;
        }

        // 成功减少库存量，接下来保存订单信息
        int count = orderService.insert(order);
        if (count == 0) {
            message.setMsg("订单添加失败！");
            if (order.getType().equals("租")) {
                materialService.increaseStocks(orderDetails);
            } else if (order.getType().equals("还")) {
                materialService.reduceStocks(orderDetails);
            }
            // Log.log(message.toString());
            return message;
        }

        // 添加订单明细到数据库
        Message orderDetailMessage = orderDetailService.insert(orderDetails);
        if (orderDetailMessage.getMsgCode() == Message.SF_FAILURE) {
            return orderDetailMessage;
        }

        message.setMsg("订单添加成功！");
        message.setMsgCode(Message.SF_SUCCESS);
        // Log.log("返回值", message.toString());
        return message;
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Message doDeleteOrder(Order order) {
        // Log.log("OrderController.doDeleteOrder");
        // Log.log(order.toString());

        Message message = new Message();
        int count = 0;

        if (order == null) {
            message.setMsg("参数错误！");
            return message;
        }

        // 更新库存
        if (order.getType().equals("租")) {
            message = materialService.increaseStocks(order.getOrderDetails());
        } else if (order.getType().equals("还")) {
            message.setMsgCode(Message.SF_SUCCESS);
            message.setMsg("库存更新成功");
        }
        if (message.getMsgCode() == Message.SF_FAILURE) {
            return message;
        }

        count = orderDetailService.deleteLogicallyByOrderID(order.getId());
        if (count < order.getOrderDetails().size()) {
            message.setMsg("订单明细删除失败！");
            return message;
        }

        count = orderService.delete(order);
        if (count == 1) {
            message.setMsgCode(Message.SF_SUCCESS);
            message.setMsg("订单删除成功！");
        } else {
            message.setMsg("订单删除失败！");
        }

        return message;
    }

}
