<%@ page language="java" import="java.util.*,com.url.bean.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		<title>
		    91url 分享互联网书签 <s:property value="#session.user.nickName" />
		</title>
		<link href="css/home.css" rel="stylesheet" type="text/css" />
		<script src="script/home.js"></script>
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
							<div id="userDiv" class="userDiv">
							    <div id="linkNickName" class="nickName">
							    <a href="getProfile.action?id=<s:property value="#request.user.userId"/>" class="hyper" style="font-size:16px;">
							        <s:property value="#request.user.nickName" /></a>
							    </div>
							    <div id="nickNameInfo" style="color:red;font-size:12px;height:20px;"></div>
                                <div id="addFollowInfo">
							        <s:if test="#request.relationType == 0 ">
							            <a class="hyper" style="font-size:12px;" title="添加关注" onclick="addProfileFollow(<s:property value="#request.user.userId" />)">添加关注</a>
							        </s:if>
							        <s:else>
							            <font style="font-size:12px;color:gray" title="已添加关注">已添加关注</font>
							        </s:else>
						        </div>
                                <br/>
							    <div id="fansSum" class="sumInfo" style="margin-left:0px;"> 
							        <a class="hyper" href="Fans.action?id=<s:property value="#request.user.userId"/>" style="font-size:12px;">粉丝 </a> <br/>
							        <a class="hyper" href="Fans.action?id=<s:property value="#request.user.userId"/>" style="font-size:14px;font-weight:bold" id="fansSumLink"><s:property value="#request.fansSum" /></a>
							    </div>
							    <div id="followersSum" class="sumInfo"> 
							        <a class="hyper" href="Follows.action?id=<s:property value="#request.user.userId"/>" style="font-size:12px;">关注</a> <br/> 
							        <a class="hyper" href="Follows.action?id=<s:property value="#request.user.userId"/>" style="font-size:14px;font-weight:bold" id="followersSumLink"><s:property value="#request.followersSum" /></a> 
							    </div>
							    <div id="urlsSum" class="sumInfo" style="border:0px solid #9CA8A6;"> 
							        <a class="hyper" href="getProfile.action?id=<s:property value="#request.user.userId"/>" style="font-size:12px;">书签</a> <br/> 
							        <a class="hyper" href="getProfile.action?id=<s:property value="#request.user.userId"/>" style="font-size:14px;font-weight:bold" id="urlsSumLink"><s:property value="#request.urlsSum" /></a> 
							    </div>
							    <br/>
							</div>
							<div id="userTagDiv">
								<br />
								<s:property value="#request.user.nickName" />的标签
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
								</div><br/>
							</div>

							<div id="tagDiv">
								<br />
								<s:property value="#request.user.nickName" />的书签标签
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
							<s:property value="#request.user.nickName" />的书签
							<s:hidden name="userId" id="userId"
									value="%{#session.user.userId}">
								</s:hidden>
								<s:hidden name="nickName" id="nickName"
									value="%{#session.user.nickName}">
								</s:hidden>
							<br />
							<br />
							<div id="urlList">
								<s:if test="#request.urls!=null">
									<s:iterator value="#request.urls" id="url" status="urlIndex">
										<div class="url" id="urlIndex">
										    <div id="urlContent">   
										        <div id="hyperDiv" class="hyperDiv">
										        <a href="getProfile.action?id=<s:property value="#url.urlOwnerId"/>" class="hyper"><s:property value="#url.urlOwner"/></a><font style="font-size:12px">:</font>
													<a href="<s:property value="#url.url"/>" target="_blank"
														class="hyper" title="<s:property value="#url.url"/>"
														id="urlLink"> <s:property value="#url.urlTitle" /> </a>
											    </div>
												<div id="urlId${urlIndex.index}" style="display:none">
													<s:property value="#url.urlId" />
												</div>
	
												<div class="tags" id="tags${urlIndex.index}" name="tags">
													<s:iterator value="#url.tags" id="tag" status="tagIndex">
														<div class="tag" id="urlTag<s:property value="#tag.tagId"/>_${urlIndex.index}" onmouseover="changeOver(this)" onmouseout="changeBlur(this)" onclick="document.location='Url.action?tagId=<s:property value="#tag.tagId"/>'">
															&nbsp;<s:property value="#tag.tagName" />
														</div>
													</s:iterator>
												</div>
	
	
												<div class="urlFeatures" id="urlFeatures">
													<div id="tagTime${urlIndex.index}" class="tagTime">
								
														<font style="color:gray">最先收录: </font>
															<a href="getProfile.action?id=<s:property value="#url.firstOwnerId" />" class="hyper" style="font-size:12px;">
																<s:if test="#url.firstOwnerId == #session.user.userId">
																        我</s:if>
																<s:else>
																   <s:property value="#url.firstOwner" /></s:else></a>&nbsp;&nbsp;&nbsp;
															
															
															<a href="getProfile.action?id=<s:property value="#url.urlOwnerId" />" class="hyper" style="font-size:12px;">
																   <s:property value="#url.urlOwner"/></a>
															<s:if test="#url.urlOwnerId==#url.firstOwnerId">
															    保存于:
															</s:if>
															<s:else>
															     分享于:
															</s:else>
															<s:property value="#url.addDate" />
													</div>
													
													<div id="tagComment" class="tagComment">
														<span id="spanCom${urlIndex.index}" class="spanText" onclick="showComments(this)" onmouseover="spanTextOver(this)" onmouseout="spanTextOut(this)">评论(<s:property value="#url.commentSum" />)</span>								
													</div>
													<div id="share${urlIndex.index}" class="share">
													    <span id="spanShare${urlIndex.index}" class="spanText" onclick="shareUrl(<s:property value="#url.shareId" />,'${urlIndex.index}')" onmouseover="spanTextOver(this)" onmouseout="spanTextOut(this)">
													           分享(<s:property value="#url.shareSum" />)
													    </span> &nbsp;&nbsp;|&nbsp;&nbsp;
													</div>
													<div id="addUrlTagInfo${urlIndex.index}"
														class="addUrlTagInfo">
														
													</div>
												</div>
											</div>
										</div>
									</s:iterator>
								</s:if>
							</div>
						</div>
					</div>
				</div>
				<div id="divFoot" >
					<div style="text-align:left;padding-left:300px;"><a href="#top" class="anchor" onmouseover="spanTextOver(this)" onmouseout="spanTextOut(this)">返回顶端</a></div>
				    <div id="urlSum" style="display:none;"><s:property value="#request.urlsSum"/></div>
				    <div id="curPage" style="display:none;"><s:property value="#request.curPage"/></div>
				    <div id="profileUserId" style="display:none;"><s:property value="#request.user.userId"/></div>
				    <div id="selPage" style=""></div>
				    <script>
				        var userId = document.getElementById("profileUserId").innerHTML;
				    
				        var urlsSum = document.getElementById("urlSum");
				        var intUrlSum = parseInt(urlsSum.innerHTML);
				        
				        
				        var curPage = document.getElementById("curPage");
				        var intCurPage = parseInt(curPage.innerHTML);
				        
				        var totalPage = 0;
				        if((intUrlSum % 10) == 0){
				            totalPage = parseInt(intUrlSum / 10);
				        }else{
				            totalPage = parseInt(intUrlSum / 10) + 1;
				        }
				       
				       	var pageSel = document.createElement("select");
				        pageSel.onchange = function(){
				            document.location = "getProfile.action?id="+userId+"&pageNum="+(pageSel.selectedIndex+1);
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
			<div id="comments" class="comments">
		        <div id="commentsContent" class="commentsContent">
			       
		        </div>
		        <div id="reply" class="reply">
                <div id="commentPage" class="commentPage"> </div>
	               <textarea id="replyText" onkeyup="calCommentLength(this)" class="replyText" onfocus="this.style.color='black';" onblur="this.style.color='gray';"></textarea>
	               <div id="commentWordCounter" class="replyTextInfo" > 140/140 </div>
	               <input type="button" value="提交" class="replyButton" onclick="subUrlComment()"/>
                </div>
	       </div>
		</div>
	</body>
</html>
