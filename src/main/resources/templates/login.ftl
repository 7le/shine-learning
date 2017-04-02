<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <title>Shine - 登录</title>
    <link href="/static/backend/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/backend/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="/static/backend/css/animate.min.css" rel="stylesheet">
    <link href="/static/backend/css/style.min.css" rel="stylesheet">
    <link href="/static/backend/css/login.min.css" rel="stylesheet">
    <link href="/static/backend/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <script>
        if (window.top !== window.self) {
            window.top.location = window.location
        }
        ;
    </script>

</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-7">
            <div class="signin-info">
                <div class="logopanel m-b">
                    <h1>[ Shine ]</h1>
                </div>
                <div class="m-b"></div>
                <h4>欢迎使用 <strong>Shine</strong></h4>
                <ul class="m-b">
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势一</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势二</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势三</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势四</li>
                    <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势五</li>
                </ul>
                <strong>还没有账号？ <a href="/backend/register">立即注册&raquo;</a></strong><br>
                <strong>轻松一下？ <a href="/gogame">Let's play&raquo;</a></strong>
            </div>
        </div>
        <div class="col-sm-5">
                <h4 class="no-margins">登录：</h4>

                <p class="m-t-md">登录到Shine 后台</p>
                <input type="text" class="form-control uname" placeholder="用户名" id="login-loginName"/>
                <input type="password" class="form-control pword m-b" placeholder="密码" id="login-password"/>
                <a href="#">忘记密码了？</a>
                <button id="btn-login-submit" class="btn btn-success btn-block" onclick="submit();">登录</button>
        </div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2016 All Rights Reserved. Shine
        </div>
    </div>
</div>
</body>
<script src="/static/backend/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/backend/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/static/js/common/validate.js"></script>
<script src="/static/js/common/easyAjax.js"></script>
<script src="/static/js/backend/login.js"></script>
<script src="/static/backend/js/plugins/sweetalert/sweetalert.min.js"></script>
</html>
