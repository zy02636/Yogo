<%@ page language="java" import="java.util.*,com.url.bean.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base target=_self>
		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<s:if test="#session.user==null || #request.validate == null"><!-- 防止未登录用户直接访问home.jsp页面 -->
			<script type="text/javascript">document.location = "login.jsp";</script>
		</s:if>
		<s:else>
		    
		</s:else>
		<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="mage/x-icon" />
		<title>
		    91url 分享互联网书签 <s:property value="#session.user.nickName" />
		</title>
		<link href="css/follow.css" rel="stylesheet" type="text/css" />
		<link href="css/home.css" rel="stylesheet" type="text/css" />
		<script src="script/home.js"></script>
		<script src="script/follow.js"></script>
	</head>
	<body class="bodyStyle" onload="init()" onresize="rePosSuggestion();">
		<div id="mainPositionDiv" class="mainPositionDiv">
			<div id="positionDiv" class="positionDiv">
				<div id="divHeader" class="divHeader"> 
					<div id="headerLink" class="headerLink">
					    <a href="Home.action" class="hyper" style="font-size:12px;" name="top" title="显示好友们收录的书签">我的首页</a>&nbsp;&nbsp;
					    <a href="Profile.action" class="hyper" style="font-size:12px;" title="显示自己收录的书签">我的书签</a>&nbsp;&nbsp;
                        <a href="download.jsp" class="hyper" style="font-size:12px;" title="下载桌面书签工具">桌面软件</a>&nbsp;&nbsp;
					    <a href="Logoff.action" class="hyper"style="font-size:12px;"  title="退出91url">注销退出</a>&nbsp;&nbsp;&nbsp;
					</div>
				    <div id="logo" class="logo">
				        <img src="img/logo_IE.png" id="imgLogo" title="91url.com"/>
				    </div>
				</div>
				<div id="middleIEDiv" class="middleIEDiv">
					<div id="divMain" class="divMain">
						<div id="leftDiv" class="leftDiv">
						
							<div id="userDiv" class="userDiv" style="height:109px;">
							    <div id="linkNickName" class="nickName">
							    <a href="getProfile.action?id=<s:property value="#request.user.userId" />" class="hyper" style="font-size:16px;">
							        <s:property value="#request.user.nickName" /></a>
							    </div>
							    <br/>
							    <div id="fansSum" class="sumInfo" style="margin-left:0px;"> 
							        <a class="hyper" href="Fans.action?id=<s:property value="#request.user.userId" />" style="font-size:12px;">粉丝 </a> <br/>
							        <a class="hyper" href="Fans.action?id=<s:property value="#request.user.userId" />" style="font-size:14px;font-weight:bold" id="fansSumLink"><s:property value="#request.fansSum" /></a>
							    </div>
							    <div id="followersSum" class="sumInfo"> 
							        <a class="hyper" href="Follows.action?id=<s:property value="#request.user.userId" />" style="font-size:12px;">关注</a> <br/> 
							        <a class="hyper" href="Follows.action?id=<s:property value="#request.user.userId" />" style="font-size:14px;font-weight:bold" id="followersSumLink"><s:property value="#request.followersSum" /></a> 
							    </div>
							    <div id="urlsSum" class="sumInfo" style="border:0px solid #9CA8A6;"> 
							        <a class="hyper" href="getProfile.action?id=<s:property value="#request.user.userId" />" style="font-size:12px;">书签</a> <br/> 
							        <a class="hyper" href="getProfile.action?id=<s:property value="#request.user.userId" />" style="font-size:14px;font-weight:bold" id="urlsSumLink"><s:property value="#request.urlsSum" /></a> 
							    </div>
							</div>
							<div id="userTagDiv">
								<br />
								推荐好友
								<br />
								<br />

							</div>

						</div>
						<div id="rightDiv" class="rightDiv">
							<!--定于需要的表单-->
							<s:hidden name="userId" id="userId" value="%{#session.user.userId}"></s:hidden>
							<s:hidden name="nickName" id="nickName" value="%{#session.user.nickName}"></s:hidden>
							<s:if test="#request.user.nickName == #session.user.nickName">
							          我的关注
							</s:if>
							<s:else>
							    <s:property value="#request.user.nickName" />的关注
							</s:else>
							<br />
							<br />
							<div id="followsList">
								<s:if test="#request.follows!=null">
									<s:iterator value="#request.follows" id="follow" status="followIndex">
										<div class="follow" id="followIndex${followIndex.index}">
									       <div id="followName${followIndex.index}">
										       <a href="getProfile.action?id=<s:property value="#follow.userId"/>" class="hyper">
							                      <s:property value="#follow.nickName" />
							                   </a>
						                   </div>
						                   <div id="followId${followIndex.index}" style="display:none"><s:property value="#follow.userId"/></div>
						                   <div id="followOp${followIndex.index}" class="followOp">
						                       <s:if test="#follow.relationType == 0 && #request.user.userId != #session.user.userId">
							                       <a href="javascript:void(0)" class="hyper" style="font-size:12px;" onclick="addFollow(${followIndex.index},'<s:property value="#request.followsowner" />')">
								                                                          添加关注</a>
							                   </s:if>
						                       <s:if test="#request.user.userId == #session.user.userId">
											       <a href="javascript:void(0)" class="hyper" style="font-size:12px;" onclick="queryDelFollows(${followIndex.index})">
								                                                          移除关注</a>
							                   </s:if>
						                   </div>
										</div>
									</s:iterator>
								</s:if>
							</div>
						</div>
					</div>
				</div>
				<div id="divFoot" >
					<div id="followLink" style=""></div>
					<div id="curPage" style="display:none;"><s:property value="#request.curPage"/></div>
					<div id="followUserId" style="display:none;"><s:property value="#request.user.userId"/></div>
					<script>
				        var userId = document.getElementById("followUserId").innerHTML;
				    
				        var followsSum = document.getElementById("followersSumLink");
				        var intFollowsSum = parseInt(followsSum.innerHTML);
				        
				        
				        var curPage = document.getElementById("curPage");
				        var intCurPage = parseInt(curPage.innerHTML);
				        
				        var totalPage = 0;
				        if((intFollowsSum % 20) == 0){
				            totalPage = parseInt(intFollowsSum / 20);
				        }else{
				            totalPage = parseInt(intFollowsSum / 20) + 1;
				        }
				        
				        var pageSel = document.createElement("select");
				        pageSel.onchange = function(){
				            document.location = "Follows.action?id="+userId+"&pageNum="+(pageSel.selectedIndex+1);
				        }
				        for(var counter = 1; counter <= totalPage;counter++){
				            var newItem = new Option("第"+counter+"页",counter);
				            pageSel.options.add(newItem);
				        }
				        pageSel.selectedIndex = intCurPage - 1;

	                    var followLink = document.getElementById("followLink");
				        followLink.appendChild(pageSel);
				    </script>
				</div>
			</div>
			
			<div id="delAlert">
			     <div class="closeAlert"><a id="closeAlert" class="hyper" title="关闭提示" style="font-size:12px;">关闭</a></div>
		         <div style="text-align:center;"> 
		         <div style="text-align:left;font-size:14px;padding-left:35px;">移除你对该用户的关注?</div><br/>
		         <input type="button" value="确定" id="delUrl" class="choiceButton" onmouseover="this.style.borderColor='#909090';" onmouseout="this.style.borderColor='#cccccc';"/> &nbsp;&nbsp;&nbsp;
		         <input type="button" value="取消" id="cancelUrl" class="choiceButton" onmouseover="this.style.borderColor='#909090';" onmouseout="this.style.borderColor='#cccccc';"/></div>
		    </div>
		</div>
	</body>
</html>
