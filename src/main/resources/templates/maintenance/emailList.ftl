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
                <h2>发件邮箱列表</h2>
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
                                        <label>发件邮箱</label>
                                        <input type="text" class="form-control" id="name" style="width: 150px;">
                                    </div>
                                    &nbsp&nbsp&nbsp
                                    <div class="form-group">
                                        <label>地址</label>
                                        <select class="form-control" id="address">
                                            <option value="" >---请选择---</option>
                                            <option value="CZ" >常州</option>
                                            <option value="CQ">重庆</option>
                                        </select>
                                    </div>
                                    &nbsp&nbsp&nbsp

                                    <button class="btn btn-success"  id="search" type="button" onclick="Email.search()">搜索</button>&nbsp
                                    <button class="btn btn-success" type="button" onclick="Email.resetSearch()">重置</button>&nbsp
                                    <button class="btn btn-primary" type="button" onclick="Email.create()">新增</button>
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
<#--邮箱-->
<div class="modal fade" id="addressModal" tabindex="-1"  role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalTitle">选择邮箱</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="address-form">
                    <div class="form-group">
                        <div class="col-sm-2">
                            <input type="hidden" id="emailAddress" name="emailAddress">
                        </div>
                        <div class="col-sm-10">
                            <div class="radio" id="radio">
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-sm btn-primary" onclick="Email.changeEmail()">确定</button>
                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<#--重庆邮箱-->
<#--<div class="modal fade" id="cqModal" tabindex="-1"  role="dialog" aria-labelledby="modalTitle" aria-hidden="true">-->
    <#--<div class="modal-dialog">-->
        <#--<div class="modal-content">-->
            <#--<div class="modal-header">-->
                <#--<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>-->
                <#--<h4 class="modal-title" id="modalTitle">选择邮箱</h4>-->
            <#--</div>-->
            <#--<div class="modal-body">-->
                <#--<form class="form-horizontal" id="cq-form">-->
                    <#--<div class="form-group">-->
                        <#--<div class="col-sm-2">-->
                        <#--</div>-->
                        <#--<div class="col-sm-10">-->
                            <#--<div class="radio" id="czRadio">-->
                            <#--</div>-->
                        <#--</div>-->
                    <#--</div>-->


                <#--</form>-->

            <#--</div>-->
            <#--<div class="modal-footer">-->
                <#--<button type="button" class="btn btn-sm btn-primary" onclick="EmployeeMachine.insert()">确定</button>-->
                <#--<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>-->
            <#--</div>-->
        <#--</div><!-- /.modal-content &ndash;&gt;-->
    <#--</div><!-- /.modal &ndash;&gt;-->
<#--</div>-->
<#--新增弹框-->
<div class="modal fade" id="createModal" tabindex="-1"  role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalTitle">新增邮箱</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="create-form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpAuth</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="smtpAuth">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">transportProrocol</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="transportProrocol">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">sendCharset</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="sendCharset">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpPort</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="smtpPort">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">isSsl</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="isSsl">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">host</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="host">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">authName</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="authName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">authPassword</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="authPassword">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpTimeout</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="smtpTimeout">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpTimeout</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="address">
                                <option value="CZ" selected>常州</option>
                                <option value="CQ">重庆</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">ehs邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="ehsEmail">
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-sm btn-primary" onclick="Email.insert()">确定</button>
                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<#--编辑弹框-->
<div class="modal fade" id="modifyModal" tabindex="-1"  role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="modalTitle">编辑事故种类</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="modify-form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpAuth</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="smtpAuth">
                            <input type="hidden" class="form-control" name="id">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">transportProrocol</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="transportProrocol">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">sendCharset</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="sendCharset">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpPort</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="smtpPort">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">isSsl</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="isSsl">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">host</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="host">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">authName</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="authName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">authPassword</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="authPassword">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpTimeout</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="smtpTimeout">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">smtpTimeout</label>
                        <div class="col-sm-10">
                            <select class="form-control" name="address">
                                <option value="CZ" selected>常州</option>
                                <option value="CQ">重庆</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">ehs邮箱</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="ehsEmail">
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-sm btn-primary" onclick="Email.update()">确定</button>
                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/modular/maintenance/emailList.js"></script>
<script src="/static/js/plugins/switchery/switchery.js"></script>

<script type="text/javascript">
    $(document).ready(function(){

    });
</script>
</body>
</html>
