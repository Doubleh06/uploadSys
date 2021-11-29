var Qczj = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "qczj"
};

/**
 * jqGrid初始化参数
 */
Qczj.initOptions = function () {
    var options = {
        url : "/upload/qczj/grid",
        autowidth:true,
        colNames: ['手机号','城市ID','城市名称','省份名称','品牌ID','品牌名称','创建时间'],
        colModel: [
            {name: 'phone', index: 'phone', width: 40},
            {name: 'cityCode', index: 'cityCode', width: 30},
            {name: 'cityName', index: 'cityName', width: 60},
            {name: 'province', index: 'province', width: 60},
            {name: 'brandId', index: 'brandId', width: 60},
            {name: 'brandName', index: 'brandName', width: 60},
            {name: 'createTime', index: 'createTime', width: 80,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }}
        //     {name: 'operations', index: 'operations', width: 200, sortable: false, formatter: function (cellValue, options, rowObject) {
        //         var id = "'"+rowObject["id"]+"'";
        //         var str = "";
        //         str += '<input type="button" class=" btn btn-sm btn-info"  value="编  辑" onclick="Qczj.modify(' + id + ')"/>&nbsp;';
        //         str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Qczj.delete(' + id + ')"/>&nbsp;';
        //         str += '<input type="button" class=" btn btn-sm btn-primary"  value="安排学员" onclick="Qczj.arrangeStudents(' + id + ')"/>&nbsp;';
        //
        //         return str;
        //     }
        //     }
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Qczj.search = function () {
    var searchParam = {};
    searchParam.phone = $("#phone").val();
    // searchParam.teacher = $("#teacher").val();
    // searchParam.week = $("#week").val();
    Qczj.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Qczj.resetSearch = function () {
    $("#phone").val("");
    // $("#teacher").val("");
    // $(".option_1").attr("selected",true);
    Qczj.search();
    // $(".option_1").attr("selected",false);
};

/**
 *新增
 */
Qczj.create = function () {
   window.location.href = "/upload/qczj/modify?id=";
}
Qczj.insert = function (btn) {
    var name = $("#name").val();
    if(null==name||""==name){
        error("课程名不能为空");
        return;
    }
    var startTime = $("#startTime").val();
    if(null==startTime||""==startTime){
        error("开始时间不能为空");
        return;
    }
    var endTime = $("#endTime").val();
    if(null==endTime||""==endTime){
        error("结束时间不能为空");
        return;
    }
    if(!compareTime(startTime,endTime)){
        error("结束时间不能小于开始时间");
        return;
    }
    var total = $("#total").val();
    if(null==total||""==total){
        error("总课程数不能为空");
        return;
    }
    var fee = $("#fee").val();
    if(null==fee||""==fee){
        error("金额不能为空");
        return;
    }
    var url = "";

    var id = $("#id").val();
    console.log(id);
    if(null == id||"" == id){
        url = "/upload/qczj/insert";
    }else{
        url = "/upload/qczj/update"
    }
    console.log(url);
    var upload = getFormJson($("#create-form"));
    var l = $(btn).ladda();
    l.ladda('start');

    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(qczj),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                l.ladda('stop');
                successthen("保存成功",null,"/upload/qczj/list");
            }
        }
    })
}

/**
 *编辑
 */
Qczj.modify = function (id) {
    window.location.href = "/upload/qczj/modify?id="+id;
}



/**
 * 删除
 *
 * @param id    userId
 */
Qczj.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/upload/qczj/delete?id=" + id, function () {
            success("成功删除");
            Qczj.search();
        });
    })
};

/**
 * 课程跳转
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
Qczj.arrangeStudents = function(id){
    window.location.href = '/upload/qczj/arrangeStudents/list?id='+id;
}

/**
 * 课程所报名学生
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
// Qczj.searchStudents = function(id){
//     window.location.href = '/upload/searchStudents/list?id='+id;
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

    function compareTime(time1,time2){
        var th1 = time1.split(":")[0];
        var tm1 = time1.split(":")[1];
        var th2 = time2.split(":")[0];
        var tm2 = time2.split(":")[1];
        if(th1 == th2){
            if(tm1<tm2){
                return true;
            }else{
                return false;
            }
        }else if(th1<th2){
            return true;
        }else{
            return false;
        }
    }

$(function() {
    // $('.chosen-select').chosen({width: "100%"});

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Qczj.initOptions());
    Qczj.table = jqGrid.init();

});