<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Shine - 注册</title>
    <link rel="shortcut icon" href="../favicon.ico">
    <link href="/static/backend/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="/static/backend/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
    <link href="/static/backend/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/static/backend/css/animate.min.css" rel="stylesheet">
    <link href="/static/backend/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <link href="/static/backend/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>

</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen   animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">S✟</h1>

        </div>
        <h3>欢迎注册 Shine</h3>

        <p>创建一个Shine新账户</p>

        <div class="form-group">
            <input type="text" class="form-control" placeholder="请输入用户名" required="" id="reg-loginName">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="请输入密码" required="" id="reg-password">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" placeholder="请再次输入密码" required="" id="reg-rePassword">
        </div>
        <div class="form-group text-left">
            <div class="checkbox i-checks">
                <label class="no-padding">
                    <input type="checkbox"><i></i> 我同意注册协议</label>
            </div>
        </div>
        <button type="submit" class="btn btn-primary block full-width m-b" onclick="reg_submit()">注 册</button>
        <p class="text-muted text-center">
            <small>已经有账户了？</small>
            <a href="/backend/login">点此登录</a>
        </p>

    </div>
</div>
<script src="/static/backend/js/jquery.min.js?v=2.1.4"></script>
<script src="/static/backend/js/bootstrap.min.js?v=3.3.6"></script>
<script src="/static/backend/js/plugins/iCheck/icheck.min.js"></script>
<script src="/static/js/common/validate.js" type="text/javascript" ></script>
<script src="/static/js/backend/register.js"></script>
<script src="/static/js/common/easyAjax.js"></script>
<script src="/static/backend/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>
</html>
