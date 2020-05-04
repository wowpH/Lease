package com.ph.lease.controller;

import com.ph.lease.entity.*;
import com.ph.lease.service.AdminService;
import com.ph.lease.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author PengHao
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;

    /**
     * 跳转到登录页面
     *
     * @return 登录页面名字
     */
    @RequestMapping("/index.htm")
    public String login() {
        return "login";
    }

    @RequestMapping("/check.do")
    @ResponseBody
    public Message loginCheck(String telephone, String password, HttpServletRequest request) {
        // Log.log("LoginController.loginCheck", "验证账号");
        Message message = new Message();
        // 判断是否是admin
        if (Constant.ADMIN.equals(telephone)) {
            // Log.log("该账号为管理员账号", telephone);
            Admin admin = adminService.select(new Admin(telephone, password));
            if (admin != null) {
                // Log.log("管理员密码正确", password);
                // 获取Session
                HttpSession session = request.getSession(true);
                // 获取当前的Session ID
                String sessionId = request.getRequestedSessionId();

                // 获取已经登录的账号的Session ID集合
                Map<String, String> sessionMap = SessionSave.getSessionMap();
                // if (sessionMap.isEmpty()) {
                //    Log.log("没有已登录的账号");
                // } else {
                //    Log.log("已经登录过的账号和Session ID如下：");
                //     for (Map.Entry<String, String> entry : sessionMap.entrySet()) {
                //         System.out.println(entry.getKey() + ", " + entry.getValue());
                //     }
                // }
                // Log.log("将Session ID添加到map中", sessionId);
                sessionMap.put(Constant.ADMIN, sessionId);
                // Log.log("将对象保存到Session中", admin.toString());
                session.setAttribute(Constant.ADMIN, admin);
                session.setAttribute("user", "admin");
                session.setAttribute("password", admin.getPassword());
                message.setMsgCode(message.getADMIN());
                message.setMsg("管理员");
            } else {
                // Log.log("管理员密码错误", password);
                message.setMsgCode(message.getERROR());
                message.setMsg("管理员密码错误！");
            }
        } else {
            // Log.log("客户登录", "暂未实现");
            Customer customer = customerService.selectByTelephoneAndPassword(new Customer(telephone, password));
            if (customer != null) {
                HttpSession session = request.getSession(true);
                String sessionId = session.getId();
                Map<String, String> sessionMap = SessionSave.getSessionMap();
                sessionMap.put(customer.getTelephone(), sessionId);
                session.setAttribute(customer.getTelephone(), customer);
                session.setAttribute("user", customer.getTelephone());
                session.setAttribute("password", customer.getPassword());
                message.setMsgCode(message.getCUSTOMER());
                message.setMsg("客户");
            } else {
                message.setMsgCode(message.getERROR());
                message.setMsg("客户密码错误！");
            }
        }
        return message;
    }

    @RequestMapping("/out")
    public String loginOut(HttpSession session) {
        // Log.log("LoginController.loginOut", session.getAttribute(Constant.ADMIN).toString());
        session.invalidate();
        return "login";
    }

}
