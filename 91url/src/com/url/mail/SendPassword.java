package com.url.mail;

import org.apache.commons.mail.HtmlEmail;

public class SendPassword {

    public String send(String mailToAddress,String mailToUser,String newPwd){
    	String result = "";
		try{
			HtmlEmail email = new HtmlEmail();
		    //���÷��������ķ�������ַ
		    email.setHostName("smtp.163.com");
		    email.setAuthentication("services_91url@163.com", "rf3wdicz8z");
		    //�����ռ�������
		    email.addTo(mailToAddress,mailToUser);
		    //����������
		    email.setFrom("services_91url@163.com", "91url�ͷ�");
		    //�����ʼ�������
		    email.setSubject("91url.com �����һ�");
		    //�ʼ�������Ϣ
		    email.setCharset("utf-8");   //����Charset    
            email.setHtmlMsg("�𾴵��û�:<br/><br/>����¼91url.com������Ϊ:<br/><br/><font style='font-weight:bold'>" + newPwd+"</font> <br/><br/>��Ҫ�޸�������<a href='http://zy02636.gotoip1.com/91url/'>�������</a> <br/><br/>��л����<a href='http://zy02636.gotoip1.com/91url/'>91ur.com</a>��֧��  &nbsp;&nbsp;&nbsp;91url�Ŷ�.");     //����       
            email.send(); 
		    result = "success";
		}catch(Exception ex){
			ex.printStackTrace();
			result = "fail";
		}
       return result;
    }
    public static void main(String[] args){
    	new SendPassword().send("skshoes@yahoo.cn","���","1234fds");
    }
}