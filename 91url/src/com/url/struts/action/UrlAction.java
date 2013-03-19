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

import com.opensymphony.xwork2.ActionSupport;
import com.url.bean.Tag;
import com.url.bean.Url;
import com.url.bean.User;
import com.url.bean.UserTag;
import com.url.service.ITagService;
import com.url.service.IUrlService;
import com.url.service.IUserService;
import com.url.service.IUserTagService;


public class UrlAction extends ActionSupport implements SessionAware,RequestAware{
	private IUrlService urlService;
	private IUserTagService userTagService;
	private ITagService tagService;
	private IUserService userService;
	
	public void setUrlService(IUrlService urlService) {
		this.urlService = urlService;
	}
	public void setUserTagService(IUserTagService userTagService) {
		this.userTagService = userTagService;
	}
	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
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
 	/*
 	 * 分享书签的参数
 	 */
 	private int shareId;
 	private int userId;
 	private int urlType;
 	
 	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}
	public void setShareId(int shareId) {
		this.shareId = shareId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/*
	 * 更新书签的参数
	 */
    private int urlId;
    private String urlTitle;
    private int pageNum = 1;
    private int urlRange = 0;//0代表用户自己书签,1代表所有urlType为0的书签
    
	public int getUrlRange() {
		return urlRange;
	}
	public void setUrlRange(int urlRange) {
		this.urlRange = urlRange;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	/*
	 * 根据标签ID获取书签
	 */
	private int tagId;
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	/**
	 * 根据tagId获取书签
	 * @return
	 * @throws Exception
	 */
	public String getUrlByTagId() throws Exception{
 		User user = (User)session.get("user");
		if(user == null){
		    return "FAIL";	
		}else{
			try{
				int userId = user.getUserId();
				List<Url> urls = null;

				if(urlRange <= 0){
					urls = urlService.getUrlByTagId(tagId,pageNum,userId);
				}else{
					urls = urlService.getUrlByTagId(tagId,pageNum,-1);
				}
				urlRange = 0;//还原
				int fansSum = userService.getFansSum(userId);
				int followersSum = userService.getFollowersSum(userId);
				int urlsSum = urlService.getUrlSumByUserId(userId);
				int followUrlSum = urlService.getFollowsUrlSum(userId);
				int urlTagSum = urlService.getUrlByTagIdSum(tagId);
				List<UserTag> userTags = userTagService.getUserTagsByUserId(userId);
				List<Tag> tags = tagService.getTagsByUserId(userId);
				String tagName = tagService.getTagNameById(tagId);
				
				request.put("validate", "processed");//防止登录的用户直接访问jsp页面
				request.put("userTags", userTags);
				request.put("tags", tags);
				request.put("fansSum", fansSum);
				request.put("followersSum", followersSum);
				request.put("urlsSum", urlsSum);
				request.put("followUrlSum", followUrlSum);
				request.put("user", user);
				request.put("urls", urls);
				request.put("urlTagSum", urlTagSum);
				request.put("tagName", tagName);
				request.put("tagId", tagId);
				request.put("curPage", pageNum);
				tagId = 0;
				pageNum = 1;
				
				return "SUCCESS";
			}catch(Exception ex){
				ex.printStackTrace();
				return "FAIL";
			}
		}
 	}
	/**
 	 * 添加一条评论
 	 * @return Ajax 访问
 	 * @throws Exception
 	 */
 	public String shareUrl() throws Exception{
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
				Url url = new Url();
				Calendar cal = Calendar.getInstance();
				Date time = cal.getTime();
				String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);

				url.setAddDate(curDate);
				url.setUrlOwner(user.getNickName());
				url.setUrlOwnerId(user.getUserId());
				url.setUrlType(urlType);// 默认为私有链接,urlType=0 为共有,urlType =1 为私有
				
				int[] result = urlService.shareUrl(shareId, url);
	
				//返回的第二个参数为shareSum,以后可以直接用JS,因为是在原来基础上加1的.
				if(result[0]>= 1 ){
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
 	 * ajax 更新书签标题
 	 * @return
 	 * @throws Exception
 	 */
 	public String updateUrl() throws Exception{
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
				Url url = new Url();
                url.setUrlId(urlId);
                url.setUrlTitle(URLDecoder.decode(urlTitle,"UTF-8"));
				
				int result = urlService.updateUrl(url);
		
				//返回的第二个参数为shareSum,以后可以直接用JS,因为是在原来基础上加1的.
				if(result >= 1 ){
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
 	 * ajax 删除书签
 	 * @return
 	 * @throws Exception
 	 */
 	public String delUrl() throws Exception{
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
				int result = urlService.deleteUrl(urlId);
				if(result >= 1 ){
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
}
