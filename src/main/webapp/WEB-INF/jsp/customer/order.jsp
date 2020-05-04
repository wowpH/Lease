<%--
  订单页面
  User: PengHao
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>方咀租赁站</title>
    <!-- 页面图标 -->
    <link rel="short icon" href="${APP_PATH}/lease/images/favicon.png">
    <!-- layui框架的核心样式文件 -->
    <link rel="stylesheet" href="${APP_PATH}/layui/css/layui.css">
    <!-- 引入自定义样式文件 -->
    <link rel="stylesheet" href="${APP_PATH}/lease/css/lease.css">
</head>
<body>
<!-- 页面主体 -->
<div class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
        <!--头部-->
        <jsp:include page="../heaser.jsp" flush="true"></jsp:include>

        <!--左侧导航-->
        <jsp:include page="left.jsp" flush="true"></jsp:include>

        <!-- 内容主体区域 -->
        <div class="layui-body margin10">
            <!-- 标题 -->
            <div class="margin10">
                <h1>我的订单 <i class="layui-icon layui-icon-form" style="font-size: 30px"></i></h1>
            </div>
            <!-- 订单表格 -->
            <div>
                <table class="layui-hide" id="lease-order-table-id" lay-filter="lease-order-table-filter"></table>
            </div>
            <!-- 结束 订单表格 -->
        </div>
        <!-- 结束 内容主体区域 -->

        <!--底部-->
        <jsp:include page="../footer.jsp" flush="true"></jsp:include>
    </div>
</div>
<!-- 结束 页面主体 -->

<!-- 查看订单明细弹出层 -->
<div class="margin10" id="lease-view-details-div-id" style="display: none;">
    <!-- 查看订单明细表格 -->
    <table id="lease-detail-table-id-view" lay-filter="lease-detail-table-filter-view"></table>
</div>

<!--脚本-->
<!--layui框架的基础核心库-->
<script src="${APP_PATH}/layui/layui.js"></script>
<!-- 自定义js文件，表格刷新 -->
<script src="${APP_PATH}/lease/js/customer/order.js"></script>
<!-- 表格的查看明细按钮 -->
<script type="text/html" id="lease-operation-script-id">
    <a class="layui-btn layui-btn-xs" lay-event="lease-view-details-a-event">查看明细</a>
</script>
</body>
</html>