var ClassesDetail = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "classesDetail"
};

/**
 * jqGrid初始化参数
 */
ClassesDetail.initOptions = function () {
    var options = {
        url : "/classes/arrangeStudents/grid",
        autowidth:true,
        postData : {
            id : $("#id").val()
        },
        colNames: ['id','课程名称','讲师','学生姓名','学生家长姓名','联系电话','操作'],
        colModel: [
            {name: 'csid', index: 'csid', width: 20},
            {name: 'name', index: 'name', width: 150},
            {name: 'teacher', index: 'teacher', width: 150},
            {name: 'sname', index: 'sname', width: 150},
            {name: 'parents_name', index: 'parents_name', width: 150},
            {name: 'phone', index: 'phone', width: 150},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var sid = "'"+rowObject["sid"]+"'";
                var cid = "'"+rowObject["cid"]+"'";

                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="移除学员" onclick="ClassesDetail.delete(' + sid+","+cid + ')"/>&nbsp;';
                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="ClassesDetail.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
ClassesDetail.search = function () {
    var searchParam = {};
    searchParam.id = $("#id").val();
    ClassesDetail.table.reload(searchParam);
};

/**
 * 重置搜索
 */
ClassesDetail.resetSearch = function () {
    ClassesDetail.search();
};

/**
 *新增
 */
ClassesDetail.create = function () {
    var cid = $("#id").val();
    var sid = $("#students").val();
    if(null==sid||""==sid){
        error("必须选中相应学生");
        return;
    }
    var date = $("#date").val();
    $.ajax({
        url: "/classes/arrangeStudents/insert",
        data: JSON.stringify({
            cid:cid,
            sid:sid,
            date:date
        }),
        type: 'POST',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                successthen("添加成功",null,"/classes/arrangeStudents/list?id="+cid);
            }
        }
    })
}




/**
 * 删除
 *
 * @param id    userId
 */
ClassesDetail.delete = function del(sid,cid) {
    warning("确定移除吗", "", function () {
        $.get("/classes/arrangeStudents/delete?sid=" + sid+"&cid="+cid, function () {
            success("移除成功");
            ClassesDetail.search();
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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", ClassesDetail.initOptions());
    ClassesDetail.table = jqGrid.init();

});