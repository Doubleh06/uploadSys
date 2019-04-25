<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
<#include "/templates/layout/meta.ftl">
    <link href="/static/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/js/plugins/pictureView/css/pictureViewer.css">
</head>

<body>
<div id="wrapper">
<#include "/templates/layout/left.ftl">
    <div id="page-wrapper" class="gray-bg">
    <#include "/templates/layout/header.ftl">

        <#--<div class="row wrapper border-bottom white-bg page-heading">-->
            <#--<div class="col-lg-10">-->
                <#--<h2>故障列表</h2>-->
                <#--<ol class="breadcrumb">-->
                    <#--<li>-->
                        <#--<a href="/main">Home</a>-->
                    <#--</li>-->
                    <#--<li class="active">-->
                        <#--<strong>列表</strong>-->
                    <#--</li>-->
                <#--</ol>-->
            <#--</div>-->
            <#--<div class="col-lg-2">-->

            <#--</div>-->
        <#--</div>-->

        <div class="wrapper wrapper-content">
            <div class="image-list">
                <#list imgUrls as imgUrl>
                <#--<a href="/picture/${imgUrl}" target="_blank"><img src="/picture/${imgUrl}" style="width: 150px;height: 150px" ></a>-->
                <div class="cover"><img src="/picture/${imgUrl}" alt=""></div>
                </#list>

            </div>

            <#--<a href="/picture/2.png" target="_blank"><img src="/picture/2.png" style="width: 150px;height: 150px" ></a>-->
        </div>
    <#include "/templates/layout/footer.ftl">
    </div>
</div>

<#--分配角色弹框-->
<#include "/templates/layout/commonjs.ftl">
<script src="/static/js/plugins/pictureView/js/jquery.mousewheel.min.js"></script>
<script src="/static/js/plugins/pictureView/js/pictureViewer.js"></script>
<#--<script src="/static/modular/backstage/photos.js"></script>-->

<script type="text/javascript">
    $(document).ready(function(){
        $('.image-list').on('click', '.cover', function () {
            var this_ = $(this);
            var images = this_.parents('.image-list').find('.cover');
            var imagesArr = new Array();
            $.each(images, function (i, image) {
                imagesArr.push($(image).children('img').attr('src'));
            });
            $.pictureViewer({
                images: imagesArr, //需要查看的图片，数据类型为数组
                initImageIndex: this_.index() + 1, //初始查看第几张图片，默认1
                scrollSwitch: true //是否使用鼠标滚轮切换图片，默认false
            });
        });
    });
</script>
</body>
</html>
