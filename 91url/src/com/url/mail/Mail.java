package com.url.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;


public class Mail {
	  /** ������ʽ - ��ͨ���� */
	  final public static int TO = 0;
	  /** ������ʽ - ���� */
	  final public static int CC = 1;
	  /** ������ʽ - �ܼ����� */
	  final public static int BCC = 2;

	  /** �ʼ������Ϣ - SMTP ������ */
	  private String mailSMTPHost = null;
	  /** �ʼ������Ϣ - �ʼ��û��� */
	  private String mailUser = null;
	  /** �ʼ������Ϣ - ���� */
	  private String mailPassword = null;
	  /** �ʼ������Ϣ - �������ʼ���ַ */
	  private String mailFromAddress = null;
	  /** �ʼ������Ϣ - �ʼ����� */
	  private String mailSubject = "";
	  /** �ʼ������Ϣ - �ʼ����͵�ַ */
	  private Address[] mailTOAddress = null;
	  /** �ʼ������Ϣ - �ʼ����͵�ַ */
	  private Address[] mailCCAddress = null;
	  /** �ʼ������Ϣ - �ʼ��ܼ����͵�ַ */
	  private Address[] mailBCCAddress = null;
	  /** �ʼ������Ϣ - �ʼ�����(���Ͻṹ) */
	  private MimeMultipart mailBody = null;

	  public Mail() {
	    mailBody = new MimeMultipart();
	  }

	  /**
	   * ���� SMTP ������
	   * @param strSMTPHost �ʼ����������ƻ� IP
	   * @param strUser �ʼ��û���
	   * @param strPassword ����
	   */
	  public void setSMTPHost(String strSMTPHost, String strUser,
	                          String strPassword) {
	    this.mailSMTPHost = strSMTPHost;
	    this.mailUser = strUser;
	    this.mailPassword = strPassword;
	  }

	  /**
	   * �����ʼ����͵�ַ
	   * @param strFromAddress �ʼ����͵�ַ
	   */
	  public void setFromAddress(String strFromAddress) {
	    this.mailFromAddress = strFromAddress;
	  }

	  /**
	   * �����ʼ�Ŀ�ĵ�ַ
	   * @param strAddress �ʼ�Ŀ�ĵ�ַ�б�, ��ͬ�ĵ�ַ����;�ŷָ�
	   * @param iAddressType �ʼ����ͷ�ʽ (TO 0, CC 1, BCC 2) �������ڱ��ඨ��
	   * @throws AddressException
	   */
	  public void setAddress(String strAddress, int iAddressType) throws
	      AddressException {
	    switch (iAddressType) {
	      case Mail.TO: {
	        String[] alAddress = strAddress.split(";");
	        mailTOAddress = new Address[alAddress.length];
	        for (int i = 0; i < alAddress.length; i++) {
	          mailTOAddress[i] = new InternetAddress(alAddress[i]);
	        }
	        break;
	      }
	      case Mail.CC: {
	    	  String[] alAddress = strAddress.split(";");
	        mailCCAddress = new Address[alAddress.length];
	        for (int i = 0; i < alAddress.length; i++) {
	          mailCCAddress[i] = new InternetAddress(alAddress[i]);
	        }
	        break;
	      }
	      case Mail.BCC: {
	    	 String[] alAddress = strAddress.split(";");
	        mailBCCAddress = new Address[alAddress.length];
	        for (int i = 0; i < alAddress.length; i++) {
	          mailBCCAddress[i] = new InternetAddress(alAddress[i]);
	        }
	        break;
	      }
	    }
	  }

	  /**
	   * �����ʼ�����
	   * @param strSubject �ʼ�����
	   */
	  public void setSubject(String strSubject) {
	    this.mailSubject = strSubject;
	  }

	  /**
	   * �����ʼ��ı�����
	   * @param strTextBody �ʼ��ı�����
	   * @throws MessagingException
	   */
	  public void setTextBody(String strTextBody) throws MessagingException {
	    MimeBodyPart mimebodypart = new MimeBodyPart();
	    mimebodypart.setText(strTextBody, "GBK");
	    mailBody.addBodyPart(mimebodypart);
	  }

	  /**
	   * �����ʼ����ı�����
	   * @param strHtmlBody �ʼ����ı�����
	   * @throws MessagingException
	   */
	  public void setHtmlBody(String strHtmlBody) throws MessagingException {
	    MimeBodyPart mimebodypart = new MimeBodyPart();
	    mimebodypart.setDataHandler(new DataHandler(strHtmlBody, "text/html;charset=GBK"));
	    mailBody.addBodyPart(mimebodypart);
	  }

