package com.ph.lease.controller;

import com.ph.lease.entity.Admin;
import com.ph.lease.entity.Constant;
import com.ph.lease.entity.Customer;
import com.ph.lease.entity.Message;
import com.ph.lease.service.AdminService;
import com.ph.lease.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author PengHao
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;

    @RequestMapping("/verify-old-password.do")
    @ResponseBody
    public Message doVerifyOldPassword(String oldPassword, HttpSession httpSession) {
        // Log.log("UserController.doVerifyOldPassword");
        // Log.log(oldPassword);
        // Log.log(httpSession.getAttribute("user").toString());
        // Log.log(httpSession.getAttribute("password").toString());

        Message message = new Message();

        String currentPassword = httpSession.getAttribute("password").toString();

        if (oldPassword == null) {
            message.setMsg("参数错误！");
            return message;
        }

        if (oldPassword.equals(currentPassword)) {
            message.setMsg("密码正确！");
            message.setMsgCode(Message.SF_SUCCESS);
        } else {
            message.setMsg("密码错误！");
        }

        return message;
    }

    @RequestMapping("/save-password.do")
    @ResponseBody
    public Message doSaveNewPassword(String oldPassword, String newPassword, HttpSession httpSession) {
        // Log.log("UserController.doSaveNewPassword");

        Message message = new Message();
        int count = 0;

        // 获取当前用户的密码
        String currentPassword = httpSession.getAttribute("password").toString();

        if (oldPassword == null) {
            message.setMsg("参数错误！");
            return message;
        }

        // 原密码不对，直接返回
        if (!oldPassword.equals(currentPassword)) {
            message.setMsg("密码错误！");
            return message;
        }

        if (newPassword == null) {
            message.setMsg("参数错误！");
            return message;
        }

        // 获取当前用户
        String user = httpSession.getAttribute("user").toString();
        if (user == null) {
            message.setMsg("登录账号已过期，请重修登录！");
            return message;
        }

        Admin admin = null;
        Customer customer = null;
        if (user.equals(Constant.ADMIN)) {
            // 当前用户是管理员
            admin = (Admin) httpSession.getAttribute(user);
            admin.setPassword(newPassword);
            count = adminService.updatePassword(admin);
        } else {
            // 当前用户是客户
            customer = (Customer) httpSession.getAttribute(user);
            customer.setPassword(newPassword);
            count = customerService.update(customer);
        }

        if (count == 1) {
            httpSession.setAttribute("password", newPassword);
            if (admin != null) {
                httpSession.setAttribute(Constant.ADMIN, admin);
            } else if (customer != null) {
                httpSession.setAttribute(customer.getTelephone(), customer);
            }
            message.setMsg("密码修改成功");
            message.setMsgCode(Message.SF_SUCCESS);
        } else {
            message.setMsg("密码修改失败");
        }

        return message;
    }

}
