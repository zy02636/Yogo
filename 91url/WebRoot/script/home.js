//全局变量
var tagInputId = "";//tagInput的id
var sugLength = 0; //suggestion的长度
var sugIndex = -1; //suggestion高亮条目ID
var clickValue = "";//suggestion被选中的值
var xmlHttp; //Ajax 访问对象
var urlIdIndex = "";//书签的ID
var browser = "";//获取浏览器名称
var version = "";//获取浏览器版本
var subUrlTitle = false;//检测url的title是否包含特殊字符
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


//输入url的文本框提示文字
var urlTextFocus = function(input){
	if(input.value == "请输入网页的网址"){
		input.value = "";
	}
	input.style.color = "black";
}

var urlTextBlur = function(input){
	if(input.value == "请输入网页的网址" || input.value == ""){
		input.value = "请输入网页的网址";
	}
	var urlLength = input.value.length;
	if(urlLength > 255){
		document.getElementById("infoDiv").innerHTML = "提交的网址长度超出限制";
		document.getElementById("save").disabled = true;//禁止提交
		timerId = setTimeout("hiddenInfoDiv()",1500)
	}else{
		document.getElementById("infoDiv").innerHTML = "";
		document.getElementById("save").disabled = false;//禁止提交
	}
	input.style.color = "gray";
}

//标签层变换颜色
var changeOver = function(div){
	div.style.backgroundColor = "#999999";
	div.style.color="white";
	var nodes = div.childNodes;
    //nodes.length: 右边的用户书签不具备删除功能,长度为
	if(nodes.length==2){ //页面生成的node跟js添加的node的数量不同,所以要作个判断
	    nodes[0].style.visibility = "visible";
	}else if(nodes.length==3){
	    nodes[1].style.visibility = "visible";
	}
}
var changeBlur = function(div){
	div.style.backgroundColor = "#EEEEEE";
	div.style.color="black";
	var nodes = div.childNodes;
	if(nodes.length==2){
	    nodes[0].style.visibility = "hidden";
	}else if(nodes.length==3){
	    nodes[1].style.visibility = "hidden";
	}
}

var changeImgOver = function(img){
	img.src = "img/delete_0.png";
}
var changeImgBlur = function(img){
	img.src = "img/delete.png";
}
var changeComImgOver = function(img){
	img.src = "img/delete.png";
}
var changeComImgBlur = function(img){
	img.src = "img/delete_0.png";
}
//span text 仿 超文本
var spanTextOver = function(span){
	span.style.textDecoration = "underline";
}
var spanTextOut = function(span){
	span.style.textDecoration = "none";
}
//计算文本框的文本长度
/**
 * 输入的文本框,信息层ID,设定标签的长度
 */
var calUserTagLength = function(inputTag,divId,length,event){
	var key = window.event ? event.keyCode : event.which;
	
	var suggestion = document.getElementById("tagSuggestion");

	//上移			
	if(key == 38){
		sugIndex = sugIndex - 1;
		if(sugIndex <= 0){
			sugIndex = 0;
		}
		var highLighItem = document.getElementById("sug"+sugIndex);
		highLighItem.style.backgroundColor = "#005EAC";
		highLighItem.style.color = "white";
		clickValue = highLighItem.innerHTML.replace(/^\s*|\s*$/g,"");//去掉空格;
		
		var prehighlightItem = document.getElementById("sug"+(sugIndex+1));
		prehighlightItem.style.backgroundColor = "white";
		prehighlightItem.style.color = "black";
		
	}else if(key == 40){
		sugIndex = sugIndex + 1;
		if(sugIndex >= sugLength-1){
			sugIndex = sugLength-1;
		}
		var highLighItem = document.getElementById("sug"+sugIndex);
		highLighItem.style.backgroundColor = "#005EAC";
		highLighItem.style.color = "white";
		clickValue = highLighItem.innerHTML.trim();
		
		var prehighlightItem = document.getElementById("sug"+(sugIndex-1));
		prehighlightItem.style.backgroundColor = "white";
		prehighlightItem.style.color = "black";
	}else if(key == 13){
		inputTag.value = clickValue;
		suggestion.style.visibility = "hidden";
		clickValue = "";
		sugIndex = -1;
	}else{
		var text = inputTag.value;
		if(text.length >= 1){
			if(inputTag.id == "userTagInput"){
	  	        getTagByPrefix(text,"userTag");//line 690
			}else{
				getTagByPrefix(text,"urlTag");//line 690
			}
		}else{
			var suggestion = document.getElementById("tagSuggestion");
			suggestion.style.visibility = "hidden";
		}
	    var text = inputTag.value;
		var infoDiv = document.getElementById(divId);
		infoDiv.style.visibility = "visible";
		infoDiv.style.color = "black";
		
		var lengthCounter = 0; //计算用户自我描述的标签长度
		var maxLengt = length;
		var rname=/[\u4E00-\u9FA5]/;//判断是否为中文
		var textLen = text.length;

		for(var i = 0; i < textLen; i++){
			if(rname.test(text.substr(i,i+1))){
				lengthCounter += 2;
			}else{
				lengthCounter += 1;
			}
		}
		
		subUrlTitle = true;//默认可以保存urlTitle
		var pattern = /\|/;
		if(pattern.test(text)){
			subUrlTitle = false;
			var urlTagInfo = document.getElementById("addUrlTagInfo"+urlIndex);
			urlTagInfo.style.color = "red";
			urlTagInfo.innerHTML = "标题为空";
			urlTagInfo.style.visibility = "visible";
			hideDiv(urlTagInfo);
		}
		var infoStr = (length-lengthCounter) + " / " + length;

		infoDiv.innerHTML = infoStr;
		infoDiv.Value = length-lengthCounter;
	}
}
//隐藏层
var hideDiv = function(div){
	hideDivTimer = setTimeout("temphideDiv('"+div.id+"')",1500);
}
var temphideDiv = function(divId){
	var div = document.getElementById(divId);
	if(div != null){
		div.style.visibility = "hidden";
	}
}
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

    divMain.style.overflow = "auto"; //层嵌套,父子层自动扩展

	if(browserName != "IE"){
		var imgLogo = document.getElementById("imgLogo");
		imgLogo.src = "img/logo.png";
		
		//var divHeader = document.getElementById("divHeader");
		//divHeader.style.backgroundImage = "url(img/bc.png)";
	}
	var selUrlType = document.getElementById("selUrlType");
	if(selUrlType != null){
		var urlRange = parseInt(request("urlRange"));
		if(urlRange == null){
			selUrlType.selectedIndex = 0;
		}else{
			selUrlType.selectedIndex = urlRange;
		}
	}
}
function request(paras){ 
	var url = location.href; 
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
	var paraObj = {}; 
	for (i=0; j=paraString[i]; i++){ 
		paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
	} 
	var returnValue = paraObj[paras.toLowerCase()]; 
	if(typeof(returnValue)=="undefined"){ 
		return ""; 
	}else{ 
	    return returnValue; 
	} 
} 

function loadImage(imgPath,callback) {
    var img = new Image(); //创建一个Image对象，实现图片的预下载
    img.src = imgPath; 
    if (img.complete) { // 如果图片已经存在于浏览器缓存，直接调用回调函数
        callback.call(img);
        return; // 直接返回，不用再处理onload事件
    }
    img.onload = function () { //图片下载完毕时异步调用callback函数。
        callback.call(img);//将回调函数的this替换为Image对象
    };
}

