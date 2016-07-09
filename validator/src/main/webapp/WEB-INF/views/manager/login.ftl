<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<@commonMacro.commonStyle/>
<script type="text/javascript" src="static/js/manager/login.js"></script>
<script type="text/javascript" src="static/js/security.js"></script>
<title>登录</title>
<link href="css/base.css" rel="stylesheet" />
<link href="css/login.css" rel="stylesheet" />
</head>

<body>
<@commonMacro.commonHeader/>
<div class="main-cont" id="mainCont">
	<div class="wrapper">
     <h1>登 &nbsp;录</h1>
     <div class="main main-h clearfix">
            <ul class="listwrap clearfix">
                 <li class="clearfix"><span>用户名：</span><input id="name" type="text" class="text" value="admin"/><div class="warn" id="nameError">用户名</div><p></p></li>
                 <li><span>密码：</span> <input id="password" type="password"  class="text" value="admin" /><div class="warn" id="pwdError">6-20个大小写英文字母、符号或数字</div></li>
            </ul>
            <p>
                <a href="javascript:;">用户注册</a>
                <a href="javascript:;">忘记密码？</a>
            </p>
            <div class="btngroup">
                <div class="btn"><a href="javascript:;" id="submit">确认</a></div>
                <div class="btn"><a href="javascript:;"  id="reset">取消</a></div>
            </div> 
	<input type="hidden" id="modulus" name="modulus"  value="${modulus}"/>
	<input type="hidden" id="exponent" name="exponent"  value="${exponent}"/>
     </div>
   </div>
</div>
<@commonMacro.commonFooter/>
<script>
//each遍历文本框 
$("input[type='text'],input[type='password']").each(function() {
        //保存当前文本框的值
        var vdefault = this.value;
        $(this).focus(function() {
            //获得焦点时，如果值为默认值，则设置为空
            if (this.value == vdefault) {
                this.value = "";
            }
        });
 
        $(this).blur(function() {
            //失去焦点时，如果值为空，则设置为默认值
            if (this.value == "") {
                this.value = vdefault;
            }
        });
    });
//each遍历文本框 end		
</script>
</body>
</html>
