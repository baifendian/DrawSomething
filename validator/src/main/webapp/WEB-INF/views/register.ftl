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
<script type="text/javascript" src="static/js/user/register.js"></script>
</head>
<body>
<div class="main">
	<div class="con">
		<@commonMacro.userHeader/>
		<div class="content">
			<form class="valid-form" >
				<div class="form-item">
					<em>帐号</em>
					<input type="text" class="txt" id="username" placeholder="请输入手机号码/邮箱"/>
				</div>
				<div class="form-item">
					<em>密码</em>
					<input type="password" class="txt" id="password"/>
					<label class="tip">设置密码可有效保护账号安全，<br/>当前您也可以选择不填写</label>
				</div>
				<div class="form-btn">
					<input type="button" class="submit" value="注册" id="submit"/>
				</div>
				<div class="form-msg">
					<h1>免密注册说明：</h1>
					<p>您可以不设置密码，直接注册，xxxxxx xxxxxxx xxxx xxx xxxxxx xxxx xxxx xx xxxxx xxx xxxxxxx</p>
				</div>
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
