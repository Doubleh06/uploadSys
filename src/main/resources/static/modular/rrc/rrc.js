var Common = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "common"
};
var accessToken = null;
/**
 * jqGrid初始化参数
 */
Common.initOptions = function () {
    var options = {
        url : "/upload/rrc/common/grid",
        autowidth:true,
        colNames: ['姓名','手机号','城市名称','品牌','车系','车型','公里数','上牌时间','上传时间','上传状态','线索id','是否重复'],
        // colNames: ['姓名','手机号','城市名称','品牌','车系','车型','公里数','上牌时间','是否营运','座位数','事故车','上传时间','上传状态','线索id','是否重复'],
        colModel: [
            {name: 'name', index: 'name', width: 40},
            {name: 'mobile', index: 'mobile', width: 60},
            {name: 'city', index: 'cityName', width: 40},
            {name: 'brand', index: 'brand', width: 40},
            {name: 'series', index: 'series', width: 60},
            {name: 'model', index: 'model', width: 60},
            {name: 'kilometer', index: 'kilometer', width: 40},
            {name: 'licensedDateYear', index: 'licensedDateYear', width: 40},
            {name: 'createTime', index: 'createTime', width: 80,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    if (cellvar == "" || cellvar == undefined) {
                        return "";
                    }
                    var da = new Date(cellvar);
                    return dateFtt("yyyy-MM-dd hh:mm:ss", da);
                }},
            {name: 'status', index: 'status', width: 60,align: "center", editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 1){
                        msg = "上传失败";
                    }
                    if (cellvar == 0){
                        msg = "入库成功";
                    }

                    return msg;
                }},
            {name: 'renrencheInfoId', index: 'renrencheInfoId',align: "center", width: 60},
            {name: 'isRepeat', index: 'isRepeat',align: "center", width: 40, editable: false,formatter: function (cellvar, options, rowObject) {
                    var msg = "";
                    if (cellvar == 0){
                        msg = "重复";
                    }
                    if (cellvar == 1){
                        msg = "不重复";
                    }

                    return msg;
                }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
Common.search = function () {
    var searchParam = {};
    searchParam.phone = $("#phone").val();
    searchParam.startDate = $("#startDate").val();
    searchParam.endDate = $("#endDate").val();
    searchParam.status = $("#status").val();
    searchParam.appid = $("#uid").val();
    Common.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Common.resetSearch = function () {
    $("#phone").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    $(".option_1").attr("selected",true);
    $(".option_2").attr("selected",true);
    Common.search();
    $(".option_1").attr("selected",false);
    $(".option_2").attr("selected",false);
};

/**
 * 重置搜索
 */
Common.download = function () {
    window.location.href = "/upload/rrc/common/export?phone="+$("#phone").val()+"&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val()+"&status="+$("#status").val();
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
    xhr.open("POST", "/upload/rrc/common/import");

    xhr.onload = function() {
        var response = JSON.parse(xhr.responseText);
        if(response.code == 0) {
            l.ladda('stop');
            successthen("导入成功",null,"/upload/rrc/common/list");
        } else {
            l.ladda('stop');
            successthen("导入失败",null,"/upload/rrc/common/list");
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
// Common.searchStudents = function(id){
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Common.initOptions());
    Common.table = jqGrid.init();

});