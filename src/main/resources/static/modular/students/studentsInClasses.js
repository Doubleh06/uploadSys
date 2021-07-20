var StudentsInClasses = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "StudentsInClasses"
};

/**
 * jqGrid初始化参数
 */
StudentsInClasses.initOptions = function () {
    var options = {
        url : "/students/studentsInClasses/grid",
        autowidth:true,
        colNames: ['id','课程名称','开始时间','结束时间','时长','课时','上课周期','操作'],
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
            {name: 'operations', index: 'operations', width: 200, sortable: false, formatter: function (cellValue, options, rowObject) {
                    var id = "'"+rowObject["id"]+"'";
                    var str = "";
                    // str += '<input type="button" class=" btn btn-sm btn-info"  value="编  辑" onclick="Classes.modify(' + id + ')"/>&nbsp;';
                    // str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Classes.delete(' + id + ')"/>&nbsp;';
                    // str += '<input type="button" class=" btn btn-sm btn-primary"  value="安排学员" onclick="Classes.arrangeStudents(' + id + ')"/>&nbsp;';
                    str += '<input type="button" class=" btn btn-sm btn-success"  value="查看学员" onclick="StudentsInClasses.searchStudents(' + id + ')"/>&nbsp;';
                    // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="SignUp.delete(' + id + ')"/>';
                    return str;
                }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
StudentsInClasses.search = function () {
    var searchParam = {};
    searchParam.className = $("#className").val();
    StudentsInClasses.table.reload(searchParam);
};

/**
 * 重置搜索
 */
StudentsInClasses.resetSearch = function () {
    $("#className").val("");
    StudentsInClasses.search();
};

StudentsInClasses.searchAllStudents = function () {
    window.location.href = '/students/list?cid=-1';
};




/**
 * 课程跳转
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
StudentsInClasses.searchStudents = function(id){
    window.location.href = '/students/list?cid='+id;
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

$(function() {
    // $('.chosen-select').chosen({width: "100%"});

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", StudentsInClasses.initOptions());
    StudentsInClasses.table = jqGrid.init();

});