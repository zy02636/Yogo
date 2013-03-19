package com.url.struts.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
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
import com.url.service.IUserTagService;

public class TagAction extends ActionSupport implements SessionAware,RequestAware{
	private ITagService tagService;
	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}
	
	private IUserTagService userTagService;
     public void setUserTagService(IUserTagService userTagService) {
		this.userTagService = userTagService;
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
	 * 保存url的tag的参数组
	 */
	private int urlId;
	private String tagName; 
	private int userId;
	private int urlTagId;
	public void setUrlTagId(int urlTagId) {
		this.urlTagId = urlTagId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	/*
	 * 保存用户自我描述的tag的参数组
	 */
	private String userTagName;
	private int userTagId;
	public void setUserTagName(String userTagName) {
		this.userTagName = userTagName;
	}
	public void setUserTagId(int userTagId) {
		this.userTagId = userTagId;
	}
	/*
	 * 用户输入的标签前缀
	 */
	private String prefix;
	private String prefixType;
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setPrefixType(String prefixType) {
		this.prefixType = prefixType;
	}
	/**
	 * 添加Url的Tag
	 * @return
	 * @throws Exception
	 */
	public String addTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax 返回信息需要的类
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(urlId <= 0 || user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//网络错误,不同用户访问,session不匹配
			out.close();
		    return null;	
		}else if(user != null && (user.getUserId() == userId)){ //必须为登录用户,且session中的userId 和 请求 的userId是一样的
			try{
				Tag tag = new Tag();
				tag.setTagName(URLDecoder.decode(tagName,"UTF-8")); //原来如此..这边是将网页传来的UTF-8编码的字符串,转为了数据库可识别的字符
				tag.setUrlId(urlId);
				int[] result = tagService.addTag(tag);
				
				//添加成功的标志就是受影响行数,其中有2个可能
				//第一种userTag表跟关系表同时添加了数据
				//第二种可能就是仅在关系表里面添加了数据,不管如何收影响行数大于1
				if(result[0] >= 1 && result[1] >=1){
					info = ""+result[1];//直接返回添加后的标签ID
				}else if(result[0] == 0 && result[1] >=1){
					info = "REPEAT";
				}else if(result[1] == -1){
					info = "OVER";
				}else{
					info = "ERROR";
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
		return null;
	}
	/**
	 * 删除用户书签的标签
	 * @return
	 * @throws Exception
	 */
	public String delUrlTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax 返回信息需要的类
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//网络错误,不同用户访问,session不匹配
			out.close();
		    return null;		
		}else{
			try{
				int result = tagService.delTagByTagUrlId(urlTagId,urlId);

				if(result >= 1){
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
	 * 添加用户自我描述的标签
	 * @return Ajax 返回不同字符代表不同结果,新建标签,已有标签添加入关系表
	 * @throws Exception
	 */
	public String addUserTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax 返回信息需要的类
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//网络错误,不同用户访问,session不匹配
			out.close();
		    return null;	
		}else{
			try{
				UserTag userTag = new UserTag();
				userTag.setUserTagName(URLDecoder.decode(userTagName,"UTF-8"));
				userTag.setUserId(userId);
				
				int[] result = userTagService.addUserTag(userTag);
				
				//添加成功的标志就是受影响行数,其中有2个可能
				//第一种userTag表跟关系表同时添加了数据
				//第二种可能就是仅在关系表里面添加了数据,不管如何收影响行数大于1
				if(result[0] >= 1 && result[1] >=1){
					info = ""+result[1];//直接返回添加后的标签ID
				}else if(result[0] == 0 && result[1] >=1){
					info = "REPEAT";
				}else if(result[1] == -1){
					info = "OVER";
				}else{
					info = "ERROR";
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
	 * 删除用户自我描述的标签
	 * @return
	 * @throws Exception
	 */
	public String delUserTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax 返回信息需要的类
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//网络错误,不同用户访问,session不匹配
			out.close();
		    return null;	
		}else{
			try{
				int result = userTagService.deleteUserTagByUserId(userTagId,userId);
				if(result >= 1){
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
	 * 根据用户标签的prefix查询标签
	 * @return
	 * @throws Exception
	 */
	public String getTagsByPrefix() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax 返回信息需要的类
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//网络错误,不同用户访问,session不匹配
			out.close();
		    return null;	
		}else{
			try{
				StringBuffer sb = new StringBuffer();
				if(prefixType.equals("userTag")){
					List<UserTag> userTags = userTagService.getUserTagsByTagPrefix(URLDecoder.decode(prefix,"UTF-8"));	
					for (UserTag userTag : userTags) {
						sb.append(userTag.getUserTagName() + "&_&");
					}
				}else if(prefixType.equals("urlTag")){
					List<Tag> tags = tagService.getUrlTagsByTagPrefix(URLDecoder.decode(prefix,"UTF-8"));	
					for (Tag tag : tags) {
						sb.append(tag.getTagName() + "&_&");
					}
				}
				info = sb.toString();
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
