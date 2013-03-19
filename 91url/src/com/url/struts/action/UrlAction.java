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
 	 * ��װ���session��request
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
 	 * ������ǩ�Ĳ���
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
	 * ������ǩ�Ĳ���
	 */
    private int urlId;
    private String urlTitle;
    private int pageNum = 1;
    private int urlRange = 0;//0�����û��Լ���ǩ,1��������urlTypeΪ0����ǩ
    
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
	 * ���ݱ�ǩID��ȡ��ǩ
	 */
	private int tagId;
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	/**
	 * ����tagId��ȡ��ǩ
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
				urlRange = 0;//��ԭ
				int fansSum = userService.getFansSum(userId);
				int followersSum = userService.getFollowersSum(userId);
				int urlsSum = urlService.getUrlSumByUserId(userId);
				int followUrlSum = urlService.getFollowsUrlSum(userId);
				int urlTagSum = urlService.getUrlByTagIdSum(tagId);
				List<UserTag> userTags = userTagService.getUserTagsByUserId(userId);
				List<Tag> tags = tagService.getTagsByUserId(userId);
				String tagName = tagService.getTagNameById(tagId);
				
				request.put("validate", "processed");//��ֹ��¼���û�ֱ�ӷ���jspҳ��
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
 	 * ���һ������
 	 * @return Ajax ����
 	 * @throws Exception
 	 */
 	public String shareUrl() throws Exception{
 		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){//����Ϊ��¼�û�,��session�е�userId �� ���� ��userId��һ����
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
				url.setUrlType(urlType);// Ĭ��Ϊ˽������,urlType=0 Ϊ����,urlType =1 Ϊ˽��
				
				int[] result = urlService.shareUrl(shareId, url);
	
				//���صĵڶ�������ΪshareSum,�Ժ����ֱ����JS,��Ϊ����ԭ�������ϼ�1��.
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
 	 * ajax ������ǩ����
 	 * @return
 	 * @throws Exception
 	 */
 	public String updateUrl() throws Exception{
 		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){//����Ϊ��¼�û�,��session�е�userId �� ���� ��userId��һ����
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
		
				//���صĵڶ�������ΪshareSum,�Ժ����ֱ����JS,��Ϊ����ԭ�������ϼ�1��.
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
 	 * ajax ɾ����ǩ
 	 * @return
 	 * @throws Exception
 	 */
 	public String delUrl() throws Exception{
 		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){//����Ϊ��¼�û�,��session�е�userId �� ���� ��userId��һ����
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
