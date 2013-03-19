<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="mage/x-icon" />
		<title>91url 用户注册</title>
		<s:if test="#session.user!=null"><!-- 防止未登录用户直接访问home.jsp页面,以及登录的用户直接访问jsp页面 -->
			<script type="text/javascript">document.location = "Profile.action";</script>
		</s:if>
		<link href="css/reg.css" rel="stylesheet" type="text/css" />
		<script src="script/reg.js"></script>
	</head>
	<body class="bodyClass" onload="init()">
		<div class="posDivMain">
			<div class="divMain">
			    <div id="header" class="header"><img id="imgLogo" src="img/logo_IE.png" title="91url.com"/></div>
			    <div style="text-align:right;padding-top:10px;padding-right:10px;">
			        <a class="hyper" onclick="document.location='login.jsp'">登录</a>
			    </div>
				<div class="line" style="padding-top:25px;">
					<div class="item" > 
						电子邮箱 &nbsp;&nbsp;<input type="text" name="username" id="newUserName" class="txtInput" onfocus="inputFocus(this)" onblur="validateMail(this)"/>
					</div>
					<div id="checkUserName" class="regInfo">
						用于登录以及密码找回
					</div>
				</div>

				<div class="line">
					<div class="item">
						创建密码 &nbsp;&nbsp;<input type="password" name="pwd" id="newPwd" class="txtInput" onfocus="inputFocus(this)" onblur="validatePwd(this)" />
					</div>
					<div id="checkPwd" class="regInfo">
						6-20位数字,字母组合的登录密码
					</div>

				</div>
				<div class="line">
					<div class="item">
						确认密码 &nbsp;&nbsp;<input type="password" name="repwd" id="newRePwd" class="txtInput" onfocus="inputFocus(this)" onblur="validateRePwd(this)" />
					</div>
					<div id="checkRePwd" class="regInfo">
						确认密码
					</div>
				</div>
				<div class="line">
					<div class="item">
						验证码 &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="validateCode" class="codeInput" onfocus="inputFocus(this)" onblur="validateCode(this)" />
						<iframe id="codeFrame" src="code/regCode.jsp" frameborder="no"
						height="35" width="120" scrolling="no" style="vertical-align:middle;" marginwidth="0" marginheight="0"></iframe>
				     	<a class="hyper" onclick="window.codeFrame.location.reload();" title="更换图片,不区分大小写">看不清?</a>
					</div>
					<div id="checkCode" class="regInfo" style="padding-left:32px;">
                     
					</div>
				</div>
				<div class="line">
				    <div class="item">
					    <input type="button" value="注册 " class="subButton" onclick="submitForm()" title="信息添加完毕注册" />
				    </div>
				    <div id="checkReg" class="regInfo">
                     
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
