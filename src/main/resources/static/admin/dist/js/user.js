$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/users/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'userId', index: 'userId', width: 50, key: true, hidden: true},
            {label: '昵称', name: 'nickName', index: 'nickName', width: 100},
            {label: '邮箱', name: 'email', index: 'email', width: 120},
            {label: '角色', name: 'roleName', index: 'roleName', width: 60},
            {label: '状态', name: 'locked', index: 'locked', width: 60,formatter: statusFormatter},
        ],
        height: 500,
        rowNum: 10,
        rowList: [10, 20, 30],
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
        viewrecords:true,
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });

    function statusFormatter(cellvalue) {
        if (cellvalue == 0) {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 50%;\">正常</button>";
        }
        else if (cellvalue == 1) {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">锁定</button>";
        }
    }

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
    $("#jqGrid").jqGrid("setGridParam", {url: '/admin/permissions/list'}).trigger("reloadGrid");
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

function lockUser() {
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要锁定用户吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: "GET",
                url: "/admin/users/lockUser",
                data: { "userId" : id },
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

function editUserRole() {
    var userId = getSelectedRow();
    if (userId == null){
        return;
    }
    reset();
    $('.modal-title').html('编辑用户角色');
    $('#categoryModal').modal('show');
    //alert(userId);
    $.ajax({
        type: "GET",
        url: "/admin/users/getUserRole",
        /*contentType: "application/json",
        data: JSON.stringify(ids),*/
        data: {"userId":userId},
        success: function (r) {
            if (r.resultCode == 200) {
                var roleId = r.data.roleId;
                if (roleId == null){
                    alert("id为空");
                }
                $("#roleIds option").each(function () {
                    var id = $(this).val();
                    //alert(id);
                    if (roleId == id){
                        $(this).attr("selected","selected");
                        return;
                    }
                });
                
            } else {
                swal(r.message, {
                    icon: "error",
                });
            }
        }
    });
}
function reset() {
    $("#permissionName").val('');
    $("#permissionUrl").val('');
    $("#resourceType").val('');

}

$('#saveButton').click(function () {
    var userId = getSelectedRowWithoutAlert();
    if (userId == null){
        return;
    }
    var roleId = $("#roleIds").val();
    var params ={ "roleId":roleId , "userId":userId };
    $.ajax({
        type: 'GET',//方法类型
        url: "/admin/users/editUserRole",
        data: params,
        success: function (result) {
            if (result.resultCode == 200) {
                $('#categoryModal').modal('hide');
                swal(result.message, {
                    icon: "success",
                });
                reload();
            }
            else {
                $('#categoryModal').modal('hide');
                swal(result.message, {
                    icon: "error",
                });
            };
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });

});