function imgLoaded(){
	var userImageInnerContainer = document.getElementById("userImageInnerContainer");
	userImageInnerContainer.style.width = this.width+"px";
	userImageInnerContainer.style.height = this.height+"px";
	
	var userImageContainer = document.getElementById("userImageContainer");
	userImageContainer.style.width = this.width+"px";
	userImageContainer.style.height = this.height+"px";
}
//提示信息的计时器
var timerId;
//检测输入的url
var subRequest = function() {
	var urlString = document.getElementById("url").value;
	var infoDiv = document.getElementById("infoDiv");
	if (urlString.length > 0 && urlString!="请输入网页的网址") {
		var positionHttp = urlString.indexOf("http://");
		var positionHttps = urlString.indexOf("https://");
		if (positionHttp < 0 && positionHttps < 0) {// 检测url地址开头必须为http 或者 https
			urlString = "http://" + urlString;
		}
		regExp = "[a-zA-z]+://[^\s]*"; 
		if (urlString.match(regExp)) {
			document.getElementById("url").value = urlString;
			subUrl(urlString);
		} else {
			infoDiv.style.color = "red";
			infoDiv.style.visibility = "visible";
			infoDiv.innerHTML = "提交的网址格式不正确";
			timerId = setTimeout("hiddenInfoDiv()",1500);
		}
	} else {
		infoDiv.style.color = "red";
		infoDiv.style.visibility = "visible";
		infoDiv.innerHTML = "提交的网址不能为空";
		timerId = setTimeout("hiddenInfoDiv()",1500);
	}
}

var hiddenInfoDiv = function(){
	document.getElementById("infoDiv").innerHTML = "";
	clearTimeout(timerId);
}

var createXmlHttpRequest = function() {
	if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	}
}

/**
 * 提交url
 * 
 * @param urlContent
 * @return
 */
var subUrl = function(urlContent) {
	var subButton = document.getElementById("save");
	subButton.value ="提交中...";
	subButton.disabled = true;
	createXmlHttpRequest();
	
	var infoDiv = document.getElementById("infoDiv");//以后要美化一下
	infoDiv.innerHTML="<font style='color:gray;padding-bottom:5px;paddin-top:2px;'>保存速度取决于网页响应速度</font>"
	hideDiv(infoDiv);
	
	var nickName = document.getElementById("nickName").value;
	var userId = document.getElementById("userId").value;

	var urlType = 0;
	var ckUrlType = document.getElementById("ckPrivate");
	if(ckUrlType.checked){
		urlType = 1;
	}
	var url = "addUrl.action?url=" + encodeURIComponent(urlContent)+"&userId="+userId+"&urlType="+urlType;
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function() {
		if (xmlHttp.readyState == 4) {
			if (xmlHttp.status == 200) {
				var tags = document.getElementsByName("tags");
				//var tagsId = new Number(tags[tags.length - 1].id.substring(4)) + 1;

				var resText = xmlHttp.responseText;
				var strList = resText.split("&_");//strList[3],空格
				strList[3] = strList[3].trim();
				var urlIndex = document.createElement("div");
				urlIndex.className = "url";
				urlIndex.id = "urlIndex" + strList[3];
				//strList[3] 返回的是urlId,这边动态生成url的层需要一个urlIndex,这边直接用作
				//urlId来代替,注意,当urlId为0-10时,可能会出现BUG,因为返回的urlId跟urlIndex可能会重复.
				
	
		        var urlContent = document.createElement("div");
		        urlContent.id = "urlContent" + strList[3];
		        
		        var hyperDiv = document.createElement("div");
		        hyperDiv.id = "hyperDiv" + strList[3];
		        hyperDiv.className = "hyperDiv";
		       
		        
				var userLink = document.createElement("a");
				userLink.className = "hyper";
				userLink.setAttribute("href","getProfile.action?id="+userId);
				userLink.innerHTML = "我";
				
				var comaFont = document.createElement("font");
				comaFont.style.fontSize= "12px";
				
				comaFont.innerHTML = ": ";
				
				var markLink = document.createElement("a");
				markLink.className = "hyper";
				markLink.id = "urlLink" + strList[3];
				markLink.setAttribute("target","_blank");
				markLink.setAttribute("title",strList[0]);
				markLink.setAttribute("href",strList[0]);
				markLink.innerHTML = strList[1];
				
				
				var divId = document.createElement("div");
				divId.id = "urlId" + strList[3];
				divId.style.display = "none";
				divId.innerHTML = strList[3];
				
				var br = document.createElement("br");
				
				var tags = document.createElement("div");
				tags.className = "tags";
				tags.id = "tags" + strList[3];
				
				//
				var urlFeatures = document.createElement("div");
	
				urlFeatures.id = "urlFeatures" + strList[3];
				
				////
				var share = document.createElement("div");
				share.className = "share";
				share.id = "share" + strList[3];
				
				var firstSave = document.createElement("font");
				firstSave.style.fontSize = "12px";
				firstSave.style.color = "gray";
				
				
				firstSave.innerHTML = "最先收录: ";
				
				var firstSaver = document.createElement("a");
				firstSaver.setAttribute("href","getProfile.action?id="+userId);
				firstSaver.style.fontSize = "12px";
				firstSaver.className = "hyper";
				firstSaver.innerHTML = " 我 ";
				

				
				////
				////
				var tagComment = document.createElement("div");
				tagComment.className = "tagComment";
				tagComment.id = "tagComment" + strList[3];
				
				var spanCom = document.createElement("span");
				spanCom.id = "spanCom" + strList[3];
				spanCom.className = "spanText";
				spanCom.onclick = function(){//shareId 需要返回
					showComments(spanCom);
				};
				spanCom.onmouseover = function(){
					spanTextOver(spanCom);
				};
				spanCom.onmouseout = function(){
					spanTextOut(spanCom);
				};
				spanCom.innerHTML = " 评论(0) ";	
				
				////
				
				var edit = document.createElement("div");
				edit.style.color = "gray";
				edit.style.fontSize = "12px";
				edit.className = "edit";
				edit.innerHTML = "<a class='hyper' style='font-size:12px;' id='edit"+strList[3]+"' onclick='editUrl("+strList[3]+")' title='编辑书签标题'>编辑</a> &nbsp;|&nbsp;&nbsp;" 
		        +"<a class='hyper' style='font-size:12px;'  onclick='queryDelUrl("+strList[3]+")' title='删除书签'>删除 </a>&nbsp;|";

				
		        ////
				var tagTime = document.createElement("div");
				tagTime.className = "tagTime";
				tagTime.style.fontSize = "12px";
				tagTime.style.color = "gray";
				tagTime.id = "tagTime" + strList[3];
				tagTime.innerHTML = "<font style='color:gray'>最先收录: </font>" +
						"<a href='getProfile.action?id="+userId+"/>' class='hyper' style='font-size:12px;'>我</a> &nbsp;&nbsp;&nbsp;" +
						"<a href='getProfile.action?id="+userId+" />' class='hyper' style='font-size:12px;'>我</a> 保存于: 今天 "+strList[2].substring(11,16);
	
				
				var time = document.createElement("div");
				time.style.color = "gray";
				time.style.fontSize = "12px";
				time.innerHTML = "<a href=\"getProfile.action?id="+userId+"\" class=\"hyper\" style='font-size:12px;'>我</a>  保存于: 今天"+strList[2].substring(11,16);
					

				var addUrlTagInfo = document.createElement("div");
				addUrlTagInfo.className = "addUrlTagInfo";
				addUrlTagInfo.style.fontSize = "12px";
				addUrlTagInfo.id = "addUrlTagInfo" + strList[3];
				
				var addTag = document.createElement("div");
				addTag.style.color = "gray";
				addTag.className = "addTag";
				addTag.id = "addTag" + strList[3];
				addTag.innerHTML = "<a class=\"hyper\" style=\"font-size:12px;\" id=\"addTag"+strList[3]+"\" onclick=\"addTag('"+strList[3]+"')\" title=\"添加标签\" />添加标签</a> &nbsp;|&nbsp;&nbsp;";
				
				////
				var comments = document.createElement("div");
				comments.id = "comments" + strList[3];
				comments.className = "comments";
				
				var commentsContent = document.createElement("div");
				commentsContent.id = "commentsContent" + strList[3];
				commentsContent.className = "commentsContent";
				
				
				var reply = document.createElement("div");
				reply.id = "reply" + strList[3];
				reply.className = "reply";
				
				var commentPage = document.createElement("div");
				commentPage.id = "commentPage" + strList[3];
				commentPage.className = "commentPage";
				
				var replyText = document.createElement("textarea");
				replyText.id = "replyText" + strList[3];
				replyText.className = "replyText";
				replyText.onkeyup = function(){
					calCommentLength(replyText,strList[3]);
				};
				replyText.onfocus = function(){
					replyText.style.color='black';
				};
				replyText.onblur = function(){
					replyText.style.color='gray';
				};
				
				var commentWordCounter = document.createElement("div");
				commentWordCounter.id = "commentWordCounter" + strList[3];
				commentWordCounter.className = "replyTextInfo";
				commentWordCounter.innerHTML = "140/140";
				
				
				var replyButton = document.createElement("input");
				replyButton.value = "提交";
				replyButton.className = "replyButton";
				replyButton.setAttribute("type","button");
				replyButton.onclick = function(){
					subUrlComment(strList[3]);
				};
				
		        urlIndex.appendChild(urlContent);
		        urlContent.appendChild(hyperDiv);

				hyperDiv.appendChild(userLink);
				hyperDiv.appendChild(comaFont);
				hyperDiv.appendChild(markLink);
				
				urlContent.appendChild(divId);

				urlContent.appendChild(tags);
				urlContent.appendChild(urlFeatures);
				urlContent.appendChild(comments);
				
				
				urlFeatures.appendChild(share);
				
				urlFeatures.appendChild(tagTime);
				
				urlFeatures.appendChild(addUrlTagInfo);

				urlFeatures.appendChild(tagComment);
				tagComment.appendChild(spanCom);
				
				urlFeatures.appendChild(edit);
				urlFeatures.appendChild(addTag);

				comments.appendChild(commentsContent);
				reply.appendChild(commentPage);
				reply.appendChild(replyText);
				reply.appendChild(commentWordCounter);
				reply.appendChild(replyButton);
				comments.appendChild(reply);
				
				var urlList = document.getElementById("urlList");
				var firstUrlListNode = urlList.firstChild;
				urlList.insertBefore(urlIndex,firstUrlListNode);

				var subButton = document.getElementById("save");
				subButton.disabled = false;
				subButton.value ="保存";
				subButton.style.backgroundColor = "#005EAC";
				document.getElementById("url").value = "请输入网页的网址";
				
				var urlsSum = document.getElementById("urlsSumLink");
				var intUrlsSum = parseInt(urlsSum.innerHTML)+1;
				urlsSum.innerHTML = intUrlsSum;
			}else{
				infoDiv.innerHTML="由于网络问题,书签添加失败";//以后要美化一下
				hideDiv(infoDiv);
			}
		}
	};
	xmlHttp.send(null);
}
/**
 * url标签输入框事件
 * @param input
 * @return
 */
