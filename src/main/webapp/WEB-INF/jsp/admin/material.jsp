<%--
  材料管理页面
  Created by IntelliJ IDEA.
  User: PengHao
  Date: 2020-04-19
  Time: 11:58
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
<!--页面主体-->
<div class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
        <!--头部导航-->
        <jsp:include page="../heaser.jsp" flush="true"></jsp:include>
        <!--左侧导航-->
        <jsp:include page="left.jsp" flush="true"></jsp:include>
        <!-- 内容主体区域 -->
        <div class="layui-body margin10">
            <!--标题-->
            <div class="margin10">
                <h1>材料管理 <i class="layui-icon layui-icon-util" style="font-size: 30px"></i></h1>
            </div>
            <!-- 查询表单 -->
            <form class="layui-form" id="lease-search-form-id" lay-filter="lease-search-form-filter">
                <div class="layui-form-item">
                    <!-- 名称输入框 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="lease-name-input-name-search" placeholder="名称" autocomplete="off"
                                   class="layui-input" id="lease-name-input-id-search">
                        </div>
                    </div>
                    <!-- 型号输入框 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="lease-model-input-name-search" placeholder="型号" autocomplete="off"
                                   class="layui-input" id="lease-model-input-id-search">
                        </div>
                    </div>
                    <!-- 查询，重置 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <button class="layui-btn" lay-submit lay-filter="lease-search-btn-filter">
                                <i class="layui-icon layui-icon-search"></i>查询
                            </button>
                            <!-- 只能重置form标签的内容 -->
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                    <!-- 添加材料按钮 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <button type="button" class="layui-btn layui-btn-normal" id="lease-add-material-btn-id">
                                添加材料
                            </button>
                        </div>
                    </div>
                </div>
            </form>
            <!-- 结束 查询表单 -->
            <!-- 材料表格 -->
            <div>
                <table class="layui-hide" id="lease-material-table-id" lay-filter="lease-material-table-filter"></table>
            </div>
            <!-- 结束 材料表格 -->
        </div>
        <!-- 结束 内容主体区域 -->
        <!--底部-->
        <jsp:include page="../footer.jsp" flush="true"></jsp:include>
    </div>
</div>
<!-- 结束 页面主体 -->

<!-- 添加和编辑材料弹出层，默认不显示 -->
<div class="margin10" id="lease-material-div-id" style="display: none">
    <!-- 添加和编辑材料表单 -->
    <form class="layui-form" id="lease-material-form-id" lay-filter="lease-material-form-filter">
        <!-- 材料名称 -->
        <div class="layui-form-item">
            <label class="layui-form-label">名称</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-name-input-name-layer" lay-verify="required|lease-name-input-verify"
                       autocomplete="off" placeholder="示例：6.0米钢管" class="layui-input">
            </div>
        </div>
        <!-- 型号 -->
        <div class="layui-form-item">
            <label class="layui-form-label">型号</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-model-input-name-layer" lay-verify="required" placeholder="示例：钢管"
                       autocomplete="off" class="layui-input">
            </div>
        </div>
        <!-- 规格 -->
        <div class="layui-form-item">
            <label class="layui-form-label">规格</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-specification-input-name" lay-verify="required" autocomplete="off"
                       class="layui-input" placeholder="示例：6.0">
            </div>
        </div>
        <!-- 单位 -->
        <div class="layui-form-item">
            <label class="layui-form-label">单位</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-unit-input-name" lay-verify="required" autocomplete="off"
                       class="layui-input" placeholder="示例：根">
            </div>
        </div>
        <!-- 单价 -->
        <div class="layui-form-item">
            <label class="layui-form-label">单价</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-price-input-name" lay-verify="required|number" placeholder="示例：0.01"
                       autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">元/天</div>
        </div>
        <!-- 库存量 -->
        <div class="layui-form-item">
            <label class="layui-form-label">库存量</label>
            <div class="layui-input-inline">
                <input type="number" name="lease-stocks-input-name" lay-verify="required|number" placeholder="示例：123"
                       autocomplete="off" class="layui-input" min="0">
            </div>
        </div>
        <!-- 总数量 -->
        <div class="layui-form-item">
            <label class="layui-form-label">总数量</label>
            <div class="layui-input-inline">
                <input type="number" name="lease-total-input-name" placeholder="示例：456" autocomplete="off"
                       class="layui-input" lay-verify="required|number" min="0">
            </div>
        </div>
        <!-- 赔偿金 -->
        <div class="layui-form-item">
            <label class="layui-form-label">赔偿金</label>
            <div class="layui-input-inline">
                <input type="number" name="lease-cost-input-name" placeholder="示例：5.0" autocomplete="off"
                       class="layui-input" lay-verify="required|number" min="0">
            </div>
            <div class="layui-form-mid layui-word-aux">元</div>
        </div>
        <!-- 描述 -->
        <div class="layui-form-item">
            <label class="layui-form-label">描述</label>
            <div class="layui-input-block">
                <input type="text" name="lease-description-input-name" placeholder="示例：长度为6.0米的钢管" autocomplete="off"
                       class="layui-input" lay-verify="required">
            </div>
        </div>
        <!-- 确认，取消按钮 -->
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button type="submit" class="layui-btn" lay-submit="" lay-filter="lease-confirm-btn-filter">确认</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<!-- 结束 添加和编辑材料弹出层 -->

<!--脚本-->
<!--layui框架的基础核心库-->
<script src="${APP_PATH}/layui/layui.js"></script>
<!-- 自定义js文件，表格刷新 -->
<script src="${APP_PATH}/lease/js/admin/material.js"></script>
<!--表格的编辑删除按钮-->
<script type="text/html" id="lease-operation-script-id">
    <a class="layui-btn layui-btn-xs" lay-event="lease-edit-a-event">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="lease-del-a-event">删除</a>
</script>
</body>
</html>