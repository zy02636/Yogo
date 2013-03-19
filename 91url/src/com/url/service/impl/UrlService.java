package com.url.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.url.bean.Tag;
import com.url.bean.Url;
import com.url.dao.ICommentDAO;
import com.url.dao.ITagDAO;
import com.url.dao.IUrlDAO;
import com.url.dao.IUserDAO;
import com.url.service.IUrlService;

public class UrlService implements IUrlService {
    private IUrlDAO urlDAO;
	public void setUrlDAO(IUrlDAO urlDAO) {
		this.urlDAO = urlDAO;
	}
    private ITagDAO tagDAO;
	public void setTagDAO(ITagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}
	private ICommentDAO commentDAO;
    public void setCommentDAO(ICommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}
	/*
     * ����һ��Url��¼,����Ϊ��װ�õ�Url���� (non-Javadoc)
     * @see com.url.service.IUrlService#addUrl(com.url.bean.Url)
     */
	public int addUrl(Url url) {
		//���ص�ΪurlId
		int urlId = urlDAO.addUrl(url);
		//��ֹ���ʧ�ܺ�������
		if(url.getUrlTag() != null && urlId >= 1){
			String[] tags = url.getUrlTag().split(" ");
		    for(String tagName : tags){
		    	tagName = tagName.replace(" " ,"");
		    	if(tagName.length() > 5)
		    	{
		    		tagName = tagName.substring(0,5);
		    	}else if((tagName.length() > 1) && (tagName.length() <=5)){
			    	Tag _tag = new Tag();
			    	_tag.setUrlId(urlId);
			    	_tag.setTagName(tagName);
			    	tagDAO.addTag(_tag);
		    	}
		    }
		}
		return urlId;
	}
	 public int updateUrl(Url url){
		 int result = urlDAO.updateUrl(url);
		 return result;
	 }
	/*
	 * ��ȡĿ���û�������Url,����Ϊ�û�Id (non-Javadoc)
	 */
	public List<Url> getUrlsByUserId(int userId,int pageNum,int queryType){
    	Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String curYear = new SimpleDateFormat("yyyy").format(time);
		String curDate = new SimpleDateFormat("MM-dd").format(time);
		
		List<Url> urls = urlDAO.getUrlsByUserId(userId,pageNum,queryType);
		for(Url url : urls){
			List<Tag> tags = tagDAO.getTagsByUrlId(url.getUrlId());
			url.setTags(tags);
			HashMap<String,Object> map = urlDAO.getUrlShareSum(url.getShareId());
			if(map != null){
				url.setShareSum((Integer)map.get("shareSum"));
				url.setFirstOwner((String)map.get("firstOwner"));
				url.setFirstOwnerId((Integer)map.get("firstOwnerId"));
			}
			int commentSum = commentDAO.getCommentSumByUrlId(url.getUrlId());
			url.setCommentSum(commentSum);
			
			String orDate = url.getAddDate();
			String year = orDate.substring(0,4);
			//�ж��Ƿ����
			if(curYear.equals(year)){
				String times = orDate.substring(11,16);
				//�ж��Ƿ���� 
				if(curDate.equalsIgnoreCase(orDate.substring(5,10))){
					url.setAddDate("����   "+times);
				}else{
					String month = orDate.substring(5,7).replace("0","");
					String day = orDate.substring(8,10).replace("0","");
					url.setAddDate(month+"��"+day+"��  "+times);
				}
			}else{
				url.setAddDate(orDate.substring(0,10));
			}
			//��ѯurl�Ƿ��ѱ�������
			int indexed = urlDAO.getShareState(url.getShareId());
			url.setIndexed(indexed);
		}
		return urls;
	}
	/*
	 * ��ȡĿ���û����ѵĵ����й���Url,����Ϊ�û�Id
	 */
	public List<Url> getGuestUrlsByUserId(int userId,int pageNum){
    	Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String curYear = new SimpleDateFormat("yyyy").format(time);
		String curDate = new SimpleDateFormat("MM-dd").format(time);

		List<Url> urls = urlDAO.getGuestUrlsByUserId(userId,pageNum);
		for(Url url : urls){
			List<Tag> tags = tagDAO.getTagsByUrlId(url.getUrlId());
			url.setTags(tags);

			HashMap<String,Object> map = urlDAO.getUrlShareSum(url.getShareId());
			if(map != null){
				url.setShareSum((Integer)map.get("shareSum"));
				url.setFirstOwner((String)map.get("firstOwner"));
				url.setFirstOwnerId((Integer)map.get("firstOwnerId"));
			}
			int commentSum = commentDAO.getCommentSumByUrlId(url.getUrlId());
			url.setCommentSum(commentSum);
			
			String orDate = url.getAddDate();
			String year = orDate.substring(0,4);
			//�ж��Ƿ����
			if(curYear.equals(year)){
				String times = orDate.substring(11,16);
				//�ж��Ƿ���� 
				if(curDate.equalsIgnoreCase(orDate.substring(5,10))){
					url.setAddDate("����   "+times);
				}else{
					String month = orDate.substring(5,7).replace("0","");
					String day = orDate.substring(8,10).replace("0","");
					url.setAddDate(month+"��"+day+"��  "+times);
				}
			}else{
				url.setAddDate(orDate.substring(0,10));
			}
			//��ѯurl�Ƿ��ѱ�������
			int indexed = urlDAO.getShareState(url.getShareId());
			url.setIndexed(indexed);
		}
		return urls;
	}
	
