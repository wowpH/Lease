package com.ph.lease.interceptor;

import com.ph.lease.entity.Admin;
import com.ph.lease.entity.Constant;
import com.ph.lease.entity.Customer;
import com.ph.lease.entity.SessionSave;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 拦截器
 * 某些页面必须要在登录之后才能展示，有些页面可以直接访问。比如登录页面
 * 所以登录页面不许要拦截，那些需要登录才能展示的页面就需要拦截并判断
 *
 * @author PengHao
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Log.log("LoginInterceptor类 preHandle方法", "拦截器");

        //1.定义那些路径是不需要拦截
        Set<String> uri = new HashSet<String>();

        uri.add("/login/index.htm");
        uri.add("/login/check.do");

        // 获取当前请求的路径
        String servletPath = request.getServletPath();
        // Log.log("当前请求路径", servletPath);
        // 如果包含，说明不需要拦截，直接前端展示
        if (uri.contains(servletPath)) {
            // Log.log("不用拦截", servletPath);
            return true;
        }

        // 判断是否是登录状态
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login/index.htm");
            return false;
        } else if (user.equals(Constant.ADMIN)) {
            Admin admin = (Admin) session.getAttribute(user);
            if (admin != null) {
                // 管理员登录
                // 获取旧的sessionId，可能为null
                Map<String, String> sessionMap = SessionSave.getSessionMap();

                String oldSessionId = sessionMap.get(admin.getUsername());
                // Log.log("旧的SessionId", oldSessionId);
                String newSessionId = session.getId();
                // Log.log("新的SessionId： ", newSessionId);

                if ((oldSessionId != null) && !newSessionId.equals(oldSessionId)) {
                    System.out.println("账号已在其他地方登录，即将进入登录页面");
                    response.sendRedirect(request.getContextPath() + "/login/index.htm");
                    return false;
                } else {
                    // Log.log("即将进入管理员主页", admin.toString());
                    return true;
                }
            } else {
                // 否则跳转到登录页面
                // Log.log("未登录账号");
                response.sendRedirect(request.getContextPath() + "/login/index.htm");
                return false;
            }
        } else {
            Customer customer = (Customer) session.getAttribute(user);
            if (customer != null) {
                // 获取旧的sessionId，可能为null
                Map<String, String> sessionMap = SessionSave.getSessionMap();

                String oldSessionId = sessionMap.get(customer.getTelephone());
                // Log.log("旧的SessionId", oldSessionId);
                String newSessionId = session.getId();
                // Log.log("新的SessionId： ", newSessionId);

                if ((oldSessionId != null) && !newSessionId.equals(oldSessionId)) {
                    System.out.println("账号已在其他地方登录，即将进入登录页面");
                    response.sendRedirect(request.getContextPath() + "/login/index.htm");
                    return false;
                } else {
                    return true;
                }
            } else {
                // 否则跳转到登录页面
                response.sendRedirect(request.getContextPath() + "/login/index.htm");
                return false;
            }
        }
    }

}
