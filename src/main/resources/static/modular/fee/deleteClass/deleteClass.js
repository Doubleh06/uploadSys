var DeleteClass = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "signUp"
};

/**
 * jqGrid初始化参数
 */
DeleteClass.initOptions = function () {
    var options = {
        url : "/fee/deleteClass/grid",
        autowidth:true,
        colNames: ['课程名称','学生姓名','课时','费用','消课理由','消课时间'],
        colModel: [
            {name: 'className', index: 'className', width: 120},
            {name: 'studentName', index: 'studentName', width: 80},
            {name: 'total', index: 'total', width: 80},
            {name: 'fee', index: 'fee', width: 80},
            {name: 'reason', index: 'reason', width: 80,formatter:function(cellValue, options, rowObject){
                switch (cellValue) {
                    case 1:return "常规课";break;
                    case 2:return "比赛";break;
                    case 3:return "考级";break;
                    case 4:return "补课";break;
                    case 5:return "特色课";break;
                    case 6:return "其他";break;
                }
                }},
            {name: 'deleteDate', index: 'deleteDate', width: 150,formatter(cellValue, options, rowObject){
                    if (cellValue == "" || cellValue == undefined) {
                        return "";
                    }
                    var da = new Date(cellValue);
                    return dateFtt("yyyy-MM-dd", da);
                }},
        ],
        gridComplete:function () {
            $("#totalFee").html("总已消课费用："+DeleteClass.table.getTotalFee())
        }
    };
    return options;
};

/**
 * 根据关键词搜索
 */
DeleteClass.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    searchParam.year = $("#year").val();
    searchParam.month = $("#month").val();
    DeleteClass.table.reload(searchParam);
};

/**
 * 重置搜索
 */
DeleteClass.resetSearch = function () {
    $("#name").val("");
    $("#year").val("");
    $("#month").val("");
    DeleteClass.search();
};

/**
 *新增
 */
DeleteClass.create = function () {
   window.location.href = "/fee/signUp/modify?id=";
}
DeleteClass.insert = function (btn) {
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
        url = "/fee/signUp/insert";
    }else{
        url = "/fee/signUp/update"
    }
    console.log(url);
    var classes = getFormJson($("#create-form"));
    var l = $(btn).ladda();
    l.ladda('start');

    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(classes),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                l.ladda('stop');
                successthen("保存成功",null,"/fee/signUp/list");
            }
        }
    })
}

/**
 *编辑
 */
DeleteClass.modify = function (id) {
    window.location.href = "/fee/signUp/modify?id="+id;
}



/**
 * 删除
 *
 * @param id    userId
 */
DeleteClass.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/fee/signUp/delete?id=" + id, function () {
            success("成功删除");
            DeleteClass.search();
        });
    })
};

/**
 * 课程跳转
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
DeleteClass.arrangeStudents = function(id){
    window.location.href = '/fee/signUp/arrangeStudents/list?id='+id;
}



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
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", DeleteClass.initOptions());
    DeleteClass.table = jqGrid.init();

});