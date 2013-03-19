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
	 * ����url��tag�Ĳ�����
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
	 * �����û�����������tag�Ĳ�����
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
	 * �û�����ı�ǩǰ׺
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
	 * ���Url��Tag
	 * @return
	 * @throws Exception
	 */
	public String addTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ϣ��Ҫ����
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(urlId <= 0 || user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//�������,��ͬ�û�����,session��ƥ��
			out.close();
		    return null;	
		}else if(user != null && (user.getUserId() == userId)){ //����Ϊ��¼�û�,��session�е�userId �� ���� ��userId��һ����
			try{
				Tag tag = new Tag();
				tag.setTagName(URLDecoder.decode(tagName,"UTF-8")); //ԭ�����..����ǽ���ҳ������UTF-8������ַ���,תΪ�����ݿ��ʶ����ַ�
				tag.setUrlId(urlId);
				int[] result = tagService.addTag(tag);
				
				//��ӳɹ��ı�־������Ӱ������,������2������
				//��һ��userTag�����ϵ��ͬʱ���������
				//�ڶ��ֿ��ܾ��ǽ��ڹ�ϵ���������������,���������Ӱ����������1
				if(result[0] >= 1 && result[1] >=1){
					info = ""+result[1];//ֱ�ӷ�����Ӻ�ı�ǩID
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
	 * ɾ���û���ǩ�ı�ǩ
	 * @return
	 * @throws Exception
	 */
	public String delUrlTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ϣ��Ҫ����
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//�������,��ͬ�û�����,session��ƥ��
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
	 * ����û����������ı�ǩ
	 * @return Ajax ���ز�ͬ�ַ�����ͬ���,�½���ǩ,���б�ǩ������ϵ��
	 * @throws Exception
	 */
	public String addUserTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ϣ��Ҫ����
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//�������,��ͬ�û�����,session��ƥ��
			out.close();
		    return null;	
		}else{
			try{
				UserTag userTag = new UserTag();
				userTag.setUserTagName(URLDecoder.decode(userTagName,"UTF-8"));
				userTag.setUserId(userId);
				
				int[] result = userTagService.addUserTag(userTag);
				
				//��ӳɹ��ı�־������Ӱ������,������2������
				//��һ��userTag�����ϵ��ͬʱ���������
				//�ڶ��ֿ��ܾ��ǽ��ڹ�ϵ���������������,���������Ӱ����������1
				if(result[0] >= 1 && result[1] >=1){
					info = ""+result[1];//ֱ�ӷ�����Ӻ�ı�ǩID
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
	 * ɾ���û����������ı�ǩ
	 * @return
	 * @throws Exception
	 */
	public String delUserTag() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ϣ��Ҫ����
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//�������,��ͬ�û�����,session��ƥ��
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
	 * �����û���ǩ��prefix��ѯ��ǩ
	 * @return
	 * @throws Exception
	 */
	public String getTagsByPrefix() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ϣ��Ҫ����
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "FAILSESSION";
			out.println(info);//�������,��ͬ�û�����,session��ƥ��
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