var fillTag=function(input){
	catchInputPos(input);
    input.style.color = "black";
    if(input.value=="请输入标签"){
        input.value="";
    }
    tagInputId = input.id;
}
var clearTag=function(input){
	hideSuggestion();
    input.style.color = "gray";
    if(input.value==""){
        input.value="请输入标签";
    }

    if(clickValue!=""){
    	input.value = clickValue;
    	clickValue = "";
    }
    tagInputId = "";
}
/**
 * 提交书签的标签
 * @param tagContent
 * @return
 */
var submitTag = function(urlId, tagsId) {
	var tagName = document.getElementById("newTag" + tagsId).value;
	var pattern =  /\|/;

	if(tagName =="请输入标签"){
		var addUrlTagInfo = document.getElementById("addUrlTagInfo" + tagsId);
		addUrlTagInfo.style.visibility = "visible";
		addUrlTagInfo.innerHTML = "<font style='color:red'>标签为空</font>";
		hideDiv(addUrlTagInfo);
	}else if(pattern.test(tagName)){
		var addUrlTagInfo = document.getElementById("addUrlTagInfo" + tagsId);
		addUrlTagInfo.style.visibility = "visible";
		addUrlTagInfo.innerHTML = "<font style='color:red'>特殊字符</font>";
		hideDiv(addUrlTagInfo);
	}else{
		var lengthCounter = 0; //计算用户自我描述的标签长度
		var rname=/[\u4E00-\u9FA5]/;//判断是否为中文
		var text = tagName;
		var textLen = text.length;
		for(var i = 0; i < textLen; i++){
			if(rname.test(text.substr(i,i+1))){
				lengthCounter += 2;
			}else{
				lengthCounter += 1;
			}
		}

		if(lengthCounter > 0 && lengthCounter <= 10){
			subTag(urlId, tagName, tagsId);
		}else{
			var addUrlTagInfo = document.getElementById("addUrlTagInfo" + tagsId);
			addUrlTagInfo.style.visibility = "visible";
			addUrlTagInfo.innerHTML = "<font style='color:red'>标签过长</font>";
			hideDiv(addUrlTagInfo);
		}
	}
}
//tagsId = urlIndex
var subTag = function(urlId, tagName, tagsId) {
	var userId = document.getElementById("userId").value;
	createXmlHttpRequest();
	var url = "addTag.action?urlId=" + urlId + "&tagName=" + tagName +"&userId="+userId;
	url = encodeURI(url);
	url = encodeURI(url);//加两次..
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function() {
		afterTagRequest(tagsId,tagName);
	};
	xmlHttp.send(null);
}

var addIconTimer;//计时器,添加成功后用于更换图标
var afterTagRequest = function(tagsId,tagName) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200){
			hideDiv("addUrlTagInfo"+tagsId);//隐藏信息层
			var result = xmlHttp.responseText;
			result = result.trim();//去掉空格
		
			var urlTagInfo = document.getElementById("addUrlTagInfo"+tagsId);
			if (result == "REPEAT"){
				urlTagInfo.style.color = "red";
				urlTagInfo.innerHTML = "重复添加";
				urlTagInfo.style.visibility = "visible";
				urlTagTimer = setTimeout("clearUrlTagInfo("+tagsId+")",1500);
			}else if(result == "ERROR"){
				urlTagInfo.style.color = "red";
				urlTagInfo.innerHTML = "添加失败";
				urlTagInfo.style.visibility = "visible";
				urlTagTimer = setTimeout("clearUrlTagInfo("+tagsId+")",1500);
			}else if(result == "OVER"){
				urlTagInfo.style.color = "red";
				urlTagInfo.innerHTML = "超出限制";
				urlTagInfo.style.visibility = "visible";
				urlTagTimer = setTimeout("clearUrlTagInfo("+tagsId+")",1500);
			}else{
				var urlTags = document.getElementById("tags" + tagsId);
			    urlTags.removeChild(urlTags.lastChild);
			    
			    var tag = document.createElement("div");
			    tag.className = "tag";
			    tag.id = "urlTag"+result+"_"+tagsId;
			    
			    tag.onmouseover = function(){
			    	    changeOver(this);
			    };
			    tag.onmouseout = function(){
			    		changeBlur(this);
			    };

			    tag.innerHTML = "<div name='delDiv' class='delTagDiv' onclick='delUrlTag("+result+","+tagsId+")'><img src='img/delete.png' onmouseover=\"changeImgOver(this)\" onmouseout=\"changeImgBlur(this)\"/></div>" +tagName;
			    tag.onclick = function(){
			    	document.location = 'Url.action?tagId='+result;
			    };
			    urlTags.appendChild(tag);

				var addTag = document.getElementById("addTag" + tagsId);
				addTag.innerHTML = "<a style='color:gray;font-size:12px;'>添加成功</a>&nbsp;&nbsp;|&nbsp;&nbsp;";
				addIconTimer = setTimeout("changIcon("+tagsId+")",1500);
				
				var urlTagInfo = document.getElementById("addUrlTagInfo"+tagsId);
				hideDiv(urlTagInfo);
			} 
		}else{
			document.getElementById("infoDiv").innerHTML="添加失败";//以后要美化一下
			document.getElementById("save").value = "保存";
			var urlTags = document.getElementById("tags" + tagsId);
			urlTags.removeChild(urlTags.lastChild);
			var addTag = document.getElementById("addTag" + tagsId);
			addTag.innerHTML = "<a style='color:red;font-size:12px;'>添加失败</a>&nbsp;&nbsp;|&nbsp;&nbsp;";
			addIconTimer = setTimeout("changIcon("+tagsId+")",1500);
			timerId = setTimeout("hiddenInfoDiv()",1500);
		}
	}
}

