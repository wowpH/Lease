<%--
  客户左侧导航
  User: PengHao
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--left.jsp-->
<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree" lay-filter="test">
            <!-- 我的订单 -->
            <li class="layui-nav-item"><a href="${APP_PATH}/customer/order.htm" style="text-align: center">订单信息</a></li>
            <!-- 密码管理 -->
            <li class="layui-nav-item">
                <a href="${APP_PATH}/customer/change-password.htm" style="text-align: center">密码管理</a>
            </li>
        </ul>
    </div>
</div>
