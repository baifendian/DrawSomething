$().ready(function() {
	/**
	 * 常量配置
	 * 
	 */
	

	// 普通认证登录框用户名获焦事件
	$("#name").focus(function() {
		$("#nameError").val("");
	});
	$("#password").focus(function() {
		$("#pwdError").val("");
	});

	$("#reset").click(function() {
		$("#nameError").val("");
		$("#pwdError").val("");
	});

	// 普通认证登录 获取动态短信
	$("#submit").click(function() {

		var name = $("#name").val();
		var password = $("#password").val();

		if ($.trim(name) == "") {
			$("#nameError").text("用户名不能为空");
			return;
		}
		if ($.trim(password) == "") {
			$("#pwdError").text("密码不能为空");
			return;
		}
		
		var modulus = $("#modulus").val();
		var exponent =$("#exponent").val();
		var epwd = $("#password").val();
		if (epwd.length != 256) {
			var publicKey = RSAUtils.getKeyPair(exponent, '', modulus);
			password = RSAUtils.encryptedString(publicKey, epwd);
		}
		var url = basePath + "manager/loginTo";

		var params = "username=" + name;
		 params += "&password=" + password;

		$.post(url, params, function(data) {
			$("#pwdError").text(data);
			if(data == "登录成功"){
				location.href=basePath + "manager/index";
			}

		});

	});
	
	

});
