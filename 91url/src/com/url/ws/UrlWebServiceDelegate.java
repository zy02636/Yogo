
package com.url.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import com.url.ws.bean.Url;
import com.url.ws.bean.UrlCollection;

@WebService(
		targetNamespace = "http://ws.url.com/", 
		serviceName = "UrlService", 
		portName = "UrlServicePort", 
		wsdlLocation = "WEB-INF/wsdl/UrlService.wsdl")
public class UrlWebServiceDelegate {
	com.url.ws.UrlWebService urlWebService = new com.url.ws.UrlWebService();


	@WebMethod(action = "http://ws.url.com/getUrl")
    @WebResult(name = "urls")
    @RequestWrapper(targetNamespace = "http://ws.url.com/", className = "com.url.ws.GetUrl")
    @ResponseWrapper(targetNamespace = "http://ws.url.com/", className = "com.url.ws.GetUrlResponse")
    public Url[] getUrl(
        @WebParam(name = "username")
        String username,
        @WebParam(name = "password")
        String password,
        @WebParam(name = "groupCount")
        int groupCount){
    	return urlWebService.getUrl(username,password,groupCount);
    }
	
	@WebMethod(action = "http://ws.url.com/setUrl")
    @WebResult(name = "result")
    @RequestWrapper(targetNamespace = "http://ws.url.com/", className = "com.url.ws.SetUrl")
    @ResponseWrapper(targetNamespace = "http://ws.url.com/", className = "com.url.ws.SetUrlResponse")
    public String setUrl(
        @WebParam(name = "urlCollection")
        UrlCollection urlCollection,
        @WebParam(name = "username")
        String username,
        @WebParam(name = "password")
        String password){
    	return urlWebService.setUrl(urlCollection, username, password);
    }
	
	 /**
     * web service添加url记录
     * @param urlTitle
     * @param urlOwner
     * @param urlOwnerId
     * @param urlAddDate
     * @param urlType
     * @param url
     * @return
     *    
     */
    @WebMethod(action = "http://ws.url.com/addUrl")
    @WebResult(name = "result")
    @RequestWrapper(localName = "addUrl", targetNamespace = "http://ws.url.com/", className = "com.url.ws.AddUrl")
    @ResponseWrapper(localName = "addUrlResponse", targetNamespace = "http://ws.url.com/", className = "com.url.ws.AddUrlResponse")
    public String addUrl(
        @WebParam(name = "url")
        String url,
        @WebParam(name = "urlTitle")
        String urlTitle,
        @WebParam(name = "urlOwner")
        String urlOwner,
        @WebParam(name = "urlOwnerId")
        int urlOwnerId,
        @WebParam(name = "urlAddDate")
        String urlAddDate,
        @WebParam(name = "urlType")
        int urlType,
        @WebParam(name = "urlTag")
        String urlTag){
    	return urlWebService.addUrl(url, urlTitle, urlOwner, urlOwnerId, urlAddDate, urlType, urlTag);
    }

    /**
     * ws登录
     * @param username 用户账号
     * @param password 用户密码
     * @return 返回登录结果
     *     
     */
    @WebMethod(action = "http://ws.url.com/login")
    @WebResult(name = "result")
    @RequestWrapper(localName = "login", targetNamespace = "http://ws.url.com/", className = "com.url.ws.Login")
    @ResponseWrapper(localName = "loginResponse", targetNamespace = "http://ws.url.com/", className = "com.url.ws.LoginResponse")
    public String login(
        @WebParam(name = "username")
        String username,
        @WebParam(name = "password")
        String password){
    	return urlWebService.login(username, password);
    }
    public static void main(String[] args){
    	System.out.println("123");
    }
}
