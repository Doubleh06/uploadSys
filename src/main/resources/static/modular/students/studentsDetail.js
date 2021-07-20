var StudentsDetail = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "studentsDetail"
};

/**
 * jqGrid初始化参数
 */
StudentsDetail.initOptions = function () {
    var options = {
        url : "/students/detail/grid",
        autowidth:true,
        postData : {
            id : $("#id").val(),
            status:$("#status").val()
        },
        colNames: ['姓名','课程名','上课日期','上课时间','时长','缴费日期','状态','消课时间','消课原因','操作'],
        colModel: [
            // {name: 'id', index: 'id', width: 20},
            {name: 'studentName', index: 'studentName', width: 80},
            {name: 'className', index: 'className', width: 80},
            {name: 'date', index: 'date', width: 80,formatter(cellValue, options, rowObject){
                    if (cellValue == "" || cellValue == undefined) {
                        return "";
                    }
                    var da = new Date(cellValue);
                    return dateFtt("yyyy-MM-dd", da);
                }},
            {name: 'spare', index: 'spare', width: 100},
            {name: 'duration', index: 'duration', width: 30},
            {name: 'createTime', index: 'createTime', width: 150,formatter(cellValue, options, rowObject){
                    if (cellValue == "" || cellValue == undefined) {
                        return "";
                    }
                    var da = new Date(cellValue);
                    return dateFtt("yyyy-MM-dd", da);
                }},
            {name: 'status', index: 'status', width: 60,formatter:function(cellValue, options, rowObject){
                if(1==cellValue){
                    return "已消课";
                }else if(0==cellValue){
                    return "未消课";
                }else{
                    return "";
                }
            }},
            {name: 'deleteDate', index: 'deleteDate', width: 80,formatter(cellValue, options, rowObject){
                    if (cellValue == "" || cellValue == undefined) {
                        return "";
                    }
                    var da = new Date(cellValue);
                    return dateFtt("yyyy-MM-dd", da);
                }},
            {name: 'reason', index: 'reason', width: 80,formatter:function(cellValue, options, rowObject){
                var html = '';

                    if (cellValue != 0) {
                        switch (cellValue) {
                            case 1:return "常规课";break;
                            case 2:return "比赛";break;
                            case 3:return "考级";break;
                            case 4:return "补课";break;
                            case 5:return "特色课";break;
                            case 6:return "其他";break;
                        }
                    }else{
                        html += '<select id="reason'+rowObject["id"]+'">' +
                            '<option value="1" name="1">常规课</option>' +
                            '<option value="2" name="2">比赛</option>' +
                            '<option value="3" name="3">考级</option>' +
                            '<option value="4" name="4">补课</option>' +
                            '<option value="5" name="5">特色课</option>' +
                            '<option value="6" name="6">其他</option>' +
                            '</select>'
                        return html;
                    }
                }},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var status = rowObject["status"];

                var str = "";
                    if (1 != status) {
                        str += '<input type="button" class=" btn btn-sm btn-info"  value="消  课" onclick="StudentsDetail.deleteClass(' + id + ')"/>&nbsp;';
                    }

                // str += '<input type="button" class=" btn btn-sm btn-danger"  value="删除" onclick="StudentsDetail.delete(' + id + ')"/>';
                return str;
            }}
        ]
    };
    return options;
};

/**
 * 根据关键词搜索
 */
StudentsDetail.search = function () {
    var searchParam = {};
    searchParam.className = $("#classesName").val();
    searchParam.status = $("#status").val();
    searchParam.year = $("#year").val();
    searchParam.month = $("#month").val();
    StudentsDetail.table.reload(searchParam);
};

// /**
//  * 重置搜索
//  */
StudentsDetail.resetSearch = function () {
    $("#classesName").val("");
    $(".option_1").attr("selected",true);
    $("#year").val("");
    $("#month").val("");
    StudentsDetail.search();
    $(".option_1").attr("selected",false);

};

// /**
//  *新增
//  */
// StudentsDetail.create = function () {
//     $("#createModal").modal();
// }
StudentsDetail.deleteClass = function (id) {
    warning("确定消课吗", "", function () {
        $.ajax({
            url: "/students/detail/deleteClass?id=" + id+"&reasonValue="+$("#reason"+id).val(),
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (r) {
                if (r.code === 0) {
                    success("消课成功");
                    StudentsDetail.search();
                }
            }
        })
    });


}

// /**
//  *编辑
//  */
// StudentsDetail.modify = function (id) {
//     $.ajax({
//         url: "/students/get?id=" + id,
//         type: 'GET',
//         dataType: "json",
//         success: function (r) {
//             if (r.code === 0) {
//                 var students = r.obj;
//                 var form = $("#modify-form");
//                 form.find("input[name='id']").val(students.id);
//                 form.find("input[name='name']").val(students.name);
//                 form.find("input[name='phone']").val(students.phone);
//                 form.find("input[name='school']").val(students.school);
//                 form.find("input[name='parentsName']").val(students.parentsName);
//                 form.find("input[name='fee']").val(students.fee);
//                 $("#modifyModal").modal();
//             }
//         }
//     })
//     $("#modifyModal").modal();
// }
// StudentsDetail.update = function () {
//     var students = getFormJson($("#modify-form"));
//     $.ajax({
//         url: "/students/update",
//         type: 'POST',
//         data: JSON.stringify(students),
//         contentType: "application/json;charset=utf-8",
//         dataType: "json",
//         success: function (r) {
//             if (r.code === 0) {
//                 $("#modifyModal").modal("hide");
//                 success("编辑成功");
//                 StudentsDetail.search();
//                 $("#modify-form")[0].reset();
//             }
//         }
//     })
// }


// /**
//  * 删除
//  *
//  * @param id    userId
//  */
// StudentsDetail.delete = function del(id) {
//     warning("确定删除吗", "", function () {
//         $.get("/students/delete?id=" + id, function () {
//             success("成功删除");
//             StudentsDetail.search();
//         });
//     })
// };
//
// /**
//  * 课程跳转
//  * @param fmt
//  * @param date
//  * @returns {void | string | *}
//  */
// StudentsDetail.classes = function(id){
//     window.location.href = '/students/listDetail?id='+id;
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

$(function() {
    // $('.chosen-select').chosen({width: "100%"});
    var jqGrid = new JqGrid("#grid-table", "#grid-pager", StudentsDetail.initOptions());
    StudentsDetail.table = jqGrid.init();

});