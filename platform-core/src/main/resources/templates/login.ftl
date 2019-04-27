<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title!'运维管理系统'}-用户登录</title>
    <script src="/js/jquery-1.7.2.min.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link href="/BJUI/themes/css/bootstrap.min.css" rel="stylesheet">
    <link href="/login.css" rel="stylesheet">

    <script type="text/javascript">

        $(document).ready(function () {
            var getCaptcha = function () {
                $.get("/getCaptcha.ajax", function (result) {
                    if (result.code === 'SUCCESS') {
                        $("#captchaImage").attr('src', result.data);
                    }
                });
            };

            getCaptcha();
            $("#captchaImage").click(getCaptcha);

            $("#loginForm").submit(function () {
                $("#loginSubmitBtn").attr("disabled", true).val('登录中..');
                $.ajax({
                    url: "/login.ajax",
                    data: {
                        'loginAccount': $("#loginAccount").val(),
                        'loginPassword': $("#loginPassword").val(),
                        'captcha': $("#captcha").val()
                    },
                    dataType: 'json',
                    type: 'POST',
                    success: function (result) {
                        $("#loginSubmitBtn").removeAttr("disabled").val('立即登录');
                        if (result.code === 'SUCCESS') {
                            window.location.href = "/";
                        } else {
                            $("#loginMsg").html(result.message);
                        }

                    }
                });
                /*注意：生产环境时请删除此行*/
                return false;
            });
        });
    </script>
</head>
<body>
<!--[if lte IE 7]>
<style type="text/css">
    #errorie {
        position: fixed;
        top: 0;
        z-index: 100000;
        height: 30px;
        background: #FCF8E3;
    }

    #errorie div {
        width: 900px;
        margin: 0 auto;
        line-height: 30px;
        color: orange;
        font-size: 14px;
        text-align: center;
    }

    #errorie div a {
        color: #459f79;
        font-size: 14px;
    }

    #errorie div a:hover {
        text-decoration: underline;
    }
</style>
<div id="errorie">
    <div>您还在使用老掉牙的IE，请升级您的浏览器到 IE8以上版本
        <a target="_blank" href="http://windows.microsoft.com/zh-cn/internet-explorer/ie-8-worldwide-languages">点击升级</a>
        &nbsp;&nbsp;强烈建议您更改换浏览器：
        <a href="http://down.tech.sina.com.cn/content/40975.html" target="_blank">谷歌 Chrome </a>
    </div>
</div>
<![endif]-->
<div class="main_box">
    <div class="login_box">
        <div class="login_logo">
            <#if logoUrl!=''>
                <img src="${logoUrl}">
            <#else>
                ${title!'运维管理系统'}
            </#if>
        </div>
        <div class="login_msg">
            <font color="red" id="loginMsg"></font>
        </div>
        <div class="login_form">
            <form action="/index.htm" id="loginForm" method="post">
                <div class="form-group">
                    <label for="loginAccount" class="t">用户名：</label>
                    <input id="loginAccount" name="loginAccount" type="text" class="form-control x319 in"
                           autocomplete="off">
                </div>
                <div class="form-group">
                    <label for="loginPassword" class="t">密　码：</label>
                    <input id="loginPassword" value="" name="loginPassword" type="password"
                           class="form-control x319 in">
                </div>
                <div class="form-group">
                    <label for="captcha" class="t">验证码：</label>
                    <input id="captcha" name="captcha" type="text" class="form-control x164 in">
                    <img id="captchaImage" alt="点击更换" title="点击更换" src="" class="m">
                </div>
                <div class="form-group space" style="text-align: center">
                    <input type="submit" id="loginSubmitBtn" value="立即登录" class="btn btn-primary btn-lg">
                </div>
            </form>
        </div>
    </div>
    <div class="bottom">Copyright &copy; 2019 <a href="http://www.dliyun.com">dliyun.com</a></div>
</div>
</body>
</html>