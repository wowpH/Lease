/**
 * 登录脚本
 * PengHao
 */
layui.use(['layer', 'form', 'jquery'], function () {
    // 获取加载模块对象
    var layer = layui.layer;
    var $ = layui.jquery;
    var form = layui.form;

    // 表单验证
    form.verify({
        // 验证手机号
        lease_telephone_input_verify: function (value, item) {
            if (value != 'admin') {
                if (!new RegExp('^[0-9]{11}$').test(value)) {
                    // 清空手机号输入框
                    form.val('lease-login-form-filter', {
                        'telephone': ''
                    });
                    return '手机号不合法！';
                }
            }
        }
        // 验证密码
        , lease_password_input_verify: function (value, item) {
            if (!new RegExp('^[a-zA-Z0-9\\s·]+$').test(value)) {
                return '密码只能是字母或数字';
            }
            if (value.length > 12) {
                // 清空密码输入框
                form.val('lease-login-form-filter', {
                    'password': ''
                });
                return '密码不能超过12位';
            }
        }
    });

    // 登录
    form.on('submit(LAY-user-login-submit)', function (data) {
        let loading; // 加载中
        let formVal = form.val('lease-login-form-filter');
        let ajaxData = {
            telephone: formVal.telephone
            , password: formVal.password
        };
        $.ajax({
            url: '/login/check.do'
            , type: 'post'
            , dataType: 'json'
            , data: ajaxData
            , beforeSend: function () {
                $('#lease-login-btn-id').text('登 录 中');
                loading = layer.load(2); // 显示加载中
            },
            success: function (message) {
                let msgCode = message.msgCode;
                if (msgCode == message.admin) {
                    location.href = '/admin/index.htm'; // 跳转到管理员主页
                } else if (msgCode == message.customer) {
                    location.href = '/customer/index.htm'; // 跳转到客户主页
                } else if (msgCode == message.error) {
                    // 清空输入框
                    form.val('lease-login-form-filter', {
                        'telephone': ''
                        , 'password': ''
                    });
                    // 弹出层提示密码错误
                    // console.log('密码错误');
                    layer.msg('手机号或密码错误', {
                        icon: 5,
                        offset: '40px'
                    });
                }
            },
            error: function () {
                layer.msg('系统繁忙', {icon: 5});
            },
            complete: function () {
                // console.log('登录完成');
                $('#lease-login-btn-id').text('登 录');
                // 关闭加载中弹出层
                layer.close(loading);
            }
        });
        return false;
    });

    // 忘记密码，鼠标滑过事件
    $('#id-forget-password').hover(
        function () {
            layer.tips('联系管理员：130 1640 5502', this, {
                tips: [1, '#3595CC'],
                time: 2000
            });
        }
        , function () {
        }
    );

    // 注册账号，鼠标滑过事件
    $('#a-id-register').hover(
        function () {
            layer.tips('无法注册，哈哈哈！</br>注：管理员手机号是用户名：admin', this, {
                tips: [1, '#3595CC'],
                time: 2000
            });
        }
        , function () {
        }
    );

});
