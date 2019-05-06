<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>申达经编</title>

    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="/static/css/animate.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
    <style>
        .loginColumns{max-width:555px !important;}
        .form-control{padding:20px 12px !important;}
        .setBtnStyle{padding: 12px 12px !important;}
        .ibox-content{padding:30px 20px 30px 20px !important;}
        .titleBox{margin-bottom: 30px;text-align: center;}
    </style>
</head>

<body class="gray-bg">

    <div class="loginColumns animated fadeInDown">
        <div class="row">

            <div class="col-md-12">
                <h2 class="font-bold titleBox">申达经编管理系统</h2>
                <div class="ibox-content">
                    <form class="m-t" role="form" action="/j_spring_security_check" method="post">
                        <div class="form-group">
                            <input type="text" name="username" class="form-control" placeholder="Username" required="">
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" class="form-control" placeholder="Password" required="">
                        </div>
                        <button type="submit" class="btn btn-primary block full-width m-b setBtnStyle">Login</button>
                        <#if msg??>
                            <span style="color: red;">${msg}</span>
                        </#if>
                    </form>
                </div>
            </div>
        </div>
        <hr/>
        <#--<div class="row">-->
            <#--<div class="col-md-6">-->
                <#--数赢云-->
            <#--</div>-->
            <#--<div class="col-md-6 text-right">-->
               <#--<small>© 2018-2018</small>-->
            <#--</div>-->
        <#--</div>-->
    </div>

</body>

</html>
