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
                <h2>报名汇总</h2>
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
                                        <label>姓名</label>
                                        <input type="text" class="form-control" id="name" style="width: 150px;">
                                    </div>
                                    &nbsp&nbsp&nbsp
                                    <div class="form-group">
                                        <label>年份</label>
                                        <select class="form-control" id="year">
                                            <option value="" class="option_year" ></option>
                                            <option value="2021" class="2021" >2021</option>
                                            <option value="2022" class="2022" >2022</option>
                                            <option value="2023" class="2023" >2023</option>
                                            <option value="2024" class="2024" >2024</option>
                                            <option value="2025" class="2025" >2025</option>
                                            <option value="2026" class="2026" >2026</option>
                                            <option value="2027" class="2027" >2027</option>
                                            <option value="2028" class="2028" >2028</option>
                                            <option value="2029" class="2029" >2029</option>
                                            <option value="2030" class="2030" >2030</option>
                                        </select>
                                    </div>
                                    &nbsp&nbsp&nbsp
                                    <div class="form-group">
                                        <label>月份</label>
                                        <select class="form-control" id="month">
                                            <option value=""  class="option_month" ></option>
                                            <option value="1"  class="1" >1</option>
                                            <option value="2"  class="2" >2</option>
                                            <option value="3"  class="3" >3</option>
                                            <option value="4"  class="4" >4</option>
                                            <option value="5"  class="5" >5</option>
                                            <option value="6"  class="6" >6</option>
                                            <option value="7"  class="7" >7</option>
                                            <option value="8"  class="8" >8</option>
                                            <option value="9"  class="9" >9</option>
                                            <option value="10" class="10" >10</option>
                                            <option value="11" class="11" >11</option>
                                            <option value="12" class="12" >12</option>
                                            <option value="13" class="13" >上半年度</option>
                                            <option value="14" class="14" >下半年度</option>
                                            <option value="15" class="15" >年度</option>

                                        </select>
                                    </div>
                                    &nbsp&nbsp&nbsp


                                    <button class="btn btn-success"  id="search" type="button" onclick="DeleteClass.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="DeleteClass.resetSearch()">重置</button>&nbsp
<#--                                    <button class="btn btn-primary" type="button" onclick="Students.create()">新增</button>-->
                                    &nbsp&nbsp&nbsp

                                    <#--<button class="btn btn-primary" type="button" onclick="EmployeeMachine.chooseEmail('CZ')">选择常州发件邮箱</button>-->
                                    <#--<button class="btn btn-primary" type="button" onclick="EmployeeMachine.chooseEmail('CQ')">选择重庆发件邮箱</button>-->

                                </div>
                            </div>
                            </div>
                            <div class="ibox-content">
                                <div class="bg-primary p-xs b-r-sm" id="totalFee"></div>
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
<script src="/static/modular/fee/deleteClass/deleteClass.js"></script>

<script type="text/javascript">
    $(document).ready(function(){

    });
</script>
</body>
</html>
