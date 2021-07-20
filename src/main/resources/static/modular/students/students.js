var Students = {
    tableId: "#grid-table",
    pagerId: "#grid-pager",
    table: null,
    domain: "students"
};

/**
 * jqGrid初始化参数
 */
Students.initOptions = function () {
    var options = {
        url : "/students/grid",
        autowidth:true,
        colNames: ['id','姓名','联系电话','学校','操作'],
        colModel: [
            {name: 'id', index: 'id', width: 20},
            {name: 'name', index: 'name', width: 80},
            {name: 'phone', index: 'phone', width: 150},
            {name: 'school', index: 'school', width: 150},
            // {name: 'parentsName', index: 'parentsName', width: 100},
            // {name: 'fee', index: 'fee', width: 100},
            {name: 'operations', index: 'operations', width: 150, sortable: false, formatter: function (cellValue, options, rowObject) {
                var id = "'"+rowObject["id"]+"'";
                var str = "";
                str += '<input type="button" class=" btn btn-sm btn-info"  value="编  辑" onclick="Students.modify(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-warning"  value="删  除" onclick="Students.delete(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-primary"  value="消  课" onclick="Students.classes(' + id + ')"/>&nbsp;';
                str += '<input type="button" class=" btn btn-sm btn-success"  value="课程查询" onclick="Students.show(' + id + ')"/>&nbsp;';
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
Students.search = function () {
    var searchParam = {};
    searchParam.name = $("#name").val();
    searchParam.phone = $("#phone").val();
    Students.table.reload(searchParam);
};

/**
 * 重置搜索
 */
Students.resetSearch = function () {
    $("#name").val("");
    $("#phone").val("");
    Students.search();
};

/**
 *新增
 */
Students.create = function () {
    $("#createModal").modal();
}
Students.insert = function () {
    var students = getFormJson($("#create-form"));
    $.ajax({
        url: "/students/insert",
        type: 'POST',
        data: JSON.stringify(students),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#createModal").modal("hide");
                success("保存成功");
                Students.search();
                $("#create-form")[0].reset();
            }
        }
    })
}

/**
 *编辑
 */
Students.modify = function (id) {
    $.ajax({
        url: "/students/get?id=" + id,
        type: 'GET',
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                var students = r.obj;
                var form = $("#modify-form");
                form.find("input[name='id']").val(students.id);
                form.find("input[name='name']").val(students.name);
                form.find("input[name='phone']").val(students.phone);
                form.find("input[name='school']").val(students.school);
                form.find("input[name='parentsName']").val(students.parentsName);
                form.find("input[name='fee']").val(students.fee);
                $("#modifyModal").modal();
            }
        }
    })
    $("#modifyModal").modal();
}
Students.update = function () {
    var students = getFormJson($("#modify-form"));
    $.ajax({
        url: "/students/update",
        type: 'POST',
        data: JSON.stringify(students),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#modifyModal").modal("hide");
                success("编辑成功");
                Students.search();
                $("#modify-form")[0].reset();
            }
        }
    })
}


/**
 * 删除
 *
 * @param id    userId
 */
Students.delete = function del(id) {
    warning("确定删除吗", "", function () {
        $.get("/students/delete?id=" + id, function () {
            success("成功删除");
            Students.search();
        });
    })
};

/**
 * 课程跳转
 * @param fmt
 * @param date
 * @returns {void | string | *}
 */
Students.classes = function(id){
    window.location.href = '/students/detail/list?id='+id;
}

Students.show = function(id){
    $.ajax({
        url: "/students/show?id="+id,
        type: 'get',
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code == 0) {
                var obj = r.obj;
                var html = "";
                $("#showDiv").empty();
                if (obj.length>0){
                    for(var i=0;i<obj.length;i++){
                        html+='<div class="form-group">';
                        html+='<label class="col-sm-2 control-label">课程名</label>';
                        html+='<label class="col-sm-3 control-label text-danger">'+obj[i].className+'</label>';
                        html+='</div>';
                        html+='<div class="form-group">';
                        html+='<label class="col-sm-2 control-label">总课时</label>';
                        html+='<label class="col-sm-3 control-label text-danger">'+obj[i].total+'</label>';
                        html+='</div>';
                        html+='<div class="form-group">';
                        html+=' <label class="col-sm-2 control-label">已消课</label>';
                        html+='<label class="col-sm-3 control-label text-danger">'+obj[i].deleteClasses+'</label>';
                        html+='<label class="col-sm-2 control-label">未消课</label>';
                        html+=' <label class="col-sm-3 control-label text-danger">'+obj[i].noDeleteClasses+'</label>';
                        html+='</div>';

                    }
                }else{
                    html+='<div class="form-group">';
                    html+='<label class="col-sm-2 control-label">无课程</label>';
                    html+='</div>';
                }
                $("#showDiv").append(html);
                $("#showModal").modal();
            }
        }
    })

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

    var jqGrid = new JqGrid("#grid-table", "#grid-pager", Students.initOptions());
    Students.table = jqGrid.init();

});