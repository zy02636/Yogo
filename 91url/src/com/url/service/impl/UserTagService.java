package com.url.service.impl;

import java.util.List;

import com.url.bean.UserTag;
import com.url.dao.IUserTagDAO;
import com.url.service.IUserTagService;

public class UserTagService implements IUserTagService {
    private IUserTagDAO userTagDAO;
	public void setUserTagDAO(IUserTagDAO userTagDAO) {
		this.userTagDAO = userTagDAO;
	}
	public int[] addUserTag(UserTag userTag){
		return userTagDAO.addUserTag(userTag);
	}
	@Override
	public List<UserTag> getUserTagsByUserId(int userId) {
		List<UserTag> userTags = userTagDAO.getUserTagsByUserId(userId);
		return userTags;
	}
    /**
     * 根据自我描述的标签的前缀查询userTag
     * @param userId
     * @return
     */
    public List<UserTag> getUserTagsByTagPrefix(String tagPrefix){
    	List<UserTag> userTags = userTagDAO.getUserTagsByTagPrefix(tagPrefix);
		return userTags;
    }
    /**
     * 根据标签ID,用户ID删除对应用户标签
     * @param userId
     * @return
     */
    public int deleteUserTagByUserId(int userTagId,int userId){
    	return userTagDAO.deleteUserTagByUserId(userTagId, userId);
    }
    /**
     * 根据用户标签ID获取标签名
     * @param userTagId
     * @return
     */
    public String getUserTagNameById(int userTagId){
    	String tagName = userTagDAO.getUserTagNameById(userTagId);
    	return tagName;
    }
}
