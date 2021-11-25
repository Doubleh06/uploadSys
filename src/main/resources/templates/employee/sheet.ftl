<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>上传管理</title>
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
    <link href="/static/css/plugins/clockpicker/clockpicker.css" rel="stylesheet">
    <#--<link href="/static/css/animate.css" rel="stylesheet">-->
    <#--<link href="/static/css/style.css" rel="stylesheet">-->

    <link href="/static/css/plugins/dropzone/dropzone.css" rel="stylesheet">
    <link href="/static/css/plugins/chosen/bootstrap-chosen.css" rel="stylesheet">
    <#--<link rel="stylesheet" href="/static/css/bootstrap-datetimepicker.min.css" />-->
    <#--<link href="/static/css/plugins/datapicker/datepicker3.css" rel="stylesheet">-->
    <#--<link href="/static/css/plugins/clockpicker/clockpicker.css" rel="stylesheet">-->
    <link href="/static/css/style.css" rel="stylesheet">

    <style>
        .loginColumns{max-width:655px !important;}
        /*.form-control{padding:20px 12px !important;}*/
        .setBtnStyle{padding: 12px 12px !important;}
        .ibox-content{padding:30px 20px 30px 20px !important;}
        .titleBox{margin-bottom: 30px;text-align: center;}
    </style>
</head>

<body class="gray-bg">

    <div class="loginColumns animated fadeInDown">
        <div class="row">

            <div class="col-md-12">
                <h2 class="font-bold titleBox">员工安全事故报告</h2>
                <div class="ibox-content">
                    <form class="form-horizontal"  id="create-form" enctype="multipart/form-data" method="post" >
                        <input type="hidden" class="form-control"  id="uuid" name="uuid"  value="${uuid}" >
                        <div class="form-group">
                            <label class="col-sm-2 control-label">事故种类</label><span style="color: red">*</span>
                            <div class="col-sm-4">
                                <select class="form-control" name="accidentType" id="accidentType">
                                    <#--<option value="" selected>---请选择---</option>-->
                                   <#list accidentTypes as accidentType>
                                       <option value="${accidentType.id}">${accidentType.name}</option>
                                   </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">事发时间</label><span style="color: red">*</span>
                            <div class="col-sm-2">
                                <div class="input-group date">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span><input id="date" name="date" type="text" class="form-control" placeholder="MM/dd/yyyy">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-2">
                                <div class="input-group clockpicker" data-autoclose="true">
                                    <input type="text" class="form-control" placeholder="xx:xx" id="time" name="time">
                                    <span class="input-group-addon">
                                    <span class="fa fa-clock-o"></span>
                                </span>
                                </div>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 control-label">涉及人员</label>
                            <div class="col-sm-10">
                                <input id="accidentMan" type="text" class="form-control" name="accidentMan">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">人员所在地区</label><span style="color: red">*</span>
                            <div class="col-sm-10">
                                <select class="form-control" name="address" id="address">
                                       <option value="CZ" selected>常州</option>
                                       <option value="CQ">重庆</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">人员所在部门/人员接待部门</label><span style="color: red">*</span>
                            <div class="col-sm-10">
                                <select class="form-control" name="dept" id="dept">
                                    <#--<option value="" selected>---请选择---</option>-->
                                   <#list depts as dept>
                                       <option value="${dept.dept}">${dept.dept}</option>
                                   </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">事发地点</label><span style="color: red">*</span>
                            <div class="col-sm-10">
                                <input id="accidentPlace" type="text" class="form-control" name="accidentPlace">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">事发情况</label><span style="color: red">*</span>
                            <div class="col-sm-10">
                                <input id="accidentSituation" type="text" class="form-control" name="accidentSituation">
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-2 control-label">汇报人</label><span style="color: red">*</span>
                            <div class="col-sm-10">
                                <input id="reportMan" type="text" class="form-control" name="reportMan">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">汇报人工号</label><span style="color: red">*</span>
                            <div class="col-sm-10">
                                <input id="workNum" type="text" class="form-control" name="workNum">
                            </div>
                        </div>

                        <div class="form-group">
                            &nbsp&nbsp&nbsp&nbsp&nbsp<span style="color: red">带*号的均为必填项</span>
                        </div>

                    </form>
                    <div class="col-sm-10 col-sm-offset-2">
                        <form action="#" class="dropzone " id="dropzoneForm">
                            <div class="fallback">
                                <input name="subImgs" type="file"  multiple />
                            </div>
                        </form>
                    </div>
                    <#--<input id="test" type="text">-->
                    <div class="col-sm-4 col-sm-offset-2">
                        <br>
                        <button type="button" class="btn btn-sm btn-primary" onclick="Sheet.insert()">确定提交</button>
                        <button type="button" class="btn btn-sm btn-primary" onclick="Sheet.reset()">重   置</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "/templates/layout/commonjs.ftl">
    <script src="/static/js/plugins/chosen/chosen.jquery.js"></script>
    <script src="/static/js/plugins/datapicker/bootstrap-datepicker.js"></script>
    <script src="/static/js/plugins/clockpicker/clockpicker.js"></script>
    <script src="/static/modular/sheet/sheet.js"></script>
    <script src="/static/js/plugins/dropzone/dropzone.js"></script>


    <script type="text/javascript">
        Dropzone.autoDiscover = false;
        var myDropzone ;
        $("#dropzoneForm").dropzone({
            url: "/employee/uploadFile?uuid=${uuid}", //必须填写
            method: "post",  //也可用put
            paramName: "files", //默认为file
            maxFiles: 5,//一次性上传的文件数量上限
            maxFilesize: 50, //MB
            acceptedFiles: ".jpg,.png,.jpeg", //上传的类型
            parallelUploads: 3,
            addRemoveLinks: true,
            uploadMultiple:true,//是否在一个请求中发送多个文件
            dictMaxFilesExceeded: "您最多只能上传10个文件！",
            dictResponseError: '文件上传失败!',
            dictRemoveFile:'移除文件',
            dictInvalidFileType: "你不能上传该类型文件,文件类型只能是*.jpg,*.png,*.jpeg",
            dictFallbackMessage: "浏览器不受支持",
            dictFileTooBig: "文件过大上传文件最大支持.",
            dictDefaultMessage: "<strong>点击此处上传图片 上传图片最大不能超过5M</strong>",
            init: function () {
                myDropzone=this;
                this.on("addedfile", function (file) {
                    //上传时触发的方法
                });
                this.on("queuecomplete", function (file) {
                    //上传完成后触发的方法
                });
                this.on("removedfile", function (file) {
                    //删除文件时触发的方法
                    // alert(file.name);
                    $.get("/employee/deleteFile?imgSourceName=" + file.name+"&uuid=${uuid}", function () {
                        success("图片删除成功");
                    });
                });
            },
            success: function(file, data) {
                // console.log(file.name);
                // console.log(data.obj);
                $("#subImgs").val(data.obj);
            }

        });
        $(document).ready(function() {
            $('#date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                calendarWeeks: true,
                autoclose: true
            });
            $("#address").change(function () {
                    $.ajax({
                        url:"/employee/address?address="+$("#address").val(),
                        type: 'GET',
                        contentType: "application/json",
                        success:function (r) {
                            var depts = r.obj;
                            $("#dept").empty();
                            var option = "";
                            for(var i=0;i<depts.length;i++){
                                option += "<option  value='"+depts[i].dept+"'>"+depts[i].dept+"</option>";
                            }
                            $("#dept").append(option);
                        }
                    });
            });
        });
        $('.clockpicker').clockpicker();
    </script>
</body>

</html>
