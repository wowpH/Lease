/**
 * 我的订单脚本文件
 * 2020-4-27 16:21:01
 */
layui.use(['element', 'form', 'table', 'jquery', 'layer', 'laydate'], function () {
    // 获取layui模块对象
    let table = layui.table;
    let $ = layui.jquery;
    let layer = layui.layer;

    // 全局变量，添加材料弹出层的索引，用于添加成功后关闭弹出层
    let orderDetails = [];

    /*********************************************************************************
     * table模块处理
     *********************************************************************************/

    /**
     * 我的订单页面的订单表格
     */
    table.render({
        elem: '#lease-order-table-id' // 需要渲染的表格ID
        , cols: [[                    // 表头
            {title: '序号', type: 'numbers', fixed: 'left'}
            , {field: 'id', title: '订单编号', width: 180, sort: true, align: 'center'}
            , {field: 'type', title: '类型（租/还）', width: 150, align: 'center'}
            // 嵌套数据需要进行解析
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
            , {title: '操作', fixed: 'right', width: 140, align: 'center', toolbar: '#lease-operation-script-id'}
        ]]
        , url: '/customer/list-order.do' // 数据接口，向后端发送请求获取表格数据
        , height: 469
        , page: true                  // 开启分页
        , limits: [5, 10, 15, 20]     // 每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]
        , title: '订单表'
        , text: {none: '暂无相关数据'}
        , id: 'lease-order-render-id'
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
     * 表格中的 查看明细 按钮事件
     * tool 是工具条事件名，lease-order-table-filter 是 table 原始容器的属性 lay-filter="对应的值"
     */
    table.on('tool(lease-order-table-filter)', function (obj) {
        let data = obj.data;
        // 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let layEvent = obj.event;

        if (layEvent === 'lease-view-details-a-event') {
            console.log('点击查看明细');
            console.log(obj.data);

            // 将订单明细数据赋值到表格data变量中
            let orderDetails = obj.data.orderDetails;
            console.log(orderDetails);
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
     * 函数定义
     *********************************************************************************/

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
