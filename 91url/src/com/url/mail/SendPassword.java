package com.url.mail;

import org.apache.commons.mail.HtmlEmail;

public class SendPassword {

    public String send(String mailToAddress,String mailToUser,String newPwd){
    	String result = "";
		try{
			HtmlEmail email = new HtmlEmail();
		    //设置发送主机的服务器地址
		    email.setHostName("smtp.163.com");
		    email.setAuthentication("services_91url@163.com", "rf3wdicz8z");
		    //设置收件人邮箱
		    email.addTo(mailToAddress,mailToUser);
		    //发件人邮箱
		    email.setFrom("services_91url@163.com", "91url客服");
		    //设置邮件的主题
		    email.setSubject("91url.com 密码找回");
		    //邮件正文消息
		    email.setCharset("utf-8");   //设置Charset    
            email.setHtmlMsg("尊敬的用户:<br/><br/>您登录91url.com的密码为:<br/><br/><font style='font-weight:bold'>" + newPwd+"</font> <br/><br/>需要修改密码请<a href='http://zy02636.gotoip1.com/91url/'>点击这里</a> <br/><br/>感谢您对<a href='http://zy02636.gotoip1.com/91url/'>91ur.com</a>的支持  &nbsp;&nbsp;&nbsp;91url团队.");     //内容       
            email.send(); 
		    result = "success";
		}catch(Exception ex){
			ex.printStackTrace();
			result = "fail";
		}
       return result;
    }
    public static void main(String[] args){
    	new SendPassword().send("skshoes@yahoo.cn","大饼","1234fds");
    }
}