package com.ph.lease.controller;

import com.ph.lease.entity.Customer;
import com.ph.lease.entity.Message;
import com.ph.lease.entity.Order;
import com.ph.lease.service.CustomerService;
import com.ph.lease.service.OrderService;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author PengHao
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/index.htm")
    public String toIndex() {
        return "/customer/index";
    }

    @RequestMapping("/order.htm")
    public String toOrder() {
        return "/customer/order";
    }

    @RequestMapping("/change-password.htm")
    public String toChangePassword() {
        return "/customer/change-password";
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public Message doDeleteCustomer(Customer customer) {
        // Log.log("CustomerController.doDeleteCustomer", "删除客户");
        // Log.log("待删除的客户", customer.toString());

        Message message = new Message();
        int count = 0;

        // 查询是否有有效订单
        List<Order> orders = orderService.selectOrderByCustomerID(customer);
        if (orders != null && !orders.isEmpty()) {
            message.setMsg("客户【" + customer.getName() + "】还有订单，不能删除！");
            return message;
        }

        // 该客户没有订单，可以删除
        count = customerService.delete(customer);
        if (count == 1) {
            // Log.log("删除成功");
            message.setMsgCode(message.getSUCCESS());
            message.setMsg("客户【" + customer.getName() + "】删除成功！");
        }

        return message;
    }

    /**
     * sName和sTelephone用于查询数据，必须和前端的名字一样
     *
     * @param page
     * @param limit
     * @param sName      查询的姓名
     * @param sTelephone 查询的手机号
     * @return
     */
    @RequestMapping("/list-customer.do")
    @ResponseBody
    public String listCustomers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                String sName, String sTelephone) {
        // Log.log("AdminController类  listCustomers方法", "获取数据库的用户信息");
        // Log.log("page", page);
        // Log.log("limit", limit);
        // Log.log("sName", sName);
        // Log.log("sTelephone", sTelephone);

        Customer searchCustomer = new Customer();
        if (sName != null) {
            searchCustomer.setName(sName);
        }
        if (sTelephone != null) {
            searchCustomer.setTelephone(sTelephone);
        }

        // 查询所有客户，按照ID降序
        List<Customer> allCustomers = customerService.selectFuzzyByNameAndTelephoneOrderByIDDesc(searchCustomer);
        // Log.log("查询到的所有用户");
        // for (Customer customer : allCustomers) {
        //     Log.log(customer.toString());
        // }

        // 获取第page页的limit条记录的起止索引
        int fromIndex = (page - 1) * limit;
        int toIndex = Math.min(fromIndex + limit, allCustomers.size());

        List<Customer> currentPageCustomers = allCustomers.subList(fromIndex, toIndex);

        // Log.log("总条数", allCustomers.size());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "用户信息");
        jsonObject.put("count", allCustomers.size());
        jsonObject.put("data", currentPageCustomers);

        // Log.log("返回给前端的JSON数据", jsonObject.toString());

        return jsonObject.toString();
    }

    @RequestMapping("/add-customer.do")
    @ResponseBody
    public Message doAddCustomer(Customer customer) {
        // Log.log("AdminController类 doAddCustomer方法", "添加用户到数据库");
        // Log.log("待添加的用户", customer.toString());

        int count = 0;
        Message message = new Message();

        // 查询此手机号是否存在
        count = customerService.selectCountByTelephone(customer);
        if (count > 0) {
            // 存在，返回不能添加客户的消息
            message.setMsgCode(Message.SF_FAILURE);
            message.setMsg("客户【" + customer.getName() + "】已存在，请不要重复添加！");
            // Log.log("存在", message.toString());
            return message;
        }

        // 不存在，添加客户
        count = customerService.insert(customer);
        if (count == 1) {
            // Log.log("添加成功");
            message.setMsgCode(Message.SF_SUCCESS);
        } else {
            // Log.log("添加失败", "可能是手机号重复（用户已存在）");
            message.setMsgCode(Message.SF_FAILURE);
        }
        return message;
    }

    @RequestMapping("/edit-customer.do")
    @ResponseBody
    public Message doEditCustomer(Customer customer) {
        // Log.log("AdminController类 doEditCustomer方法", customer.toString());
        Message message = new Message();

        // 查询该手机号客户
        Customer select = customerService.selectByTelephone(customer.getTelephone());

        if (select != null && !customer.getId().equals(select.getId())) {
            // 修改后的手机号存在且不是同一个人，那么是重复
            message.setMsgCode(Message.SF_FAILURE);
            message.setMsg("手机号【" + customer.getTelephone() + "】已存在，编辑失败！");
            // Log.log("手机号已存在", customer.getTelephone());
        } else {
            int count = customerService.update(customer);
            if (count == 1) {
                message.setMsgCode(Message.SF_SUCCESS);
                message.setMsg("更新成功");
                // Log.log("更新成功");
            } else {
                message.setMsgCode(Message.SF_FAILURE);
                message.setMsg("更新失败");
                // Log.log("更新失败");
            }
        }
        return message;
    }

    @RequestMapping("/list-order.do")
    @ResponseBody
    public String doListOrders(Integer page, Integer limit, HttpSession httpSession) {
        // Log.log("page", page);
        // Log.log("limit", limit);

        String telephone = (String) httpSession.getAttribute("user");
        Customer customer = (Customer) httpSession.getAttribute(telephone);
        // Log.log(customer.toString());

        // 按照创建时间降序排序
        List<Order> allOrders = orderService.selectOrderByCustomerID(customer);
        // Log.log("查询到的订单");
        // for (Order o : allOrders) {
        //     Log.log(o.toString());
        // }
        // Log.logLn();

        int fromIndex = (page - 1) * limit;
        int toIndex = Math.min(fromIndex + limit, allOrders.size());

        List<Order> currentPageOrders = allOrders.subList(fromIndex, toIndex);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "订单信息");
        jsonObject.put("data", currentPageOrders);
        jsonObject.put("count", allOrders.size());

        return jsonObject.toString();
    }

}
