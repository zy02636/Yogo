var submitUserName = false;//用于管理信息是否可以提交
var submitExPwd = false;
var submitPwd = false;
var submitRePwd = false;
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
			var checkUserNameInfo = document.getElementById("checkUserName");
			checkUserNameInfo.innerHTML = "";
			var img = document.createElement("img");
			img.src = "img/success.png";
			img.setAttribute("title","用户名可以使用");
			checkUserNameInfo.appendChild(img);
			submitUserName = true;
		}
	}
}



//验证密码
var validateExPwd = function(input){
	inputBlur(input);
	var pwd = input.value;
	
	var checkExPwd = document.getElementById("checkExPwd");
	
	var containSpecial = false;
	var pattern =/^[A-Za-z0-9]+$/; 
	if(!pattern.test(pwd)){ 
		checkExPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码只能为数字或者字母</font>";
		submitExPwd = false;
		containSpecial = true;
	} 
	
	if(!containSpecial){
		if(pwd.length <= 0){
			checkExPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码不能为空</font>";
			submitExPwd = false;
		}else if(pwd.length < 6){
			checkExPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码长度不能小于6个字符</font>";
			submitExPwd = false;
		}else if(pwd.length > 20 ){
			checkExPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码长度不能超过20个字符</font>";
			submitExPwd = false;
		}else{
			checkExPwd.innerHTML = "";
			var img = document.createElement("img");
			img.src = "img/success.png";
			img.setAttribute("title","密码长度符合");
			checkExPwd.appendChild(img);
			submitExPwd = true;
		}
	}
}
//验证密码
var validatePwd = function(input){
	inputBlur(input);
	var pwd = input.value;
	
	var checkPwd = document.getElementById("checkPwd");
	
	var containSpecial = false;
	var pattern =/^[A-Za-z0-9]+$/; 
	if(!pattern.test(pwd)){ 
		checkPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码只能为数字或者字母</font>";
		submitPwd = false;
		containSpecial = true;
	} 
	
	if(!containSpecial){
		if(pwd.length <= 0){
			checkPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码不能为空</font>";
			submitPwd = false;
		}else if(pwd.length < 6){
			checkPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码长度不能小于6个字符</font>";
			submitPwd = false;
		}else if(pwd.length > 20 ){
			checkPwd.innerHTML = "<font style='color:red;font-size:12px;'>密码长度不能超过20个字符</font>";
			submitPwd = false;
		}else{
			checkPwd.innerHTML = "";
			var img = document.createElement("img");
			img.src = "img/success.png";
			img.setAttribute("title","密码长度符合");
			checkPwd.appendChild(img);
			submitPwd = true;
		}
	}
}

//验证再次输入的密码
var validateRePwd = function(input){
	inputBlur(input);
	var rePwd = input.value;
	
	var inputPwd = document.getElementById("newPwd");
	var pwd = inputPwd.value;
	
	var checkRePwd = document.getElementById("checkRePwd");
	
	var containSpecial = false;
	var pattern =/^[A-Za-z0-9]+$/; 
	if(!pattern.test(rePwd)){ 
		checkRePwd.innerHTML = "<font style='color:red;font-size:12px;'>密码只能为数字或者字母</font>";
		submitRePwd = false;
		containSpecial = true;
	} 
	
	if(!containSpecial){
		if(rePwd.length <= 0){
			checkRePwd.innerHTML = "<font style='color:red;font-size:12px;'>密码不能为空</font>";
			submitRePwd = false;
		}else if(rePwd.length < 6){
			checkRePwd.innerHTML = "<font style='color:red;font-size:12px;'>密码长度不能小于6个字符</font>";
			submitRePwd = false;
		}else if(rePwd.length > 20 ){
			checkRePwd.innerHTML = "<font style='color:red;font-size:12px;'>密码长度不能超过20个字符</font>";
			submitRePwd = false;
		}else if(rePwd != pwd){
			checkRePwd.innerHTML = "<font style='color:red;font-size:12px;'>两次输入的密码不同</font>";
			submitRePwd = false;
		}else{
			checkRePwd.innerHTML = "";
			var img = document.createElement("img");
			img.src = "img/success.png";
			img.setAttribute("title","两次密码相同");
			checkRePwd.appendChild(img);
			submitRePwd = true;
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
//提交信息
var submitForm = function(){
	if(submitCode && submitUserName && submitPwd && submitRePwd && submitExPwd){
		var sbBtn = document.getElementById("submitBtn");
		sbBtn.value = "提交中...";
		submitCgPwdInfo();
	}else{
		var checkReg = document.getElementById("checkReg");
		checkReg.innerHTML = "<font style='color:red;font-size:12px;'>请检查账号信息是否填写完毕</font>";
	}
}

var submitCgPwdInfo = function(){
	var username = document.getElementById("newUserName").value;
	var exPwd = document.getElementById("exPwd").value;
	var pwd = document.getElementById("newPwd").value;
	var rePwd = document.getElementById("newRePwd").value;
	var code = document.getElementById("validateCode").value;
	
	createXmlHttpRequest();
	var url = "changePwd.action?username="+username+"&password="+pwd+"&rePassword="+rePwd+"&code="+code+"&exPassword="+exPwd;
	url = encodeURI(url);
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = afterSubmitCgPwdInfo;
	xmlHttp.send(null);
}
var afterSubmitCgPwdInfo = function(){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200){
			var result = xmlHttp.responseText.trim();
			var checkReg = document.getElementById("checkReg");
			var sbBtn = document.getElementById("submitBtn");
			sbBtn.value = "提交";
			if(result == "SUCCESS"){
				document.getElementById("newUserName").value="";
				document.getElementById("exPwd").value="";
				document.getElementById("newPwd").value="";
				document.getElementById("newRePwd").value="";
				document.getElementById("validateCode").value="";
				checkReg.innerHTML = "修改密码成功!";
			}else if(result == "CODEFAIL"){
				checkReg.innerHTML = "<font style='color:red;'>验证码错误</font>";
			}else{
				checkReg.innerHTML = "<font style='color:red;'>更新失败,请检查账号信息</font>";
			}
		}
	}
}