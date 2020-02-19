/**
 * 用户管理
 */
var pageCurr;
var form;
$(function () {
    layui.use(['table', 'form'], function () {
        var table = layui.table;
        form = layui.form;

        tableIns = table.render({
            elem: '#userList',
            url: '/api/user/getUserList',
            method: 'post', //默认：get请求
            cellMinWidth: 80,
            page: true,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：pageNum
                limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
            },
            response: {
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: 'totals', //数据总数的字段名称，默认：count
                dataName: 'list' //数据列表的字段名称，默认：data
            },
            cols: [[
                {type: 'numbers', title: '序号', align: 'center'}
                , {field: 'username', title: '用户名', align: 'center'}
                , {field: 'realName', title: '真实姓名', align: 'center'}
                , {field: 'userPhone', title: '手机号', align: 'center'}
                , {field: 'userEmail', title: '邮箱号', align: 'center'}
                , {field: 'sex', title: '性别', align: 'center'}
                , {field: 'age', title: '年龄', align: 'center'}
                , {field: 'profession', title: '职业', align: 'center'}
                , {field: 'address', title: '家庭住址', align: 'center'}
                , {field: 'identity', title: '身份证号', align: 'center'}
                , {field: 'userStatus', title: '是否有效', align: 'center'}
                , {field: 'createTime', title: '创建时间', align: 'center'}
                , {title: '操作', align: 'center', toolbar: '#optBar'}
            ]],
            done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                console.log(curr);
                $("[data-field='userStatus']").children().each(function () {
                    if ($(this).text() == '1') {
                        $(this).text("有效")
                    } else if ($(this).text() == '0') {
                        $(this).text("失效")
                    }
                });
                $("[data-field='sex']").children().each(function () {
                    if ($(this).text() == '0') {
                        $(this).text("女")
                    } else if ($(this).text() == '1') {
                        $(this).text("男")
                    }
                });
                //得到数据总量
                pageCurr = curr;
            }
        });

        //监听工具条
        table.on('tool(userTable)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                //删除
                delUser(data, data.id, data.username);
            } else if (obj.event === 'edit') {
                //编辑
                editUser(data, "完善信息");
            } else if (obj.event === 'recover') {
                //恢复
                recoverUser(data, data.id);
            }
        });

        //监听提交
        form.on('submit(userSubmit)', function (data) {
            // TODO 校验
            if (data.field.uid!=null){
                editFormSubmit(data)
            } else {
                addFormSubmit(data);
            }

            return false;
        });
    });

    //搜索框
    layui.use(['form', 'laydate'], function () {
        var form = layui.form
        var laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function (data) {
            //重新加载table
            pageCurr = 0;
            load(data);
            return false;
        });
    });
});

//提交表单  新增用户
function addFormSubmit(obj) {
    $.ajax({
        type: "POST",
        data: $("#userForm").serialize(),
        url: "/api/user/addUser",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.message, function () {
                    layer.closeAll();
                    var params = {"pageNum": obj.field.pageNum};
                    //过滤参数
                    load(params);
                });
            } else {
                layer.alert(data.message);
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试", function () {
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}

//开通用户
function addUser() {
    openUser(null, "开通用户");
}

function openUser(data, title) {
    if (data == null || data == "") {
        $("#id").val("");
    }
    var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#pageNum").val(pageNum);

    layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['550px'],
        content: $('#addUser'),
        end: function () {
            cleanUser();
        }
    });
}

//完善用户信息
function editFormSubmit(obj) {
    $.ajax({
        type: "POST",
        data: $("#editUserForm").serialize(),
        url: "/api/user/setUser",
        success: function (data) {
          console.log(data)
            if (data.code == 1) {
                layer.alert(data.message, function () {
                    layer.closeAll();
                    var params = {"pageNum": obj.field.pageNum};
                    //过滤参数
                    load(params);
                });
            } else {
                layer.alert(data.message);
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试", function () {
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}

function editUser(data,title) {

    $("#uid").val(data.id);

    var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#editPageNum").val(pageNum);



    layer.open({
        type: 1,
        title: title,
        fixed: false,
        resize: false,
        shadeClose: true,
        area: ['550px'],
        content: $('#editUser'),
        end: function () {
            cleanUser();
        }
    });
}

function delUser(obj, id, name) {

            layer.confirm('您确定要删除' + name + '用户吗？', {
                btn: ['确认', '返回'] //按钮
            }, function () {
                $.post("/api/user/updateUserStatus", {"id": id, "status": 0}, function (data) {
                    if (data.code == 1) {
                        layer.alert(data.message, function () {
                            layer.closeAll();
                            load(obj);
                        });
                    } else {
                        layer.alert(data.message);
                    }
                });
            }, function () {
                layer.closeAll();
            });

}

//恢复
function recoverUser(obj, id) {
    if (null != id) {
        layer.confirm('您确定要恢复吗？', {
            btn: ['确认', '返回'] //按钮
        }, function () {
            $.post("/api/user/updateUserStatus", {"id": id, "status": 1}, function (data) {
                if (data.code == 1) {
                    layer.alert(data.message, function () {
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.message);
                }
            });
        }, function () {
            layer.closeAll();
        });
    }
}

function load(obj) {
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

function cleanUser() {
    $("#username").val("");
    $("#mobile").val("");
    $("#password").val("");
    $('#roleId').html("");
}