	  /**
	   * �����ʼ������ⲿ���� URL, �����н�����������ָ�������
	   * @param strURLAttachment �ʼ������ⲿ���� URL
	   * @throws MessagingException
	   * @throws MalformedURLException
	   */
	  public void setURLAttachment(String strURLAttachment) throws
	      MessagingException, MalformedURLException {
	    MimeBodyPart mimebodypart = new MimeBodyPart();
	    mimebodypart.setDataHandler(new DataHandler(new URL(strURLAttachment)));
	    mailBody.addBodyPart(mimebodypart);
	  }

	  /**
	   * �����ʼ�����
	   * @param strFileAttachment �ļ���ȫ·��
	   * @throws MessagingException
	   * @throws UnsupportedEncodingException
	   */
	  public void setFileAttachment(String strFileAttachment) throws
	      MessagingException, UnsupportedEncodingException {
	    File path = new File(strFileAttachment);
	    if (!path.exists() || path.isDirectory()) {
	      return;
	    }
	    String strFileName = path.getName();
	    MimeBodyPart mimebodypart = new MimeBodyPart();
	    mimebodypart.setDataHandler(new DataHandler(new FileDataSource(
	        strFileAttachment)));
	    // modified by zord @ 2003/6/16 to support Chinese File Name
	    // mimebodypart.setFileName(strFileName);
	    mimebodypart.setFileName(MimeUtility.encodeText(strFileName));
	    // end of modify
	    mailBody.addBodyPart(mimebodypart);
	  }

	  /**
	   * �ʼ�����(һ�η��Ͷ����ַ, �ŵ��ٶȿ�, �����зǷ��ʼ���ַʱ���жϷ��Ͳ���)
	   * @throws MessagingException
	   */
	  public void sendBatch() throws MessagingException {
	    Properties properties = new Properties();
	    
		 properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
	     properties.setProperty("mail.smtp.socketFactory.fallback", "false"); 
	     properties.setProperty("mail.smtp.port", "465"); 
	     properties.setProperty("mail.smtp.socketFactory.port", "465");
	    
	    properties.put("mail.smtp.host", this.mailSMTPHost);
	    Session session = Session.getInstance(properties, null);
	    MimeMessage mimemessage = new MimeMessage(session);
	    mimemessage.setFrom(new InternetAddress(this.mailFromAddress));
	    if (mailTOAddress != null) {
	      mimemessage.addRecipients(javax.mail.Message.RecipientType.TO,
	                                this.mailTOAddress);
	    }
	    if (mailCCAddress != null) {
	      mimemessage.addRecipients(javax.mail.Message.RecipientType.CC,
	                                this.mailCCAddress);
	    }
	    if (mailBCCAddress != null) {
	      mimemessage.addRecipients(javax.mail.Message.RecipientType.BCC,
	                                this.mailBCCAddress);
	    }
	    mimemessage.setSubject(this.mailSubject);
	    mimemessage.setContent(this.mailBody);
	    mimemessage.setSentDate(new Date());
	    Transport transport = session.getTransport("smtp");
	    transport.connect(this.mailSMTPHost, this.mailUser, this.mailPassword);
	    Transport.send(mimemessage);
	    System.out.println("�����������䷢�����ʼ�");
	    if (mailTOAddress != null) {
	      for (int i = 0; i < mailTOAddress.length; i++) {
	        System.out.println(mailTOAddress[i]);
	      }
	    }
	    if (mailCCAddress != null) {
	      for (int i = 0; i < mailTOAddress.length; i++) {
	        System.out.println(mailCCAddress[i]);
	      }
	    }
	    if (mailBCCAddress != null) {
	      for (int i = 0; i < mailTOAddress.length; i++) {
	        System.out.println(mailBCCAddress[i]);
	      }
	    }
	  }

	static public void main(String str[]) throws MessagingException
	{
	 Mail mail=new Mail();
	 mail.setAddress("61096845@qq.com",Mail.TO);
	 mail.setFromAddress("91url.com@gmail.com");
	 mail.setSMTPHost("smtp.gmail.com","91url.com@gmail.com","rf3wdicz8z");
	  
	 mail.setSubject("����һ��");
	 mail.setHtmlBody("");
	 mail.sendBatch();
	}
	}
