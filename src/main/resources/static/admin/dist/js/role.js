$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/roles/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'roleId', index: 'roleId', width: 50, key: true, hidden: true},
            {label: '名称', name: 'roleName', index: 'roleName', width: 140},
            {label: '描述', name: 'roleDesc', index: 'roleDesc', width: 120},
            {label: '创建时间', name: 'createTime', index: 'createTime', width: 60},
        ],
        height: 500,
        rowNum: 5,
        rowList: [5, 10, 15],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.pageNum",
            total: "data.pages",
            records: "data.total"
        },
        prmNames: {
            page: "pageNum",
            rows: "pageSize",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });



});

/**
 * 搜索功能
 */
function search() {
    //标题关键字
    var keyword = $('#keyword').val();
    if (!validLength(keyword, 20)) {
        swal("搜索字段长度过大!", {
            icon: "error",
        });
        return false;
    }
    //数据封装
    var searchData = {keyword: keyword};
    //传入查询条件参数
    $("#jqGrid").jqGrid("setGridParam", {postData: searchData});
    //点击搜索按钮默认都从第一页开始
    $("#jqGrid").jqGrid("setGridParam", {page: 1});
    //提交post并刷新表格
    $("#jqGrid").jqGrid("setGridParam", {url: '/admin/blogs/list'}).trigger("reloadGrid");
}

/**
 * jqGrid重新加载
 */
function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function addBlog() {
    window.location.href = "/admin/blogs/edit";
}

function editBlog() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    window.location.href = "/admin/blogs/edit/" + id;
}

/**
 * 编辑角色权限
 */
function editPermissionToRole() {
    $("#jqGridPermissions").trigger("reloadGrid");
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    $("#jqGridPermissions").jqGrid({
        url: '/admin/permissions/listWithoutPage',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'permissionId', index: 'permissionId', width: 50, key: true, hidden: true},
            {label: '名称', name: 'permissionName', index: 'permissionName', width: 125},
            {label: 'url', name: 'permissionUrl', index: 'permissionUrl', width: 200},
            {label: '类型', name: 'resourceType', index: 'resourceType', width: 125},
        ],
        height: 500,
        width: 450,
        // rowNum: 5,
        // rowList: [5, 10, 15],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: false,
        multiselect: true,
        jsonReader: {
            root: "data",
            // page: "data.pageNum",
            // total: "data.pages",
            // records: "data.total"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGridPermissions").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        },
        loadComplete:function () {
            var allPermissions = getJQAllData();
            var roleId = getSelectedRowWithoutAlert();
            $.ajax({
                type: "GET",
                url: "/admin/roles/getPermissions",
                data: {"roleId":roleId},
                success: function (r) {
                    if (r.resultCode == 200) {
                        // $("#jqGrid").trigger("reloadGrid");
                        var result = r.data;
                        //alert(r.data[0].permissionName);
                        for (var i = 0; i < allPermissions.length; i++){
                            var permission = allPermissions[i].permissionId;
                            //alert(permission);
                            for (var k = 0; k < result.length;k++){
                                if (permission == result[k].permissionId){
                                    //alert("checked");
                                    $("#jqGridPermissions").jqGrid("setSelection", permission, false);
                                }
                            }
                        }

                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                }
            });
            //alert(allPermissions);


        }
    });


    $('.modal-title').html('编辑权限');
    $('#categoryModal2').modal('show');
}

function deleteBlog() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "POST",
                url: "/admin/blogs/delete",
                contentType: "application/json",
                data: JSON.stringify(ids),
                success: function (r) {
                    if (r.resultCode == 200) {
                        swal("删除成功", {
                            icon: "success",
                        });
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        swal(r.message, {
                            icon: "error",
                        });
                    }
                }
            });
        }
    }
);
}

function permissionAdd() {
    reset();
    $('.modal-title').html('权限添加');
    $('#categoryModal').modal('show');
}
function reset() {
    $("#permissionName").val('');
    $("#permissionUrl").val('');
    $("#resourceType").val('');

}

$('#saveButton').click(function () {
    var roleName = $("#roleName").val();
    var roleDesc = $("#roleDesc").val();
    if (!validCN_ENString2_18(roleName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的分类名称！");
    } else {
        var params = $("#categoryForm").serialize();
        var url = '/admin/roles/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/roles/update';
        }
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#categoryModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                }
                else {
                    $('#categoryModal').modal('hide');
                    swal(result.message, {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    }
});

$('#updatePermission').click(function () {
    var permissions = getSelectedPermissions();
    alert(permissions);
    var roleId = getSelectedRowWithoutAlert();
    alert("roleId"+roleId);
    permissions.push(roleId);
    alert(permissions);
    $.ajax({
        type: "POST",
        url: "/admin/roles/updatePermissions",
        contentType: "application/json",
        data: JSON.stringify(permissions),
        //data: {"permissions":permissionIds,"roleId":roleId},
        success: function (r) {
            if (r.resultCode == 200) {
                swal(r.message, {
                    icon: "success",
                });
                $("#jqGridPermissions").trigger("reloadGrid");
            } else {
                swal(r.message, {
                    icon: "error",
                });
            }
        }
    });
});

/**
 * 获取jqGrid选中的多条记录
 * @returns {*}
 */
function getSelectedPermissions() {
    var grid = $("#jqGridPermissions");
    var rowKey = grid.getGridParam("selrow");
    /*if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }*/
    return grid.getGridParam("selarrrow");
}