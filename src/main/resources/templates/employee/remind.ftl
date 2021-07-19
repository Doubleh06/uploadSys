<!DOCTYPE html>
<html>

<head>
<#include "/templates/layout/meta.ftl">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>脑立方</title>
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
                <h2 class="font-bold titleBox">温馨提醒：</h2>
                <h3 class="font-bold titleBox">如果您使用的是苹果手机请按下方继续按钮</h3>
                <br>
                <h3 class="font-bold titleBox">如果您使用的是安卓手机，请勿用微信扫码，请下载火狐或其他浏览器，再继续使用</h3>
                <div class="titleBox">
                    <button type="button" class="btn btn-sm btn-primary " id="jump" >继续</button>
                </div>

            </div>
        </div>
    </div>
    <#include "/templates/layout/commonjs.ftl">


    <script type="text/javascript">

        $(document).ready(function() {
            $("#jump").click(function () {
                window.location.href="/employee/remind";
                // window.location.href="/employee/sheet";
            });

        });
    </script>
</body>

</html>
