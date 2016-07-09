$().ready(function() {

	$("#reset").click(function() {
		$("#gameName").val("");
		$("#gameDescribe").val("");
		$("#gameUrl").val("");
		$("#logo").val("");
	});

	// 普通认证登录 获取动态短信
	$("#submit").click(function() {
		var gameName = $("#gameName").val();
		var gameUrl = $("#gameUrl").val();

		if ($.trim(gameName) == "" || $.trim(gameName) == "游戏名称不能为空") {
			$("#gameName").val("游戏名称不能为空");
			return;
		}
		if ($.trim(gameUrl) == "" || $.trim(gameUrl) == "游戏链接不能为空") {
			$("#gameUrl").val("游戏链接不能为空");
			return;
		}
		$('input[name="onbox"]:checked').each(function() {
			$("#isOn").val("1");
		});
		$('input[name="mainbox"]:checked').each(function() {
			$("#isMain").val("1");
		});

		$("#form").submit();

	});

});
