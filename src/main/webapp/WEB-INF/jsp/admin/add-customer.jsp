<%--
  添加客户页面
  PengHao
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>

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
<!--添加用户弹出层-->
<div class="margin10">
    <form class="layui-form" id="lease-add-customer-form-id">
        <!--姓名-->
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-name-input-name" lay-verify="required|lease-name-input-verify"
                       placeholder="示例：彭豪"
                       autocomplete="off" class="layui-input" id="lease-name-input-id-add">
            </div>
        </div>
        <!--手机号-->
        <div class="layui-form-item">
            <label class="layui-form-label">手机号</label>
            <div class="layui-input-inline">
                <input type="text" name="lease-telephone-input-name" required lay-verify="required|phone"
                       placeholder="示例：13016405502" autocomplete="off" class="layui-input">
            </div>
        </div>
        <!--密码-->
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="lease-password-input-name" required lay-verify="required" value="123456"
                       placeholder="示例：123456"
                       autocomplete="off"
                       class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">初始密码：123456</div>
        </div>
        <!--地址-->
        <div class="layui-form-item">
            <label class="layui-form-label">地址</label>
            <div class="layui-input-inline" style="width: 300px">
                <input type="text" name="lease-address-input-name" required lay-verify="required"
                       placeholder="示例：方家咀乡方家咀村1组1号"
                       autocomplete="off"
                       class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">xxx乡/镇xxx村xxx组xxx号</div>
        </div>
        <!--按钮-->
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="lease-confirm-btn-filter">确认</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>

<!--脚本-->
<!--layui框架的基础核心库-->
<script src="${APP_PATH}/layui/layui.js"></script>
<!--自定义脚本-->
<script src="${APP_PATH}/lease/js/admin/add-customer.js"></script>

</body>

</html>
