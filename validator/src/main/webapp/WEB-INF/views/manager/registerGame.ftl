<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http：//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http：//www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加产品</title>
<@commonMacro.commonStyle/>
<script src="static/js/manager/register.js"></script>
<link href="css/base.css" rel="stylesheet" />
<link href="css/page1.css" rel="stylesheet" />
 
</head>

<body>
<@commonMacro.commonHeader/>
<div class="main-cont clearfix" >
	<div class="leftwrap" id="leftWrap">
			<ul>
                <li><h4><a href="javascript：;">系统管理</a></h4>
				    <ul class="droplist">
				        <li><a href="${basePath}manager/index" class="active">游戏管理</a></li>
				    </ul>
				</li>
			
              </ul>
    </div>
	
    <div class="rightwrap">
        <div class="top clearfix">
            <ul>
                <li><a href="#" class="active">添加游戏</a></li>
               
            </ul>
       </div>
        <div class="main">
        	<div class="borwrap">
                <form id="form" action="manager/registerGame"  enctype="multipart/form-data" method="post">
                    <ul class="listwrap clearfix">
                       <li><span>游戏名称：</span><input type="text" class="text" name="gameName" id="gameName" /></li>
                       <li><span>游戏描述：</span><input type="text" class="text" name="gameDescribe"  id="gameDescribe"/></li>
                       <li><span>游戏链接：</span><input type="text" class="text"  name="gameUrl" id="gameUrl"/></li>
                       <li><span>游戏logo：</span><input type="file" class="text" name="logo" id="logo"/></li>
          <li><span>是否上架：</span><label><input id="onbox" name="onbox" type="checkbox" value="1" /></label></li>
          <li><span>是否主打：</span><label><input  id="mainbox" name="mainbox" type="checkbox" value="1" /></label></li>
                    </ul>
                    	<input type="hidden" id="isOn" name="isOn"  value="2"/>
                    		<input type="hidden" id="isMain" name="isMain"  value="2"/>
                    <div class="btngroup">
                        <div class="btn"><a href="javascript:;"  id="submit">添加</a></div>
                        <div class="btn"><a href="javascript:;"  id="reset">重置</a></div>
                    </div> 
               </form>
            </div>
       </div>
    </div>
   
</div>
<@commonMacro.commonFooter/>
<script src="js/docexport.js"></script>

</body>
</html>
