/**
 * 管理员客户页面脚本
 * PengHao
 * 2020-4-15 19:50:02
 */
layui.use(['form', 'table', 'jquery'], function () {
    // 获取layui模块对象
    let form = layui.form;
    let table = layui.table;
    let $ = layui.jquery;

    /*********************************************************************************
     * form模块处理
     *********************************************************************************/

    /**
     * 条件查询按钮  点击事件
     * data 是表单对象
     */
    form.on('submit(lease-search-btn-filter)', function (data) {
        console.log(data);
        let field = data.field;
        console.log(field);

        // 要去掉首尾空格，空格无意义
        let nameField = $.trim(field['lease-name-input-name']);
        let telephoneField = $.trim(field['lease-telephone-input-name']);

        // if (nameField.length == 0 && telephoneField.length == 0) {
        //     // 重置表单，可能存在空格
        //     form.val('lease-search-form-filter', {
        //         'lease-name-input-name': '',
        //         'lease-telephone-input-name': ''
        //     });
        //     // 让姓名输入框获取焦点
        //     $('#lease-name-input-id-search').focus();
        //     // 提醒输入查询条件
        //     layer.tips('请输入查询条件', '#lease-name-input-id-search', {tips: 3});
        //     console.log('没有查询条件');
        //     return false;
        // }
        // 查询，重载

        // 重载表格，用于条件查询
        table.reload('customer-render-id', {
            // 不加method会乱码
            method: 'post',
            page: {
                curr: 1
            },
            where: {
                sName: nameField,
                sTelephone: telephoneField
            }
        });
        return false;
    });

    /*********************************************************************************
     * table模块处理
     *********************************************************************************/

    table.render({
        elem: '#customer-table-id' /*需要渲染的表格，使用id选择器*/
        , cols: [[ /*表头*/
            {field: 'id', title: 'ID', width: 100, sort: true, align: 'center'}
            , {field: 'name', title: '姓名', width: 150, sort: true, align: 'center'}
            , {field: 'telephone', title: '手机号', width: 150, align: 'center'}
            , {field: 'password', title: '密码', width: 150, align: 'center'}
            , {field: 'address', title: '地址', width: 400, align: 'center'}
            , {field: 'create_date', title: '创建日期', width: 150, align: 'center'}
            , {fixed: 'right', title: '操作', width: 190, align: 'center', toolbar: '#bar-row-render'} /*这里的toolbar值是模板元素的选择器*/
        ]]
        , url: '/customer/list-customer.do' /*数据接口，向后端发送请求获取表格数据*/
        , height: 469 /*表格容器高度*/
        // , cellMinWidth: 150 /*单元格最小宽度*/
        , page: true /*开启分页*/
        , limits: [5, 10, 15, 20] /*每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。*/
        , title: '客户表' /*定义 table 的大标题（在文件导出等地方会用到）*/
        , text: {none: '暂无相关数据'}
        , id: 'customer-render-id' /*设定容器唯一 id*/
    });

    /**
     * 表格中的 编辑，删除 按钮事件
     */
    table.on('tool(customer-table-filter)', function (obj) {
        // console.log('点击删除客户按钮');
        // console.log(obj);

        // 待删除的客户
        let customer = obj.data;
        // console.log(JSON.stringify(customer));

        // 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let layEvent = obj.event;

        if (layEvent === 'del') {
            /**
             * 删除确认框
             * content: 表示确认框的内容
             * options: 图标，标题
             * yes: 确认函数
             */
            let content = '确定删除【' + customer['name'] + '】吗？';
            layer.confirm(content
                , {icon: 3, title: '警告'}
                , function (index) {
                    deleteCustomer(customer, obj, index);
                }
            );
        } else if (layEvent === 'edit') {
            editCustomer(customer); // 编辑
        }
    });

    /*********************************************************************************
     * jquery模块处理
     *********************************************************************************/

    /**
     * 添加客户按钮  点击事件
     */
    $('#lease-add-customer-btn-id').on('click', function () {
        // 弹出层，填写客户信息
        layer.open({
            type: 2
            , title: ['添加客户', 'font-size: 18px']
            , content: '/admin/add-customer.htm' /*请求添加客户页面*/
            , area: ['700px', '340px'] /*默认自适应*/
            /*
                        , btn: ['确认', '重置', '取消']
                        , yes: function (index, addCustomerLayer) {
                            var addCustomerFormData = document.getElementById('add-customer-form');
                            console.log(addCustomerFormData);
                            console.log(JSON.stringify(addCustomerFormData));
                            layer.close(index);
                        }
                        , btn2: function (index, addCustomerLayer) {
                            /!*重置按钮事件*!/
                            layer.msg('点击重置按钮');
                            document.getElementById('add-customer-form').reset();
                            return false; /!*不关闭弹出层*!/
                        }
                        , btn3: function (index, addCustomerLayer) {
                            layer.msg('点击取消');
                        }
                        , btnAlign: 'c' /!*按钮居中*!/
            */
        });
    });

    /*********************************************************************************
     * 函数定义
     *********************************************************************************/

    /**
     * 编辑客户
     * @param customer 待编辑的客户
     */
    function editCustomer(customer) {
        // console.log('function editCustomer(' + JSON.stringify(customer) + ')');
        // 弹出编辑客户页面
        layer.open({
            type: 1  // 如果content是DOM，那么type要设为1
            , title: ['编辑客户', 'font-size: 18px']
            , content: $('#lease-edit-div-id')  // 打开编辑DOM，在customer.jsp文件中
            , area: ['700px', '340px']
            , btn: ['保存', '取消']
            /**
             * 弹出后回调
             * @param objDom 当前层dom
             * @param index 当前层索引
             */
            , success: function (objDom, index) {
                // console.log('layer.open.success回调函数');
                // console.log(objDom);
                /**
                 * 为表单输入框赋值之方法一
                 */
                // let inputArr = objDom.context.forms['lease-edit-customer-form-id'].elements;
                // /* let inputArr = objDom.context.forms[1].elements; 效果同上 */
                // inputArr[0].value = customer['name'];
                // inputArr[1].value = customer['telephone'];
                // inputArr[2].value = customer['password'];
                // inputArr[3].value = customer['address'];
                /**
                 * 为表单输入框赋值之方法二
                 */
                form.val('lease-edit-customer-form-filter', {
                    'lease-name-input-name': customer['name']
                    , 'lease-telephone-input-name': customer['telephone']
                    , 'lease-password-input-name': customer['password']
                    , 'lease-address-input-name': customer['address']
                });
                // console.log('输入框初始化完成');
            },
            /**
             * 保存按钮回调函数
             * @param index 当前层索引
             * @param objDom 当前层dom对象
             */
            yes: function (index, objDom) {
                // console.log('保存按钮回调函数');
                // console.log(index);
                // console.log(objDom);
                // 获取编辑后的表单内容
                let val = form.val('lease-edit-customer-form-filter');
                // console.log(val);
                // 修改customer对象的内容
                customer['name'] = val['lease-name-input-name'];
                customer['telephone'] = val['lease-telephone-input-name'];
                customer['password'] = val['lease-password-input-name'];
                customer['address'] = val['lease-address-input-name'];
                // console.log('customer:  ' + JSON.stringify(customer));
                saveEdit(customer, index);
                // console.log('保存');
            },
            btn2: function (index, objDom) {
                console.log('取消');
            },
            /**
             * 按钮居中
             */
            btnAlign: 'c'
        });
    }

    /**
     * 向后端发送请求删除客户
     * @param customer 要删除的客户
     * @param obj 表格对象
     * @param index 确认框索引，用于关闭确认框
     */
    function deleteCustomer(customer, obj, index) {
        // 发送请求，删除该客户
        $.ajax({
            url: '/customer/delete.do',
            type: 'post',
            dataType: 'json',
            data: customer,
            success: function (message) {
                // console.log(message);
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    // 刷新网页，后期再优化为只刷新表格
                    window.location.reload();
                    // console.log('删除成功');
                } else if (msgCode == message['failure']) {
                    layer.msg('删除失败：' + message['msg'], {icon: 5});
                    // console.log('删除失败');
                }
            },
            error: function () {
                layer.msg('系统繁忙', {icon: 5});
            },
            complete: function () {
                // 关闭确认框
                layer.close(index);
                // console.log('删除完成');
            }
        });
    }

    /**
     * 向后端发送请求更新客户
     * @param customer 待更新的客户
     * @param index 当前层索引
     */
    function saveEdit(customer, index) {
        // 发送请求到后端，更新数据库
        $.ajax({
            url: '/customer/edit-customer.do',
            type: 'post',
            dataType: 'json',
            data: customer,
            success: function (message) {
                // console.log(message);
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    layer.close(index); // 关闭当前层
                    window.location.reload(); // 刷新网页，后期再优化为只刷新表格
                    // console.log('编辑成功');
                } else if (msgCode == message['failure']) {
                    layer.msg('编辑失败：' + message['msg'], {icon: 5});
                    // console.log('编辑失败');
                }
            },
            error: function () {
                layer.msg('系统繁忙', {icon: 5});
            },
            complete: function () {
                // console.log('编辑完成');
            }
        });
    }

});
