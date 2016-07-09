<#macro commonStyle>
<base href="${basePath}">
<link rel="shortcut icon" href="${basePath}favicon.ico" type="images/x-icon">
<script src="${basePath}assets/js/jquery-1.11.1.min.js"></script>
<#--<script src="${basePath}assets/js/json.js"></script> -->
<script type="text/javascript" >
	basePath= "${basePath}";
</script>
</#macro>


<#macro commonNav>
	<br/><a href="${basePath}count/index">考勤管理页面</a>
<#--	<br/><a href="${basePath}count/registerPage">添加考勤</a>&nbsp; -->
<#--	<br/><a href="${basePath}count/injuryPage">查看战力统计</a>&nbsp;-->
	<br/><a href="${basePath}user/index">用户管理页面</a>&nbsp;
	<br/><a href="${basePath}user/logOut">退出</a>&nbsp;
<#--	<br/><a href="${basePath}user/registerPage">添加用户</a>&nbsp;-->
</#macro>

<#macro commonHeader>
<div class="header">
  <div class="logo"><a href = "javascript:history.go(-1);"><img src="images/logo.png" alt="" /></a></div>
  <p><em>
  	 <#if Session["user"]?exists>
                  	您好,${user.name}
                  	<#else>
                    您未登录
                   </#if>
  </em> <#if Session["user"]?exists>
                  <a href = "${basePath}manager/logOut">退出
                   </#if></a></p>
            <span class="logo_font">游戏管理平台</span>
</div>
</#macro>

<#macro commonFooter>
<div class="footer">
<p>版权所有：中国移动通信研究院</p>
</div>
</#macro>
<#macro userHeader>
	<header>
			<a href="javascript:history.go(-1);" class="back"></a>
			<h1>XXX游戏平台</h1>
			<a href="javascript:;" class="nav-link J_NavLink"></a>
		</header>
</#macro>


<#macro userNav>
<div class="nav">
		<div class="bg">XXX游戏平台</div>
		<ul>
			<li class="heart">
				<a href="#">
				<em></em>
				<h1>关注我们</h1>
				<p>直接关注平台的微信公众号</p>
				</a>
			</li>
			<li class="share">
				<a href="javascript:;" id="J_Share">
				<em></em>
				<h1>分享</h1>
				<p>分享给您的好友</p>
				</a>
			</li>
			<li class="app">
				<a href="#">
				<em></em>
				<h1>下载APP</h1>
				<p>点击下载我们的客户端</p>
				</a>
			</li>
			<li class="about">
				<a href="${basePath}about">
				<em></em>
				<h1>关于我们</h1>
				<p>公司相关信息</p>
				</a>
			</li>
		</ul>
		<div class="mark" id="J_CloseShareMark"></div>
	</div>
	<div class="share-wrap">
		<h1>分享到</h1>
		<div class="share-con clearfix">
			<a href="#">
				<img src="assets/images/qq-weibo.jpg"/>
				<p>xxxx</p>
			</a>
			<a href="#">
				<img src="assets/images/weixin.jpg"/>
				<p>xxxx</p>
			</a>
			<a href="#">
				<img src="assets/images/sina.jpg"/>
				<p>xxxx</p>
			</a>
			<a href="#">
				<img src="assets/images/qq.jpg"/>
				<p>xxxx</p>
			</a>
		</div>
	</div>
</#macro>
