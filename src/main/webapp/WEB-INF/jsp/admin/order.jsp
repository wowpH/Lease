<%--
  订单页面
  Created by IntelliJ IDEA.
  User: PengHao
  Date: 2020-04-20
  Time: 17:16
  To change this template use File | Settings | File Templates.
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
        <!-- 头部导航 -->
        <jsp:include page="../heaser.jsp" flush="true"></jsp:include>
        <!-- 左侧导航 -->
        <jsp:include page="left.jsp" flush="true"></jsp:include>
        <!-- 内容主体区域 -->
        <div class="layui-body margin10">
            <!--标题-->
            <div class="margin10">
                <h1>订单管理 <i class="layui-icon layui-icon-form" style="font-size: 30px"></i></h1>
            </div>
            <!-- 查询表单 -->
            <form class="layui-form" id="lease-search-form-id" lay-filter="lease-search-form-filter">
                <div class="layui-form-item">
                    <!-- 客户姓名输入框 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="lease-name-input-name-search" placeholder="客户姓名" autocomplete="off"
                                   class="layui-input" id="lease-name-input-id-search">
                        </div>
                    </div>
                    <!-- 日期范围输入框 -->
                    <div class="layui-inline">
                        <input type="text" class="layui-input" id="lease-start-date-input-id"
                               name="lease-start-date-input-name"
                               placeholder="开始日期">
                    </div>
                    <div class="layui-inline">-</div>
                    <div class="layui-inline">
                        <input type="text" class="layui-input" id="lease-end-date-input-id"
                               name="lease-end-date-input-name"
                               placeholder="结束日期">
                    </div>
                    <!-- 查询，重置 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <button class="layui-btn" lay-submit lay-filter="lease-search-btn-filter">
                                <i class="layui-icon layui-icon-search"></i>查询
                            </button>
                            <!-- 只能重置form标签的内容 -->
                            <button type="layui-btn" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                    <!-- 添加订单按钮 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <button type="button" class="layui-btn layui-btn-normal" id="lease-add-order-btn-id">
                                添加订单
                            </button>
                        </div>
                    </div>
                </div>
            </form>
            <!-- 结束 查询表单 -->
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

<!-- 添加订单弹出层，默认不显示 -->
<div class="margin10" id="lease-order-div-id" style="display: none">
    <!-- 添加订单表单 -->
    <form class="layui-form" id="lease-order-form-id" lay-filter="lease-order-form-filter">
        <!-- 订单编号，由后端生成 -->
        <div class="layui-form-item">
            <label class="layui-form-label">订单编号</label>
            <div class="layui-input-inline" style="width: 250px;">
                <input type="text" name="lease-id-input-name" autocomplete="off" class="layui-input" readonly>
            </div>
        </div>
        <!-- 选择客户 下拉选择框 -->
        <div class="layui-form-item">
            <label class="layui-form-label">客户</label>
            <div class="layui-input-inline" style="width: 250px;">
                <select id="lease-id-select-id" name="lease-customer-select-name" lay-verify="required" lay-search>
                </select>
            </div>
        </div>
        <!-- 类型选择框 租/还 -->
        <div class="layui-form-item">
            <label class="layui-form-label">类型</label>
            <div class="layui-input-block">
                <input type="radio" name="lease-type-input-name" value="100" title="租">
                <input type="radio" name="lease-type-input-name" value="200" title="还" checked>
            </div>
        </div>
        <!-- 创建日期 -->
        <div class="layui-form-item">
            <label class="layui-form-label">创建日期</label>
            <div class="layui-input-inline" style="width: 250px;">
                <input type="text" name="lease-date-input-name" autocomplete="off" class="layui-input" readonly>
            </div>
        </div>
        <!-- 订单明细 按钮 -->
        <div class="layui-form-item">
            <label class="layui-form-label">订单明细</label>
            <!-- 选择材料 -->
            <div class="layui-input-inline" style="width: 250px;" id="lease-material-div-id">
                <select id="lease-material-select-id" name="lease-material-select-name" lay-search>
                </select>
            </div>
            <!-- 数量 -->
            <div class="layui-input-inline" style="width: 150px;">
                <input type="number" name="lease-amount-input-name" id="lease-amount-input-id" placeholder="示例：123"
                       autocomplete="off" class="layui-input" min="0" value="0">
            </div>
            <!-- 添加和删除按钮 -->
            <div class="layui-input-inline">
                <button type="button" class="layui-btn layui-btn-primary" id="lease-add-detail-btn-id"
                        lay-filter="lease-add-detail-btn-filter">添加
                </button>
                <button type="button" class="layui-btn layui-btn-primary" id="lease-del-detail-btn-id"
                        lay-filter="lease-del-detail-btn-filter">删除
                </button>
            </div>
        </div>
        <!-- 添加订单明细表格 -->
        <table id="lease-detail-table-id" lay-filter="lease-detail-table-filter"></table>
        <!-- 确认按钮 -->
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn" lay-submit lay-filter="lease-confirm-btn-filter">确认</button>
            </div>
        </div>
    </form>
</div>
<!-- 结束 添加和编辑材料弹出层 -->

<!-- 查看订单明细弹出层 -->
<div class="margin10" id="lease-view-details-div-id" style="display: none;">
    <!-- 查看订单明细表格 -->
    <table id="lease-detail-table-id-view" lay-filter="lease-detail-table-filter-view"></table>
</div>

<!--脚本-->
<!--layui框架的基础核心库-->
<script src="${APP_PATH}/layui/layui.js"></script>
<!-- 自定义js文件，表格刷新 -->
<script src="${APP_PATH}/lease/js/admin/order.js"></script>
<!--表格的编辑删除按钮-->
<script type="text/html" id="lease-operation-script-id">
    <a class="layui-btn layui-btn-xs" lay-event="lease-view-details-a-event">查看明细</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="lease-del-a-event">删除</a>
</script>
</body>
</html>