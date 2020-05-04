<%--
  客户主页面
  User: PengHao
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
</head>
<body class="layui-layout-body">

<!-- 页面 -->
<div class="layui-layout layui-layout-admin">
    <!--头部-->
    <jsp:include page="../heaser.jsp" flush="true"></jsp:include>

    <!--左侧导航-->
    <jsp:include page="left.jsp" flush="true"></jsp:include>

    <!-- 中间主体部分 -->
    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="text-align: center; margin-top: 250px; font-size: 60px;">欢迎使用</div>
    </div>
    <!-- 结束 中间主体部分 -->

    <!--底部-->
    <jsp:include page="../footer.jsp" flush="true"></jsp:include>

</div>
<!-- 结束 页面 -->

<!--layui框架的基础核心库-->
<script src="${APP_PATH}/layui/layui.js"></script>

</body>
</html>