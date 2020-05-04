<%--
  修改密码页面
  User: PengHao
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 中间主体部分 -->
<div class="layui-body margin10">
    <!--标题-->
    <div class="margin10">
        <h1>密码管理 <i class="layui-icon layui-icon-password" style="font-size: 30px"></i></h1>
    </div>
    <!-- 内容主体区域 -->
    <form class="layui-form margin10" lay-filter="lease-change-pwd-form-filter">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">当前密码</label>
                <div class="layui-input-inline">
                    <input id="lease-old-pwd-input-id" name="lease-old-pwd-input-name" class="layui-input"
                           type="password" required lay-verify="required" autocomplete="off">
                </div>
                <div class="layui-form-mid layui-word-aux" id="lease-old-pwd-tips-div-id"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                    <input id="lease-new-pwd-input-id" class="layui-input" name="lease-new-pwd-input-name"
                           type="password" required lay-verify="required" autocomplete="off">
                </div>
                <div id="lease-new-pwd-tips-div-id" class="layui-form-mid layui-word-aux">6-12位数字或字母</div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">确认新密码</label>
                <div class="layui-input-inline">
                    <input id="lease-confirm-pwd-input-id" name="lease-confirm-pwd-input-name" type="password"
                           class="layui-input" required lay-verify="required" autocomplete="off">
                </div>
                <div class="layui-form-mid layui-word-aux" id="lease-confirm-pwd-tips-div-id"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="lease-save-btn-filter">保存</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>
<!-- 结束 中间主体部分 -->
