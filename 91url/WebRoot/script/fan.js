var browser = "";//获取浏览器名称
var version = "";//获取浏览器版本
//获取浏览器类型
function getBrowser(){
	 var bro = navigator.userAgent.toLowerCase();
	 if(/msie/.test(bro)) return 'IE' + bro.match(/msie ([\d.]*);/)[1]
	 else if(/navigator/.test(bro)) return 'NS' + bro.match(/navigator\/([\d.]*)/)[1]
	 else if(/chrome/.test(bro)) return 'CR' + bro.match(/chrome\/([\d]*)/)[1]
	 else if(/safari/.test(bro)) return 'SF' + bro.match(/version\/([\d]*)/)[1]
	 else if(/opera/.test(bro)) return 'OP' + bro.match(/version\/([\d]*)/)[1]
	 else if(/firefox/.test(bro)) return 'FF' + bro.match(/firefox\/([\d]*)/)[1]
}
//初始化页面信息
var init = function(imgPath){
	browser = getBrowser();
    version = browser.substr(2,3);
    browserName = browser.substr(0,2);
    
    var divMain = document.getElementById("divMain");
    if(browserName=="CR"){
    	divMain.style.overflow = "auto"; //层嵌套,父子层自动扩展
    }else{
    	divMain.style.overflow = "auto"; 
    }
    
	
	if(browserName != "IE"){
		var imgLogo = document.getElementById("imgLogo");
		imgLogo.src = "img/logo.png";
		
		//var divHeader = document.getElementById("divHeader");
		//divHeader.style.backgroundImage = "url(img/bc.png)";
	}

}

function resize(){
	var delBg = document.getElementById("delBg");
	if(delBg != null){
		delBg.style.width = document.body.scrollWidth;
		delBg.style.height = document.body.scrollHeight;
	}
	
	var delAlert = document.getElementById("delAlert"); 
	if(delAlert != null){
		var scrollTop = getScrollTop();
		var clientHeight = document.body.clientHeight/2;
		var clientWidth = document.body.clientWidth/2;
		delAlert.style.top = clientHeight + scrollTop + "px";
		delAlert.style.left = clientWidth + "px"; 
	}
}
//获取滚动条高度   
function getScrollTop() {   
    var scrollPos = 0;    
    if (typeof window.pageYOffset != 'undefined') {    
       scrollPos = window.pageYOffset;    
    }    
    else if (typeof window.document.compatMode != 'undefined' &&    
       window.document.compatMode != 'BackCompat') {    
       scrollPos = window.document.documentElement.scrollTop;    
    }    
    else if (typeof window.document.body != 'undefined') {    
       scrollPos = window.document.body.scrollTop;    
    }    
    return scrollPos;   
} 
/**
 * 删除url
 * @return
 */
var queryDelFans = function(urlIndex){
	var delAlert = document.getElementById("delAlert"); 
	delAlert.style.display = "block"; 
	delAlert.style.position = "absolute"; 
	
	var scrollTop = getScrollTop();
	var clientHeight = document.body.clientHeight/2;
	var clientWidth = document.body.clientWidth/2;
	
	delAlert.style.top = clientHeight + scrollTop + "px";
	delAlert.style.left = clientWidth + "px"; 
	delAlert.style.marginTop = "-75px"; 
	delAlert.style.marginLeft = "-150px";
	delBg = document.createElement("div"); 
	delBg.setAttribute("id","delBg"); 
	delBg.style.background = "#000"; 
	delBg.style.position = "absolute"; 

	delBg.style.width = document.body.scrollWidth; 
	browserName = browser.substr(0,2);
	if(browserName == "IE"){
		delBg.style.height = document.body.scrollHeight+document.body.clientHeight/2; //根据页面的实际高度决定
	}else{
		delBg.style.height = document.body.scrollHeight; //根据页面的实际高度决定
	}
	delBg.style.top = "0"; 
	delBg.style.left = "0";  
	delBg.style.zIndex = "500"; 
	delBg.style.opacity = "0.1"; 
	delBg.style.filter = "Alpha(opacity=10)"; 
	document.body.appendChild(delBg);
	//document.body.style.overflow = "hidden"; 
	
	var closeAlert = document.getElementById("closeAlert");
	closeAlert.onclick = function(){
		hideAlert(delAlert,delBg);
	}
	
	var cancelUrl = document.getElementById("cancelUrl");
	cancelUrl.onclick = function(){
		hideAlert(delAlert,delBg);
	}
	
	var delUrlButton = document.getElementById("delUrl");
	delUrlButton.onclick = function(){
		delFans(urlIndex,delAlert,delBg);
	}
	
}

var hideAlert = function(delAlert,delBg){
	delAlert.style.display = "none"; 
	delBg.style.display = "none"; 
}

var delFans = function(fanIndex,delAlert,delBg){
	var guestId = document.getElementById("fanId" + fanIndex).innerHTML;
	var userId = document.getElementById("userId").value;
	
	createXmlHttpRequest();
	var url = "delFan.action?userId="+userId+"&guestId="+guestId;
	url = encodeURI(url);
	//url = encodeURI(url);//加两.次..
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterDelFan(fanIndex,delAlert,delBg);
	};
	xmlHttp.send(null);
}

var afterDelFan = function(fanIndex,delAlert,delBg){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();
			
			if(result == "SUCCESS"){
				hideAlert(delAlert,delBg);
				
				var fan = document.getElementById("fanIndex" + fanIndex);
				fan.style.display = "none";
				
				var fansSum = document.getElementById("fansSumLink");
				var intFansSum = parseInt(fansSum.innerHTML)-1;
				fansSum.innerHTML = intFansSum;
			}
		}
	}
}

var addFollow = function(fanIndex,fansOwner){
	var hostId = document.getElementById("fanId" + fanIndex).innerHTML;
	var userId = document.getElementById("userId").value;
	createXmlHttpRequest();
	var url = "addFollow.action?userId="+userId+"&hostId="+hostId;
	url = encodeURI(url);
	//url = encodeURI(url);//加两.次..
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterAddFollow(fanIndex,fansOwner);
	};
	xmlHttp.send(null);
}

var afterAddFollow = function(fanIndex,fansOwner){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();
			
			if(result == "SUCCESS" && fansOwner == "self"){
				var followsSum = document.getElementById("followersSumLink");
				var intFollowsSum = parseInt(followsSum.innerHTML)+1;
				followsSum.innerHTML = intFollowsSum;
				
				var fanOp = document.getElementById("fanOp"+fanIndex);
				fanOp.innerHTML = "<font style=\"font-size:12px;color:gray\" title=\"已添加关注\">已添加关注</font>";			
			}else if(result == "SUCCESS" && fansOwner == "other"){
				var fanOp = document.getElementById("fanOp"+fanIndex);
				fanOp.innerHTML = "<font style=\"font-size:12px;color:gray\" title=\"已添加关注\">已添加关注</font>";
			}
		}
	}
}