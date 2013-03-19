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
	 * �����Ĳ�������WebService����
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
	 * ���ø÷���ĳ������Ĳ���
	 * 
	 * @param urls
	 *            ������url����
	 * @param username
	 *            ��Ȩ�������˺�
	 * @param password
	 *            ��Ȩ�������˺�����
	 * @return
	 */
	public String setUrl(UrlCollection urlCollection, String username,String password) {
		ApplicationContext oAC = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService) oAC.getBean("UserService");
		User user = userService.getUserByNamePwd(username, password);
		
		if ((user != null) && (user.getUserType() == 1)) { //Ҫ��userΪnull�Ļ�,��ô�Ͳ�����ڶ���������
			// ��ȡ�����ַ���,һ��Ǳ�ڵ�������,��һ����ҳ�޷���,��ȡ��������ô�����ҳ�Ϳ��ܵõ����������������
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
			
			//ȥ����ĩ����
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

			// ����String����
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
	
            //�жϽ�ȡ����ַ��Ƿ���Ϲ��
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

            
			return "�ϴ������������ɹ�!";
		} else {
			return "�˺Ż����������";
		}
	}
	 /**
     * ���url��¼
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
     * ��¼
     * @param username �û��˺�
     * @param password �û�����
     * @return ���ص�¼���
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
