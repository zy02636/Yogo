//隐藏层
var hideDivTimer = "";
var starthideDiv = function(div){
	hideDivTimer = setTimeout("hideDiv("+div+")",1500);
}
var hideDiv = function(div){
	div.style.visibility = "hidden";
	clearTimeout(hideDivTimer);
}
//修改按钮颜色
var overLogin=function(button){
	button.style.backgroundColor = "#0F77B7";
}
var outLogin=function(button){
	button.style.backgroundColor="#005EAC";
}

function validate(){
	var userName = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var textPwd = document.getElementById("textPwd").value;
    var infoDiv = document.getElementById("infoDiv");
    
    if((userName=="请输入账号")&&(textPwd=="请输入密码")){
    	infoDiv.innerHTML="<font style='color:red;font-size:12px;'>用户名与密码不能为空</font>";
    	infoDiv.style.visibility ="visible";
        return false;
    }
    if(userName=="请输入账号"){
    	infoDiv.innerHTML="<font style='color:red;font-size:12px;'>账号不能为空</font>";
    	infoDiv.visibility ="visible";
        return false;
    }
    if(textPwd=="请输入密码"){
        if(password=="请输入密码"){
        	infoDiv.innerHTML="<font style='color:red;font-size:12px;'>密码不能为空</font>";
        	infoDiv.visibility ="visible";
            return false;
        }
        infoDiv.innerHTML="<font style='color:red;font-size:12px;'>密码不能为空</font>";
        infoDiv.style.visibility ="visible";
        return false;
    }
    return true;
}
var pwdEvent = function(){
	var tx = document.getElementById("textPwd");
    var pwd = document.getElementById("password");
	tx.onfocus = function(){
		if(this.value != "请输入密码"){
		    return;
		}
		this.style.display = "none";
		this.value="请输入";
		pwd.style.display = "inline";
		pwd.style.color="black";
		pwd.value = "";
		pwd.focus();
	}
	pwd.onblur = function(){
		if(this.value != "") {
		    this.style.color="gray";
		    return;
		}
		this.style.display = "none";
		tx.style.display = "";
		tx.value = "请输入密码";
	}
	pwd.onfocus = function(){
		this.style.color="black";
	}
	
	var browser = getBrowser();
	var version = browser.substr(2,3);
	browser = browser.substr(0,2);
	
	if(browser != "IE"){
		var imgLogo = document.getElementById("imgLogo");
		imgLogo.src = "img/logo.png";
		
		//var divHeader = document.getElementById("header");
		//divHeader.style.backgroundImage = "url(img/bc.png)";
	}

	if(browser == "IE" && version == "6.0"){
		var header = document.getElementById("header");
		header.style.paddingBottom = "12px";
	}
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
var clearUserName=function(input){
    input.style.color = "black";
    if(input.value=="请输入账号"){
        input.value="";
    }
}
var fillUserName=function(input){
    input.style.color = "gray";
    if(input.value==""){
        input.value="请输入账号";
    }
}

var reg = function(){
	document.location = "reg.jsp";
}