	/*
	 * ��ȡĿ���û�������Url,����Ϊ�û�Id �Լ���ҳҳ��
	 */
	public List<Url> getUrlsByUserIdAndIndex(int userId,int index){
		List<Url> urls = urlDAO.getUrlsByUserIdAndIndex(userId,index);
		return urls;
	}
	
	/**
	 * ����url
	 */
	public int[] shareUrl(int shareId,Url url){
		int[] result = urlDAO.shareUrl(shareId, url);
	    return result;
	}
	
	public int deleteUrl(int urlId){
		int result = urlDAO.deleteUrl(urlId);
		return result;
	}

	 /**
     * �����û�ID��ѯ�û��������ǩ��Ŀ
     * @param userId
     * @return
     */
    public int getUrlSumByUserId(int userId){
    	int sum = urlDAO.getUrlSumByUserId(userId);
    	return sum;
    }
    
    /**
     * ��ȡ��ע�û�������url��Ŀ
     * @param userId
     * @return
     */
    public int getFollowsUrlSum(int userId){
    	int sum = urlDAO.getFollowsUrlSum(userId);
    	return sum;
    }
    
    /**
     * ����tagId��ȡUrl
     * @param tagId 
     * @param pageNum
     * @param userId ���û���ѯ�Լ�����ǩʱ ֵ��Ҫ���� 0
     * @return
     */
    public List<Url> getUrlByTagId(int tagId, int pageNum,int userId){
    	Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String curYear = new SimpleDateFormat("yyyy").format(time);
		String curDate = new SimpleDateFormat("MM-dd").format(time);
		
    	List<Url> urls = urlDAO.getUrlByTagId(tagId, pageNum, userId);
    	for(Url url : urls){
			List<Tag> tags = tagDAO.getTagsByUrlId(url.getUrlId());
			url.setTags(tags);
			HashMap<String,Object> map = urlDAO.getUrlShareSum(url.getShareId());
			if(map != null){
				url.setShareSum((Integer)map.get("shareSum"));
				url.setFirstOwner((String)map.get("firstOwner"));
				url.setFirstOwnerId((Integer)map.get("firstOwnerId"));
			}
			int commentSum = commentDAO.getCommentSumByUrlId(url.getUrlId());
			url.setCommentSum(commentSum);
			
			String orDate = url.getAddDate();
			String year = orDate.substring(0,4);
			//�ж��Ƿ����
			if(curYear.equals(year)){
				String times = orDate.substring(11,16);
				//�ж��Ƿ���� 
				if(curDate.equalsIgnoreCase(orDate.substring(5,10))){
					url.setAddDate("����   "+times);
				}else{
					String month = orDate.substring(5,7).replace("0","");
					String day = orDate.substring(8,10).replace("0","");
					url.setAddDate(month+"��"+day+"��  "+times);
				}
			}else{
				url.setAddDate(orDate.substring(0,10));
			}
			//��ѯurl�Ƿ��ѱ�������
			int indexed = urlDAO.getShareState(url.getShareId());
			url.setIndexed(indexed);
		}
		return urls;
    }
    /**
     * ��ȡ��Ӧ��ǩ����ǩ����
     * @param tagId
     * @return
     */
    public int getUrlByTagIdSum(int tagId){
    	int sum = urlDAO.getUrlByTagIdSum(tagId);
    	return sum;
    }
    /**
     * ����urlId ��ѯ�涨��С��url��
     * @param groundCount һ��Ҫ��ȡ��url��Ŀ
     * @return
     */
    public List<com.url.ws.bean.Url> getUrlGroup(int groupCount,String username){
    	int[] shareIdInfo = urlDAO.getLastShareIdInfo();
    	int firstShareId = shareIdInfo[0] + shareIdInfo[1];
    	
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String requestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    	//������Ҫ��һ���޸�,��Ϊ�����groupCount���п��ܴ������ݿ���δ�����
		//Url,�����������ȷ���������ʼ��shareId �����һ����¼(groupCount),��������һ����
		//����ÿһ�εĿͻ�������ʱ,�ڻ�ȡ�����صļ�¼ʱӦ������url_ShareId�����groupCount
    	int shareIdInfoId = urlDAO.addShareIdInfo(firstShareId, groupCount, username, requestTime);
    	
    	List<com.url.ws.bean.Url> urls = null;
    	if(shareIdInfoId >= 1){
        	urls = urlDAO.getUrlGroupByStartUrlId(groupCount,firstShareId,shareIdInfoId);
    	}
    	return urls;
    }
    /**
     * url ���������һ��url��shareId
     * @param urlId
     * @return
     */
    public int updateShareState(int lastShareId){
    	int result = urlDAO.updateShareState(lastShareId);
    	return result;
    }
    /**
     * ���ϴ�������url��Ϣ�ɹ���,����shareId״̬
     * @param id shareId���identitier
     * @return
     */
    public int updateShareIdInfo(int id){
    	int result =  urlDAO.updateShareIdInfo(id);
    	return result;
    }
}
