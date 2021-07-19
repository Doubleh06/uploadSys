<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/style.css" rel="stylesheet">
    <link href="/static/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="/static/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">

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
                                    <div class="form-group">
                                        <label>课程名</label>
                                        <input type="text" class="form-control" id="name" style="width: 150px;">
                                    </div>
                                    &nbsp&nbsp&nbsp
                                    <div class="form-group">
                                        <label>讲师</label>
                                        <input type="text" class="form-control" id="teacher" style="width: 150px;">
                                    </div>
                                    &nbsp&nbsp&nbsp
                                    <div class="form-group">
                                        <label>上课周期</label>
                                        <select class="form-control" id="week">
                                            <option class="option_1" value="" >全部</option>
                                            <option value="1">周一</option>
                                            <option value="2">周二</option>
                                            <option value="3">周三</option>
                                            <option value="4">周四</option>
                                            <option value="5">周五</option>
                                            <option value="6">周六</option>
                                            <option value="7">周日</option>
                                        </select>
                                    </div>
                                    &nbsp&nbsp&nbsp



                                    <button class="btn btn-success"  id="search" type="button" onclick="Classes.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="Classes.resetSearch()">重置</button>&nbsp
                                    <button class="btn btn-primary" type="button" onclick="Classes.create()">新增</button>
                                    &nbsp&nbsp&nbsp
                                    <#--<button class="btn btn-primary" type="button" onclick="EmployeeMachine.chooseEmail('CZ')">选择常州发件邮箱</button>-->
                                    <#--<button class="btn btn-primary" type="button" onclick="EmployeeMachine.chooseEmail('CQ')">选择重庆发件邮箱</button>-->

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
<#--                        <label class="col-sm-2 control-label">课程名</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="text" class="form-control" name="name">-->
<#--                        </div>-->
<#--                    </div>-->

<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">开始时间</label>-->
<#--                        <div class="col-sm-4">-->
<#--                            <input type="text" class="form-control" name="startTimeH">-->
<#--                        </div>-->
<#--                        <div class="col-sm-1">-->
<#--                            <label class="col-sm-1 control-label">:</label>-->
<#--                        </div>-->
<#--                        <div class="col-sm-5">-->
<#--                            <input type="text" class="form-control" name="startTimeM">-->
<#--                        </div>-->
<#--                    </div>-->

<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">结束时间</label>-->
<#--                        <div class="col-sm-4">-->
<#--                            <input type="text" class="form-control" name="endTimeH">-->
<#--                        </div>-->
<#--                        <div class="col-sm-1">-->
<#--                            <label class="col-sm-1 control-label">:</label>-->
<#--                        </div>-->
<#--                        <div class="col-sm-5">-->
<#--                            <input type="text" class="form-control" name="endTimeM">-->
<#--                        </div>-->

<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">讲师</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="text" class="form-control" name="teacher">-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">上课周期</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <select class="form-control" name="week">-->
<#--                                <option value="1">周一</option>-->
<#--                                <option value="2">周二</option>-->
<#--                                <option value="3">周三</option>-->
<#--                                <option value="4">周四</option>-->
<#--                                <option value="5">周五</option>-->
<#--                                <option value="6">周六</option>-->
<#--                                <option value="7">周日</option>-->
<#--                            </select>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </form>-->

<#--            </div>-->
<#--            <div class="modal-footer">-->
<#--                <button type="button" class="btn btn-sm btn-primary" onclick="SignUp.insert()">确定</button>-->
<#--                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->
<#--            </div>-->
<#--        </div><!-- /.modal-content &ndash;&gt;-->
<#--    </div><!-- /.modal &ndash;&gt;-->
<#--</div>-->
<#--编辑弹框-->
<#--<div class="modal fade" id="modifyModal" tabindex="-1"  role="dialog" aria-labelledby="modalTitle" aria-hidden="true">-->
<#--    <div class="modal-dialog">-->
<#--        <div class="modal-content">-->
<#--            <div class="modal-header">-->
<#--                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
<#--                <h4 class="modal-title" id="modalTitle">编辑</h4>-->
<#--            </div>-->
<#--            <div class="modal-body">-->
<#--                <form class="form-horizontal" id="modify-form">-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">课程名</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="text" class="form-control" name="name">-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">开始时间</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="text" class="form-control" name="startTime">-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">结束时间</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="text" class="form-control" name="endTime">-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">讲师</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="text" class="form-control" name="teacher">-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="form-group">-->
<#--                        <label class="col-sm-2 control-label">上课周期</label>-->
<#--                        <div class="col-sm-10">-->
<#--                            <input type="text" class="form-control" name="week">-->
<#--                        </div>-->
<#--                    </div>-->

<#--                </form>-->

<#--            </div>-->
<#--            <div class="modal-footer">-->
<#--                <button type="button" class="btn btn-sm btn-primary" onclick="SignUp.update()">确定</button>-->
<#--                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->
<#--            </div>-->
<#--        </div><!-- /.modal-content &ndash;&gt;-->
<#--    </div><!-- /.modal &ndash;&gt;-->
<#--</div>-->
<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/modular/classes/classes.js"></script>

<script type="text/javascript">
    $(document).ready(function() {

    });

</script>
</body>
</html>
