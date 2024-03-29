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
                <h2>人人车通用数据列表</h2>
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
                                        <input type="text" class="form-control" id="phone" >
                                        <label>状态</label>
                                        <select class="form-control" id="status">
                                            <option class="option_1" value="" >全部</option>
                                            <option value="0">入库成功</option>
                                            <option value="1">入库失败</option>
                                        </select>
                                        <div class="form-group" id="data_5">
                                            <label class="font-normal">选择日期：</label>
                                            <div class="input-daterange input-group" id="datepicker">
                                                <input type="text" class="input-sm form-control" id="startDate" name="start" />
                                                <span class="input-group-addon">to</span>
                                                <input type="text" class="input-sm form-control" id="endDate" name="end" />
                                            </div>
                                        </div>
                                        <button class="btn btn-success"  id="search" type="button" onclick="Common.search()">搜索</button>&nbsp
                                        <button class="btn btn-success" type="button" onclick="Common.resetSearch()">重置</button>&nbsp
                                        <button class="btn btn-primary" type="button" onclick="Common.download()">下载</button>
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
<script src="/static/modular/rrc/rrc.js"></script>
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