var changIcon = function(tagsId){
	var addTag = document.getElementById("addTag" + tagsId);
	addTag.innerHTML = "<a class=\"hyper\" style=\"font-size:12px;\" id=\"addTag"+tagsId+"\" onclick=\"addTag('"+tagsId+"')\" title=\"添加标签\" />添加标签</a> &nbsp;|&nbsp;&nbsp;";
	clearTimeout(addIconTimer);
}

// 增加书签的标签的按钮事件
var addTag = function(tagsId) {
	var urlId = document.getElementById("urlId" + tagsId).innerHTML;
	var addTag = document.getElementById("addTag" + tagsId);
	var urlTags = document.getElementById("tags" + tagsId);
	var urlTagInfo = document.getElementById("addUrlTagInfo"+tagsId);
	
    var childCounter = 0;
    for(var i=0; i < urlTags.childNodes.length; i++){
        if(urlTags.childNodes[i].nodeType == 1 ){
        	childCounter = childCounter + 1;
        }                  
    };

	if(childCounter >= 6){
		urlTagInfo.style.color = "red";
		urlTagInfo.innerHTML = "数量超限";
		urlTagInfo.style.visibility = "visible";
		urlTagTimer = setTimeout("clearUrlTagInfo("+tagsId+")",1500);
	}else{
		addTag.innerHTML = 
		"<a class=\"hyper\" style=\"font-size:12px;\" id=\"addTag"+tagsId+"\" onclick=\"cancelTag('"+tagsId+"')\" />取消</a>&nbsp;&nbsp;|&nbsp;&nbsp;"
		+"<a class=\"hyper\" style=\"font-size:12px;\" id=\"addTag"+tagsId+"\" onclick=\"submitTag(" + urlId + ","
		+ tagsId + ")\" title=\"提交标签\" />提交标签</a> &nbsp;|&nbsp;&nbsp;"	


		var urlTag = document.createElement("div");
		

		browserName = browser.substr(0,2);//全局变量
		
		var newTag = document.createElement("input");
		newTag.id = "newTag" + tagsId;
		newTag.className = "inputTag";
		newTag.value = "请输入标签";

		newTag.onkeyup = function(){
			calUserTagLength(newTag,"addUrlTagInfo"+tagsId,10,event);
		}
		newTag.onfocus = function(){
			fillTag(newTag);
		}
		newTag.onblur = function(){
			clearTag(newTag);
		}

		urlTags.appendChild(newTag);
	}
}


// 取消Tag的添加的按钮事件
var cancelTag = function(tagsId) {
	hideDiv("addUrlTagInfo"+tagsId);
	var addTag = document.getElementById("addTag" + tagsId);
	addTag.innerHTML = "<a class=\"hyper\" style=\"font-size:12px;\" id=\"addTag"+tagsId+"\" onclick=\"addTag('"+tagsId+"')\" title=\"添加标签\" />添加标签</a> &nbsp;|&nbsp;&nbsp;";

	var urlTags = document.getElementById("tags" + tagsId);
	urlTags.removeChild(urlTags.lastChild);
}
//修改按钮颜色
var overSub=function(button){
	button.style.backgroundColor = "#0F77B7";
}
var blurSub=function(button){
	button.style.backgroundColor="#005EAC";
}


/**
 * 修改用户头像
 */
var img = new Image();

var userImageTimer;
var changeUserImage = function(){
	var imgFile = document.getElementById("imgFile");
	imgFile.style.display = "block";
}

var callBackChangeUserImage = function(imgPath,callBacInfo,width,height){
	var imgFile = document.getElementById("uploadInfo");
	imgFile.innerHTML = callBacInfo;
	
	var userImageInnerContainer = document.getElementById("userImageInnerContainer");
	userImageInnerContainer.style.backgroundImage = "url('"+imgPath+"')";
	userImageInnerContainer.style.width = width+"px";
	userImageInnerContainer.style.height = height+"px";
	
	var userImageContainer = document.getElementById("userImageContainer");
	userImageContainer.style.width = width+"px";
	userImageContainer.style.height = height+"px";
	
	userImageTimer = setTimeout("hideUserImage()",1500);
}
var hideUserImage = function(){
	var imgFile = document.getElementById("imgFile");
	imgFile.style.display = "none";
	document.getElementById("userImg").Value = "";
	var uploadInfo = document.getElementById("uploadInfo");
	uploadInfo.innerHTML = "";
	
	clearTimeout(userImageTimer);
}

var callIFrame = function(){
	var hidden_frame = document.getElementById('hidden_frame').contentWindow.test(); 
}
/**
 * 修改用户昵称
 */
var editNickName = function(link){
	var nickNameDiv = document.getElementById("linkNickName");
	var tempHTML = nickNameDiv.innerHTML;//存先前的HTML
	
	browserName = browser.substr(0,2);
	var nickName = "";
	if(browserName != "FF"){
		nickName = nickNameDiv.innerText;
	}else{
		nickName = nickNameDiv.textContent;
	}
	
	var inputName = document.createElement("input");
	inputName.id = "newNickName";
	inputName.value = nickName.trim();
	inputName.style.color = "gray";
	inputName.style.height = "25px";
	inputName.style.width = "100px";
	inputName.style.fontSize = "16";
	inputName.onfocus = function(){
		this.style.color = "black";
	}
	inputName.onblur = function(){
		this.style.color = "gray";
	}
	
	nickNameDiv.innerHTML = "";
	
	var br = document.createElement("br");
	nickNameDiv.appendChild(inputName);
	nickNameDiv.appendChild(br);

	link.innerHTML = "保存";
	link.onclick = function(){
		saveNickName(link,tempHTML,nickName,inputName.value);
	}
}

var saveNickName = function(link,exHTML,oldName,newName){
	newName = newName.trim();
	var pattern = /^([\u4E00-\uFA29]|[\uE7C7-\uE7F3]|[\w])*$/;
	if(!pattern.test(newName)){
		var nickNameInfoDiv = document.getElementById("nickNameInfo");
		nickNameInfoDiv.innerHTML = "用户名只能包含中文,英文或者数字";
		nickNameInfoDiv.style.visibility = "visible";
		hideDiv(nickNameInfoDiv);
	}else{
		if(newName.length > 6){
			var nickNameInfoDiv = document.getElementById("nickNameInfo");
			nickNameInfoDiv.innerHTML = "用户名过长";
			nickNameInfoDiv.style.visibility = "visible";
			hideDiv(nickNameInfoDiv);
		}else if(newName.length <=0){
			var nickNameInfoDiv = document.getElementById("nickNameInfo");
			nickNameInfoDiv.innerHTML = "用户名不能为空";
			nickNameInfoDiv.style.visibility = "visible";
			hideDiv(nickNameInfoDiv);
		}else{
			var userId = document.getElementById("userId").value;
			
			link.innerHTML = "修改昵称";
			link.onclick = function(){
				editNickName(link);
			}
			
			createXmlHttpRequest();
			var url = "updateUserName.action?nickName="+encodeURIComponent(newName.trim())+"&userId="+userId;
			url = encodeURI(url);
			xmlHttp.open("POST", url, true);
			xmlHttp.onreadystatechange = function(){
				afterSaveNickName(exHTML,oldName,newName,userId);
			};
			xmlHttp.send(null);
		}
	}
}

var afterSaveNickName = function(exHTML,oldName,newName,userId){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200){
			var result = xmlHttp.responseText.trim();
			if(result == "SUCCESS"){
				var nickNameDiv = document.getElementById("linkNickName");
				nickNameDiv.innerHTML = "<a style='font-size:16px' class='hyper' href='getProfile.action?id="+userId+"'>"+newName+"</a>";
			}else{
				var nickNameInfoDiv = document.getElementById("nickNameInfo");
				nickNameInfoDiv.innerHTML = "因为网络问题更新失败";
				nickNameInfoDiv.style.visibility = "visible";
				hideDiv(nickNameInfoDiv);
			}
		}
	}
}
/**
 * 添加用户标签js
 * @param input
 * @return
 */
