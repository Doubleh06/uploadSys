<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />
    <link href="/static/css/plugins/select2/select2.min.css" rel="stylesheet">
    <link href="/static/css/plugins/clockpicker/clockpicker.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
    <link href="/static/css/plugins/ladda/ladda-themeless.min.css" rel="stylesheet">

</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <#if classes ??>
                    <h2>修改</h2>
                <#else >
                    <h2>新建</h2>
                </#if>

                <ol class="breadcrumb">
                    <li>
                        <a href="/main">Home</a>
                    </li>
                    <li>
                        <a href="/classes/list">课程列表</a>
                    </li>
                    <li class="active">
                        <#if classes ??>
                            <strong>修改</strong>
                        <#else >
                            <strong>新建</strong>
                        </#if>

                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>

        <div class="wrapper wrapper-content">
            <div class="row">
                <div class="col-lg-12">
                    <form class="form-horizontal"  id="create-form" enctype="multipart/form-data" method="post" >

                        <div class="form-group">
                            <label class="col-sm-2 control-label">课程名</label>
                            <div class="col-sm-4">
                                <input id="name" type="text" class="form-control" name="name" value=<#if classes??>${classes.name}</#if>>
                                <input id="id" type="hidden" class="form-control" name="id" value=<#if classes??>${classes.id}</#if>>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-4">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" value="<#if classes??>${classes.startTime}</#if>" id="startTime" name="startTime">
                                    <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                                </span>
                                </div>
                            </div>
                            <label class="col-sm-2 control-label">结束时间</label>
                            <div class="col-sm-4">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" value="<#if classes??>${classes.endTime}</#if>" id="endTime" name="endTime">
                                    <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                                </span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">课程周期</label>
                            <div class="col-sm-4">
                                <select class="select2_demo_1 form-control" name="week" id="week">
                                    <option value="1">周一</option>
                                    <option value="2">周二</option>
                                    <option value="3">周三</option>
                                    <option value="4">周四</option>
                                    <option value="5">周五</option>
                                    <option value="6">周六</option>
                                    <option value="7">周日</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">讲师</label>
                            <div class="col-sm-4">
                                <input id="teacher" type="text" class="form-control" name="teacher" value=<#if classes??>${classes.teacher}</#if>>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">总课程数</label>
                            <div class="col-sm-4">
                                <input id="total" type="text" class="form-control" name="total" value=<#if classes??>${classes.total}</#if>>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">费用</label>
                            <div class="col-sm-4">
                                <input id="fee" type="text" class="form-control" name="fee" value=<#if classes??>${classes.fee}</#if>>
                            </div>
                        </div>
                    </form>
                    <div class="form-group">
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-4">
                            <button type="button" class="btn btn-sm btn-primary ladda-button" data-style="slide-left" onclick="Classes.insert(this)">提  交</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>

a
<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/plugins/ladda/spin.min.js"></script>
<script src="/static/js/plugins/ladda/ladda.min.js"></script>
<script src="/static/js/plugins/ladda/ladda.jquery.min.js"></script>
<script src="/static/js/plugins/chosen/chosen.jquery.js"></script>
<script src="/static/modular/action/action.js"></script>
<script src="/static/js/plugins/select2/select2.full.min.js"></script>
<script src="/static/js/plugins/clockpicker/clockpicker.js"></script>
<script src="/static/modular/classes/classes.js"></script>

<script type="text/javascript">
    $('.clockpicker').clockpicker();
    // $(".select2_demo_2").select2();
    $(".select2_demo_1").select2();
</script>
</body>
</html>
