var Machine = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "email"
};

/**
 * jqGrid初始化参数
 */
Machine.initOptions = function () {
    var options = {
        url : "/employeeMachine/machine/grid",
        autowidth:true,
        colNames: ['id','机床号','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'name', index: 'name', width: 150},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Machine.delete(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="Machine.modify(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="Machine.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Machine.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    Machine.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Machine.resetSearch = function () {
    $("#name").val("");
    Machine.search();
};

/**
 *新增
 */
Machine.create = function () {
    $("#createModal").modal();
}
Machine.insert = function () {
    var machine = getFormJson($("#create-form"));
    $.ajax({
        url: "/employeeMachine/machine/insert",
        type: 'POST',
        data: JSON.stringify(machine),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                Machine.search();
                $("#create-form")[0].reset();
            }
        }
    })
}

/**
 *编辑
 */
Machine.modify = function (id) {
    $.ajax({
        url: "/employeeMachine/machine/get?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var machine = r.obj;
                var form = $("#modify-form");
                form.find("input[name='id']").val(machine.id);
                form.find("input[name='machineNo']").val(machine.machineNo);
                form.find("input[name='name']").val(machine.name);
                $("#modifyModal").modal();
            }
        }
    })
    $("#modifyModal").modal();
}
Machine.update = function () {
    var machine = getFormJson($("#modify-form"));
    $.ajax({
        url: "/employeeMachine/machine/update",
        type: 'POST',
        data: JSON.stringify(machine),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#modifyModal").modal("hide");
                success("编辑成功");
                Machine.search();
                $("#modify-form")[0].reset();
            }
        }
    })
}





/**
 * 删除
 *
 * @param id    userId
 */
Machine.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/employeeMachine/machine/delete?id=" + id, function () {
            success("成功删除");
            Machine.search();
        });
    })
};

    function dateFtt(fmt,date)
    { //author: meizz
        var o = {
            "M+" : date.getMonth()+1,                 //月份
            "d+" : date.getDate(),                    //日
            "h+" : date.getHours(),                   //小时
            "m+" : date.getMinutes(),                 //分
            "s+" : date.getSeconds(),                 //秒
            "q+" : Math.floor((date.getMonth()+3)/3), //季度
            "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }

$(function() {
    // $('.chosen-select').chosen({width: "100%"});

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Machine.initOptions());
    Machine.table = jqGrid.init();

});