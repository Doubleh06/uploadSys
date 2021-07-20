<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/style.css" rel="stylesheet">
    <link href="/static/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="/static/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>学生信息列表</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <li>
                        <a href="/classes/list">课程列表</a>
                    </li>
                    <li class="active">
                        <strong>列表</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>

        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox ">
                        <div class="ibox-content">
                                <div class="bar search-bar">
                                    <div class="form-inline">
                                        <input type="hidden" class="form-control" id="id" style="width: 150px;" >

                                    </div>
                                </div>
                                <div class="row" id="data_1">
                                    <div class="col-md-2">
                                        <label>学生姓名</label>
                                        <select class="select2_demo_1 form-control"  id="students">
                                        </select>
                                    </div>
                                    <label>第一次上课日期</label>
                                    <div class="input-group date col-md-2">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="date" type="text" class="form-control" value="${today}">
                                    </div>
                                    <div class="col-md-2">
                                        <button class="btn btn-primary" type="button" onclick="ClassesDetail.create()">新增</button>
                                    </div>

                                </div>

                            </div>
                            <div class="jqGrid_wrapper">
                            <#--jqgrid 表格栏-->
                                <table id="grid-table"></table>
                            <#--jqgrid 分页栏-->
                                <div id="grid-pager"></div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>
<#--新增弹框-->
<#--<div class="modal fade" id="createModal" tabindex="-1"  role="dialog" aria-labelledby="modalTitle" aria-hidden="true">-->
<#--    <div class="modal-dialog">-->
<#--        <div class="modal-content">-->
<#--            <div class="modal-header">-->
<#--                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
<#--                <h4 class="modal-title" id="modalTitle">新增</h4>-->
<#--            </div>-->
<#--            <div class="modal-body">-->
<#--                <form class="form-horizontal" id="create-form">-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">学生</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <select data-placeholder="请选择" class="chosen-select"  tabindex="2" id="students">-->
<#--                            </select>-->
<#--                        </div>-->
<#--                    </div>-->

<#--                </form>-->

<#--            </div>-->
<#--            <div class="modal-footer">-->
<#--                <button type="button" class="btn btn-sm btn-primary" onclick="ClassesDetail.insert()">确定</button>-->
<#--                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->
<#--            </div>-->
<#--        </div><!-- /.modal-content &ndash;&gt;-->
<#--    </div><!-- /.modal &ndash;&gt;-->
<#--</div>-->
<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script src="/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#id").val('${id}');
        $(".select2_demo_1").select2();
        $('#data_1 .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $.ajax({
            url: "/classes/arrangeStudents/get",
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            dataType: "json",
            success: function (r) {
                if (r.code === 0) {
                    var obj = r.obj;
                    $("#students").empty();
                    var option = "";
                    option += "<option value=''>"+"---请  选  择---"+"</option>";
                    for(var i=0;i<obj.length;i++){
                        option += "<option name='"+obj[i].id+"' value='"+obj[i].id+"'>"+obj[i].name+"</option>";
                    }
                    $("#students").append(option);
                }
            }
        })
    });

</script>
<script src="/static/modular/classes/classesDetail.js"></script>
</body>
</html>
