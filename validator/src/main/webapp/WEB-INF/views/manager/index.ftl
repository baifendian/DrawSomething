<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<@commonMacro.commonStyle/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>产品列表</title>
	<script src="js/jquery-1.9.1.min.js"></script>
	<script src="js/demoInfo.js"></script>
	<link href="css/base.css" rel="stylesheet" />
</head>

<body>
<@commonMacro.commonHeader/>
<div class="main-cont clearfix" id='mainCont'>
   <div class="leftwrap" id="leftWrap">
		<ul>
			<ul>
                <li><h4><a href="javascript:;">游戏管理</a></h4>
				    <ul class="droplist">
				        <li><a href="javascript:;" class="active">游戏列表</a></li>
				    </ul>
				</li>
			
              </ul>
   </div>
   
   <div class="rightwrap">
     <div class="top clearfix">
        <ul>
            <li><a href="javascript:;" class="active">游戏列表</a></li>
        </ul>
     </div>
     <div class="main main-h">
     	<div class="borwrap">
           <div class="main-top">
          
               <a href="manager/registerGamePage" class="active">添加游戏</a>
           </div>
        </div>
       
       <div class="tabwrap">
        	<table border="0" cellpadding="0" cellspacing="0" class="tab1">
                  <tr>
                    <td  class="tit">游戏名称</td>
                    <td  class="tit">游戏描述</td>
                    <td  class="tit">游戏链接</td>
                	<td class="tit">游戏LOGO</td>
                	<td class="tit">是否上架</td>
                	<td class="tit">是否主打</td>
                	<td class="tit">创建时间</td>
                	<td class="tit">修改时间</td>
                	<td  class="tit">操作</td>
                  </tr>

	              <#if games?exists > 
	                  <#list games as info>
						<tr>
							<td >${info.gameName}</td>
							<td>${info.gameDescribe}</td>
							<td>${info.gameUrl}</td>
							<td>${info.logoUrl}</td>
							<td>  <#if info.isOn==1 >是<#else>否  </#if> </td>
							<td>  <#if info.isMain==1 >是<#else>否  </#if> </td>
							<td>${info.createTime?datetime}</td>
							<td>${info.updateTime?datetime}</td>
						
							<td rowspan="1"><a href="manager/game/updatePage?pkid=${info.id}&currentPage=${pageMessage.currentPage}">编辑</a> | <a href="manager/game/delete?pkid=${info.id}&currentPage=${pageMessage.currentPage}">删除</a></td>
						</tr>
					</#list>
				 <#else>
				 	<tr>
				 		<td colspan="13">没有查到数据</td>
				 	</tr>
				 </#if>
              
                </table>
                
                <center>
          		<a href="manager/index?currentPage=1">首页</a>&nbsp;
				<a href="manager/index?currentPage=${pageMessage.previousPage}">上一页</a>&nbsp;
				<a href="manager/index?currentPage=${pageMessage.nextPage }">下一页</a>&nbsp;
				<a href="manager/index?currentPage=${pageMessage.endPage }">尾页</a>&nbsp;
				 共<span>${pageMessage.endPage}</span>页&nbsp;
				  当前<span>${pageMessage.currentPage }/${pageMessage.endPage }</span>页
		
				<form action="manager/index">
				跳转到
				<input type="text" name="currentPage" class="displayText" value="${pageMessage.currentPage}" />
				<input type="submit" value="跳转" />
				</form>
				<br/> <#-- <a href="download">下载</a>&nbsp;-->
				</center>
        </div>
    </div>
   </div> 
</div>
<@commonMacro.commonFooter/>

<script src="js/docexport.js"></script>

</body>  
</html>
