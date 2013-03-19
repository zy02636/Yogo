<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="keywords" content="网络书签,91url,信息管理,不一样的信息获取方式,社交网站"/>
		<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="mage/x-icon" />
		<title>欢迎来到91url网络书签 您的网络书签管家</title>
		<s:if test="#session.user!=null">
			<script type="text/javascript">
			    document.location="Profile.action";
			</script>
		</s:if>
		<link href="css/login.css" rel="stylesheet" type="text/css" />
		<script src="script/login.js"></script>
		    
	</head>
     <%
     String username = "";
	 String password = "";

     if(request.getAttribute("fail") == null){
		    Cookie[] cookies = request.getCookies();
			if(cookies!=null)
			{
			    for (int i = 0; i < cookies.length; i++) 
			    {
			       Cookie c = cookies[i];     
			       if(c.getName().equalsIgnoreCase("username"))
			       {
			          username = c.getValue();
			        }
			        else if(c.getName().equalsIgnoreCase("password"))
			        {
			           password = c.getValue();
			        }    
			    } 
		    }
		    if(username.length() >= 3 && password.length() >= 4){
		        response.sendRedirect(response.encodeRedirectURL("CookieLogin.action?username="+username+"&password="+password));
		    }
	    }
    %>
	<body class="body" onload="pwdEvent()">
			<div id="divMain" class="divMain" style="height:470px;">
			    <div id="header" class="header"><img id="imgLogo" src="img/logo_IE.png" title="91url.com" /></div>

					<div id="divSignId"  style="padding-left:60px;float:left;">
					    <div id="infoDiv" class="infoDiv">
					      <%=request.getAttribute("fail") == null ? "" : request.getAttribute("fail")%>
					    </div><br/>

							<s:form action="Login.action" onsubmit="return validate();">
								<br/>
								<input type="text" name="username" id="username" class="txtInput" onfocus="clearUserName(this)" onblur="fillUserName(this)"
									value="<%=(username.length() > 3 ?  username:"请输入账号")%>" />
								<br/>
								<br/>
								<input type="text" name="txtPwd" id="textPwd" class="txtInput" value="请输入密码" />
								<input type="password" name="password" id="password" class="pwdInput" value="" /><br/><br/>
								<input type="submit" value=" 登录 " class="loginButton" onmouseover="overLogin(this)" onmouseout="outLogin(this)" /> &nbsp;  
								<s:checkbox  name="autoLogin" id="autoLogin" value="false" fieldValue="auto" cssClass="autoLogin" title="公共场所建议取消"/>自动登录<br />
								<br/>
								<a href="getPwd.jsp" class="hyper">忘记密码?</a> <br/><br/>
							</s:form>
					</div>
					<div class="reg">
			            <iframe id="regFrame" src="iframeReg.jsp" target="_parent" frameborder="no" height="370" width="540" scrolling="no" style="vertical-align:middle;" marginwidth="0" marginheight="20" />
			        </div>
				</div>
	</body>
</html>
