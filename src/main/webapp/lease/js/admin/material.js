/**
 * material.js
 * 材料管理js文件
 * 1、刷新材料表格
 * 2020-4-19 12:42:18
 */
layui.use(['element', 'form', 'table', 'jquery', 'layer'], function () {
    // 获取layui模块对象
    let element = layui.element;
    let form = layui.form;
    let table = layui.table;
    let $ = layui.jquery;
    let layer = layui.layer;

    // 全局变量，添加材料弹出层的索引，用于添加成功后关闭弹出层
    let addIndex = 1;
    let editIndex = 1;
    let addOrEdit = 0;// 1添加2编辑
    let editMaterialId = 0;// 待编辑的材料ID

    /*********************************************************************************
     * form模块处理
     *********************************************************************************/

    /**
     * 条件查询按钮  点击事件
     * data 是表单对象
     */
    form.on('submit(lease-search-btn-filter)', function (data) {
        let material = data.field;
        console.log(material);

        // 要去掉首尾空格，空格无意义
        let searchName = $.trim(material['lease-name-input-name-search']);
        let searchModel = $.trim(material['lease-model-input-name-search']);

        // 查询后，刷新表格，展示查询到的数据
        table.reload('lease-material-render-id', {
            // 不加method会乱码
            method: 'post'
            , page: {
                curr: 1
            }
            , where: {
                name: searchName
                , model: searchModel
            }
        });
        return false;
    });

    /**
     * 确认按钮 事件
     */
    form.on('submit(lease-confirm-btn-filter)', function (data) {
        let material = data.field;
        console.log('待添加的材料为');
        console.log(material);
        if (addOrEdit == 1) {
            saveNewMaterial(material);
        } else if (addOrEdit == 2) {
            saveEditMaterial(material);
        }
    });

    /*********************************************************************************
     * table模块处理
     *********************************************************************************/

    table.render({
        /* 需要渲染的表格，使用id选择器 */
        elem: '#lease-material-table-id'
        /* 表头 */
        , cols: [[
            {field: 'id', title: 'ID', width: 70, sort: true, align: 'center'}
            , {field: 'name', title: '名称', width: 120, sort: true, align: 'center'}
            , {field: 'model', title: '型号', width: 100, sort: true, align: 'center'}
            , {field: 'specification', title: '规格', width: 100, sort: true, align: 'center'}
            , {field: 'unit', title: '单位', width: 70, align: 'center'}
            , {field: 'price', title: '单价（元/天）', width: 150, sort: true, align: 'center'}
            , {field: 'stocks', title: '库存量', width: 100, sort: true, align: 'center'}
            , {field: 'total', title: '总数量', width: 100, sort: true, align: 'center'}
            , {field: 'description', title: '描述', width: 200, align: 'center'}
            , {field: 'cost', title: '赔偿金（元）', width: 140, sort: true, align: 'center'}
            , {title: '操作', width: 120, align: 'center', toolbar: '#lease-operation-script-id'}
        ]]
        /* 数据接口，向后端发送请求获取表格数据 */
        , url: '/admin/list-material.do'
        , height: 469 /*表格容器高度*/
        , page: true /*开启分页*/
        , limits: [5, 10, 15, 20] /*每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。*/
        , title: '材料表'
        , text: {none: '暂无相关数据'}
        , id: 'lease-material-render-id'
    });

    /**
     * 表格中的 编辑，删除 按钮事件
     * tool 是工具条事件名，lease-material-table-filter 是 table 原始容器的属性 lay-filter="对应的值"
     */
    table.on('tool(lease-material-table-filter)', function (obj) {
        // 待编辑或者删除的材料
        let material = obj.data;
        console.log('待编辑或者删除的材料为');
        console.log(material);
        // 获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let layEvent = obj.event;

        if (layEvent === 'lease-del-a-event') {
            /**
             * 删除确认框
             * content: 表示确认框的内容
             * options: 图标，标题
             * yes: 确认函数
             */
            layer.confirm(
                '确定删除【' + material['name'] + '】吗？'
                , {
                    icon: 3
                    , title: '警告'
                }
                , function (index) {
                    deleteMaterial(material);
                    layer.close(index);
                }
            );
        } else if (layEvent === 'lease-edit-a-event') {
            // 编辑材料
            editMaterial(material);
        }
    });

    /*********************************************************************************
     * jquery模块处理
     *********************************************************************************/

    /**
     * 添加材料按钮  点击事件
     */
    $('#lease-add-material-btn-id').on('click', function () {
        // 弹出层，填写材料信息
        layer.open({
            type: 1
            , title: ['添加材料', 'font-size: 18px']
            , content: $('#lease-material-div-id') // 添加'添加材料'的dom对象到弹出层
            , area: ['650px', '600px'] // 默认自适应
            , resize: false // 不允许拉伸
            /**
             * 层弹出后的回调方法
             * @param domObj
             * @param index
             */
            , success: function (domObj, index) {
                // 直接给表单赋值的测试数据，避免手填
                /*
                    form.val('lease-material-form-filter', {
                        'lease-name-input-name-layer': '长杆'
                        , 'lease-model-input-name-layer': '脚手架'
                        , 'lease-specification-input-name': '1'
                        , 'lease-unit-input-name': '根'
                        , 'lease-price-input-name': '0.02'
                        , 'lease-stocks-input-name': '12'
                        , 'lease-total-input-name': '13'
                        , 'lease-cost-input-name': '3'
                        , 'lease-description-input-name': '连接两个立柱'
                    });
                */
                console.log('层弹出后的回调方法');
                addIndex = index;// 保存索引，添加成功后关闭当前层
                addOrEdit = 1;// 添加
            }
            , end: function () {
                console.log('层销毁后的回调方法');
                // 重置表单
                document.getElementById('lease-material-form-id').reset();
                // 刷新表格
                table.reload('lease-material-render-id', {
                    // 不加method会乱码
                    method: 'post'
                    , page: {
                        curr: 1
                    }
                });
            }
        });
    });

    /*********************************************************************************
     * 函数定义
     *********************************************************************************/

    /**
     * 发送请求保存新添加的材料
     * @param material 材料
     */
    function saveNewMaterial(material) {
        console.log('发送请求保存新添加的材料');
        $.ajax({
            url: '/admin/add-material.do'
            , type: 'post'
            , dataType: 'json'
            , async: false
            , data: {
                name: material['lease-name-input-name-layer']
                , model: material['lease-model-input-name-layer']
                , specification: material['lease-specification-input-name']
                , unit: material['lease-unit-input-name']
                , price: material['lease-price-input-name']
                , stocks: material['lease-stocks-input-name']
                , total: material['lease-total-input-name']
                , cost: material['lease-cost-input-name']
                , description: material['lease-description-input-name']
            }
            , success: function (message) {
                console.log(message);
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    console.log('添加成功');
                    layer.close(addIndex);
                } else if (msgCode == message['failure']) {
                    layer.msg('添加失败：' + message['msg'] + '！！！', {icon: 5});
                    console.log('添加失败');
                }
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
            , complete: function () {
                console.log('添加材料完成');
            }
        });
    }

    /**
     * 发送请求更新材料
     * @param material
     */
    function saveEditMaterial(material) {
        $.ajax({
            url: '/admin/edit-material.do'
            , type: 'post'
            , dataType: 'json'
            , data: {
                id: editMaterialId
                , name: material['lease-name-input-name-layer']
                , model: material['lease-model-input-name-layer']
                , specification: material['lease-specification-input-name']
                , unit: material['lease-unit-input-name']
                , price: material['lease-price-input-name']
                , stocks: material['lease-stocks-input-name']
                , total: material['lease-total-input-name']
                , cost: material['lease-cost-input-name']
                , description: material['lease-description-input-name']
            }
            , success: function (message) {
                console.log(message);
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    console.log('编辑成功');
                    // 刷新表格
                    table.reload('lease-material-render-id', {
                        // 不加method会乱码
                        method: 'post'
                        , page: {
                            curr: 1
                        }
                    });
                    layer.close(editIndex);
                } else if (msgCode == message['failure']) {
                    layer.msg('编辑失败：' + message['msg'] + '！！！', {icon: 5});
                    console.log('编辑失败');
                }
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
            , complete: function () {
                console.log('编辑材料完成');
            }
        });
    }

    /**
     * 发送请求删除材料
     * @param material
     */
    function deleteMaterial(material) {
        $.ajax({
            url: '/admin/delete-material.do'
            , type: 'post'
            , dataType: 'json'
            , data: material
            , success: function (message) {
                console.log(message);
                let msgCode = message['msgCode'];
                if (msgCode == message['success']) {
                    // 刷新表格
                    table.reload('lease-material-render-id', {
                        // 不加method会乱码
                        method: 'post'
                        , page: {
                            curr: 1
                        }
                    });
                    console.log('删除成功');
                } else if (msgCode == message['failure']) {
                    layer.msg('删除失败：' + message['msg'], {icon: 5});
                    console.log('删除失败');
                }
            }
            , error: function () {
                layer.msg('系统繁忙', {icon: 5});
            }
            , complete: function () {
                console.log('删除完成');
            }
        });
    }

    /**
     * 更新材料弹出层
     * @param material
     */
    function editMaterial(material) {
        console.log('function editMaterial');
        console.log(material);

        // 弹出层，编辑材料信息
        layer.open({
            type: 1
            , title: ['编辑材料', 'font-size: 18px']
            , content: $('#lease-material-div-id') // 添加'编辑材料'的dom对象到弹出层
            , area: ['650px', '600px'] // 默认自适应
            , resize: false // 不允许拉伸
            /**
             * 层弹出后的回调方法
             * @param domObj
             * @param index
             */
            , success: function (domObj, index) {
                // 将待编辑的材料信息赋值到表单中
                form.val('lease-material-form-filter', {
                    'lease-name-input-name-layer': material['name']
                    , 'lease-model-input-name-layer': material['model']
                    , 'lease-specification-input-name': material['specification']
                    , 'lease-unit-input-name': material['unit']
                    , 'lease-price-input-name': material['price']
                    , 'lease-stocks-input-name': material['stocks']
                    , 'lease-total-input-name': material['total']
                    , 'lease-cost-input-name': material['cost']
                    , 'lease-description-input-name': material['description']
                });
                console.log('层弹出后的回调方法');
                editIndex = index;// 保存索引，更新成功后关闭当前层
                addOrEdit = 2;// 编辑
                editMaterialId = material['id'];
            }
            , end: function () {
                console.log('层销毁后的回调方法');
                // 重置表单
                document.getElementById('lease-material-form-id').reset();
                // 刷新表格
                table.reload('lease-material-render-id', {
                    // 不加method会乱码
                    method: 'post'
                    , page: {
                        curr: 1
                    }
                });
            }
        });
    }

});
