
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
