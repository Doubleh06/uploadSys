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
        colNames: ['手机号','城市ID','城市名称','省份名称','品牌ID','品牌名称','上传状态','信息','创建时间'],
        colModel: [
            {name: 'phone', index: 'phone', width: 40},
            {name: 'cityCode', index: 'cityCode', width: 30},
            {name: 'cityName', index: 'cityName', width: 40},
            {name: 'province', index: 'province', width: 40},
            {name: 'brandId', index: 'brandId', width: 60},
            {name: 'brandName', index: 'brandName', width: 60},
            {name: 'status', index: 'status', width: 60,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
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
    searchParam.startDate = $("#startDate").val();
    searchParam.endDate = $("#endDate").val();
    searchParam.status = $("#status").val();
    Qczj.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Qczj.resetSearch = function () {
    $("#phone").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    $(".option_1").attr("selected",true);
    Qczj.search();
    $(".option_1").attr("selected",false);
};

/**
 * 重置搜索
 */
Qczj.download = function () {
    window.location.href = "/upload/qczj/export?phone="+$("#phone").val()+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&status="+$("#status").val();
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
    xhr.open("POST", "/upload/qczj/import");

    xhr.onload = function() {
        var response = JSON.parse(xhr.responseText);
        if(response.code == 0) {
            l.ladda('stop');
            successthen("导入成功",null,"/upload/qczj/list");
        } else {
            l.ladda('stop');
            successthen("导入失败",null,"/upload/qczj/list");
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Qczj.initOptions());
    Qczj.table = jqGrid.init();

});