var PersonMachine = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "email"
};

/**
 * jqGrid初始化参数
 */
PersonMachine.initOptions = function () {
    var options = {
        url : "/personMachine/grid",
        autowidth:true,
        colNames: ['id','机床号','姓名','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'machineNo', index: 'machineNo', width: 150},
            {name: 'name', index: 'name', width: 150},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="PersonMachine.delete(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编辑" onclick="PersonMachine.modify(' + id + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="PersonMachine.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
PersonMachine.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    searchParam.address = $("#address").val();
    PersonMachine.table.reload(searchParam);
};

/**
 * 重置搜索
 */
PersonMachine.resetSearch = function () {
    window.location.href = "/personMachinelist";
};

/**
 *新增
 */
PersonMachine.create = function () {
    $("#createModal").modal();
}
PersonMachine.insert = function () {
    var personMachine = getFormJson($("#create-form"));
    $.ajax({
        url: "/personMachine/insert",
        type: 'POST',
        data: JSON.stringify(personMachine),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                PersonMachine.search();
                $("#create-form")[0].reset();
            }
        }
    })
}

/**
 *编辑
 */
PersonMachine.modify = function (id) {
    $.ajax({
        url: "/personMachine/get?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var personMachine = r.obj;
                var form = $("#modify-form");
                form.find("input[name='id']").val(personMachine.id);
                form.find("input[name='machineNo']").val(personMachine.machineNo);
                form.find("input[name='name']").val(personMachine.name);
                $("#modifyModal").modal();
            }
        }
    })
    $("#modifyModal").modal();
}
PersonMachine.update = function () {
    var personMachine = getFormJson($("#modify-form"));
    $.ajax({
        url: "/personMachine/update",
        type: 'POST',
        data: JSON.stringify(personMachine),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#modifyModal").modal("hide");
                success("编辑成功");
                PersonMachine.search();
                $("#modify-form")[0].reset();
            }
        }
    })
}
//选择发件邮箱
PersonMachine.chooseEmail = function (address) {
    $("#emailAddress").val(address);
    $.ajax({
        url: "/personMachinechooseEmail?address="+address,
        type: 'GET',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#radio").empty();
                var radios = r.obj;
                var radio = "";
                for(var i=0;i<radios.length;i++){
                    if(radios[i].isUsing==0){
                        radio += '<input id="'+radios[i].authName+'" type="radio" name="radio" value="'+radios[i].authName+'" checked><label for="'+radios[i].authName+'">'+radios[i].authName+'</label><br>';
                    }else{
                        radio += '<input id="'+radios[i].authName+'" type="radio" name="radio" value="'+radios[i].authName+'" ><label for="'+radios[i].authName+'">'+radios[i].authName+'</label><br>';
                    }
                }
                $("#radio").append(radio);
                    $("#addressModal").modal();

            }
        }
    })
}

PersonMachine.changeEmail = function () {
    var addressForm = getFormJson($("#address-form"));
    $.ajax({
        url: "/personMachinechangeEmail",
        type: 'POST',
        data:JSON.stringify(addressForm),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#addressModal").modal("hide");
                success("切换成功");
                PersonMachine.search();
                $("#address-form")[0].reset();
            }
        }
    })
}


PersonMachine.clickSwitch = function (id,isUsing,address) {
    $.ajax({
        url: "/personMachineclickSwitch",
        type: 'POST',
        data:JSON.stringify({
            id:id,
            isUsing:isUsing,
            address:address
        }),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                if(r.obj){
                    success("切换成功");
                    PersonMachine.search();
                }else {
                    error("切换失败，一个地区不能同时打开多个发件邮箱")
                    PersonMachine.search();
                }

            }
        }
    })
}


/**
 * 删除
 *
 * @param id    userId
 */
PersonMachine.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/personMachine/delete?id=" + id, function () {
            success("成功删除");
            PersonMachine.search();
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", PersonMachine.initOptions());
    PersonMachine.table = jqGrid.init();

});