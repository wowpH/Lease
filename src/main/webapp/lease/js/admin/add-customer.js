/**
 * 添加用户
 * PengHao
 * 2020-4-16 21:41:24
 */
layui.use(['form', 'layer', 'jquery'], function () {

    let form = layui.form;
    let layer = layui.layer;
    let $ = layui.jquery;

    /*确认按钮事件*/
    form.on('submit(lease-confirm-btn-filter)', function (data) {
        /*lease-confirm-btn-filter是确认按钮的lay-filter值*/
        /*data是form对象，data.field是form的各字段的键值对*/
        let field = data.field;
        // console.log('表单数据为：' + field);

        /*发送请求将用户添加到后端数据库*/
        $.ajax({
            url: '/customer/add-customer.do',
            type: 'post',
            dataType: 'json',
            data: {
                /*转换成后端对应的名字，方便封装*/
                name: field['lease-name-input-name'],
                telephone: field['lease-telephone-input-name'],
                password: field['lease-password-input-name'],
                address: field['lease-address-input-name'],
            },
            success: function (message) {
                /*成功回调函数，message是从后端获取的Message类在前端转换为JSON后的对象*/
                /*获取状态码*/
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    layer.msg('添加成功');
                    /*刷新父窗口，目的是：刷新表格，将新添加的用户添加到表格中*/
                    window.parent.location.reload();
                    /*关闭添加用户弹出层*/
                    parent.layer.close(index);
                } else if (msgCode == message['failure']) {
                    layer.msg('添加失败！！！\</br>可能的原因：1、手机号重复');
                }
            },
            error: function () {
                layer.msg('系统繁忙', {icon: 5});
            },
            complete: function () {
                console.log('完成');
            }
        });
        return false;
    });

});
