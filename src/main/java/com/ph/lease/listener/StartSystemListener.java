package com.ph.lease.listener;

import com.ph.lease.util.Log;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author PengHao
 */
public class StartSystemListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //1.将项目上下文路径放入application
        ServletContext application = servletContextEvent.getServletContext();
        String contextPath = application.getContextPath();
        application.setAttribute("APP_PATH", contextPath);
        Log.log("服务器已启动", "[" + contextPath + "]");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
