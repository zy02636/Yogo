<%@ page language="java" import="java.util.*,com.url.bean.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="mage/x-icon" />
		<base target=_self>
		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<s:if test="#session.user==null || #request.validate == null"><!-- 防止未登录用户直接访问home.jsp页面 -->
			<script type="text/javascript">document.location = "login.jsp";</script>
		</s:if>
		<s:else>
		    
		</s:else>
		<title>
		    91url 分享互联网书签 <s:property value="#session.user.nickName" />
		</title>
		<link href="css/home.css" rel="stylesheet" type="text/css" />
		<script src="script/home.js"></script>
		<script src="script/follow.js"></script>
	</head>
	<body class="bodyStyle" onload="init()" >
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
							    <a href="getProfile.action?id=<s:property value="#request.user.userId"/>" class="hyper" style="font-size:16px;">
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
							    <br/><br/><br/>
							</div>
                            <div id="userTagDiv">
								<br />
								我的标签
								<br />
								<br />
								<div id="userTags">
									<s:if test="#request.userTags!=null">
										<s:iterator value="#request.userTags" id="userTag"
											status="userTagIndex">
											<div class="userTag" name="userTag" id="userTag<s:property value="#userTag.userTagId"/>" onmouseover="changeOver(this)" onmouseout="changeBlur(this)" onclick="document.location='User.action?userTagId=<s:property value="#userTag.userTagId" />'">
												<s:property value="#userTag.userTagName" />
											</div>
										</s:iterator>
									</s:if>
								</div>
							</div><br />

							<div id="tagDiv">
								<br />
								我的书签标签
								<br />
								<br />
								<s:if test="#request.tags!=null">
									<s:iterator value="#request.tags" id="tag" status="tagIndex">
										<div class="userTag" name="urlTag"
											onmouseover="changeOver(this)" onmouseout="changeBlur(this)" onclick="document.location='Url.action?tagId=<s:property value="#tag.tagId"/>'">
											<s:property value="#tag.tagName" />
										</div>
									</s:iterator>
								</s:if>
							</div>
						</div>
						<div id="rightDiv" class="rightDiv">
							<!--定于需要的表单-->
							<s:hidden name="userId" id="userId"
									value="%{#session.user.userId}">
								</s:hidden>
								<s:hidden name="nickName" id="nickName"
									value="%{#session.user.nickName}">
								</s:hidden>
							贴有<a href="User.action?userTagId=<s:property value="#request.userTagId"/>" class="hyper" style="font-size:16px;"> <s:property value="#request.userTagName"/> </a>标签的用户
							<br />
							<br />
							<div id="userList">
								<s:if test="#request.users!=null">
									<s:iterator value="#request.users" id="user" status="userIndex">
										<div class="url" id="userIndex" style="height:auto;">
										    <div id="userContent" style="height:auto;padding-top:10px;">   
										        <div id="userDiv" class="hyperDiv" style="height:auto;padding-bottom:10px;">
										           <a href="getProfile.action?id=<s:property value="#user.userId"/>" class="hyper"><s:property value="#user.nickName"/></a>
											    </div>
												<div class="tags" id="tags${userIndex.index}" name="tags" style="height:auto;overflow:auto;">
													<s:iterator value="#user.userTags" id="tag" status="tagIndex">
														<div class="tag" id="userTag<s:property value="#tag.userTagId"/>_${userIndex.index}" onmouseover="changeOver(this)" onmouseout="changeBlur(this)" onclick="document.location='User.action?userTagId=<s:property value="#tag.userTagId" />'">
															&nbsp;<s:property value="#tag.userTagName" />
														</div>
													</s:iterator>
												</div>
												<!-- 为了确保js正确运行这里不修改div的ID -->
												<div id="followOp${userIndex.index}" class="followOp" style="float:right;padding-top:10px;">
													<s:if test="#user.relationType == 0">
								                       <a href="javascript:void(0)" class="hyper" style="font-size:12px;" onclick="userSearcgAddFollow(${userIndex.index},<s:property value="#user.userId"/>)">
									                                                          添加关注</a>
								                   </s:if>
							                   </div>
											</div>
										</div><br/>
									</s:iterator>
								</s:if>
							</div>
						</div>
					</div>
				</div>
				<div id="divFoot" >
					<div style="text-align:left;padding-left:300px;"><a href="#top" class="anchor" onmouseover="spanTextOver(this)" onmouseout="spanTextOut(this)">返回顶端</a></div>
				    <div id="userSum" style="display:none;"><s:property value="#request.userSum"/></div>
				    <div id="curPage" style="display:none;"><s:property value="#request.curPage"/></div>
				    <div id="curTagId" style="display:none;"><s:property value="#request.userTagId"/></div>
				    <div id="selPage" style=""></div>
				    <script>
				        var tagId = document.getElementById("curTagId");
				    
				        var sum = document.getElementById("userSum");
				        var intSum = parseInt(sum.innerHTML);
				        
				        var curPage = document.getElementById("curPage");
				        var intCurPage = parseInt(curPage.innerHTML);
				        
				        var totalPage = 0;
				        if((intSum % 10) == 0){
				            totalPage = intSum / 10;
				        }else{
				            totalPage = parseInt(intSum / 10) + 1;
				        }
				       
				        var pageSel = document.createElement("select");
				        pageSel.onchange = function(){
				            document.location = "Profile.action?pageNum="+(pageSel.selectedIndex+1);
				        }
				        for(var counter = 1; counter <= totalPage;counter++){
				            var newItem = new Option("第"+counter+"页",counter);
				            pageSel.options.add(newItem);    
				        }
				        pageSel.selectedIndex = intCurPage - 1;
				        
	                    var selPage = document.getElementById("selPage");
				        selPage.appendChild(pageSel);
				    </script>
				</div>
			</div>
			<div id="tagSuggestion" ></div>
		</div>
	</body>
</html>