var clearUserTag = function(input){
	catchInputPos(input);
    input.style.color = "black";
    if(input.value=="你的兴趣?爱好?特长?"){
        input.value="";
    }
    tagInputId = input.id;//获取html元素,对suggestion层进行定位
}
var fillUserTag = function(input){
	hideSuggestion();
    input.style.color = "gray";
    if(input.value==""){
        input.value="你的兴趣?爱好?特长?";
    }

    if(clickValue!=""){
    	input.value = clickValue;
    	clickValue = "";
    }
    tagInputId = "";
}
var hideSuggestion = function(){
	var sug = document.getElementById("tagSuggestion");
	sug.innerHTML = "";
	sug.style.visibility = "hidden";
}
var hiddeUserTag = function(){
	var userTagDiv = document.getElementById("addUserTagFormDiv");
	userTagDiv.style.display = "none";
	var userTagInfoDiv = document.getElementById("userTagInfoDiv");
	userTagInfoDiv.style.visibility = "hidden";
}
var showUserTag = function(span){
	var userTagDiv = document.getElementById("addUserTagFormDiv");
	userTagDiv.style.display = "block";
	
	span.innerHTML = "关闭";
	
	browserName = browser.substr(0,2);
    if(browserName=="CR"){
    	span.setAttribute("onclick","closeUserTag(this)");
    }else if(browserName=="IE"){
    	span.onclick = function(){
    		closeUserTag(this);
	    };
    }else{
    	span.setAttribute("onclick","closeUserTag(this)");
    }
	
}
var closeUserTag = function(span){
	var userTagDiv = document.getElementById("addUserTagFormDiv");
	userTagDiv.style.display = "none";
	span.innerHTML = "添加";
	
	browserName = browser.substr(0,2);
    if(browserName=="CR"){
    	span.setAttribute("onclick","showUserTag(this)");
    }else if(browserName=="IE"){
    	span.onclick = function(){
    		showUserTag(this);
	    };
    }else{
    	span.setAttribute("onclick","showUserTag(this)");
    }
}
var userTagTimer;
var saveUserTag = function(){
	var userTagInput = document.getElementById("userTagInput");
	var userTagName = userTagInput.value;
	
	if(userTagName=="你的兴趣?爱好?特长?" || userTagName==""){
	    var userTagInfoDiv = document.getElementById("userTagInfoDiv")
	    userTagInfoDiv.style.color = "red";
	    userTagInfoDiv.innerHTML = "写点什么吧";
	    userTagInfoDiv.style.visibility = "visible";
		userTagTimer = setTimeout("hiddenUserTagInfo()",1500);
	}else if(document.getElementById("userTagInfoDiv").Value > 15){
		
	}else{
		subUserTag(); //验证通过后提交用户自我描述的标签
	}
}

var hiddenUserTagInfo = function(){
	document.getElementById("userTagInfoDiv").style.visibility = "hidden";
	clearTimeout(userTagTimer);
}
/**
 * 存用户的自我描述的标签
 * @return
 */
var subUserTag = function(){
	var userId = document.getElementById("userId").value;
	var userTagName = document.getElementById("userTagInput").value;
	if(userTagName.length >15){
		var userTagInfoDiv = document.getElementById("userTagInfoDiv");
		userTagInfoDiv.innerHTML = "<font style='color:red;'>标签过长</font>";
	}else{
		var subUserTag = document.getElementById("subUserTag");
		subUserTag.value = "保存中..";
		subUserTag.disabled = true;
	
		createXmlHttpRequest();
	
		var url = "addUserTag.action?userId=" + userId + "&userTagName=" + encodeURIComponent(userTagName);
		url = encodeURI(url);
		xmlHttp.open("POST", url, true);
		xmlHttp.onreadystatechange = afterAddUserTagRequest;
		xmlHttp.send(null);
	}
}

var afterAddUserTagRequest = function() {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var subUserTag = document.getElementById("subUserTag");
			subUserTag.value = "保存";
			subUserTag.disabled = false;
			var result = xmlHttp.responseText;
			result = result.replace(/^\s*|\s*$/g,"");//去掉空格
			if(result=="REPEAT"){
				document.getElementById("userTagInfoDiv").style.visibility ="visible";
				document.getElementById("userTagInfoDiv").innerHTML = "<font style='color:red'> 重复添加相同标签</font>";
				userTagTimer = setTimeout("hiddenUserTagInfo()",1500);
			}else if(result=="ERROR"){
				document.getElementById("userTagInfoDiv").style.visibility ="visible";
				document.getElementById("userTagInfoDiv").innerHTML = "<font style='color:red'> 网络问题添加失败</font>";
				userTagTimer = setTimeout("hiddenUserTagInfo()",1500);
			}else if(result=="OVER"){
				document.getElementById("userTagInfoDiv").style.visibility ="visible";
				document.getElementById("userTagInfoDiv").innerHTML = "<font style='color:red'> 超过限定数目的用户标签数量</font>";
				userTagTimer = setTimeout("hiddenUserTagInfo()",1500);
			}else{
				var userTagName = document.getElementById("userTagInput").value;
				var userTags = document.getElementById("userTags");
			    var userTag = document.createElement("div");
			    userTag.className = "userTag";
			    userTag.name = "userTag";
			    userTag.id = "userTag"+result;
			    
			    
			    browserName = browser.substr(0,2);
		    	userTag.onmouseover = function(){
			    	    changeOver(this);
			    };
			    userTag.onmouseout = function(){
			    		changeBlur(this);
			    };
			    userTag.onclick = function(){
			    	document.location = "User.action?userTagId=" + result;
			    };
			    userTag.innerHTML = "<div name='delDiv' class='delDiv' onclick='delUserTag("+result+",event)'><img src='img/delete.png' onmouseover='changeImgOver(this)' onmouseout='changeImgBlur(this)' /></div> " + userTagName;
			    
			    userTags.appendChild(userTag);
			    //document.getElementById("addUserTagFormDiv").style.display = "block";
			    //添加成功后信息全部还原
			    document.getElementById("userTagInput").value = "你的兴趣?爱好?特长?";
			    //var userTagInfoDiv = document.getElementById("userTagInfoDiv")
			    //userTagInfoDiv.style.visibility = "visible";
			    //userTagInfoDiv.style.color = "red";
			    //userTagInfoDiv.innerHTML = "";
			    lengthCounter = 0;
			}
		}
	}
}

var delUserTag = function(userTagId,evt){
	var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
	if (window.event) { 
	    e.cancelBubble=true; 
	} else { 
	//e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
	    e.stopPropagation(); 
	}
	var userId = document.getElementById("userId").value;
	createXmlHttpRequest();

	var url = "delUserTag.action?userId="+userId+"&userTagId=" + userTagId;

	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterDelUserTagRequest(userTagId);
	}
	xmlHttp.send(null);
}

var afterDelUserTagRequest = function(userTagId) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var userTag = document.getElementById("userTag"+userTagId);
			var result = xmlHttp.responseText;
			result = result.substr(0,2);
			if(result == "SU"){ //表示删除成功
				userTag.style.display = "none";
			}else{
				alert("因网络问题删除用户标签失败");
			}
			
		}
	}
}
/**
 * 删除描述url的标签
 * @return
 */
var urlTagTimer;//提示信息消失计时器
var delUrlTag = function(urlTagId,urlIndex,evt){
	
	var e=(evt)?evt:window.event; //判断浏览器的类型，在基于ie内核的浏览器中的使用cancelBubble
	if (window.event) { 
	    e.cancelBubble=true; 
	} else { 
	//e.preventDefault(); //在基于firefox内核的浏览器中支持做法stopPropagation
	    e.stopPropagation(); 
	}
	
	var userId = document.getElementById("userId").value;
	var urlId = document.getElementById("urlId"+urlIndex).innerHTML;
	createXmlHttpRequest();

	var url = "delUrlTag.action?userId="+userId+"&urlTagId=" + urlTagId+"&urlId="+urlId;

	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterDelUrlTagRequest(urlTagId,urlIndex);
	}
	xmlHttp.send(null);
}

