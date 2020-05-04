<%--
  左侧导航
  Created by IntelliJ IDEA.
  User: PengHao
  Date: 2020-04-15
  Time: 11:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style type="text/css">
    .lease-li {
        text-align: center;
    }
</style>

<!--left.jsp-->
<div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
        <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
        <ul class="layui-nav layui-nav-tree" lay-filter="test">
            <!-- 订单管理 -->
            <li class="layui-nav-item lease-li"><a href="${APP_PATH}/admin/order.htm">订单管理</a></li>
            <!-- 材料管理 -->
            <li class="layui-nav-item lease-li"><a href="${APP_PATH}/admin/material.htm">材料管理</a></li>
            <!-- 客户管理 -->
            <li class="layui-nav-item lease-li"><a href="${APP_PATH}/admin/customer.htm">客户管理</a></li>
            <!-- 修改密码 -->
            <li class="layui-nav-item lease-li">
                <a href="${APP_PATH}/admin/change-password.htm">密码管理</a>
            </li>
        </ul>
    </div>
</div>
