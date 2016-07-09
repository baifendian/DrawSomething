<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<@commonMacro.commonStyle/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>产品列表</title>
	<script src="js/jquery-1.9.1.min.js"></script>
	<link href="css/base.css" rel="stylesheet" />
</head>

<body>
<@commonMacro.commonHeader/>
<div class="main-cont clearfix" id='mainCont'>
   <div class="leftwrap" id="leftWrap">
		<ul>
			<ul>
                <li><h4><a href="javascript:;">系统管理</a></h4>
				    <ul class="droplist">
				        <li><a href="javascript:;" >用户管理</a></li>
				        <li><a href="javascript:;" class="active">产品管理</a></li>
				    </ul>
				</li>
			
              </ul>
   </div>
   
   <div class="rightwrap">
     <div class="top clearfix">
        <ul>
            <li><a href="javascript:;" class="active">产品列表</a></li>
        </ul>
     </div>
     <div class="main main-h">
     	<div class="borwrap">
           <div class="main-top">
              <ul>
                <li><p>功能名称：</p><input type="text" class="text" /></li>
                <li><p>采购信息：</p><input type="text" class="text" /></li>
                <li><p>入网信息：</p><input type="text" class="text" /></li>
                <li><p>商用信息：</p><input type="text" class="text" /></li>
                <li><p>产品信息：</p><input type="text" class="text" /></li>
                <li><div class="btn"><a href="javascript:;">查询</a></div></li>
                <li><div class="btn"><a href="javascript:;">添加产品</a></div></li>
               </ul>
                
           </div>
        </div>
       
       <div class="tabwrap">
        	<table border="0" cellpadding="0" cellspacing="0" class="tab1">
                  <tr>
                    <td rowspan="2" class="tit">功能名称</td>
                    <td colspan="3" class="tit">采购信息</td>
                    <td colspan="3" class="tit">入网信息</td>
                    <td class="tit">商用信息</td>
                    <td colspan="3" class="tit">产品信息</td>
                    <td rowspan="2" class="tit">操作</td>
                  </tr>
                  <tr>
                    <td rowspan="1" class="tit">采购时间</td>
                    <td rowspan="1" class="tit">基本包</td>
                    <td rowspan="1" class="tit">可选包</td>
                    <td rowspan="1" class="tit">入网版本号</td>
                    <td rowspan="1" class="tit">入网时间</td>
                    <td rowspan="1" class="tit">入网地点</td>
                    <td rowspan="1" class="tit">现商用情况</td>
                    <td rowspan="1" class="tit">产品类型</td>
                    <td rowspan="1" class="tit">版本号</td>
                    <td rowspan="1" class="tit">是否支持</td>
                  </tr>
                  <#list demoInfoVos as info>
					<tr>
						<td >${info.functionName}</td>
						<td >${info.purchaseTime}</td>
						<td >${info.basicPackage}</td>
						<td >${info.selectPackage}</td>
						<td >${info.netVersion}</td>
						<td >${info.netTime}</td>
						<td >${info.netLocation}</td>
						<td >${info.nownetBusiness}</td>
						<td >${info.cpType}</td>
						<td >${info.version}</td>
						<td >${info.supportFlag}</td>
						<td rowspan="1"><a href="javascript:;">编辑</a> | <a href="javascript:;">删除</a></td>
					</tr>
				</#list>
          	</table>
          		<a href="demo/findInfo?currentPage=1">首页</a>&nbsp;
				<a href="demo/findInfo?currentPage=${pageMessage.previousPage}">上一页</a>&nbsp;
				<a href="demo/findInfo?currentPage=${pageMessage.nextPage }">下一页</a>&nbsp;
				<a href="demo/findInfo?currentPage=${pageMessage.endPage }">尾页</a>&nbsp;
				 共<span>${pageMessage.endPage}</span>页&nbsp;
				  当前<span>${pageMessage.currentPage }/${pageMessage.endPage }</span>页
		
				<form action="demo/findInfo">
				跳转到
				<input type="text" name="currentPage" class="displayText" value="${pageMessage.currentPage}" />
				<input type="submit" value="跳转" />
				</form>
				<br/> <#-- <a href="download">下载</a>&nbsp;-->
        </div>
    </div>
   </div> 
</div>
 <div class="footer">
<p>版权所有：北京天路通科技有限责任公司 <em color="#001824">京ICP备05009675号</em> &nbsp;地址：北京市丰台区富丰路4号工商联大厦B座十二层 &nbsp;</p>
</div>

<script src="js/docexport.js"></script>

</body>  
</html>