var afterDelUrlTagRequest = function(urlTagId,urlIndex) {
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var urlTag = document.getElementById("urlTag"+urlTagId+"_"+urlIndex);
			var result = xmlHttp.responseText;
			result = result.substr(0,2);
			if(result == "SU"){ //表示删除成功
				var urlTagParent = urlTag.parentNode;
				urlTagParent.removeChild(urlTag);
			}else{
				var urlTagInfo = document.getElementById("addUrlTagInfo"+urlIndex);
				urlTagInfo.innerHTML = "<font style='color:red;'>删除失败</font>";
				urlTagInfo.style.visibility = "visible";
				urlTagTimer = setTimeout("clearUrlTagInfo("+urlIndex+")",1500);
			}
		}
	}
}
//清除添加url的标签时的信息
var clearUrlTagInfo = function(urlIndex){
	var urlTagInfo = document.getElementById("addUrlTagInfo"+urlIndex);
	urlTagInfo.innerHTML = "";
	urlTagInfo.style.visibility = "hidden";
	clearTimeout(urlTagTimer);
}
//获取input的坐标,对suggest框进行定位
var pos = function(input){
	//获取元素绝对位置
	    var Left=0,Top=0;
	    
	    Left+=input.offsetLeft,Top+=input.offsetTop;
	    
	    
	    while(input = input.offsetParent) 
	    { 
	    	Top += input.offsetTop; 
	    	Left += input.offsetLeft; 
	    } 
	    return {"Left":Left,"Top":Top};
	}
/*获取元素在页面中x坐标的绝对位置*/ 
function getX(obj){      
    return obj.offsetLeft + (obj.offsetParent ? getX(obj.offsetParent) : obj.x ? obj.x : 0);      
}    
/*获取元素在页面中y坐标的绝对位置*/  
function getY(obj){      
    return (obj.offsetParent ? obj.offsetTop + getY(obj.offsetParent) : obj.y ? obj.y : 0);      
} 
var catchInputPos =	function (input){
    with(pos(input)){
    	var suggestion = document.getElementById("tagSuggestion");
    	suggestion.style.top = Top+input.offsetHeight+"px";
    	suggestion.style.left = Left+"px";
    }
}
//隐藏suggestion层,再重新定位
var rePosSuggestion = function(){
	//var suggestion = document.getElementById("tagSuggestion");
	//suggestion.style.visibility = "hidden";
	
	var tagInput = document.getElementById(tagInputId);
	if(tagInput != null){
	    catchInputPos(tagInput);
	}
	
	var spanSave = document.getElementById("spanCom"+urlIdIndex);
	if(spanSave != null){
	    catchSpanPos(spanSave,urlIdIndex);
	}
	
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
//捕捉scroll 定位可能出现的del url 建议层
window.onscroll =function(){  
	var delAlert = document.getElementById("delAlert"); 
	if(delAlert != null){
		var scrollTop = getScrollTop();
		var clientHeight = document.body.clientHeight/2;
		var clientWidth = document.body.clientWidth/2;
		delAlert.style.top = clientHeight + scrollTop + "px";
		delAlert.style.left = clientWidth + "px"; 
	}  
}

var getTagByPrefix = function(prefix,prefixType){
	var userId = document.getElementById("userId").value;
	createXmlHttpRequest();
	var url = "getTagsByPrefix.action?prefix="+prefix+"&prefixType=" + prefixType+"&userId="+userId;
	url = encodeURI(url);
	url = encodeURI(url);
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterGetTagByPrefix();
	}
	xmlHttp.send(null);
}

var afterGetTagByPrefix = function(){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText;
			var tagName = result.split("&_&");
			var suggestion = document.getElementById("tagSuggestion");
			var sugStr = "";
			
			if(tagName.length <= 1){
				suggestion.style.visibility = "hidden";
			}else{
		    	suggestion.style.visibility = "visible";
				sugLength = tagName.length-1;
				for(var counter = 0; counter < tagName.length-1; counter++){//数组最后一个为空,不取
					sugStr += "<div id='sug"+counter+"' onmouseover='sugOver(this,\""+tagName[counter]+"\",\"userTag\")'  class='sug'> "+tagName[counter]+"</div>";
				}
				suggestion.style.height = 25*(tagName.length-1)+"px";
				suggestion.innerHTML = sugStr;
			}
		}
	}
}

var sugOver = function(div,tagName){
	var highlightItem = document.getElementById("sug"+sugIndex);
	if(highlightItem!=null){
		highlightItem.style.backgroundColor = "white";
		highlightItem.style.color = "black";
	}
	sugIndex = div.id.substring(3);
	div.style.backgroundColor = "#005EAC";
	div.style.color = "white";
	clickValue = tagName;
}
var sugOut = function(div){
	div.style.backgroundColor = "white";
	div.style.color = "black"
}

/*分享url*/
var shareUrl = function(shareId,tagsId){
	var userId = document.getElementById("userId").value;
	createXmlHttpRequest();
	var url = "shareUrl.action?shareId="+shareId+"&userId="+userId+"&urlType="+0;
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterShareUrl(tagsId);
	}
	xmlHttp.send(null);
}

var afterShareUrl = function(tagsId){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();
			
			if(result == "SUCCESS"){
				var shareSum = document.getElementById("spanShare"+tagsId);
				var shareSumText = shareSum.innerHTML;
				var begin = shareSumText.indexOf("(");
				var end =  shareSumText.indexOf(")");
				var curSum = parseInt(shareSumText.substring(begin+1,end));
				curSum = curSum + 1;
				shareSum.innerHTML = "分享("+curSum+")";
				
			}else{
				var urlTagInfo = document.getElementById("addUrlTagInfo"+tagsId);
				urlTagInfo.style.color = "red";
				urlTagInfo.innerHTML = "重复分享";
				urlTagInfo.style.visibility = "visible";
				hideDiv(urlTagInfo);
			}
		}
	}
}
/**
 * 编辑url
 * @return
 */
var editUrl = function(urlIndex){
	var hyperDiv = document.getElementById("hyperDiv"+urlIndex);
	var spanSave = document.getElementById("edit"+urlIndex);
	spanSave.innerHTML = "保存";

	var urlHTML = hyperDiv.innerHTML;
	
	browserName = browser.substr(0,2);
	
	var urlTitle = "";
	if(browserName != "FF"){
		urlTitle = hyperDiv.innerText;
	}else{
		urlTitle = hyperDiv.textContent;
	}
    
	var commaPos = urlTitle.indexOf(":");
	var ownerName = urlTitle.substring(0,commaPos);
    
	var urlText =  urlTitle.substring(commaPos+1);
	
    var textarea = document.createElement("textarea");
    textarea.className = "editArea";

    browserName = browser.substr(0,2);
    urlText = urlText.trim();
    //if(browserName == "IE"){
    	//urlText = urlText.substring(1);
    //}else{
    	//urlText = urlText.substring(2);
    //}
    textarea.onkeyup = function(){
		calUserTagLength(textarea,"addUrlTagInfo"+urlIndex,140,event);
	}
	textarea.onfocus = function(){
		focusTextArea(textarea);
	}
	textarea.onblur = function(){
		blurTextArea(textarea);
	}
	
    textarea.value = urlText;
    hyperDiv.innerHTML = "" ;
    hyperDiv.appendChild(textarea);
    
	spanSave.onclick = function(){
		reFillSpan(hyperDiv,urlIndex,textarea,urlHTML);
	}
	
    //textarea.value = urlText;
    //hyperDiv.innerHTML = "" ;
    //hyperDiv.appendChild(textarea);
}
//urlIndex == tagsId,urlHTML 为原先的div的HTML内容
var reFillSpan = function(hyperDiv,urlIndex,textarea,urlHTML){
	if(!subUrlTitle){
		var urlTagInfo = document.getElementById("addUrlTagInfo"+urlIndex);
		urlTagInfo.style.color = "red";
		urlTagInfo.innerHTML = "含特殊符号";
		urlTagInfo.style.visibility = "visible";
		hideDiv(urlTagInfo);
	}else{
		//先把先前的HTML赋值给改层用于显示,当保存成功后替代url的title
		textarea.disabled = true;
		var newTitleLentgh = textarea.value.length;
	
		if(newTitleLentgh <= 0){
			var urlTagInfo = document.getElementById("addUrlTagInfo"+urlIndex);
			urlTagInfo.style.color = "red";
			urlTagInfo.innerHTML = "标题为空";
			urlTagInfo.style.visibility = "visible";
			hideDiv(urlTagInfo);
			
			textarea.disabled = false;
			hyperDiv.innerHTML = urlHTML;
			var spanSave = document.getElementById("edit"+urlIndex);
			spanSave.innerHTML = "编辑";
			spanSave.onclick = function(){
		    	editUrl(urlIndex);
			}
		}else{
			createXmlHttpRequest();
			var userId = document.getElementById("userId").value;
			var urlId = document.getElementById("urlId"+urlIndex).innerHTML;
			var newUrlTitle = textarea.value;
			var url = "updateUrl.action?userId="+userId+"&urlTitle="+encodeURIComponent(newUrlTitle)+"&urlId="+urlId;
		
			url = encodeURI(url);
			//url = encodeURI(url);//加两.次..
			xmlHttp.open("POST", url, true);
			xmlHttp.onreadystatechange = function(){
				afterUpdateUrl(hyperDiv,urlIndex,textarea,urlHTML);
			};
			xmlHttp.send(null);
		}
	}
}

