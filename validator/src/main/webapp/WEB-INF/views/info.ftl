<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<link href="assets/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="assets/j.js"></script>
<script type="text/javascript" src="assets/m.js"></script>
</head>
<body>
<div class="main">
	<div class="con">
		<header>
			<@commonMacro.userHeader/>
		</header>
		<div class="content">
			<div class="info">
				<div class="i-item clearfix">
					<h1>头像</h1>
					<a href="javascript:;"><img src="assets/images/photo.jpg"/></a>
				</div>
				<div class="i-item clearfix">
					<h1>昵称</h1>
					<a href="javascript:;">${user.nick}</a>
				</div>
				<div class="i-item clearfix">
					<h1>账号</h1>
					<a href="javascript:;">${user.username}</a>
				</div>
				<div class="i-btn">
					<a href="javascript:;">修改/设置密码</a>
				</div>
			</div>
			<div class="titler">玩过的游戏</div>
			<div class="items">
				<div class="item">
					<img src="assets/images/barner.jpg"/>
					<h2>游戏名称</h2>
					<p><label>当前关卡：</label><span>3</span></p>
					<p><label>最高得分：</label><span>250</span></p>
					<a href="javascript:;">玩游戏</a>
				</div>
				<div class="item">
					<img src="assets/images/barner.jpg"/>
					<h2>游戏名称</h2>
					<p><label>当前关卡：</label><span>3</span></p>
					<p><label>最高得分：</label><span>250</span></p>
					<a href="javascript:;">玩游戏</a>
				</div>
			</div>
			<div class="titler">我的收藏</div>
			<div class="items">
				<div class="item">
					<img src="assets/images/barner.jpg"/>
					<h2>游戏名称</h2>
					<p>游戏介绍游戏介绍游戏介绍游戏介绍 游戏介绍游戏介绍游戏介绍</p>
					<a href="javascript:;">玩游戏</a>
				</div>
			</div>
		</div>
		<div class="mark" id="J_CloseConMark"></div>
	</div>
	<@commonMacro.userNav/>
</div>
</body>
</html>
