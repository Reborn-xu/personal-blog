$(function () {
    $("#jqGrid").jqGrid({
        url: '/admin/categories/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'categoryId', index: 'categoryId', width: 50, key: true, hidden: true},
            {label: '分类名称', name: 'categoryName', index: 'categoryName', width: 240},
            {label: '分类图标', name: 'categoryIcon', index: 'categoryIcon', width: 120, formatter: imgFormatter},
            {label: '添加时间', name: 'createTime', index: 'createTime', width: 120}
        ],
        height: 560,
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

    jQuery("select.image-picker").imagepicker({
        hide_select: false,
    });

    jQuery("select.image-picker.show-labels").imagepicker({
        hide_select: false,
        show_label: true,
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    var container = jQuery("select.image-picker.masonry").next("ul.thumbnails");
    container.imagesLoaded(function () {
        container.masonry({
            itemSelector: "li",
        });
    });

});

function imgFormatter(cellvalue) {
    return "<img src='" + cellvalue + "' height=\"64\" width=\"64\" alt='icon'/>";
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

function categoryAdd() {

    reset();
    $('.modal-title').html('分类添加');
    $('#categoryModal').modal('show');
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var categoryName = $("#categoryName").val();
    if (!validCN_ENString2_18(categoryName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的分类名称！");
    } else {
        $("#categoryId").val(null);
        var params = $("#categoryForm").serialize();
        var url = '/admin/categories/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/categories/update';
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

function categoryEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    $('.modal-title').html('分类编辑');
    $('#categoryModal').modal('show');
    $("#categoryId").val(id);
    $.ajax({
        type: "GET",
        url: "/admin/categories/getCategoryByPrimary",
        data: {"categoryId":id},
        success: function (r) {
            if (r.resultCode == 200) {
               var name =r.data.categoryName;
               var categoryUrl =r.data.categoryIcon;
               $("#categoryName").val(name);
                $("#categoryIcon option").each(function () {
                    var url = $(this).val();
                    //alert(id);
                    if (categoryUrl == url){
                        $(this).prop("selected", 'selected');
                        return;
                    }
                });
            } else {
                swal(r.message, {
                    icon: "操作错误",
                });
            }
        }
    });
}

function deleteCagegory() {
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
                    url: "/admin/categories/delete",
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


function reset() {
    $("#categoryName").val('');
    $("#categoryIcon option:first").prop("selected", 'selected');
}