var afterUpdateUrl = function(hyperDiv,urlIndex,textarea,urlHTML){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();			
			var newTitle = textarea.value;
			textarea.disabled = false;
			hyperDiv.innerHTML = urlHTML;

			//替换url1的标题
			var urlLink = document.getElementById("urlLink"+urlIndex);
			urlLink.innerText = newTitle;
			
			var spanSave = document.getElementById("edit"+urlIndex);
			spanSave.innerHTML = "编辑";
			spanSave.onclick = function(){
		    	editUrl(urlIndex);
			}
			
			if(result != "SUCCESS"){
				var urlTagInfo = document.getElementById("addUrlTagInfo"+urlIndex);
				urlTagInfo.style.color = "red";
				urlTagInfo.innerHTML = "因网络问题更新书签标题失败";
				urlTagInfo.style.visibility = "visible";
				hideDiv(urlTagInfo);
			}else{
				var urlTagInfo = document.getElementById("addUrlTagInfo"+urlIndex);
				hideDiv(urlTagInfo);
			}
		}
	}
}
var focusTextArea = function(textarea){
	textarea.style.color = "black";
}
var blurTextArea = function(textarea){
	textarea.style.color = "gray";
}

var saveEdit = function(urlIndex){
	var spanSave = document.getElementById("edit"+urlIndex);
	
	browserName = browser.substr(0,2);
    if(browserName == "IE"){
    	spanSave.onclick = function(){
    		alert("123");
    	}
    }else{
    	spanSave.setAttribute("onclick","alert('123')");
    }
}

/**
 * 删除url
 * @return
 */
var queryDelUrl = function(urlIndex){
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
	delBg.style.height = document.body.scrollHeight; //根据页面的实际高度决定
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
		delUrl(urlIndex,delAlert,delBg);
	}
	
}

var hideAlert = function(delAlert,delBg){
	delAlert.style.display = "none"; 
	delBg.style.display = "none"; 
}

var delUrl = function(urlIndex,delAlert,delBg){
	var urlId = document.getElementById("urlId" + urlIndex).innerHTML;
	var userId = document.getElementById("userId").value;
	
	createXmlHttpRequest();
	var url = "delUrl.action?userId="+userId+"&urlId="+urlId;
	url = encodeURI(url);
	//url = encodeURI(url);//加两.次..
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterDelUrl(urlIndex,delAlert,delBg);
	};
	xmlHttp.send(null);
}

var afterDelUrl = function(urlIndex,delAlert,delBg){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();
			if(result == "SUCCESS"){
				hideAlert(delAlert,delBg);
			}
			var url = document.getElementById("urlIndex" + urlIndex);
			url.style.display = "none";
			
			var urlsSum = document.getElementById("urlsSumLink");
			var intUrlsSum = parseInt(urlsSum.innerHTML)-1;
			urlsSum.innerHTML = intUrlsSum;
		}
	}
}
/**
 * 定位评论层,并ajax获取评论
 * @param span
 * @return
 */
var showComments = function(span){
	var exSpan = document.getElementById("spanCom"+urlIdIndex);
	if(exSpan != null){
		exSpan.innerHTML = exSpan.innerHTML.replace("关闭评论","评论");
		exSpan.onclick = function(){
			showComments(exSpan);
		};
	}
	
	var spanText = span.innerHTML;
	var begin = spanText.indexOf("(");
	var end = spanText.indexOf(")");
	span.innerHTML = "关闭评论"+spanText.substring(begin,end+1);
    span.onclick = function(){
    		closeComments(span);
	};

	var urlIndex = span.id.substring(7);
	urlIdIndex = urlIndex;
	var comments = document.getElementById("comments");
	comments.style.visibility = "visible";
	catchSpanPos(span);//获取点击的link的位置
	
	var commentsContent = document.getElementById("commentsContent");
	getComments(urlIdIndex,1);

}
/**
 * pos : line 838
 * @param span
 * @return
 */
var catchSpanPos =	function (span){
    with(pos(span)){
    	var comments = document.getElementById("comments");
    	browserName = browser.substr(0,2);
    	comments.style.top = Top+15;
    	comments.style.left = Left-255;
    }
}

var getComments = function(urlIndex,comPage){
	var userId = document.getElementById("userId").value;
	var urlId = document.getElementById("urlId"+urlIndex).innerHTML;
	createXmlHttpRequest();
	var url = "getComments.action?userId="+userId+"&urlId="+urlId+"&urlIndex="+urlIndex+"&comPage="+comPage;

	url = encodeURI(url);
	//url = encodeURI(url);//加两.次..
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterGetComments(userId,urlId,urlIndex);
	};
	xmlHttp.send(null);
}
var afterGetComments = function(userId,urlId,urlIndex){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText;
			var comments = document.getElementById("commentsContent");
			comments.innerHTML = result;
			
			var commentSum = document.getElementById("commentSum");
	        var intCommentSum = parseInt(commentSum.value);
	        
	        var commentCurPage = document.getElementById("commentCurPage");
	        var intCurPage = parseInt(commentCurPage.value);
	        
	        var totalPage = 0;
	        if((intCommentSum % 10) == 0){
	            totalPage = intCommentSum / 10;
	        }else{
	            totalPage = parseInt(intCommentSum / 10) + 1;
	        }
	        
	        var linkString = ""; 
	      
	        for(var counter = 1; counter <= totalPage;counter++){
	        	if(intCurPage == counter){
	        		linkString += "<a class='hyper' style='font-weight:bold' href=\"javascript:void(0)\" onclick='getComments("+urlIdIndex+","+counter+")'>"+counter+"</a> ";
	        	}else{
	                linkString += "<a class='hyper' href=\"javascript:void(0)\" onclick='getComments("+urlIdIndex+","+counter+")'>"+counter+"</a> ";
	        	}
	        }

	        var commentPage = document.getElementById("commentPage");
	        commentPage.innerHTML = linkString;
		}
	}
}

var delComment = function(link,commentId){
	var userId = document.getElementById("userId").value;
	createXmlHttpRequest();
	var url = "delComment.action?userId="+userId+"&commentId="+commentId;
	
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterDelComment(link,commentId);
	};
	xmlHttp.send(null);
}
var afterDelComment = function(link,commentId){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();
			if(result == "SUCCESS"){
				var commentDiv = link.parentNode.parentNode;
				var commentDivParent = document.getElementById("commentsContent");
				commentDivParent.removeChild(commentDiv);
				
				var commentSum = document.getElementById("spanCom"+urlIdIndex);
				var commentSumText = commentSum.innerHTML;
				var begin = commentSumText.indexOf("(");
				var end =  commentSumText.indexOf(")");
				var curSum = parseInt(commentSumText.substring(begin+1,end));
				curSum = curSum - 1;
				commentSum.innerHTML = "关闭评论("+curSum+")";
			}
		}
	}
}
var closeComments = function(span){
	urlIdIndex = "";//关闭评论被触发,说明页面上没有评论页面了.
	var spanText = span.innerHTML;
	var begin = spanText.indexOf("(");
	var end = spanText.indexOf(")");
	span.innerHTML = "评论"+spanText.substring(begin,end+1);
	
	browserName = browser.substr(0,2);
	span.onclick = function(){
		showComments(span);
    };

	var urlIndex = span.id.substring(7);
    var replyId = document.getElementById("replyUser"+urlIndex);
    var replyName = document.getElementById("replyUserName"+urlIndex);
    if(replyId != null){
    	replyId.value = "";
    }
    if(replyName != null){
    	replyName.value = "";
    }

	var comments = document.getElementById("comments");
	comments.style.visibility = "hidden";
}


