<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--heaser.jsp-->
<div class="layui-header">
    <div class="layui-logo" style="font-size: 25px;">
        <i class="layui-icon layui-icon-home" style="font-size: 30px;"></i> 方咀租赁站
    </div>
    <!--头部右侧-->
    <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item">
            <!--头像-->
            <img src="${APP_PATH}/lease/images/admin-head.jpg" class="layui-nav-img">
            <%=(String) session.getAttribute("user")%>
        </li>
        <!--退出系统-->
        <li class="layui-nav-item">
            <a href="${APP_PATH}/login/out.htm">
                <i class="layui-icon layui-icon-logout"></i> 退出
            </a>
        </li>
    </ul>
</div>
