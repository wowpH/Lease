<%--
  管理员后台管理，客户管理页面
  Created by IntelliJ IDEA.
  User: PengHao
  Date: 2020-04-12
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>方咀租赁站</title>
    <!--页面图标-->
    <link rel="short icon" href="${APP_PATH}/lease/images/favicon.png">
    <!--layui框架的核心样式文件-->
    <link rel="stylesheet" href="${APP_PATH}/layui/css/layui.css">

    <!--内联样式-->
    <style type="text/css">
        .margin10 {
            margin: 10px;
        }
    </style>

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
                <h1>客户管理 <i class="layui-icon layui-icon-user" style="font-size: 30px"></i></h1>
            </div>

            <!-- 查询表单 -->
            <form class="layui-form" id="lease-search-form-id" lay-filter="lease-search-form-filter">
                <div class="layui-form-item">
                    <!--layui-inline定义外层行内-->
                    <!--姓名输入框-->
                    <div class="layui-inline">
                        <!--layui-input-inline定义内层行内-->
                        <div class="layui-input-inline">
                            <input type="text" name="lease-name-input-name" placeholder="姓名" autocomplete="off"
                                   class="layui-input" id="lease-name-input-id-search">
                        </div>
                    </div>
                    <!--手机号输入框-->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <input type="text" name="lease-telephone-input-name" placeholder="手机号" autocomplete="off"
                                   class="layui-input" id="lease-telephone-input-id-search">
                        </div>
                    </div>
                    <!--查询，重置，添加按钮-->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <button class="layui-btn" lay-submit lay-filter="lease-search-btn-filter">
                                <i class="layui-icon layui-icon-search"></i>查询
                            </button>
                            <!-- 只能重置form标签的内容 -->
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                    <!-- 添加客户按钮 -->
                    <div class="layui-inline">
                        <div class="layui-input-inline">
                            <button type="button" class="layui-btn layui-btn-normal" id="lease-add-customer-btn-id">添加客户
                            </button>
                        </div>
                    </div>
                </div>
            </form>
            <!-- 结束 查询表单 -->

            <!-- 数据表格 -->
            <div>
                <table class="layui-hide" id="customer-table-id" lay-filter="customer-table-filter"></table>
            </div>
        </div>
        <!-- 结束 内容主体区域 -->

        <!--底部-->
        <jsp:include page="../footer.jsp" flush="true"></jsp:include>

    </div>
</div>
<!--结束 页面主体-->

<!--编辑客户弹出层-->
<div class="margin10" style="display: none" id="lease-edit-div-id">
    <form class="layui-form" id="lease-edit-customer-form-id" lay-filter="lease-edit-customer-form-filter">
        <!--姓名-->
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-name-input-name" required lay-verify="required" placeholder="示例：彭豪"
                       autocomplete="off" class="layui-input" id="lease-name-input-id-edit">
            </div>
        </div>
        <!--手机号-->
        <div class="layui-form-item">
            <label class="layui-form-label">手机号</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-telephone-input-name" required lay-verify="required|phone"
                       placeholder="示例：13016405502" autocomplete="off" class="layui-input"
                       id="lease-telephone-input-id-edit">
            </div>
        </div>
        <!--密码-->
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="lease-password-input-name" required lay-verify="required" value="123456"
                       placeholder="示例：123456" autocomplete="off" class="layui-input" id="lease-password-input-id-edit">
            </div>
            <div class="layui-form-mid layui-word-aux">初始密码：123456</div>
        </div>
        <!--地址-->
        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" name="lease-address-input-name" required lay-verify="required"
                       placeholder="示例：方家咀乡方家咀村1组1号" autocomplete="off" class="layui-input"
                       id="lease-address-input-id-edit">
            </div>
            <div class="layui-form-mid layui-word-aux">xxx乡/镇xxx村xxx组xxx号</div>
        </div>
        <!--按钮-->
        <%--
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="lease-confirm-btn-filter">确认</button>
                                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                            </div>
                        </div>
        --%>
    </form>
</div>
<!--结束：编辑客户弹出层-->

<!--脚本-->
<!--layui框架的基础核心库-->
<script src="${APP_PATH}/layui/layui.js"></script>
<!--自定义js文件，表格刷新-->
<script src="${APP_PATH}/lease/js/admin/customer.js"></script>
<!--表格的编辑删除按钮-->
<script type="text/html" id="bar-row-render">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

</body>

</html>