package com.url.ws;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.url.bean.User;
import com.url.lucene.CreateIndex;
import com.url.service.impl.UrlService;
import com.url.service.impl.UserService;
import com.url.ws.bean.Url;
import com.url.ws.bean.UrlCollection;

public class UrlWebService {
	private UserService userService;
	private UrlService urlService;

	public UrlWebService() {

	}

	/**
	 * 传来的参数返回WebService请求
	 * 
	 * @param username
	 * @param password
	 * @param groupCount
	 * @param startUrlId
	 * @return
	 */
	public Url[] getUrl(String username, String password, int groupCount) {
		ApplicationContext oAC = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService) oAC.getBean("UserService");
		urlService = (UrlService) oAC.getBean("UrlService");

		Url[] urls = null;
		User user = userService.getUserByNamePwd(username, password);

		if ((user != null) && (user.getUserType() == 1)) {
			List<Url> urlList = urlService.getUrlGroup(groupCount,username);
			int urlSize = urlList.size();

			urls = new Url[urlSize];

			for (int counter = 0; counter < urlSize; counter++) {
				urls[counter] = urlList.get(counter);
			}

		}
		return urls;
	}

	/**
	 * 调用该服务的程序传来的参数
	 * 
	 * @param urls
	 *            处理后的url集合
	 * @param username
	 *            授权操作的账号
	 * @param password
	 *            授权操作的账号密码
	 * @return
	 */
	public String setUrl(UrlCollection urlCollection, String username,String password) {
		ApplicationContext oAC = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService) oAC.getBean("UserService");
		User user = userService.getUserByNamePwd(username, password);
		
		if ((user != null) && (user.getUserType() == 1)) { //要是user为null的话,那么就不会检测第二个条件了
			// 获取数组字符串,一个潜在的问题是,若一个网页无法打开,读取到数据那么这个网页就可能得到的特征计算会有误
			String urlAddressCollection = urlCollection.getUrl();
			String urlTitleCollection = urlCollection.getUrlTitle();
			String urlIdCollection = urlCollection.getUrlId();
			String urlAddDateCollection = urlCollection.getAddDate();
			String urlOwnerCollection = urlCollection.getUrlOwner();
			String urlOwnerIdCollection = urlCollection.getUrlOwnerId();
			String urlFeaturesCollection = urlCollection.getUrlFeatures();
			String urlScoresCollection = urlCollection.getFeatureScores();
			String urlShareIdCollection = urlCollection.getShareId();
			String urlShareIdInfoIdCollection = urlCollection.getShareIdInfoId();
			
			//去掉句末逗号
			urlAddressCollection = urlAddressCollection.substring(0,urlAddressCollection.length() - 1);
			urlTitleCollection = urlTitleCollection.substring(0,urlTitleCollection.length() - 1);
			urlIdCollection = urlIdCollection.substring(0, urlIdCollection.length() - 1);
			urlAddDateCollection = urlAddDateCollection.substring(0,urlAddDateCollection.length() - 1);
			urlOwnerCollection = urlOwnerCollection.substring(0,urlOwnerCollection.length() - 1);
			urlOwnerIdCollection = urlOwnerIdCollection.substring(0,urlOwnerIdCollection.length() - 1);
			urlFeaturesCollection = urlFeaturesCollection.substring(0,urlFeaturesCollection.length()-1);
			urlScoresCollection = urlScoresCollection.substring(0,urlScoresCollection.length() - 1);
			urlShareIdCollection = urlShareIdCollection.substring(0,urlShareIdCollection.length() - 1);
			urlShareIdInfoIdCollection = urlShareIdInfoIdCollection.substring(0,urlShareIdInfoIdCollection.length() - 1);

			// 生成String集合
			String[] urlAddressGroup = urlAddressCollection.split("\\|");
			String[] urlTitleGroup = urlTitleCollection.split("\\|");
			String[] urlIdGroup = urlIdCollection.split("\\|");
			String[] urlAddDateGroup = urlAddDateCollection.split("\\|");
			String[] urlOwnerGroup = urlOwnerCollection.split("\\|");
			String[] urlOwnerIdGroup = urlOwnerIdCollection.split("\\|");
			String[] urlFeaturesGroup = urlFeaturesCollection.split("\\|");
			String[] urlScoresGroup = urlScoresCollection.split("\\|");
			String[] urlShareIdGroup = urlShareIdCollection.split("\\|");
			String[] urlShareIdInfoIdGroup = urlShareIdInfoIdCollection.split("\\|");

			int urlAddressGroupL = urlAddressGroup.length;
	
            //判断截取后的字符是否符合规格
			List<com.url.bean.Url> urlList = new ArrayList<com.url.bean.Url>();
			for(int counter = 0; counter < urlAddressGroupL;counter++){
				com.url.bean.Url url = new com.url.bean.Url();

				url.setUrl(urlAddressGroup[counter]);
				url.setUrlTitle(urlTitleGroup[counter]);
				url.setUrlId(Integer.parseInt(urlIdGroup[counter]));
				url.setAddDate(urlAddDateGroup[counter]);
				url.setUrlOwner(urlOwnerGroup[counter]);
				url.setUrlOwnerId(Integer.parseInt(urlOwnerIdGroup[counter]));
				url.setFeatureOne(urlFeaturesGroup[counter]);
				url.setScoreOne(urlScoresGroup[counter]);
				url.setShareId(Integer.parseInt(urlShareIdGroup[counter]));
				url.setShareIdInfoId(Integer.parseInt(urlShareIdInfoIdGroup[counter]));
				urlList.add(url);
			}

            CreateIndex createIndex = new CreateIndex();
            createIndex.createIndex(urlList);

            
			return "上传并生成索引成功!";
		} else {
			return "账号或者密码错误";
		}
	}
	 /**
     * 添加url记录
     * @param urlTitle
     * @param urlOwner
     * @param urlOwnerId
     * @param urlAddDate
     * @param urlType
     * @param url
     * @return
     *    
     */
    public String addUrl(String url, String urlTitle, String urlOwner, int urlOwnerId, String urlAddDate, int urlType,String urlTag){
    	com.url.bean.Url newUrl = new com.url.bean.Url();
    	
    	Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String requestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    	
        newUrl.setAddDate(requestTime);
	    newUrl.setUrl(url);
	    newUrl.setUrlTitle(urlTitle);
	    newUrl.setUrlOwner(urlOwner);
	    newUrl.setUrlOwnerId(urlOwnerId);
	    newUrl.setUrlType(urlType);
	    newUrl.setUrlTag(urlTag);
	  
	    ApplicationContext oAC = new ClassPathXmlApplicationContext("applicationContext.xml");
	    urlService = (UrlService) oAC.getBean("UrlService");
	    int result = urlService.addUrl(newUrl);
	    
	    String returnInfo = "";
	    if(result >= 1){
	    	returnInfo = "success";
	    }else{
	    	returnInfo = "fail";
	    }
    	return returnInfo;
    }
    /**
     * 登录
     * @param username 用户账号
     * @param password 用户密码
     * @return 返回登录结果
     *     
     */
    public String login(String username, String password){
    	ApplicationContext oAC = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService) oAC.getBean("UserService");
		User user = userService.getUserByNamePwd(username, password);
		
		String result = "";
		if(user != null){
			return user.getNickName()+"|"+user.getUserId();
		}else{
			result = "fail";
		}
    	return result;
    }
}
