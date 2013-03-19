var submitUserName = false;//用于管理信息是否可以提交
var submitCode = false;
/**
 * 对属性方法进行扩展
 * @param name
 * @param func
 * @return
 */
Function.prototype.method = function(name,func){
	this.prototype[name] = func;
	return this;
}
String.method("trim",function(){
	return this.replace(/^\s*|\s*$/g,"");
});
var xmlHttp;
var createXmlHttpRequest = function() {
	if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
}
var inputFocus = function(input){
	input.style.color = "black";
	input.style.backgroundColor = "#FAFFBD";
}

var inputBlur = function(input){
	input.style.color = "gray";
	input.style.backgroundColor = "white";
}

function getBrowser(){
	 var bro = navigator.userAgent.toLowerCase();
	 if(/msie/.test(bro)) return 'IE' + bro.match(/msie ([\d.]*);/)[1]
	 else if(/navigator/.test(bro)) return 'NS' + bro.match(/navigator\/([\d.]*)/)[1]
	 else if(/chrome/.test(bro)) return 'CR' + bro.match(/chrome\/([\d]*)/)[1]
	 else if(/safari/.test(bro)) return 'SF' + bro.match(/version\/([\d]*)/)[1]
	 else if(/opera/.test(bro)) return 'OP' + bro.match(/version\/([\d]*)/)[1]
	 else if(/firefox/.test(bro)) return 'FF' + bro.match(/firefox\/([\d]*)/)[1]
}

var init = function(){
	var browser = getBrowser();
    var version = browser.substr(2,3);
    browser = browser.substr(0,2);
    
	if(browser != "IE"){
		var imgLogo = document.getElementById("imgLogo");
		imgLogo.src = "img/logo.png";
		
		//var divHeader = document.getElementById("header");
		//divHeader.style.backgroundImage = "url(img/bc.png)";
	}
}

//验证邮件
var validateMail = function(input){
	inputBlur(input);
	var username = input;
	var checkUserNameInfo = document.getElementById("checkUserName");
	//对电子邮件的验证
	if(username.value.length <= 0){
		checkUserNameInfo.innerHTML = "<font style='color:red;font-size:12px;'>用户名不能为空</font>";
		submitUserName = false;
	}else if(username.value.length >= 70){
		checkUserNameInfo.innerHTML = "<font style='color:red;font-size:12px;'>用户名过长</font>";
		submitUserName = false;
	}else{
		var mailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(!mailReg.test(username.value)){
			checkUserNameInfo.innerHTML = "<font style='color:red;font-size:12px;'>请输入有效的邮件格式</font>";
			submitUserName = false;
		}else{
			submitUserName = true;
		}
	}
}
//检验验证码
var validateCode = function(input){
	inputBlur(input);
	var code = input.value;
	var checkCode = document.getElementById("checkCode");
	if(code.length <= 0){
		checkCode.innerHTML = "<font style='color:red;font-size:12px;'>验证码不能为空</font>";
		submitCode = false;
	}else{
		checkCode.innerHTML = "";
		submitCode = true;
	}
}

var submitForm = function(){
	if(submitCode && submitUserName){
		var sbBtn = document.getElementById("submitBtn");
		sbBtn.value = "提交中...";
		submitInfo();
	}
}

var submitInfo = function(){
	var username = document.getElementById("username").value;
	var code = document.getElementById("validateCode").value;
	
	createXmlHttpRequest();
	var url = "mailPwd.action?username="+username+"&code="+code;
	url = encodeURI(url);
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = afterSubmitInfo;
	xmlHttp.send(null);
}
var afterSubmitInfo = function(){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200){
			var result = xmlHttp.responseText.trim();
			var checkReg = document.getElementById("checkReg");
			var sbBtn = document.getElementById("submitBtn");
			sbBtn.value = "提交";
			if(result == "SUCCESS"){
				document.getElementById("username").value="";
				document.getElementById("validateCode").value="";
				checkReg.innerHTML = "密码已发送至您的邮箱,请查收";
			}else if(result == "CODEFAIL"){
				checkReg.innerHTML = "<font style='color:red;'>验证码错误</font>";
			}else{
				checkReg.innerHTML = "<font style='color:red;'>请检查输入的用户名</font>";
			}
		}
	}
}