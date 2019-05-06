var Employee = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "employee"
};
var modifyDualSelector;
//定义旧的数组接收机床id
// var oldId = new Array();
/**
 * jqGrid初始化参数
 */
Employee.initOptions = function () {
    var options = {
        url : "/employeeMachine/employee/grid",
        autowidth:true,
        colNames: ['id','姓名','机床号','机床id','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'name', index: 'name', width: 150},
            {name: 'm_name', index: 'm_name', width: 150 },
            {name: 'm_id', index: 'm_id', width: 150 ,hidden:true},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var m_id = "'"+rowObject["m_id"]+"'";
                var str = "";
                // str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Employee.delete(' + id+','+m_id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="Employee.modify(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="Employee.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Employee.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    Employee.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Employee.resetSearch = function () {
    $("#name").val("");
    Employee.search();
};

/**
 *新增
 */
Employee.create = function () {
    $("#createModal").modal();
}
Employee.insert = function () {
    var employee = getFormJson($("#create-form"));
    var machineName = employee.machineName;
    //判断是否是数组
    if(!(machineName instanceof Array)){
        var arrayObj = new Array();
        arrayObj. push(machineName);
        employee.machineName = arrayObj;
    }
    $.ajax({
        url: "/employeeMachine/employee/insert",
        type: 'POST',
        data: JSON.stringify(employee),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                Employee.search();
                $("#create-form")[0].reset();
            }
        }
    })
}

/**
 *编辑
 */
Employee.modify = function (id) {
    $.ajax({
        url: "/employeeMachine/employee/get?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var employeeAndMachine = r.obj;
                var noSelectedMachine = r.obj2;
                var form = $("#modify-form");
                form.find("input[name='employeeId']").val(employeeAndMachine[0].employeeId);
                form.find("input[name='employeeName']").val(employeeAndMachine[0].employeeName);
                var option = "";
                for (var i=0;i<employeeAndMachine.length;i++){
                    option += "<option value='"+employeeAndMachine[i].machineId+"' selected>"+employeeAndMachine[i].machineName+"</option>"
                    // oldId.push(employeeAndMachine[i].machineId.toString());
                }
                for(var i=0;i<noSelectedMachine.length;i++){
                    option += "<option value='"+noSelectedMachine[i].id+"'>"+noSelectedMachine[i].name+"</option>"
                }
                // form.find("select[name=' machineName']").html(option);
                form.find(".dual_select").html(option);
                modifyDualSelector.bootstrapDualListbox('refresh');
                $("#modifyModal").modal();
            }
        }
    })
    $("#modifyModal").modal();
}
Employee.update = function () {
    var employeeAndMachine = getFormJson($("#modify-form"));
    var machineName = employeeAndMachine.machineName;
    var employeeId = employeeAndMachine.employeeId;
    //判断是否为空 为空则是删除操作
    if(null==machineName||""==machineName){
        // console.log("删除个人数据");
        warning("确定删除吗", "", function () {
            $.get("/employeeMachine/employee/delete?employeeId=" + employeeId, function () {
                success("成功删除");
                $("#modifyModal").modal("hide");
                Employee.search();
            });
        })
    }else{
        //判断是否是数组
        if(!(machineName instanceof Array)){
            var arrayObj = new Array();
            arrayObj. push(machineName);
            employeeAndMachine.machineName = arrayObj;
        }

        // var diff = new Array();
        // //对比旧的和新的机床id；
        // for(var i=0;i<oldId.length;i++){
        //     if(!array_contain(employeeAndMachine.machineName,oldId[i])){
        //         diff.push(oldId[i]);
        //     }
        // }
        $.ajax({
            url: "/employeeMachine/employee/update",
            type: 'POST',
            data: JSON.stringify(employeeAndMachine),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (r) {
                if (r.code === 0) {
                    $("#modifyModal").modal("hide");
                    success("编辑成功");
                    Employee.search();
                    $("#modify-form")[0].reset();
                }
            }
        })
    }

}


/**
 * 删除
 *
 * @param id    userId
 */
Employee.delete = function del(id,machineId) {
    if(machineId.indexOf("|")!=-1){
        showError("此人操作不止一台机床，不能直接删除");
    }else{
        warning("确定删除吗", "", function () {
            $.get("/employeeMachine/employee/delete?id=" + id+"&machineId="+machineId, function () {
                success("成功删除");
                Employee.search();
            });
        })
    }
};
    // function array_contain(array, obj){
    //     for (var i = 0; i < array.length; i++){
    //         if (array[i] == obj)//如果要求数据类型也一致，这里可使用恒等号===
    //             return true;
    //     }
    //     return false;
    // }

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

var dualOptions = {
    selectorMinimalHeight: 160,
    selectedListLabel: "已选：",
    nonSelectedListLabel: "未选：",
    // showFilterInputs: false,
    infoText: false,
};
$(function() {
    // $('.chosen-select').chosen({width: "100%"});

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Employee.initOptions());
    modifyDualSelector = $("#modify-form").find(".dual_select").bootstrapDualListbox(dualOptions);
    Employee.table = jqGrid.init();

});