var replyComment = function(replyUserId,replyUserName){
	var replyId = document.getElementById("replyUser");
	replyId.value = replyUserId;
	
	var replyName = document.getElementById("replyUserName");
	replyName.value = replyUserName;
	
	var replyText = document.getElementById("replyText");
	replyText.value = "回复 "+replyUserName+" :";
}
//计算评论内容长度

var calCommentLength = function(textarea,urlIndex){
	var text = textarea.value;

	var infoDiv = document.getElementById("commentWordCounter");

	var maxLength = 140;//回复最大限制
	var textLen = text.length;//回复内容实际长度

	var infoStr = (maxLength-textLen) + "/" + maxLength;
	if(textLen > maxLength){
		infoStr += "  <font style='color:red'> 评论内容过长</font>";
	}
	infoDiv.innerHTML = infoStr;
	infoDiv.Value = textLen;//记录回复内容长度,提交时候最为判断以及
}
/**
 * 提交用户对url的评论
 * @return
 */
var subUrlComment = function(){
	var urlId = document.getElementById("urlId"+urlIdIndex).innerHTML;
	var userId = document.getElementById("userId").value;
	var comTextArea = document.getElementById("replyText");
	
	var replyUserId = document.getElementById("replyUser");//回复用户ID,conditional
	var replyUserName = document.getElementById("replyUserName");
	var commentContent =  comTextArea.value;
	var posComma = commentContent.indexOf(":");
	commentContent = commentContent.substring(posComma+1);
	commentContent = commentContent.replace(/\r\n|\n/g,'<br />')
	
	
	var infoDiv = document.getElementById("commentWordCounter");
	
	if(infoDiv.Value > 140){
		infoDiv.innerHTML = "<font style='color:red'> 评论内容过长</font>";
	}else if(infoDiv.Value <= 1){
		infoDiv.innerHTML = "<font style='color:red'> 评论过短</font>";
	}else{
		createXmlHttpRequest();
		var url = "";
		if(replyUserId.value != ""){
			url = "postComment.action?userId="+userId+"&commentContent="+encodeURIComponent(commentContent)+"&urlId="+urlId+"&replyToUserId="+replyUserId.value+"&replyToUserName="+encodeURIComponent(replyUserName.value)+"&t="+Math.random();
		}else{
			url = "postComment.action?userId="+userId+"&commentContent="+encodeURIComponent(commentContent)+"&urlId="+urlId+"&t="+Math.random();;
		}
	  
		url = encodeURI(url);
		//url = encodeURI(url);//加两.次..
		xmlHttp.open("POST", url, true);
		xmlHttp.onreadystatechange = function(){
			afterSubUrlComment(urlIdIndex,commentContent,comTextArea);
		};
		xmlHttp.send(null);
	}
}

var afterSubUrlComment = function(urlIndex,commentContent,comTextArea){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();
			var posSuccess = result.indexOf("SUCCESS");
			
			var commentSum = document.getElementById("commentSum");
	        var intCommentSum = parseInt(commentSum.value);
	        
	        var curPage = document.getElementById("curPage");
	        var intCurPage = parseInt(curPage.value);
	        
	        var totalPage = 0;
	        if((intCommentSum % 10) == 0){
	            totalPage = intCommentSum / 10;
	        }else{
	            totalPage = parseInt(intCommentSum / 10) + 1;
	        }
	        
			if(posSuccess >= 0 && ((intCommentSum <= 9) || (intCurPage == totalPage))){
				comTextArea.value = "";
				var commentDivParent = document.getElementById("commentsContent");
				var nickName = document.getElementById("nickName").value;
				var userId = document.getElementById("userId").value;
				
				var newComment = document.createElement("div");
				newComment.className = "comment"; //reponse 需要返回commentId
				
				var replyUserId = document.getElementById("replyUser");
				var replyUserName = document.getElementById("replyUserName");//判断评论是否为回复某个用户的
				var now = new Date();  
				if(browser == "IE"){
					var year = now.getYear();
				}else{
				    var year = now.getYear()+1900; 
				} 
			    var month = now.getMonth()+1;  
			    var day = now.getDate();  
			    var hour = now.getHours();  
			    var minute = now.getMinutes();  
			    var second = now.getSeconds();  
			    var nowdate = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second; 
				
			    if(replyUserName.value != ""){ 
					newComment.innerHTML = "<div class='commentContent'><a href='getProfile.action?id="+userId+"' class='hyper' style='font-size:12px;'>"
					+ nickName + "</a> <font style='color:gray;'>"+nowdate+"</font><br/>"
					+ " 回复 <a href='getProfile.action?id="+replyUserId.value+"' class='hyper' style='font-size:12px;'>"
					+ replyUserName.value
					+ "</a> :"
					+ commentContent 
					+ "</div>";

				}else{
					newComment.innerHTML = "<div class='commentContent'><a href='getProfile.action?id="+userId+"' class='hyper' style='font-size:12px;'>"
					+ nickName
					+ "</a> " + "<font style='color:gray;'>"+nowdate+"</font><br/>"
					+ commentContent 
					+ "</div>";
				}
				var opComment = document.createElement("div");
				var span = document.createElement("span");
				var deleteText = document.createElement("a");
				var replyText = document.createElement("a");
				
				opComment.className = "opComment";
				
				span.innerHTML = " | ";
				
				deleteText.className = "hyperComment";
				deleteText.innerHTML = "删除";
				deleteText.onclick = function(){
					delComment(deleteText,result.substring(7));//第三参数为commentId;
				}
				
				var replyName = replyUserName.value;
				replyText.className = "hyperComment";
				replyText.innerHTML = "回复";
				replyText.onclick = function(){
					replyComment(userId,nickName);//第一个参数为userId;
				}
				
				opComment.appendChild(deleteText);
				opComment.appendChild(span);
				opComment.appendChild(replyText);
				newComment.appendChild(opComment);
				commentDivParent.appendChild(newComment);
				
				var commentSum = document.getElementById("spanCom"+urlIndex);
				var commentSumText = commentSum.innerHTML;
				var begin = commentSumText.indexOf("(");
				var end =  commentSumText.indexOf(")");
				var curSum = parseInt(commentSumText.substring(begin+1,end));
				curSum = curSum + 1;
				commentSum.innerHTML = "关闭评论("+curSum+")";
				
				document.getElementById("replyUser").value = "";//清空评论回复用户的信息
				document.getElementById("replyUserName").value = "";
			}else{
				comTextArea.value = "";
			}
		}
	}
}
/**
 * 添加关注
 * @param fanIndex
 * @param fansOwner
 * @param link
 * @return
 */
var addProfileFollow = function(hostId){
	var userId = document.getElementById("userId").value;
	createXmlHttpRequest();
	var url = "addFollow.action?userId="+userId+"&hostId="+hostId;
	url = encodeURI(url);
	//url = encodeURI(url);//加两.次..
	xmlHttp.open("POST", url, true);
	xmlHttp.onreadystatechange = function(){
		afterProfileAddFollow();
	};
	xmlHttp.send(null);
}

var afterProfileAddFollow = function(){
	if (xmlHttp.readyState == 4) {
		if (xmlHttp.status == 200) {
			var result = xmlHttp.responseText.trim();
			
			if(result == "SUCCESS"){
				var addFollowInfo = document.getElementById("addFollowInfo");
				addFollowInfo.innerHTML = "<font style=\"font-size:12px;color:gray\" title=\"已添加关注\">已添加关注</font>";
			}
		}
	}
}

var getUrlByTagId = function(tagId){
	var selUrlType = document.getElementById("selUrlType");
	document.location = "Url.action?tagId="+tagId+"&urlRange="+selUrlType.selectedIndex;
}