var Upload = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "upload"
};

/**
 * jqGrid初始化参数
 */
Upload.initOptions = function () {
    var options = {
        url : "/upload/grid",
        autowidth:true,
        colNames: ['id','课程名称','开始时间','结束时间','时长','课时','上课周期','费用','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'name', index: 'name', width: 120},
            {name: 'startTime', index: 'startTime', width: 80},
            {name: 'endTime', index: 'endTime', width: 80},
            {name: 'duration', index: 'duration', width: 60},
            {name: 'total', index: 'total', width: 60},
            {name: 'week', index: 'week', width: 80,formatter(cellValue, options, rowObject){
                switch (cellValue) {
                    case 1:return "每周一";break;
                    case 2:return "每周二";break;
                    case 3:return "每周三";break;
                    case 4:return "每周四";break;
                    case 5:return "每周五";break;
                    case 6:return "每周六";break;
                    case 7:return "每周日";break;
                }
                }},
            {name: 'fee', index: 'fee', width: 80},
            {name: 'operations', index: 'operations', width: 200, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编  辑" onclick="Upload.modify(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Upload.delete(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-primary"  value="安排学员" onclick="Upload.arrangeStudents(' + id + ')"/>&nbsp;';

                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Upload.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    searchParam.teacher = $("#teacher").val();
    searchParam.week = $("#week").val();
    Upload.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Upload.resetSearch = function () {
    $("#name").val("");
    $("#teacher").val("");
    $(".option_1").attr("selected",true);
    Upload.search();
    $(".option_1").attr("selected",false);
};

/**
 *新增
 */
Upload.create = function () {
   window.location.href = "/upload/modify?id=";
}
Upload.insert = function (btn) {
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
        url = "/upload/insert";
    }else{
        url = "/upload/update"
    }
    console.log(url);
    var upload = getFormJson($("#create-form"));
    var l = $(btn).ladda();
    l.ladda('start');

    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify(upload),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                l.ladda('stop');
                successthen("保存成功",null,"/upload/list");
            }
        }
    })
}

/**
 *编辑
 */
Upload.modify = function (id) {
    window.location.href = "/upload/modify?id="+id;
}



/**
 * 删除
 *
 * @param id    userId
 */
Upload.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/upload/delete?id=" + id, function () {
            success("成功删除");
            Upload.search();
        });
    })
};

/**
 * 课程跳转
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
Upload.arrangeStudents = function(id){
    window.location.href = '/upload/arrangeStudents/list?id='+id;
}

/**
 * 课程所报名学生
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
// Upload.searchStudents = function(id){
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Upload.initOptions());
    Upload.table = jqGrid.init();

});