var QczjHQ = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "qczjHQ"
};

/**
 * jqGrid初始化参数
 */
QczjHQ.initOptions = function () {
    var options = {
        url : "/upload/qczj/hq/grid",
        autowidth:true,
        colNames: ['手机号','城市ID','车辆品牌id','车系id','车型id','项目号','上传状态','分发状态','申诉状态','创建时间'],
        colModel: [
            {name: 'mobile', index: 'mobile', width: 40},
            {name: 'countyid', index: 'countyid', width: 30},
            {name: 'brandid', index: 'brandid', width: 40},
            {name: 'seriesid', index: 'seriesid', width: 40},
            {name: 'specid', index: 'specid', width: 40},
            {name: 'appid', index: 'appid', width: 30},
            {name: 'status', index: 'status', width: 60,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 0){
                        msg = "已接收";
                    }
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    // if (cellvar == 10){
                    //     msg = "入库成功";
                    // }
                    // if (cellvar == 11){
                    //     msg = "邀约成功";
                    // }
                    // if (cellvar == 15){
                    //     msg = "上拍成功";
                    // }
                    // if (cellvar == 20){
                    //     msg = "交易成功";
                    // }
                    return msg;
                }},
            {name: 'distributeStatus', index: 'distributeStatus', width: 60,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                        var msg = "";
                    if (cellvar == 0){
                        msg = "未分发";
                    }
                    if (cellvar == 1){
                        msg = "已分发";
                    }
                    return msg;
            }},
            {name: 'appealStatus', index: 'appealStatus', width: 60,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";

                    if (cellvar == 1){
                        msg = "申诉成功";
                    }
                    if (cellvar == 0){
                        msg = "未申诉或申诉失败";
                    }
                    return msg;
            }},
            {name: 'createTime', index: 'createTime', width: 80,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }}
            //     ,
            // {name: 'operations', index: 'operations', width: 50, sortable: false, formatter: function (cellValue, options, rowObject) {
            //     var id = "'"+rowObject["id"]+"'";
            //     var str = "";
            //     str += '<input type="button" class=" btn btn-sm btn-info"  value="申  诉" onclick="QczjHQ.appeal(' + id + ')"/>&nbsp;';
            //     // str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Qczj.delete(' + id + ')"/>&nbsp;';
            //     // str += '<input type="button" class=" btn btn-sm btn-primary"  value="安排学员" onclick="Qczj.arrangeStudents(' + id + ')"/>&nbsp;';
            //
            //     return str;
            // }
            // }
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
QczjHQ.search = function () {
    var searchParam = {};
    searchParam.phone = $("#mobile").val();
    searchParam.startDate = $("#startDate").val();
    searchParam.endDate = $("#endDate").val();
    searchParam.status = $("#status").val();
    searchParam.appid = $("#appid").val();
    QczjHQ.table.reload(searchParam);
};

/**
 * 重置搜索
 */
QczjHQ.resetSearch = function () {
    $("#mobile").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    $(".option_1").attr("selected",true);
    $(".option_2").attr("selected",true);
    QczjHQ.search();
    $(".option_1").attr("selected",false);
    $(".option_2").attr("selected",false);
};

/**
 * 下载城市模版
 */
QczjHQ.downloadCity = function () {
    window.location.href = "/upload/qczj/exportArea";
};

/**
 * 下载车模版
 */
QczjHQ.downloadCar = function () {
    var appid = $("#appid").val();
    if (!appid){
        error("项目号不能为空");
    }else {
        window.location.href = "/upload/qczj/exportCarInfo?appId="+appid;
        warning("注意","10分钟内请勿重复点击，请勿切换至其他页面");
    }

};
/**
 * 申诉
 */
QczjHQ.appeal = function (id) {

};

var uploadForm = document.querySelector("#uploadForm");
var uploadInput = document.querySelector("#uploadInput");
uploadForm.addEventListener('submit', function(event){
    var l = $(btn).ladda();
    l.ladda('start');
    var files = uploadInput.files;
    if(files.length === 0) {
        // singleFileUploadError.innerHTML = "Please select a file";
        // singleFileUploadError.style.display = "block";
    }
    uploadSubmit(files[0],l);
    event.preventDefault();
}, true);

function uploadSubmit(file,l) {
    var formData = new FormData();
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/upload/qczj/hq/import");

    xhr.onload = function() {
        var response = JSON.parse(xhr.responseText);
        if(response.code == 0) {
            l.ladda('stop');
            successthen("导入成功",null,"/upload/qczj/hq/list");
        } else {
            l.ladda('stop');
            successthen("导入失败",null,"/upload/qczj/hq/list");
        }
    }

    xhr.send(formData);
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", QczjHQ.initOptions());
    QczjHQ.table = jqGrid.init();

});