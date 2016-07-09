<!DOCTYPE html>
<html>
<head>
	<@commonMacro.commonStyle/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<link href="assets/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/j.js"></script>
<script type="text/javascript" src="assets/m.js"></script>
<script type="text/javascript" src="static/js/security.js"></script>
<script type="text/javascript" src="static/js/user/login.js"></script>
</head>
<body>
<div class="main">
	<div class="con">
		<header>
		<@commonMacro.userHeader/>
		</header>
		<div class="content">
			<form class="valid-form">
				<div class="form-item">
					<em class="e-user"></em>
					<input type="text" class="txt" placeholder="请输入手机号码/邮箱" id="username"/>
				</div>
				<div class="form-item">
					<em class="e-psd"></em>
					<input type="password" class="txt" placeholder="请输入密码，如果尚未设置可不填" id="password"/ >
				</div>
				<div class="form-btn">
					<input type="button" class="submit" value="登录" id="submit"/>
				</div>
				<div class="form-link clearfix">
					<a href="javascript:;" class="fl">忘记密码</a>
					<a href="register" class="fr">立即注册</a>
				</div>
				<h6>注册之后，您可以畅玩游戏世界</h6>
					<input type="hidden" id="modulus" name="modulus"  value="${modulus}"/>
				<input type="hidden" id="exponent" name="exponent"  value="${exponent}"/>
			</form>
		</div>
		<div class="mark" id="J_CloseConMark"></div>
	</div>
		<@commonMacro.userNav/>
</div>
</body>
</html>
