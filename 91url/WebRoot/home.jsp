 <%@ page language="java" import="java.util.*,com.url.bean.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv=X-UA-Compatible content=IE=EmulateIE7 />
		<s:if test="#session.user==null || #request.validate == null"><!-- 防止未登录用户直接访问home.jsp页面 -->
			<script type="text/javascript">document.location = "login.jsp";</script>
		</s:if>
		<link rel="icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="img/favicon.ico" mce_href="img/favicon.ico" type="mage/x-icon" />
		<title>
		    91url 分享互联网书签 <s:property value="#session.user.nickName" />
		</title>
		<link href="css/home.css" rel="stylesheet" type="text/css" />
		<script src="script/home.js"></script>
	</head>
	<body class="bodyStyle" onload="init()"  onresize="rePosSuggestion();">
		<div id="mainPositionDiv" class="mainPositionDiv">
			<div id="positionDiv" class="positionDiv">
				<div id="divHeader" class="divHeader">
					<div id="headerLink" class="headerLink">
						<a href="Home.action" class="hyper" name="top" style="font-size:12px;" title="显示好友们收录的书签">我的首页</a>&nbsp;&nbsp;
						<a href="Profile.action" class="hyper" style="font-weight:bold;font-size:12px;" title="显示自己收录的书签">我的书签</a>&nbsp;&nbsp;
						<a href="download.jsp" class="hyper" style="font-size:12px;" title="下载桌面书签工具">桌面软件</a>&nbsp;&nbsp;
						<a href="Logoff.action" class="hyper" style="font-size:12px;" title="退出91url">注销退出</a>&nbsp;&nbsp;&nbsp;
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
							    
                                <a class="hyper" style="font-size:12px;" title="点击修改自己的昵称" onclick="editNickName(this)">修改昵称</a><br/><br/>
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
											<div class="userTag" name="userTag"
												id="userTag<s:property value="#userTag.userTagId"/>"
												onmouseover="changeOver(this)" onmouseout="changeBlur(this)" onclick="document.location='User.action?userTagId=<s:property value="#userTag.userTagId" />'">
												
												<div name="delDiv" class="delDiv"
													onclick="delUserTag('<s:property value="#userTag.userTagId"/>',event)">
													<img src="img/delete.png" onmouseover="changeImgOver(this)" onmouseout="changeImgBlur(this)"/>
												</div>
												<s:property value="#userTag.userTagName" />
											</div>
										</s:iterator>
									</s:if>
								</div>
							</div>
							<div id="addUserTagsDiv">
								<span id="spanAdd" onclick="showUserTag(this)"
									onmouseover="spanTextOver(this)" onmouseout="spanTextOut(this)" class="spanText" title="点击增加描述自己的标签">添加</span>
							</div>
							<div id="addUserTagFormDiv" class="addUserTagFormDiv">
								<div id="userTagInfoDiv">
									写点什么吧
								</div>
								<input type="text" id="userTagInput" class="userTagInput"
									value="你的兴趣?爱好?特长?" onfocus="clearUserTag(this)"
									onblur="fillUserTag(this)" onKeyUp="calUserTagLength(this,'userTagInfoDiv',15,event)"/>
								<input type="button" id="subUserTag" value=" 保存 "
									onclick="saveUserTag()" />
							</div>
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
							<form id="urlForm">
								<input name="url" id="url" class="urlInput" value="请输入网页的网址"
									onfocus="urlTextFocus(this)" onblur="urlTextBlur(this)" />
								
								<div id="infoDiv" class="infoDiv">
								</div>
								<input type="button" value=" 保存 " id="save" class="saveBtn" onclick="subRequest()" onmouseover="overSub(this)" onmouseout="blurSub(this)" /> 
								<input type="checkbox" value="1" id="ckPrivate" style="vertical-align:middle;" title="私有书签仅对自己可见"/><font style="font-size:12px;">私有书签</font>
								<s:hidden name="userId" id="userId"
									value="%{#session.user.userId}">
								</s:hidden>
								<s:hidden name="nickName" id="nickName"
									value="%{#session.user.nickName}">
								</s:hidden>
							</form>
							我的书签
							<br />
							<br />
							<div id="urlList">
								<s:if test="#request.urls!=null">
									<s:iterator value="#request.urls" id="url" status="urlIndex">
										<div class="url" id="urlIndex${urlIndex.index}">
										    <div id="urlContent${urlIndex.index}">   
										        <div id="hyperDiv${urlIndex.index}" class="hyperDiv">
										            <a href="getProfile.action?id=<s:property value="#url.urlOwnerId"/>" class="hyper" >我</a><font style="font-size:12px">:</font>	
													<a href="<s:property value="#url.url"/>" target="_blank" class="hyper" title="<s:property value="#url.url"/>" id="urlLink${urlIndex.index}"><s:property value="#url.urlTitle" /></a>
											    </div>
												<div id="urlId${urlIndex.index}" style="display:none">
													<s:property value="#url.urlId" />
												</div>
	
												<div class="tags" id="tags${urlIndex.index}" name="tags">
													<s:iterator value="#url.tags" id="tag" status="tagIndex">
														<div class="tag" id="urlTag<s:property value="#tag.tagId"/>_${urlIndex.index}" onmouseover="changeOver(this)" onmouseout="changeBlur(this)" onclick="document.location='Url.action?tagId=<s:property value="#tag.tagId"/>'">
															<div name="delDiv" class="delTagDiv" onclick="delUrlTag(<s:property value="#tag.tagId"/>,${urlIndex.index},event);">
																<img src="img/delete.png" onmouseover="changeImgOver(this)" onmouseout="changeImgBlur(this)"/>
															</div>
															&nbsp;<s:property value="#tag.tagName" />
														</div>
													</s:iterator>
												</div>
	
												<div class="urlFeatures" id="urlFeatures">
													<div id="share" class="share">
													    
													    <!--<span id="spanShare${urlIndex.index}" class="spanText" onclick="shareUrl(<s:property value="#url.shareId" />,'${urlIndex.index}')" onmouseover="spanTextOver(this)" onmouseout="spanTextOut(this)">
													           分享(<s:property value="#url.shareSum" />)
													    </span> -->       
													</div>
													<div id="tagTime${urlIndex.index}" class="tagTime">
													    <font style="color:gray">最先收录: </font>
															<s:if test="#url.firstOwnerId == #session.user.userId">
															     <a href="getProfile.action?id=<s:property value="#url.firstOwnerId" />" class="hyper" style="font-size:12px;">我</a> &nbsp;&nbsp;&nbsp;
															     <a href="getProfile.action?id=<s:property value="#url.firstOwnerId" />" class="hyper" style="font-size:12px;">我</a> 保存于: <s:property value="#url.addDate" />
															</s:if>
															<s:else>
																<a href="getProfile.action?id=<s:property value="#url.firstOwnerId" />" class="hyper" style="font-size:12px;"><s:property value="#url.firstOwner" /></a>&nbsp;&nbsp;&nbsp;
																<a href="getProfile.action?id=<s:property value="#url.firstOwnerId" />" class="hyper" style="font-size:12px;">我</a> 分享于: <s:property value="#url.addDate" />
															</s:else>
													</div>
													<div id="addUrlTagInfo${urlIndex.index}" class="addUrlTagInfo"></div>
													
													<div id="tagComment" class="tagComment"><span id="spanCom${urlIndex.index}" class="spanText" onclick="showComments(this)" onmouseover="spanTextOver(this)" onmouseout="spanTextOut(this)">评论(<s:property value="#url.commentSum" />)</span></div>
													<div class="edit">													
													    <a class="hyper" style="font-size:12px;" id="edit${urlIndex.index}" onclick="editUrl('${urlIndex.index}')" title="编辑书签标题">编辑</a> &nbsp;|&nbsp; 
												        <a class="hyper" style="font-size:12px"  onclick="queryDelUrl(${urlIndex.index})" title="删除书签">删除 </a> &nbsp;|
												    </div>
												    <div class="addTag" id="addTag${urlIndex.index}">
														<a class="hyper" style="font-size:12px;" id="addTag${urlIndex.index}" onclick="addTag(${urlIndex.index})" title="添加标签">添加标签</a> &nbsp;|&nbsp;&nbsp;
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
				    <div id="selPage" style=""></div>
				    <script>
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
			<div id="delAlert">
			     <div class="closeAlert"><a id="closeAlert" class="hyper" title="关闭提示" style="font-size:12px;">关闭</a></div>
		         <div style="text-align:center;"> 
		         <div style="text-align:left;font-size:14px;padding-left:35px;">删除该条书签以及关联标签?</div><br/>
		         <input type="button" value="确定" id="delUrl" class="choiceButton" onmouseover="this.style.borderColor='#909090';" onmouseout="this.style.borderColor='#cccccc';"/> &nbsp;&nbsp;&nbsp;
		         <input type="button" value="取消" id="cancelUrl" class="choiceButton" onmouseover="this.style.borderColor='#909090';" onmouseout="this.style.borderColor='#cccccc';"/></div>
		    </div>
		</div>
	</body>
</html>
