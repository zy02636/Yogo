package com.url.struts.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.url.bean.Tag;
import com.url.bean.User;
import com.url.bean.UserTag;
import com.url.mail.SendPassword;
import com.url.service.ITagService;
import com.url.service.IUrlService;
import com.url.service.IUserService;
import com.url.service.IUserTagService;

public class UserAction extends ActionSupport implements SessionAware,RequestAware{
	private IUserService userService;
	private IUrlService urlService;
	private IUserTagService userTagService;
	private ITagService tagService;
	public void setUserTagService(IUserTagService userTagService) {
		this.userTagService = userTagService;
	}
	public void setTagService(ITagService tagService) {
		this.tagService = tagService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public void setUrlService(IUrlService urlService) {
		this.urlService = urlService;
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
	 * HTMLҳ��Ĳ���,�Զ���װ
	 */
	private String username;
	private String password;
	private String rePassword;
    private String code;
    private String autoLogin;
    private int userId;
    private String nickName;
    private int guestId;
	private int hostId;
	private int id;
	private int pageNum = 1;
	private String exPassword;
	
    public String getExPassword() {
		return exPassword;
	}
	public void setExPassword(String exPassword) {
		this.exPassword = exPassword;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}
	public void setHostId(int hostId) {
		this.hostId = hostId;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    /*
     * �����û���ǩ��ȡ�û��Ĳ���
     */
	private int userTagId;
	public void setUserTagId(int userTagId) {
		this.userTagId = userTagId;
	}
	/*
	 * ��ҳ��¼
	 */
	public String login() throws Exception {
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if(user==null){
				user = userService.getUserByNamePwd(username, password);
				if( user != null){
			        session.put("user",user);
			        if(autoLogin.equals("auto")){
			        	Cookie namecookie = new Cookie("username",username);
			        	Cookie passwordcookie = new Cookie("password",password);
			        	
			        	namecookie.setMaxAge(60*60*24*30);
			        	passwordcookie.setMaxAge(60*60*24*30);
			        	
			        	response.addCookie(namecookie);
			        	response.addCookie(passwordcookie); 
			        }else{
			        	Cookie namecookie = new Cookie("username",username);
			        	namecookie.setMaxAge(60*60*24*30);
			        	response.addCookie(namecookie);
			        }
					return "SUCCESS";
				}else{
					request.put("fail","<font style='color:red;font-size:12px'>�û��������������</font>");
					return "FAIL";
				}
			}else{
				return "SUCCESS";
			}
		} catch (Exception ex) {
			System.out.println("Error");
			ex.printStackTrace();
			return "FAIL";
		}
	}
	/*
	 * ��cookie���û�ֱ�ӵ�¼
	 */
	public String cookieLogin() throws Exception {
		User user = (User)session.get("user");
		try {
			if(user==null){
				user = userService.getUserByNamePwd(username,password);
				if(user != null){
			        session.put("user",user);
					return "SUCCESS";
				}else{
					request.put("fail","<font style='color:red;font-size:12px'>�û��������������</font>");
					return "FAIL";
				}
			}else{
				return "SUCCESS";
			}
		} catch (Exception ex) {
			System.out.println("Error");
			ex.printStackTrace();
			return "FAIL";
		}
	}
	/*
	 * ����û���¼
	 */
	public String pluginLogin() throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        
		try {
			User user = (User)session.get("user");
			if(user==null){
				user = userService.getUserByNamePwd(username, password);
				if(user != null){
			        session.put("user",user);
			        response.getWriter().println(user.getNickName()+"&_"+user.getUserId());
					return null;
				}else{
					response.getWriter().println("L");
					return null;
				}
			}else{
		        response.getWriter().println("�û��ѵ�¼");
		        return null;
			}
		}catch (Exception ex) {
			System.out.println("����û���¼ʧ��");
			ex.printStackTrace();
			return null;
		}finally{
	        response.getWriter().close();
		}
	}
	/**
	 * �û��ύע����Ϣ����ajax������Ϣ,��֤ͨ�����ٽ�����ת
	 * @return
	 * @throws Exception
	 */
	public String preAddUser() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String sessionCode = (String)session.get("code");
		int result = 0;
		try{
			if(!password.equals(rePassword)){
				info = "<font style='color:red;font-size:12px;'>������������벻ͬ</font>&&2";
			}else if(userService.userNameExists(username)){
				info = "<font style='color:red;font-size:12px;'>�û����Ѿ�����</font>&&1";
			}else if(!sessionCode.equalsIgnoreCase(code)){
				info = "<font style='color:red;font-size:12px;'>��֤�����</font>&&3";
			}else{
				Calendar cal = Calendar.getInstance();
				Date time = cal.getTime();
				String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
				
				User user = new User();
				user.setUsername(username);
				user.setPwd(password);
				user.setRegDate(curDate);
				user.setNickName("91url�û�");
				result = userService.addUser(user);
				if(result == 1){
					info = "SUCCESS";
                    //����cookie�����ֵ
					Cookie namecookie = new Cookie("username",username);
		        	namecookie.setMaxAge(60*60*24*365);
		        	response.addCookie(namecookie);
		        	
		        	session.put("user",user);
		        	return null;
				}else{
					info = "<font style='color:red;font-size:12px;'>��������ע��ʧ��,������</font>&&4";
					return null;
				}
			}

	        out.println(info);
	        out.close();
		}catch (Exception ex) {
			ex.printStackTrace();
			info = "FAIL";
			out.println(info);
	        out.close();
			return null;
		}
		return null;
	}
	/**
	 * ����û����Ƿ��Ѿ�����
	 * @return
	 * @throws Exception
	 */
	public String checkUserName() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try {
            boolean result = userService.userNameExists(username);

            if(result){
            	info = "FAIL";
            }else{
            	info = "SUCCESS";
            }

	        out.println(info);
	        out.close();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			info = "����û����Ƿ����ʧ��";
			out.println(info);
		    out.close();
			return null;
		}
	}
	
	/**
	 * �����û����ǳ�
	 * @return
	 * @throws Exception
	 */
	public String updateUserName() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user == null || (user.getUserId() != userId)){
			info = "SESSIONFAIL";
			out.println(info);
			out.close();
		    return null;	
		}else{
			int result = 0;
			user.setNickName(URLDecoder.decode(nickName,"UTF-8"));
			result = userService.updateUser(user);
			if(result >= 1){
				info = "SUCCESS";
				out.println(info);
				out.close();
			}else{
				info = "FAIL";
				out.println(info);
				out.close();
			}
		}
		return null;
	}
	/**
	 * ��ȡ�û���fans
	 * @return
	 * @throws Exception
	 */
	public String getFans() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
			if(user == null){
			    return "SESSIONFAIL";	
			}else{
				User fanUser = userService.getUser(id);
				List<User> users = userService.getFans(id,user.getUserId(),pageNum);
				int fansSum = userService.getFansSum(id);
				int followersSum = userService.getFollowersSum(id);
				int urlsSum = urlService.getUrlSumByUserId(id);
                
				//����û��ɹ�,�����ж��Ƿ�js����ҳ���ע��Ŀ�Ƿ�����
				if(id == user.getUserId()){
					request.put("fansowner","self");
				}else{
					request.put("fansowner","other");
				}
				request.put("validate", "processed");//��ֹ��¼���û�ֱ�ӷ���jspҳ��
				request.put("fansSum", fansSum);
				request.put("followersSum", followersSum);
				request.put("urlsSum", urlsSum);
				request.put("fans",users);
				request.put("user",fanUser);
				request.put("curPage",pageNum);
				pageNum = 1;
				return "SUCCESS";
			}
		}catch(Exception ex){
			System.out.println("��ȡ�û�fans�б�ʧ��");
			ex.printStackTrace();
			return "FAIL";	
		}
	}
	/**
	 * ��ȡ�û���follows
	 * @return
	 * @throws Exception
	 */
	public String getFollows() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		try{
			if(user == null){
			    return "SESSIONFAIL";	
			}else{
				User followUser = userService.getUser(id);
				List<User> users = userService.getFollowers(id,user.getUserId(),pageNum);//�ڶ������������ж��Ƿ��뱻�����߽�����ϵ
				int fansSum = userService.getFansSum(id);
				int followersSum = userService.getFollowersSum(id);
				int urlsSum = urlService.getUrlSumByUserId(id);
                
				if(id == user.getUserId()){
					request.put("followsowner","self");
				}else{
					request.put("followsowner","other");
				}
				request.put("validate", "processed");//��ֹ��¼���û�ֱ�ӷ���jspҳ��
				request.put("fansSum", fansSum);
				request.put("followersSum", followersSum);
				request.put("urlsSum", urlsSum);
				request.put("follows",users);
				request.put("user",followUser);
				request.put("curPage",pageNum);
				pageNum = 1;
				return "SUCCESS";
			}
		}catch(Exception ex){
			System.out.println("��ȡ�û�follows�б�ʧ��");
			return "FAIL";	
		}
	}
	/**
	 * �Ƴ���˿
	 * @return
	 * @throws Exception
	 */
	public String delFan() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try{
			if(user == null && (user.getUserId() == userId)){
				info = "SESSSIONFAIL";
			    return null;	
			}else{
				int result = userService.removeFan(user.getUserId(),guestId);
				if(result >= 1){
					info = "SUCCESS";
				}else{
					info = "FAIL";
				}
				return null;
			}
		}catch(Exception ex){
			System.out.println("�Ƴ���˿ʧ��");
			info = "FAIL";
			return null;	
		}finally{
			out.println(info);
			out.close();
		}
	}
	/**
	 * �Ƴ���ע
	 * @return
	 * @throws Exception
	 */
	public String delFollow() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try{
			if(user == null && (user.getUserId() == userId)){
				info = "SESSSIONFAIL";
			    return null;	
			}else{
				int result = userService.removeFollow(user.getUserId(),hostId);
				if(result >= 1){
					info = "SUCCESS";
				}else{
					info = "FAIL";
				}
				return null;
			}
		}catch(Exception ex){
			System.out.println("�Ƴ���עʧ��");
			info = "FAIL";
			return null;	
		}finally{
			out.println(info);
			out.close();
		}
	}
	/**
	 * ��ӹ�ע
	 * @return
	 * @throws Exception
	 */
	public String addFollow() throws Exception{
		User user = (User)session.get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try{
			if(user == null && (user.getUserId() == userId)){
				info = "SESSSIONFAIL";
			    return null;	
			}else{
				int result = 0;
				if(user.getUserId() != hostId){ //��ֹ�Լ�����Լ�
				    result = userService.addFollow(user.getUserId(),hostId);
				}
				if(result >= 1){
					info = "SUCCESS";
				}else{
					info = "FAIL";
				}
				return null;
			}
		}catch(Exception ex){
			System.out.println("��ӹ�עʧ��");
			info = "FAIL";
			return null;	
		}finally{
			out.println(info);
			out.close();
		}
	}
	/**
	 * ����tagId��ȡ��ǩ
	 * @return
	 * @throws Exception
	 */
	public String getUserByTagId() throws Exception{
 		User user = (User)session.get("user");
		if(user == null){
		    return "FAIL";	
		}else{
			try{
				int userId = user.getUserId();
				List<User> users = userService.getUserByTagId(userTagId,pageNum,userId);
				int fansSum = userService.getFansSum(userId);
				int followersSum = userService.getFollowersSum(userId);
				int urlsSum = urlService.getUrlSumByUserId(userId);
				int followUrlSum = urlService.getFollowsUrlSum(userId);
				int userSum = userService.getUserByTagIdSum(userTagId);
				List<UserTag> userTags = userTagService.getUserTagsByUserId(userId);
				List<Tag> tags = tagService.getTagsByUserId(userId);
				String userTagName = userTagService.getUserTagNameById(userTagId);
				request.put("validate", "processed");//��ֹ��¼���û�ֱ�ӷ���jspҳ��
				request.put("userTags", userTags);
				request.put("tags", tags);
				request.put("fansSum", fansSum);
				request.put("followersSum", followersSum);
				request.put("urlsSum", urlsSum);
				request.put("followUrlSum", followUrlSum);
				request.put("user", user);
				request.put("users", users);
				request.put("userSum", userSum);
				request.put("userTagName", userTagName);
				request.put("userTagId", userTagId);
				request.put("curPage", pageNum);
				userTagId = 0;
				pageNum = 1;
				
				return "SUCCESS";
			}catch(Exception ex){
				ex.printStackTrace();
				return "FAIL";
			}
		}
 	}
	/**
	 * �û�ע��
	 * @return
	 * @throws Exception
	 */
	public String logoff() throws Exception{
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		//cookies��Ϊ�գ������      
		if(cookies != null)
		{
		    for (int i = 0; i < cookies.length; i++) 
		    {
		        Cookie c = cookies[i];     
                if(c.getName().equalsIgnoreCase("password"))
		        {
		        	c.setValue(null);
				    ServletActionContext.getResponse().addCookie(c);
		        } 
		    } 
		} 
		request.put("fail","");//���ؿյĵ�¼����ʾ��Ϣ����¼��ҳ
		session.remove("user");
		return "SUCCESS";
	}
	
	/**
	 * �û�ע��
	 * @return
	 * @throws Exception
	 */
	public String getUserPwdByUserName() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try{
			String sessionCode = (String)session.get("pwdCode");
			if(code.equalsIgnoreCase(sessionCode)){
				String pwd = userService.getPwdByUserName(username);
				User user = userService.getUserByNamePwd(username, pwd);
				if(user != null){
					SendPassword sendPwd = new SendPassword();
					sendPwd.send(username, user.getNickName(), pwd);
					info = "SUCCESS";
				}else{
					info = "FAIL";
				}
			}else{
				info = "CODEFAIL";
			}
			return null;
		}catch(Exception ex){
			System.out.println("��������ʧ��");
			ex.printStackTrace();
			info = "FAIL";
			return null;	
		}finally{
			out.println(info);
			out.close();
		}
	}
	/**
	 * �û���������
	 * @return
	 * @throws Exception
	 */
	public String changPwd() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		//ajax ������Ҫ׼�������Լ��ַ���
		String info = "";
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		try{
			String sessionCode = (String)session.get("cgpwdCode");
			if(code.equalsIgnoreCase(sessionCode) && password.equals(rePassword)){
				User user = userService.getUserByNamePwd(username, exPassword);
				if(user != null){
					int result = userService.resetPwd(username,password);
					if(result >= 1){
						info = "SUCCESS";
					}else{
						info = "FAIL";
					}
				}else{
					info = "FAIL";
				}
			}else{
				info = "CODEFAIL";
			}
			return null;
		}catch(Exception ex){
			System.out.println("��������ʧ��");
			ex.printStackTrace();
			info = "FAIL";
			return null;	
		}finally{
			out.println(info);
			out.close();
		}
	}
}
