/**
 * order.js
 * 订单管理js文件
 * 2020-4-20 18:21:59
 */
layui.use(['element', 'form', 'table', 'jquery', 'layer', 'laydate'], function () {
    // 获取layui模块对象
    let element = layui.element;
    let form = layui.form;
    let table = layui.table;
    let $ = layui.jquery;
    let layer = layui.layer;
    let laydate = layui.laydate;

    // 全局变量，添加材料弹出层的索引，用于添加成功后关闭弹出层
    let addIndex = 1; // 添加订单弹出层
    let orderID; // 订单编号
    let materials; // 添加订单页面的材料选择框的材料数据
    let orderDetails = [];

    let startDate;
    let endDate;

    /*********************************************************************************
     * form模块处理
     *********************************************************************************/

    /**
     * 条件查询按钮  点击事件
     * data 是表单对象
     */
    form.on('submit(lease-search-btn-filter)', function (data) {
        // console.log(data);

        // 要去掉首尾空格，空格无意义
        let searchName = $.trim(data.field['lease-name-input-name-search']);
        // console.log(searchName);

        // 查询后，刷新表格，展示查询到的数据
        table.reload('lease-order-render-id', {
            // 不加method会乱码
            method: 'post'
            , page: {
                curr: 1
            }
            , where: {
                name: searchName
                , start: startDate
                , end: endDate
            }
        });
        return false;
    });

    /**
     * 点击 确认按钮
     */
    form.on('submit(lease-confirm-btn-filter)', function (data) {
        // console.log('点击确认按钮');
        let field = data.field;
        let order = {};
        order.id = field['lease-id-input-name'];
        order.cid = field['lease-customer-select-name'];
        order.type = field['lease-type-input-name'] == 100 ? '租' : '还'
        order.creationDate = field['lease-date-input-name'];
        for (let i = 0; i < orderDetails.length; ++i) {
            order['orderDetails[' + i + '].oid'] = order.id;
            order['orderDetails[' + i + '].mid'] = orderDetails[i].mid;
            order['orderDetails[' + i + '].amount'] = orderDetails[i].amount;
        }
        console.log(order);

        // 将订单数据发送到后端保存
        $.ajax({
            url: '/order/add-order.do'
            , type: 'post'
            , dataType: 'json'
            , data: order
            , success: function (message) {
                console.log(message);
                let msgCode = message.msgCode;
                if (msgCode == message.success) {
                    console.log('订单添加成功');
                    layer.close(addIndex);
                } else if (msgCode == message.failure) {
                    console.log('订单添加失败');
                }
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
            , complete: function () {
                console.log('订单添加完成');
            }
        });

        return false; // 必须要加返回值
    });

    /*********************************************************************************
     * table模块处理
     *********************************************************************************/

    /**
     * 订单管理页面的订单表格
     */
    table.render({
        elem: '#lease-order-table-id' // 需要渲染的表格ID
        , cols: [[                    // 表头
            {title: '序号', type: 'numbers', fixed: 'left'}
            , {field: 'id', title: '订单编号', width: 180, sort: true, align: 'center'}
            // 嵌套数据需要进行解析
            , {field: 'name', title: '客户', width: 120, align: 'center', templet: '<div>{{d.customer.name}}</div>'}
            , {
                field: 'telephone',
                title: '手机号',
                width: 120,
                align: 'center',
                templet: '<div>{{d.customer.telephone}}</div>'
            }
            , {field: 'type', title: '类型（租/还）', width: 150, align: 'center'}
            , {
                field: 'detailAmount',
                title: '订单明细数',
                width: 150,
                align: 'center',
                templet: '<div>{{d.orderDetails.length}}</div>'
            }
            // 自定义模板将从后端获取的时间转换成需要展示的格式
            , {
                field: 'creationDate',
                title: '创建日期',
                width: 160,
                align: 'center',
                field: 'creationDate', title: '创建日期', width: 160, align: 'center', templet: function (d) {
                    // console.log(d.creationDate);
                    return dateFormat(d.creationDate);
                }
            }
            , {title: '操作', width: 140, align: 'center', toolbar: '#lease-operation-script-id'}
        ]]
        , url: '/order/list-order.do' // 数据接口，向后端发送请求获取表格数据
        , height: 469
        , page: true                  // 开启分页
        , limits: [5, 10, 15, 20]     // 每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]
        , title: '订单表'
        , text: {none: '暂无相关数据'}
        , id: 'lease-order-render-id'
        , done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            console.log(res);

            //得到当前页码
            console.log(curr);

            //得到数据总量
            console.log(count);
        }
    });

    /**
     * 添加订单 弹出层 的 订单明细 表格
     */
    table.render({
        elem: '#lease-detail-table-id' // 需要渲染的表格ID
        , cols: [[                    // 表头
            {type: 'radio', fixed: 'left'}
            , {field: 'mid', title: '材料编号', width: 120, align: 'center'}
            , {field: 'name', title: '名称', width: 120, align: 'center'}
            , {field: 'model', title: '型号', width: 120, align: 'center'}
            , {field: 'specification', title: '规格', width: 120, align: 'center'}
            , {field: 'amount', title: '数量', width: 120, align: 'center'}
        ]]
        , data: orderDetails // 此变量是动态添加的
        , height: 300
        , limit: Number.MAX_VALUE // 解决不分页只显示10条的问题
        , title: '订单明细表'
        , text: {none: '暂无相关数据'}
        , id: 'lease-detail-render-id'
    });

    /**
     * 查看订单明细 弹出层 的 订单明细 表格
     */
    table.render({
        elem: '#lease-detail-table-id-view' // 需要渲染的表格ID
        , cols: [[                    // 表头
            {title: '序号', type: 'numbers', fixed: 'left'}
            , {field: 'mid', title: '材料编号', width: 120, align: 'center'}
            , {field: 'name', title: '名称', width: 120, align: 'center'}
            , {field: 'model', title: '型号', width: 120, align: 'center'}
            , {field: 'specification', title: '规格', width: 100, align: 'center'}
            , {field: 'amount', title: '数量', width: 100, align: 'center'}
        ]]
        , data: orderDetails // 此变量是动态添加的
        , height: 310
        , limit: Number.MAX_VALUE // 解决不分页只显示10条的问题
        , title: '订单明细表'
        , text: {none: '暂无相关数据'}
        , id: 'lease-detail-render-id-view'
    });

    /**
     * 表格中的 查看明细，删除 按钮事件
     * tool 是工具条事件名，lease-order-table-filter 是 table 原始容器的属性 lay-filter="对应的值"
     */
    table.on('tool(lease-order-table-filter)', function (obj) {
        let data = obj.data;
        // 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let layEvent = obj.event;

        if (layEvent === 'lease-del-a-event') {
            /**
             * 删除确认框
             * content: 表示确认框的内容
             * options: 图标，标题
             * yes: 确认函数
             */
            let content = '确定删除【' + data.customer.name + '】的订单【' + data.id + '】吗？';
            layer.confirm(content
                , {icon: 3, title: '警告'}
                , function (index) {
                    // console.log('yes');
                    deleteOrder(data); // 发送请求删除订单
                    layer.close(index);// 关闭弹出层
                }
            );
        } else if (layEvent === 'lease-view-details-a-event') {
            console.log('点击查看明细');
            // console.log(obj.data);

            // 将订单明细数据赋值到表格data变量中
            let orderDetails = obj.data.orderDetails;
            let orderItems = [];
            let item;
            for (let i = 0; i < orderDetails.length; ++i) {
                item = {
                    mid: orderDetails[i].mid
                    , name: orderDetails[i].material.name
                    , model: orderDetails[i].material.model // 型号
                    , specification: orderDetails[i].material.specification // 规格
                    , amount: orderDetails[i].amount
                };
                orderItems.push(item);
            }
            console.log(orderItems);

            // 弹出层，查看订单明细
            layer.open({
                type: 1
                , title: ['订单明细', 'font-size: 18px']
                , content: $('#lease-view-details-div-id') // 绑定'订单明细'的dom对象
                , area: ['650px', '400px'] // 默认自适应，宽，高
                , resize: false // 不允许拉伸
                , shadeClose: true // 点击遮罩关闭
                /**
                 * 层弹出后的回调方法
                 * @param domObj
                 * @param index
                 */
                , success: function (domObj, index) {
                    console.log('层弹出后的回调方法');

                    // 刷新表格
                    table.reload('lease-detail-render-id-view', {
                        data: orderItems
                    });
                }
                , end: function () {
                    console.log('层销毁后的回调方法');
                }
            });
        }
    });

    /*********************************************************************************
     * jquery模块处理
     *********************************************************************************/

    /**
     * 添加订单按钮  点击事件
     */
    $('#lease-add-order-btn-id').on('click', function () {
        // 从后端获取生成的订单编号
        $.ajax({
            url: '/order/create-order-id.do'
            , type: 'post'
            , dataType: 'json'
            , async: false
            , success: function (message) {
                orderID = message.msg;
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
        });
        // console.log(orderID);

        // 截取时间作为订单的创建日期
        let creationDate = parseDate(orderID.substring(0, 14));
        // console.log(creationDate);

        // 获取客户列表
        let customers;
        $.ajax({
            url: '/admin/list-select-customer.do'
            , type: 'post'
            , dataType: 'json'
            , async: false
            , success: function (data) {
                customers = data.data;
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
            , complete: function () {
                // console.log('获取客户列表完成');
            }
        });
        // console.log(customers);

        // 获取可以添加的材料列表
        $.ajax({
            url: '/admin/list-select-material.do'
            , type: 'post'
            , dataType: 'json'
            , async: false
            , success: function (data) {
                materials = data.data;
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
            , complete: function () {
                // console.log('获取材料列表完成');
            }
        });
        // console.log(materials);

        // 弹出层，填写订单信息
        layer.open({
            type: 1
            , title: ['添加订单', 'font-size: 18px']
            , content: $('#lease-order-div-id') // 绑定'添加订单'的dom对象
            , area: ['750px', '700px'] // 默认自适应，宽，高
            , resize: false // 不允许拉伸
            /**
             * 层弹出后的回调方法
             * @param domObj
             * @param index
             */
            , success: function (domObj, index) {
                // console.log('层弹出后的回调方法');
                // 给表单赋初值，订单编号和创建日期
                form.val('lease-order-form-filter', {
                    'lease-id-input-name': orderID
                    , 'lease-date-input-name': creationDate
                });

                // 将客户信息添加到下拉选择框中
                $('#lease-id-select-id').append(new Option());
                $.each(customers, function (index, item) {
                    let text = item.id;
                    text += ' - ' + item.name;
                    text += ' - ' + item.telephone;
                    $('#lease-id-select-id').append(new Option(text, item.id));
                });

                // 将材料信息添加到下拉选择框中
                $('#lease-material-select-id').append(new Option());
                $.each(materials, function (index, item) {
                    let text = item.id;
                    text += ' - ' + item.name;
                    text += ' - ' + item.model;
                    text += ' - ' + item.specification;
                    $('#lease-material-select-id').append(new Option(text, item.id));
                });

                form.render();

                addIndex = index;// 保存索引，添加成功后关闭当前层
                // addOrEdit = 1;// 添加
            }
            , end: function () {
                // console.log('层销毁后的回调方法');

                // 清空客户下拉选择框数据
                $('#lease-id-select-id').empty();

                // 清空材料下拉选择框
                $('#lease-material-select-id').empty();

                // 清空明细表格
                orderDetails = [];
                table.reload('lease-detail-render-id', {
                    data: orderDetails
                });

                // 重置添加订单表单
                document.getElementById('lease-order-form-id').reset();

                // 刷新表格
                table.reload('lease-order-render-id', {
                    method: 'post' // 不加method会乱码
                    , page: {
                        curr: 1
                    }
                });
            }
        });
    });

    /**
     * 点击 添加 明细 按钮
     */
    $('#lease-add-detail-btn-id').on('click', function () {
        // console.log('点击添加明细按钮');

        // 添加订单 表单数据
        let order = form.val('lease-order-form-filter');
        // console.log(order);

        // 获取选中的材料信息
        let text = $('#lease-material-select-id option:selected').text();
        if (text.length == 0) {
            layer.tips('请选择材料', '#lease-material-div-id', {
                tips: [1, '#3595CC']
                , time: 2000
            });
            return;
        }
        // console.log(text);

        let array = text.split(' - ');// 分割成数组：编号，名称，型号，规格
        // console.log(array);

        // 获取数量
        let amount = order['lease-amount-input-name'];
        if (amount == 0) {
            layer.tips('请输入数量', '#lease-amount-input-id', {
                tips: [1, '#3595CC']
                , time: 2000
            });
            return;
        }

        // 获取库存量
        let stocks = 0;
        let currentMaterial = 0;
        for (let i = 0; i < materials.length; ++i) {
            if (materials[i]['id'] == array[0]) {
                stocks = materials[i]['stocks'];
                currentMaterial = i;
                break;
            }
        }

        // 库存不足，无法添加，可能出现死锁，
        // 因为有可能同时有两个人需要对方正在还的材料，而库存不足的情况
        if (amount > stocks) {
            layer.msg(array[1] + '库存不足，无法添加');
            return;
        } else {
            // 库存足够的情况下，减少库存，这只是在前端减少，最后提交依然要在后端判断
            materials[currentMaterial]['stocks'] = stocks - amount;
            // console.log(materials[currentMaterial]);
        }

        // 判断明细中是否已经存在该材料
        let orderItem = null;
        for (let i = 0; i < orderDetails.length; ++i) {
            if (orderDetails[i].mid == array[0]) {
                orderItem = orderDetails[i];
                break;
            }
        }

        if (orderItem != null) {
            // 存在，累加数量即可
            orderItem.amount = Number.parseInt(orderItem.amount) + Number.parseInt(amount);
        } else {
            // 不存在，添加到表格data数组中
            orderItem = {
                mid: array[0]
                , name: array[1]
                , model: array[2] // 型号
                , specification: array[3] // 规格
                , amount: amount
            };
            // 添加到 订单明细 数组 的头部
            orderDetails.unshift(orderItem);
        }
        // 刷新表格
        table.reload('lease-detail-render-id', {
            data: orderDetails
        });
    });

    /**
     * 点击 删除 明细 按钮
     */
    $('#lease-del-detail-btn-id').on('click', function () {
        // 获取表格单选框选择的结果，注意：data是一维数组
        let data = table.checkStatus('lease-detail-render-id').data;
        // 如果没有选择，弹框提示用户
        if (data.length == 0) {
            layer.msg('请选择要删除的行');
            return;
        }
        let material = data[0]; // 获取要删除的材料信息

        // 将此行从数据表格中删除
        for (let i = 0; i < orderDetails.length; ++i) {
            console.log(orderDetails[i].mid);
            if (material.mid == orderDetails[i].mid) {
                orderDetails.splice(i, 1); // 删除数组中从索引i开始的1个元素
                break;
            }
        }
        // 将库存数量重新恢复
        for (let i = 0; i < materials.length; ++i) {
            if (material.mid == materials[i].id) {
                materials[i].stocks += Number.parseInt(material.amount);
                break;
            }
        }
        // 刷新表格
        table.reload('lease-detail-render-id', {
            data: orderDetails
        });
    });

    /*********************************************************************************
     * laydate模块处理
     *********************************************************************************/

    /**
     * 开始日期
     */
    laydate.render({
        elem: '#lease-start-date-input-id'
        , done: function (value, date) {
            // console.log(value);
            // console.log(date);
            let s = new Date(value);
            let e = new Date(endDate);
            if (s > e) {
                layer.msg('开始时间不能晚于结束时间！');
                form.val('lease-search-form-filter', {
                    'lease-start-date-input-name': ''
                });
            } else {
                startDate = value;
            }
        }
    });

    /**
     * 结束日期
     */
    laydate.render({
        elem: '#lease-end-date-input-id'
        , done: function (value, date) {
            let s = new Date(startDate);
            let e = new Date(value);
            if (e < s) {
                layer.msg('结束时间不能早于开始时间！');
                form.val('lease-search-form-filter', {
                    'lease-end-date-input-name': ''
                });
            } else {
                endDate = value;
            }
        }
    });

    /*********************************************************************************
     * 函数定义
     *********************************************************************************/

    /**
     * 发送请求删除订单
     * @param order 要删除的订单
     */
    function deleteOrder(order) {
        console.log(order);

        let orderDetails = order.orderDetails;

        let ajaxOrder = {};
        ajaxOrder.id = order.id; // 根据id删除订单
        ajaxOrder.type = order.type; // 根据类型，判断是增加库存还是减少库存

        for (let i = 0; i < orderDetails.length; ++i) {
            ajaxOrder['orderDetails[' + i + '].id'] = orderDetails[i].id; // 根据明细id删除明细
            ajaxOrder['orderDetails[' + i + '].mid'] = orderDetails[i].mid; // 根据材料id，更新材料
            ajaxOrder['orderDetails[' + i + '].amount'] = orderDetails[i].amount; // 根据材料数量，更新库存
        }

        $.ajax({
            url: '/order/delete.do'
            , type: 'post'
            , dataType: 'json'
            , data: ajaxOrder
            , success: function (message) {
                let msgCode = message.msgCode;
                if (msgCode == message.success) {
                    // console.log('订单删除成功');
                    layer.msg('订单删除成功', {icon: 1});
                    // 刷新表格
                    table.reload('lease-order-render-id', {
                        method: 'post'
                        , page: {
                            curr: 1
                        }
                    });
                } else if (msgCode == message.failure) {
                    layer.msg('订单删除失败：' + message.msg, {icon: 2});
                }
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
        });
    }

    /**
     * 日期格式化函数
     * @param date 时间戳
     * @returns {string | *} 格式化后的日期字符串
     */
    function dateFormat(date) {
        // layui.util.toDateString(date, format)   date是时间戳，format是日期字符格式，类似Java的SimpleDateFormat
        // let result = layui.util.toDateString(date);
        // console.log(result);
        // return result;
        let d = new Date(date);
        d.setHours(d.getHours() - 14);// 搞不懂为什么前后端相差14小时，只能用这个死办法

        let year = d.getFullYear();
        let month = d.getMonth() + 1;
        month = month < 10 ? '0' + month : month;
        let day = d.getDate();
        day = day < 10 ? '0' + day : day;
        let hour = d.getHours();
        hour = hour < 10 ? "0" + hour : hour;
        let minute = d.getMinutes();
        minute = minute < 10 ? '0' + minute : minute;
        let second = d.getSeconds();
        second = second < 10 ? '0' + second : second;

        let result = year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
        return result;
    }

    function parseDate(date) {
        let result = date.substring(0, 4);
        result += '-';
        result += date.substring(4, 6);
        result += '-';
        result += date.substring(6, 8);
        result += ' ';
        result += date.substring(8, 10);
        result += ':';
        result += date.substring(10, 12);
        result += ':';
        result += date.substring(12, 14);
        return result;
    }

});
