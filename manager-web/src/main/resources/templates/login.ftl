<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>系统登录</title>
    <script src="/js/jquery-1.7.2.min.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link href="/BJUI/themes/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">
        * {
            font-family: "Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei", "Hiragino Sans GB", sans-serif;
        }

        body {
            background: url(images/loginbg_01.jpg) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }

        a:link {
            color: #285e8e;
        }

        .main_box {
            position: absolute;
            top: 50%;
            left: 50%;
            margin-top: -260px;
            margin-left: -300px;
            padding: 30px;
            width: 600px;
            height: 460px;
            background: #FAFAFA;
            background: rgba(255, 255, 255, 0.5);
            border: 1px #DDD solid;
            border-radius: 5px;
            -webkit-box-shadow: 1px 5px 8px #888888;
            -moz-box-shadow: 1px 5px 8px #888888;
            box-shadow: 1px 5px 8px #888888;
        }

        .main_box .setting {
            position: absolute;
            top: 5px;
            right: 10px;
            width: 10px;
            height: 10px;
        }

        .main_box .setting a {
            color: #FF6600;
        }

        .main_box .setting a:hover {
            color: #555;
        }

        .login_logo {
            margin-bottom: 20px;
            height: 45px;
            text-align: center;
        }

        .login_logo img {
            height: 45px;
        }

        .login_msg {
            text-align: center;
            font-size: 16px;
        }

        .login_form {
            padding-top: 20px;
            font-size: 16px;
        }

        .login_box .form-control {
            display: inline-block;
            *display: inline;
            zoom: 1;
            width: auto;
            font-size: 18px;
        }

        .login_box .form-control.x319 {
            width: 319px;
        }

        .login_box .form-control.x164 {
            width: 164px;
        }

        .login_box .form-group {
            margin-bottom: 20px;
        }

        .login_box .form-group label.t {
            width: 120px;
            text-align: right;
            cursor: pointer;
        }

        .login_box .form-group.space {
            padding-top: 15px;
            border-top: 1px #FFF dotted;
        }

        .login_box .form-group img {
            margin-top: 1px;
            height: 32px;
            vertical-align: top;
        }

        .login_box .m {
            cursor: pointer;
        }

        .bottom {
            text-align: center;
            font-size: 12px;
        }
    </style>
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
<body style="background: url('/loginbg.jpg')">
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
            <img src="images/logo.png">
        </div>
        <div class="login_msg">
            <font color="red" id="loginMsg"></font>
        </div>
        <div class="login_form">
            <form action="index.html" id="loginForm" method="post">
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
                    <img id="captchaImage" alt="点击更换" title="点击更换" src="images/captcha.jpeg" class="m">
                </div>
                <div class="form-group space">
                    <label class="t"></label>　　　
                    <input type="submit" id="loginSubmitBtn" value="立即登录" class="btn btn-primary btn-lg">
                </div>
            </form>
        </div>
    </div>
    <div class="bottom">Copyright &copy; 2013 - 2014 <a href="#">B-JUI 前端框架 - 系统登陆</a></div>
</div>
</body>
</html>