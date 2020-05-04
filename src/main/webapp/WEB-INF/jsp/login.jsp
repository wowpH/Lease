<%--
  登录页面
  PengHao
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>登录-方咀租赁站</title>
    <!--页面图标-->
    <link rel="short icon" href="${APP_PATH}/lease/images/favicon.png">
    <!--layui框架的核心样式文件-->
    <link rel="stylesheet" href="${APP_PATH}/layui/css/layui.css">
    <!--layuiadmin模板的样式文件-->
    <link rel="stylesheet" href="${APP_PATH}/layuiadmin/css/admin.css">
    <link rel="stylesheet" href="${APP_PATH}/layuiadmin/css/login.css">
</head>
<body>

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <!--登录主体-->
    <div class="layadmin-user-login-main">
        <!--标题-->
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>方咀租赁站</h2>
            <p>登录查看自己的租赁信息</p>
        </div>
        <!--登录表单-->
        <form class="layadmin-user-login-box layadmin-user-login-body layui-form" lay-filter="lease-login-form-filter">
            <!--手机号-->
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username"
                       for="LAY-user-login-telephone"></label>
                <input type="text" name="telephone" id="LAY-user-login-telephone"
                       lay-verify="required|lease_telephone_input_verify"
                       placeholder="手机号" class="layui-input">
            </div>
            <!--密码-->
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password"
                       for="LAY-user-login-password"></label>
                <!--密码输入框-->
                <input type="password" name="password" id="LAY-user-login-password"
                       lay-verify="required|lease_password_input_verify"
                       placeholder="密码" class="layui-input">
            </div>
            <!--忘记密码-->
            <div class="layui-form-item" style="margin-bottom: 20px;">
                <input type="checkbox" name="remember" lay-skin="primary" title="记住密码" disabled="disabled">
                <a id="id-forget-password" class="layadmin-user-jump-change layadmin-link"
                   style="margin-top: 7px;">忘记密码？</a>
            </div>
            <!--登录按钮-->
            <div class="layui-form-item">
                <button id="lease-login-btn-id" class="layui-btn layui-btn-fluid" lay-submit
                        lay-filter="LAY-user-login-submit">
                    登 录
                </button>
            </div>
            <!--注册-->
            <div class="layui-trans layui-form-item layadmin-user-login-other">
                <label>社交账号登录：暂未开通</label>
                <a id="a-id-register" class="layadmin-user-jump-change layadmin-link">注册帐号</a>
            </div>
        </form>
    </div>

    <!--页脚-->
    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2020 <a href="" target="_blank">方咀租赁站</a></p>
        <p>
            <span><a href="https://github.com/wowpH/Lease" target="_blank">GitHub</a></span>
            <span><a href="https://blog.csdn.net/pfdvnah" target="_blank">CSDN</a></span>
            <span><a href="https://www.layui.com" target="_blank">Layui</a></span>
            <span><a href="https://www.layui.com/admin/pro" target="_blank">LayuiAdmin</a></span>
        </p>
    </div>
</div>

<!--layui框架的基础核心库-->
<script src="${APP_PATH}/layui/layui.js"></script>
<!--lease项目自定义的登录js文件-->
<script src="${APP_PATH}/lease/js/login.js"></script>

</body>
</html>
