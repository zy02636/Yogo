package com.url.struts.action;


import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.url.bean.Comment;
import com.url.bean.User;
import com.url.service.ICommentService;


public class CommentAction implements SessionAware,RequestAware{
	private ICommentService commentService;
	public void setCommentService(ICommentService commentService) {
		this.commentService = commentService;
	}
	
	/*
 	 * 封装后的session和request
 	 */
 	private Map session;   
 	public void setSession(Map session) {
 		this.session = session;
 	}
    private Map request;
 	public void setRequest(Map request) {
 		this.request = request;
 	}
 	
 	private int urlId;
 	private int userId;
 	private int commentId;
	private String commentContent;
	private int urlIndex;
    private int replyToUserId;
    private String replyToUserName;
    private int comPage = 0;
    
	public void setComPage(int comPage) {
		this.comPage = comPage;
	}
	public String getReplyToUserName() {
		return replyToUserName;
	}
	public void setReplyToUserName(String replyToUserName) {
		this.replyToUserName = replyToUserName;
	}
	public void setReplyToUserId(int replyToUserId) {
		this.replyToUserId = replyToUserId;
	}
	public void setUrlIndex(int urlIndex) {
		this.urlIndex = urlIndex;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
 	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

 	/**
 	 * 添加一条评论
 	 * @return Ajax 访问
 	 * @throws Exception
 	 */
 	public String postComment() throws Exception{
 		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		//ajax 返回需要准备的类以及字符串
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){//必须为登录用户,且session中的userId 和 请求 的userId是一样的
			info = "FAIL";
			out.println(info);
			out.close();
		    return null;	
		}else{
			try{
				Calendar cal = Calendar.getInstance();
				Date time = cal.getTime();
				String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
				
				Comment comment = new Comment();
				comment.setCommentContent(URLDecoder.decode(commentContent,"UTF-8"));
				comment.setUrlId(urlId);
				comment.setUserId(userId);
				comment.setUserNickName(user.getNickName());
				comment.setReplyToUserId(replyToUserId);
	
				if(replyToUserName != null){
					comment.setReplyToUserName(URLDecoder.decode(replyToUserName,"UTF-8"));
					replyToUserName = null;
					replyToUserId = 0;
				}else{
					comment.setReplyToUserName(replyToUserName);
				}
				comment.setReplyDate(curDate);
	
				int result = commentService.addComment(comment);

				//添加成功的标志就是受影响行数
				if(result>= 1 ){
					info = "SUCCESS"+result;
				}else{
					info = "FAIL";
				}

				out.println(info);
			    out.close();
				return null;
			}catch(Exception ex){
				ex.printStackTrace();
				info = "FAIL";
				out.println("FAIL");
				out.close();
				return null;
			}
		}
 	}
 	/**
 	 * 删除一条评论
 	 * @return Ajax 访问
 	 * @throws Exception
 	 */
 	public String delComment() throws Exception{
 		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		//ajax 返回需要准备的类以及字符串
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "SESSIONFAIL";
			out.println(info);
			out.close();
		    return null;	
		}else{ //必须为登录用户,且session中的userId 和 请求 的userId是一样的
			try{
				int result = commentService.delCommentByCommentId(commentId);

				//添加成功的标志就是受影响行数
				if(result>= 1 ){
					info = "SUCCESS";
				}else{
					info = "FAIL";
				}
				out.println(info);
				out.close();
				return null;
			}catch(Exception ex){
				ex.printStackTrace();
				info = "FAIL";
				out.println(info);
				out.close();
				return null;
			}
		}
 	}
 	/**
 	 * 获取url的所有评论
 	 * @return Ajax 访问
 	 * @throws Exception
 	 */
 	public String getComments() throws Exception{
 		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax 返回需要准备的类以及字符串
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "SESSIONFAIL";
			out.println(info);
			out.close();
		    return null;	
		}else{ //必须为登录用户,且session中的userId 和 请求 的userId是一样的
			try{
				List<Comment> comments = commentService.getCommentsByUrlId(urlId,comPage);
				int commentSum = commentService.getCommentSumByUrlId(urlId);
				StringBuffer sb = new StringBuffer();
				//添加成功的标志就是受影响行数
				sb.append("<input type='hidden' id='commentSum' value='"+commentSum+"'/>");
				sb.append("<input type='hidden' id='commentCurPage' value='"+comPage+"'/>");
				sb.append("<input type='hidden' id='replyUser' value=''/>");
				sb.append("<input type='hidden' id='replyUserName' value=''/>");
				
				for(Comment comment : comments){
					String commentContent = comment.getCommentContent();
					int commentId = comment.getCommentId();
					int userId =comment.getUserId();
					String nickName = comment.getUserNickName();
					
					sb.append("<div id='comment' class='comment'>");
					sb.append("<div class='commentContent'>");
				    sb.append("<a href='getProfile.action?id=");
				    sb.append(userId);
				    sb.append("' class='hyper' style='font-size:12px;' target='_blank'>");
				    sb.append(nickName);
				    sb.append("</a>");
					if(comment.getReplyToUserId() > 0){
						sb.append("  &nbsp;<font style='color:gray;text-align:right'>"+comment.getReplyDate()+"</font><br/>");
						sb.append(" 回复 ");
					    sb.append("<a href='getProfile.action?id=");
					    sb.append(comment.getReplyToUserId());
					    sb.append("' class='hyper' style='font-size:12px;' target='_blank'>");
					    sb.append(comment.getReplyToUserName());
					    sb.append("</a>: ");
					}else{
						sb.append("  &nbsp;<font style='color:gray;text-align:right'>"+comment.getReplyDate()+"</font><br/>");
					}
				    sb.append(commentContent);
				    sb.append("</div><div id='opComment' style='text-align:right;'>");
				    if(user.getUserId() == comment.getUserId()){
					    sb.append("<a class='hyper' style='font-size:12px;' onclick='delComment(this,");
					    sb.append(commentId);
					    sb.append(")'>删除</a> | ");
				    }
				    
				    sb.append(	"<a class='hyper' style='font-size:12px;' onclick=\"replyComment(");
				    sb.append(comment.getUserId());
				    sb.append(",'");
				    sb.append(nickName);
				    sb.append("')\">回复</a> </div></div>");
				}
				info = sb.toString();
				out.println(info);
				out.close();
				comPage = 0;
				return null;
			}catch(Exception ex){
				ex.printStackTrace();
				info = "FAIL";
				out.println(info);
				out.close();
				return null;
			}
		}
 	}
}
