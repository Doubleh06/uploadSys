//toastr消息提示插件配置
toastr.options = {
    closeButton: true,
    progressBar: true,
    showMethod: 'slideDown'
};

//全局的ajax访问，处理ajax清求时异常
$.ajaxSetup({
    complete:function(xmlHttpRequest){
        //通过XMLHttpRequest取得响应结果
        var jsonData = xmlHttpRequest.responseJSON;
        // console.log(jsonData);
        //如果是后台成功返回的result
        if (isServerResult(jsonData)) {
            //如果是401码，用户未认证，跳到登录页
            if (jsonData.code === 401) {
                window.location.href = "/login?expired";
            } else if (jsonData.code !== 0) {
                toastr.error(jsonData.msg, "error");
            }
        }
    }
});

function showError(errorMsg){
    toastr.error(errorMsg, "error");
}

function isServerResult(json) {
    return json !== null && json.hasOwnProperty("code") && json.hasOwnProperty("msg");
}

function getFormJson(frm) {
    var o = {};
    var a = $(frm).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });

    return o;
}

//sweet alert 警告框
function warning(title, text, fn) {
    swal({
        title: title,
        text: text,
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        closeOnConfirm: false
    }, fn);
}

function info(text) {
    swal(text);
}

//sweet alert 成功框
function success(title, text) {
    swal(title, text, "success");
}
//sweet alert 成功框
function successthen(title,text,url) {
    swal({
            title: title,
            text: "",
            type: "success",
            showCancelButton: false,
            confirmButtonColor: "#8CD4F5",
            confirmButtonText: "确定",
            closeOnConfirm: false
        },
        function(){
            window.location.href = url;
        });
}
function error(title,text) {
    swal({
            title: title,
            text: text,
            type: "error",
            showCancelButton: false,
            confirmButtonColor: "#8CD4F5",
            confirmButtonText: "确定",
            closeOnConfirm: false
        });
}
function input(title,text,fn){
    swal({
            title: title,
            text: text,
            type: "select",
            showCancelButton: true,
            closeOnConfirm: false,
            cancelButtonText: "取消",
            confirmButtonText: "确定",
            inputPlaceholder: "请输入...",
        },fn);
}



//页面遮罩
function waitMask() {
    $.blockUI({
        message: '<img src="/static/img/loading.gif">',
        css: {
            border: "none",
            backgroundColor: "none"
        }
    });
}

//解除页面遮罩
function clearMask() {
    $.unblockUI();
}



/*
 * 切换Switchery开关函数
 * * switchElement Switchery对象
 * * checkedBool 选中的状态
 */
function setSwitchery(switchElement, checkedBool) {
    if ((checkedBool && !switchElement.isChecked()) || (!checkedBool && switchElement.isChecked())) {
        switchElement.setPosition(true);
        switchElement.handleOnchange(true);
    }
}

function isEmpty(str) {
    return str === undefined || str === "";
}


/**
 * 刷新页面上的权限
 * @param domain
 */
function refreshPermission(domain) {
    var permissions = JSON.parse(window.localStorage.getItem("permissions"));
    var resourceList = getResourceList(permissions, domain);
    $(".control-auth").each(function () {
        var auth = $(this).attr("data-auth");
        if (hasAuth(resourceList, auth)) {
            $(this).show();
        } else {
            $(this).remove();
        }
    });

    function getResourceList(permissions, domain) {
        for (var i = 0; i < permissions.length; i++) {
            var permission = permissions[i];
            if (permission.domain === domain) {
                return permission.resources;
            }
        }
        return [];
    }

    function hasAuth(resourceList, auth) {
        for (var i = 0; i < resourceList.length; i++) {
            var resource = resourceList[i];
            if (resource.code === auth) {
                return true;
            }
        }
        return false;
    }

}

//修改密码
function createPasswordModal() {
    $("#editPasswordModal").modal();
}

function updatePassword() {
    var userId = $("#passwordEdit-form").find("input[name=userId]").val();
    var oldPassword =  $("#passwordEdit-form").find("input[name=oldPassword]").val();
    var newPassword =  $("#passwordEdit-form").find("input[name=newPassword]").val();
    var repeatNewPassword =  $("#passwordEdit-form").find("input[name=repeatNewPassword]").val();
    $.ajax({
        url: "/updatePassword",
        type: 'POST',
        data: JSON.stringify({
            userId:userId,
            oldPassword:oldPassword,
            newPassword:newPassword,
            repeatNewPassword:repeatNewPassword
        }),
        contentType: "application/json;charset=utf-8",
        dataType: "json",
        success: function (r) {
            if (r.code === 0) {
                $("#editPasswordModal").modal("hide");
                success("修改成功");
                $("#passwordEdit-form")[0].reset();
            }
        }
    })
}