<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/style.css" rel="stylesheet">
    <link href="/static/css/plugins/switchery/switchery.css" rel="stylesheet">
    <link href="/static/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="/static/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/plugins/ladda/ladda-themeless.min.css" rel="stylesheet">
    <link href="/static/css/plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet">
    <link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">

</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>汽车之家高质数据列表</h2>
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
                                    <div class="form-group col-lg-12">
                                        <label>手机号：</label>
                                        <input type="text" class="form-control" id="mobile" >
                                        <label>状态</label>
                                        <select class="form-control" id="status">
                                            <option class="option_1" value="" >全部</option>
                                            <option value="0">成功</option>
                                            <option value="1">失败</option>
                                        </select>

                                        <label>分发状态</label>
                                        <select class="form-control" id="distributeStatus">
                                            <option class="option_3" value="" >全部</option>
                                            <option value="0">未分发</option>
                                            <option value="1">已分发</option>
                                        </select>

                                        <label>申诉状态</label>
                                        <select class="form-control" id="appealStatus">
                                            <option class="option_4" value="" >全部</option>
                                            <option value="0">未申诉或申诉失败</option>
                                            <option value="1">申诉成功</option>
                                        </select>

                                        <label>过滤状态</label>
                                        <select class="form-control" id="filterateStatus">
                                            <option class="option_5" value="" >全部</option>
                                            <option value="0">清洗中</option>
                                            <option value="1">通过</option>
                                            <option value="2">不通过</option>
                                        </select>

                                        <label>质检状态</label>
                                        <select class="form-control" id="checkStatus">
                                            <option class="option_6" value="" >全部</option>
                                            <option value="0">未知</option>
                                            <option value="1">成功</option>
                                            <option value="2">失败</option>
                                        </select>

                                        <label>项目号</label>
                                        <select class="form-control" id="appid">
                                            <option class="option_2" value="" >全部</option>
                                            <option value="1312">1312</option>
                                            <option value="1313">1313</option>
                                            <option value="1408">1408</option>
                                        </select>
                                        <p>&nbsp;</p>
                                        <div class="form-group" id="data_5">
                                            <label class="font-normal">选择日期：</label>
                                            <div class="input-daterange input-group" id="datepicker">
                                                <input type="text" class="input-sm form-control" id="startDate" name="start" />
                                                <span class="input-group-addon">to</span>
                                                <input type="text" class="input-sm form-control" id="endDate" name="end" />
                                            </div>
                                        </div>
                                        <button class="btn btn-success"  id="search" type="button" onclick="QczjHQ.search()">搜索</button>&nbsp
                                        <button class="btn btn-success" type="button" onclick="QczjHQ.resetSearch()">重置</button>&nbsp
                                        <button class="btn btn-primary" type="button" onclick="QczjHQ.download()">下载</button>
                                    </div>
                                    <p>&nbsp;</p>
                                    <div class="form-group col-lg-12">
                                        <form  enctype="multipart/form-data" id="uploadForm">
                                            <label>文件上传：</label>
                                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                                <span class="btn btn-default btn-file"><span class="fileinput-new">选择文件</span><span class="fileinput-exists">更改文件</span><input id="uploadInput" type="file" name="file"></span>
                                                <span class="fileinput-filename" id="fileinput-filename"></span>
                                                <a href="#" class="close fileinput-exists" data-dismiss="fileinput" style="float: none">&times;</a>
                                            </div>
                                            <button id="btn" class="btn btn-primary ladda-button" type="submit" >提交</button>
                                        </form>
                                    </div>
                                    <p>&nbsp;</p>
                                    <div class="form-group col-lg-12">
                                        <button class="btn btn-warning" type="button" onclick="QczjHQ.downloadCity()">下载城市模版</button>
                                        <button class="btn btn-warning" type="button" onclick="QczjHQ.downloadCar()">下载车辆信息版</button>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <br>
                            <br>

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


<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/plugins/ladda/spin.min.js"></script>
<script src="/static/js/plugins/ladda/ladda.min.js"></script>
<script src="/static/js/plugins/ladda/ladda.jquery.min.js"></script>
<script src="/static/js/plugins/jasny/jasny-bootstrap.min.js"></script>
<script src="/static/modular/qczj/qczjHQ.js"></script>
<script src="/static/js/plugins/daterangepicker/daterangepicker.js"></script>
<script src="/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>



<script type="text/javascript">
    $(document).ready(function() {
        $('#data_5 .input-daterange').datepicker({
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true,
            format:"yyyy-mm-dd",
            // todayBtn : true,
            todayHighlight : true,
            language : "zh-CN"
        });
    });




</script>
</body>
</html>
