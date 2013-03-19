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
     * �������������ı�ǩ��ǰ׺��ѯuserTag
     * @param userId
     * @return
     */
    public List<UserTag> getUserTagsByTagPrefix(String tagPrefix){
    	List<UserTag> userTags = userTagDAO.getUserTagsByTagPrefix(tagPrefix);
		return userTags;
    }
    /**
     * ���ݱ�ǩID,�û�IDɾ����Ӧ�û���ǩ
     * @param userId
     * @return
     */
    public int deleteUserTagByUserId(int userTagId,int userId){
    	return userTagDAO.deleteUserTagByUserId(userTagId, userId);
    }
    /**
     * �����û���ǩID��ȡ��ǩ��
     * @param userTagId
     * @return
     */
    public String getUserTagNameById(int userTagId){
    	String tagName = userTagDAO.getUserTagNameById(userTagId);
    	return tagName;
    }
}
