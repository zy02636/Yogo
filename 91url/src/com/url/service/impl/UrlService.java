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
     * 增加一条Url记录,参数为包装好的Url对象 (non-Javadoc)
     * @see com.url.service.IUrlService#addUrl(com.url.bean.Url)
     */
	public int addUrl(Url url) {
		//返回的为urlId
		int urlId = urlDAO.addUrl(url);
		//防止添加失败后继续添加
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
	 * 获取目标用户的所有Url,参数为用户Id (non-Javadoc)
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
			//判断是否今年
			if(curYear.equals(year)){
				String times = orDate.substring(11,16);
				//判断是否今天 
				if(curDate.equalsIgnoreCase(orDate.substring(5,10))){
					url.setAddDate("今天   "+times);
				}else{
					String month = orDate.substring(5,7).replace("0","");
					String day = orDate.substring(8,10).replace("0","");
					url.setAddDate(month+"月"+day+"日  "+times);
				}
			}else{
				url.setAddDate(orDate.substring(0,10));
			}
			//查询url是否已被索引化
			int indexed = urlDAO.getShareState(url.getShareId());
			url.setIndexed(indexed);
		}
		return urls;
	}
	/*
	 * 获取目标用户好友的的所有公开Url,参数为用户Id
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
			//判断是否今年
			if(curYear.equals(year)){
				String times = orDate.substring(11,16);
				//判断是否今天 
				if(curDate.equalsIgnoreCase(orDate.substring(5,10))){
					url.setAddDate("今天   "+times);
				}else{
					String month = orDate.substring(5,7).replace("0","");
					String day = orDate.substring(8,10).replace("0","");
					url.setAddDate(month+"月"+day+"日  "+times);
				}
			}else{
				url.setAddDate(orDate.substring(0,10));
			}
			//查询url是否已被索引化
			int indexed = urlDAO.getShareState(url.getShareId());
			url.setIndexed(indexed);
		}
		return urls;
	}
	
	/*
	 * 获取目标用户的所有Url,参数为用户Id 以及分页页数
	 */
	public List<Url> getUrlsByUserIdAndIndex(int userId,int index){
		List<Url> urls = urlDAO.getUrlsByUserIdAndIndex(userId,index);
		return urls;
	}
	
	/**
	 * 分享url
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
     * 根据用户ID查询用户分享的书签数目
     * @param userId
     * @return
     */
    public int getUrlSumByUserId(int userId){
    	int sum = urlDAO.getUrlSumByUserId(userId);
    	return sum;
    }
    
    /**
     * 获取关注用户的所发url数目
     * @param userId
     * @return
     */
    public int getFollowsUrlSum(int userId){
    	int sum = urlDAO.getFollowsUrlSum(userId);
    	return sum;
    }
    
    /**
     * 根据tagId获取Url
     * @param tagId 
     * @param pageNum
     * @param userId 当用户查询自己的书签时 值需要大于 0
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
			//判断是否今年
			if(curYear.equals(year)){
				String times = orDate.substring(11,16);
				//判断是否今天 
				if(curDate.equalsIgnoreCase(orDate.substring(5,10))){
					url.setAddDate("今天   "+times);
				}else{
					String month = orDate.substring(5,7).replace("0","");
					String day = orDate.substring(8,10).replace("0","");
					url.setAddDate(month+"月"+day+"日  "+times);
				}
			}else{
				url.setAddDate(orDate.substring(0,10));
			}
			//查询url是否已被索引化
			int indexed = urlDAO.getShareState(url.getShareId());
			url.setIndexed(indexed);
		}
		return urls;
    }
    /**
     * 获取对应标签的书签数量
     * @param tagId
     * @return
     */
    public int getUrlByTagIdSum(int tagId){
    	int sum = urlDAO.getUrlByTagIdSum(tagId);
    	return sum;
    }
    /**
     * 根据urlId 查询规定大小的url组
     * @param groundCount 一次要获取的url数目
     * @return
     */
    public List<com.url.ws.bean.Url> getUrlGroup(int groupCount,String username){
    	int[] shareIdInfo = urlDAO.getLastShareIdInfo();
    	int firstShareId = shareIdInfo[0] + shareIdInfo[1];
    	
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String requestTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    	//这里需要做一下修改,因为请求的groupCount很有可能大于数据库中未处理的
		//Url,所以这里得先确保这次请求开始的shareId 至最后一条记录(groupCount),必须做到一致性
		//所以每一次的客户端请求时,在获取到返回的记录时应当更新url_ShareId里面的groupCount
    	int shareIdInfoId = urlDAO.addShareIdInfo(firstShareId, groupCount, username, requestTime);
    	
    	List<com.url.ws.bean.Url> urls = null;
    	if(shareIdInfoId >= 1){
        	urls = urlDAO.getUrlGroupByStartUrlId(groupCount,firstShareId,shareIdInfoId);
    	}
    	return urls;
    }
    /**
     * url 组里面最后一条url的shareId
     * @param urlId
     * @return
     */
    public int updateShareState(int lastShareId){
    	int result = urlDAO.updateShareState(lastShareId);
    	return result;
    }
    /**
     * 当上传处理后的url信息成功后,更新shareId状态
     * @param id shareId表的identitier
     * @return
     */
    public int updateShareIdInfo(int id){
    	int result =  urlDAO.updateShareIdInfo(id);
    	return result;
    }
}
