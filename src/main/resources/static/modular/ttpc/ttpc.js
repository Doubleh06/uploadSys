var Ttpc = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "ttpc"
};

/**
 * jqGrid初始化参数
 */
Ttpc.initOptions = function () {
    var options = {
        url : "/upload/ttpc/grid",
        autowidth:true,
        colNames: ['报名人姓名','手机号','车辆所在城市','车辆品牌','来源','状态','信息','邀约','检测','竞拍','成交','创建时间'],
        colModel: [
            {name: 'name', index: 'name', width: 40},
            {name: 'mobile', index: 'mobile', width: 40},
            {name: 'city', index: 'city', width: 30},
            {name: 'brand', index: 'brand', width: 40},
            {name: 'source', index: 'source', width: 60},
            {name: 'status', index: 'status', width: 30,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 0){
                        msg = "上传成功";
                    }
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    return msg;
                }},
            {name: 'message', index: 'message',align: "center", width: 60},
            {name: 'invite', index: 'invite', width: 30,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 0){
                        msg = "上传成功";
                    }
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    return msg;
                }},
            {name: 'detection', index: 'detection', width: 30,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 0){
                        msg = "上传成功";
                    }
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    return msg;
                }},
            {name: 'auction', index: 'auction', width: 30,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 0){
                        msg = "上传成功";
                    }
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    return msg;
                }},
            {name: 'deal', index: 'deal', width: 30,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 0){
                        msg = "上传成功";
                    }
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    return msg;
                }},
            {name: 'createTime', index: 'createTime', width: 60,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd", da);
                }}
        //     {name: 'operations', index: 'operations', width: 200, sortable: false, formatter: function (cellValue, options, rowObject) {
        //         var id = "'"+rowObject["id"]+"'";
        //         var str = "";
        //         str += '<input type="button" class=" btn btn-sm btn-info"  value="编  辑" onclick="Ttpc.modify(' + id + ')"/>&nbsp;';
        //         str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Ttpc.delete(' + id + ')"/>&nbsp;';
        //         str += '<input type="button" class=" btn btn-sm btn-primary"  value="安排学员" onclick="Ttpc.arrangeStudents(' + id + ')"/>&nbsp;';
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
Ttpc.search = function () {
    var searchParam = {};
    searchParam.mobile = $("#phone").val();
    searchParam.startDate = $("#startDate").val();
    searchParam.endDate = $("#endDate").val();
    searchParam.status = $("#status").val();
    searchParam.appid = $("#appid").val();
    Ttpc.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Ttpc.resetSearch = function () {
    $("#phone").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    $(".option_1").attr("selected",true);
    $(".option_2").attr("selected",true);
    Ttpc.search();
    $(".option_1").attr("selected",false);
    $(".option_2").attr("selected",false);
};

/**
 * 重置搜索
 */
Ttpc.download = function () {
    window.location.href = "/upload/ttpc/export?phone="+$("#phone").val()+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&status="+$("#status").val();
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
    xhr.open("POST", "/upload/ttpc/import");

    xhr.onload = function() {
        var response = JSON.parse(xhr.responseText);
        if(response.code == 0) {
            l.ladda('stop');
            successthen("导入成功",null,"/upload/ttpc/list");
        } else {
            l.ladda('stop');
            successthen("导入失败",null,"/upload/ttpc/list");
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
// Ttpc.searchStudents = function(id){
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Ttpc.initOptions());
    Ttpc.table = jqGrid.init();

});