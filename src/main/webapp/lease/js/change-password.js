/**
 * 修改密码
 */
layui.use(['form', 'jquery'], function () {
    let form = layui.form;
    let $ = layui.jquery;

    /**
     * 保存按钮事件
     */
    form.on('submit(lease-save-btn-filter)', function (data) {
        let field = data.field;
        let oldPwd = field['lease-old-pwd-input-name'];
        let newPwd = field['lease-new-pwd-input-name'];
        let conPwd = field['lease-confirm-pwd-input-name'];
        if (newPwd != conPwd) {
            $('#lease-confirm-pwd-tips-div-id').text('两次密码不一致！');
            $('#lease-confirm-pwd-input-id').focus();
            form.val('lease-change-pwd-form-filter', {
                'lease-confirm-pwd-input-name': ''
            });
            return false;
        }
        $('#lease-confirm-pwd-tips-div-id').text('密码一致！');
        // 发送ajax保存新密码
        $.ajax({
            url: '/user/save-password.do'
            , type: 'post'
            , dataType: 'json'
            , data: {
                oldPassword: oldPwd
                , newPassword: newPwd
            }
            , success: function (message) {
                console.log(message);
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    layer.msg('密码修改成功！', {icon: 1});
                } else if (msgCode == message['failure']) {
                    layer.msg('密码修改失败！', {icon: 2});
                }
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
            , complete: function () {
                form.val('lease-change-pwd-form-filter', {
                    'lease-old-pwd-input-name': ''
                    , 'lease-new-pwd-input-name': ''
                    , 'lease-confirm-pwd-input-name': ''
                });
                $('#lease-old-pwd-tips-div-id').text('');
                $('#lease-new-pwd-tips-div-id').text('6-12位数字或字母');
                $('#lease-confirm-pwd-tips-div-id').text('');
            }
        });
        return false;
    });

    /**
     * 当前密码输入框获取焦点事件
     */
    $('#lease-old-pwd-input-id').focus(function () {
        $('#lease-old-pwd-tips-div-id').text('');
    });

    /**
     * 当前密码输入框失去焦点事件
     */
    $('#lease-old-pwd-input-id').blur(function () {
        console.log('当前密码输入框失去焦点');
        let oldPassword = form.val('lease-change-pwd-form-filter')['lease-old-pwd-input-name'];
        console.log(oldPassword);
        oldPassword = $.trim(oldPassword);
        if (oldPassword.length == 0) {
            console.log('请输入当前密码');
            $('#lease-old-pwd-tips-div-id').text('请输入当前密码！');
            $('#lease-old-pwd-input-id').focus();
            form.val('lease-change-pwd-form-filter', {
                'lease-old-pwd-input-name': ''
            });
            return;
        }
        console.log('已输入当前密码');

        // 发送ajax验证当前密码
        $.ajax({
            url: '/user/verify-old-password.do'
            , type: 'post'
            , dataType: 'json'
            , data: {
                oldPassword: oldPassword
            }
            , success: function (message) {
                console.log(message);
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    $('#lease-old-pwd-tips-div-id').text('密码正确！');
                } else if (msgCode == message['failure']) {
                    $('#lease-old-pwd-tips-div-id').text('密码错误！');
                    form.val('lease-change-pwd-form-filter', {
                        'lease-old-pwd-input-name': ''
                    });
                }
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
        });
    });

    /**
     * 确认密码获取焦点事件
     */
    $('#lease-confirm-pwd-input-id').focus(function () {
        let newPassword = form.val('lease-change-pwd-form-filter')['lease-new-pwd-input-name'];
        newPassword = $.trim(newPassword);
        console.log(newPassword);
        if (newPassword.length == 0) {
            $('#lease-confirm-pwd-tips-div-id').text('请输入新密码！');
            $('#lease-new-pwd-input-id').focus();
            return;
        } else if (newPassword.length < 6) {
            $('#lease-new-pwd-tips-div-id').text('新密码太短，安全性较差，请输入6-12位数字或字母！');
            $('#lease-new-pwd-input-id').focus();
            return;
        } else if (newPassword.length > 12) {
            $('#lease-new-pwd-tips-div-id').text('新密码太长，请输入6-12位数字或字母！');
            $('#lease-new-pwd-input-id').focus();
            return;
        }
        if (!new RegExp('^[0-9]|[a-z]|[0-9a-z]|{6, 12}$').test(newPassword)) {
            $('#lease-new-pwd-tips-div-id').text('密码只能包含数字或字母！');
            $('#lease-new-pwd-input-id').focus();
            form.val('lease-change-pwd-form-filter', {
                'lease-new-pwd-input-name': ''
            });
            return;
        }
        $('#lease-new-pwd-tips-div-id').text('新密码合法！');
    });
});