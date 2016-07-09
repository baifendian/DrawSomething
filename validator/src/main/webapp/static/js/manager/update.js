$().ready(function() {
	/**
	 * 常量配置
	 * 
	 */

	// 普通认证登录框用户名获焦事件
//	$("#name").focus(function() {
//		$("#name").val("");
//	});
//	$("#credits").focus(function() {
//		$("#credits").val("");
//	});
//
//	$("#reset").click(function() {
//		$("#name").val("");
//		$("#credits").val("");
//	});

	// 普通认证登录 获取动态短信
	$("#submit").click(function() {

		var name = $("#name").val();
		var credits = $("#credits").val();
		var userType = $("#userType").val();
		var userId = $("#userId").val();
		var prize = $("#prize").val();
		var consumeDate = $("#consumeDate").val();
		var koufen  = $('input[name="koufen"]:checked').val();
		if ($.trim(name) == "") {
			$("#name").val("name不能为空");
			return;
		}
		if ($.trim(credits) == "") {
			$("#credits").val("credits不能为空");
			return;
		}
		var url = basePath + "user/update";

		var params = "name=" + name;
		 params += "&credits=" + credits;
		 params += "&userType=" + userType;
		 params += "&userId=" + userId;
		 params += "&prize=" + prize;
		 params += "&koufen=" + koufen;
		 params += "&consumeDate=" + consumeDate;

		$.post(url, params, function(data) {
			$("#error").text(data);

		});

	});

});
