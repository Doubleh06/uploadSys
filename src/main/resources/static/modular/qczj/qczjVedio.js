var Vedio = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "vedio"
};
var accessToken = null;
/**
 * jqGrid初始化参数
 */
Vedio.initOptions = function () {
    var options = {
        url : "/upload/qczj/vedio/grid",
        autowidth:true,
        colNames: ['手机号','城市ID','城市名称','省份名称','品牌ID','品牌名称','上传状态','信息','创建时间','操作'],
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
                        msg = "已接收";
                    }
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    if (cellvar == 10){
                        msg = "入库成功";
                    }
                    if (cellvar == 11){
                        msg = "邀约成功";
                    }
                    if (cellvar == 15){
                        msg = "上拍成功";
                    }
                    if (cellvar == 20){
                        msg = "交易成功";
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
                }},
            {name: 'operations', index: 'operations', width: 100, sortable: false, formatter: function (cellValue, options, rowObject) {
                var cclid = "'"+rowObject["cclid"]+"'";
                var uid = "'"+rowObject["uid"]+"'";
                var str = "";
                    str += '<input type="button" class=" btn btn-sm btn-info"  value="录音上传" onclick="Vedio.uploadVedio('+cclid+','+uid+')"/>&nbsp;';
                    // str += '<input type="button" class=" btn btn-sm btn-info"  value="提  交" onclick="Vedio.submitVedio('+cclid+','+appid+')"/>&nbsp;';
                return str;
            }
            }
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Vedio.search = function () {
    var searchParam = {};
    searchParam.phone = $("#phone").val();
    searchParam.startDate = $("#startDate").val();
    searchParam.endDate = $("#endDate").val();
    searchParam.status = $("#status").val();
    Vedio.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Vedio.resetSearch = function () {
    $("#phone").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    $(".option_1").attr("selected",true);
    Vedio.search();
    $(".option_1").attr("selected",false);
};

/**
 * 重置搜索
 */
Vedio.download = function () {
    window.location.href = "/upload/qczj/export?phone="+$("#phone").val()+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&status="+$("#status").val();
};

Vedio.uploadVedio = function (cclid,appid) {
    $.ajax({
        url: "/upload/qczj/vedio/getAccessToken",
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                accessToken = r.obj;
                $("#accessToken").val(accessToken);
                $("#cclid").val(cclid);
                $("#appid").val(appid);
                $("#uploadModal").modal();
            }
        }
    })
}
var uploadVedioForm = document.querySelector("#uploadVedioForm");
var uploadVedioInput = document.querySelector("#uploadVedioInput");
uploadVedioForm.addEventListener('submit', function(event){
    var l = $(vedioSubmit).ladda();
    l.ladda( 'start' );
    var formData = new FormData();
    formData.append("file", uploadVedioInput.files[0]);
    formData.append("cclid", uploadVedioForm["cclid"].value);
    formData.append("appid", uploadVedioForm["appid"].value);
    $.ajax({
        // url: 'https://openapi.autohome.com.cn/che168/apiclueopen/carestimate/record.ashx?access_token='+accessToken,
         url: '/upload/qczj/vedio/upload',
        type: 'POST',
        data: formData,
        dataType: "json",
        async: true,
        cache: false,
        contentType: false,
        processData: false,
        success: function (r) {
            l.ladda('stop');
            if (r.code === 0) {
                $("#uploadModal").modal("hide");
                success("上传状态","录音上传成功")
                Vedio.search();
            } else {
                $("#uploadModal").modal("hide");
                error("上传状态","录音上传失败");
            }
        }
    });
    event.preventDefault();
}, true);








// var uploadVedioForm = document.querySelector("#uploadVedioForm");
// var uploadVedioInput = document.querySelector("#uploadVedioInput");
// uploadVedioForm.addEventListener('submit', function(event){
//     var l = $(vedioSubmit).ladda();
//     l.ladda('start');
//     var files = uploadVedioInput.files;
//     if(files.length === 0) {
//         // singleFileUploadError.innerHTML = "Please select a file";
//         // singleFileUploadError.style.display = "block";
//     }
//     uploadSubmit(files[0],l,);
//     event.preventDefault();
// }, true);
//
// function uploadSubmit(file,l,cclid,appid,accessToken) {
//     var formData = new FormData();
//     formData.append("file", file);
//     formData.append("cclid", cclid);
//     formData.append("appid", appid);
//     formData.append("accessToken", accessToken);
//     console.log(formData);
//
//     var xhr = new XMLHttpRequest();
//     xhr.open("POST", "/upload/qczj/vedio/test");
//
//     xhr.onload = function() {
//         var response = JSON.parse(xhr.responseText);
//         if(response.code == 0) {
//             l.ladda('stop');
//             successthen("导入成功",null,"/upload/qczj/list");
//         } else {
//             l.ladda('stop');
//             successthen("导入失败",null,"/upload/qczj/list");
//         }
//     }
//
//     xhr.send(formData);
// }




/**
 * 课程所报名学生
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
// Vedio.searchStudents = function(id){
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Vedio.initOptions());
    Vedio.table = jqGrid.init();

});