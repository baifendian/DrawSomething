<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>你画我猜--验证码识别</title>
        
        <!-- Our CSS stylesheet file -->
        <link rel="stylesheet" href="assets/css/index.css" />
        <@commonMacro.commonStyle/>
    </head>
    
    <body>
    		<div id="imgs">
		请选择图片类型：<br/>
		<br/>
        <span id="img1">
        <input type="radio" name="s_type" id="x" value="1" checked>
        <img src="assets/img/1.png" style= ";"/> 
        </span>
        <br/>
       <br/>
   <span id="img2"> 
   <input type="radio" name="s_type" id="y" value="2" >
   <img src="assets/img/2.gif" style= ";"/> 
   </span><br/>
   <br/>

    <span id="img3">
    <input type="radio" name="s_type" id="z" value="3">
    <img src="assets/img/3.png" style= ";"/>  </span>
    <br/>
    <span id="img4">
    <input type="radio" name="s_type" id="z" value="4">
    <img src="assets/img/t1.png" style= ";"/>  </span>
    </div>
		<div>
		<header>
			<img src="assets/img/header2.jpg" style= "width:100% ;"/>
			<div id="dropbox">
			<span class="message">拖动图片到此处上传 <br /></span>
		</div>
		</header>
		</div>
		<div id="d">
			<button class="btn primary" type="submit" id="validate">开始识别</button>
        </div>

		
		<!-- Including the HTML5 Uploader plugin -->
		<script src="assets/js/jquery.filedrop.js"></script>
		
		<!-- The main script file -->
        <script src="assets/js/script.js"></script>
    
    </body>
    
</html>

