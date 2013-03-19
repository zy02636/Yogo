package com.url.service.impl;

import java.util.List;

import com.url.service.IUserService;
import com.url.bean.User;
import com.url.bean.UserTag;
import com.url.dao.IUserDAO;
import com.url.dao.IUserTagDAO;

public class UserService implements IUserService {
    public IUserDAO userDAO;
    private IUserTagDAO userTagDAO;
	public void setUserTagDAO(IUserTagDAO userTagDAO) {
		this.userTagDAO = userTagDAO;
	}
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public int addUser(User user) {
		int result = userDAO.addUser(user);
		return result;
	}

	public int deleteUser(int userId) {
		int result = userDAO.deleteUser(userId);
		return result;
	}
    
	public User getUser(int userId){
		User user = userDAO.getUserByUserId(userId);
		return user;
	}
	
	public User getUserByNamePwd(String username,String pwd) {
		User user = userDAO.getUserByNamePwd(username,pwd);
		return user;
	}

	public int updateUser(User updatedUser) {
		int result = userDAO.updateUser(updatedUser);
		return result;
	}
	public boolean userNameExists(String username){
		boolean result = userDAO.checkUserName(username);
		return result;
	} 
    /**
     * �����û�ID��ѯfollow���û�����
     * @param ownerId ����ѯ���û�ID
     * @param viewerId ��ѯ���û�ID
     * @return
     */
    public List<User> getFans(int ownerId,int viewerId,int pageNum){
    	List<User> users = userDAO.getFansByUserId(ownerId,pageNum);
    	/*
    	 * ���ѭ�������������˿����ʾ����,�û��Լ������Լ��ķ�˿�б�ʱ
    	 * ����Ҫ��relationtype�����ٴμ���,��������û�����ʱ��Ҫ��relationType
    	 * �����ٴε��ж�,��Ȼ��ʱ���relationTypeֻ�ǳ��������޸�,���ݿ������relationTypeֵ����Ҫ
    	 * ���ݾ���Ĳ�����ʵ��
    	 */
    	if(ownerId != viewerId){
	    	for(User user : users){
        		boolean result = userDAO.checkRelation(user.getUserId(), viewerId);
        		if(result){
        			//viewer�û��Ѿ� ��ע���û�
        			user.setRelationType(1);
        		}else{
        			user.setRelationType(0);
        		}
        		//���۲�ķ�˿�����û��Լ�
        		if(user.getUserId() == viewerId){
        			user.setRelationType(1);
        		}
	        }
    	}
    	return users;
    }
    
    /**
     * �����û�ID��ѯ���û�����ע����
     * @param ownerId ����ѯ���û�ID
     * @param viewerId ��ѯ���û�ID
     * @return
     */
    public List<User> getFollowers(int ownerId,int viewerId,int pageNum){
    	List<User> users = userDAO.getFollowersByUserId(ownerId,pageNum);
    	/*
    	 * ���ѭ�������������˿����ʾ����,�û��Լ������Լ��ķ�˿�б�ʱ
    	 * ����Ҫ��relationtype�����ٴμ���,��������û�����ʱ��Ҫ��relationType
    	 * �����ٴε��ж�,��Ȼ��ʱ���relationTypeֻ�ǳ��������޸�,���ݿ������relationTypeֵ����Ҫ
    	 * ���ݾ���Ĳ�����ʵ��
    	 */
    	if(ownerId != viewerId){
	    	for(User user : users){
        		boolean result = userDAO.checkRelation(user.getUserId(), viewerId);
        		if(result){
        			//viewer�û��Ѿ� ��ע���û�
        			user.setRelationType(1);
        		}else{
        			user.setRelationType(0);
        		}
        		//���۲�ķ�˿�����û��Լ�
        		if(user.getUserId() == viewerId){
        			user.setRelationType(1);
        		}
	        }
    	}
    	return users;
    }
    /**
     * �����û�ID��ѯ��ע���û���������
     * @param userId
     * @return
     */
    public int getFansSum(int userId){
    	int sum = userDAO.getFansSum(userId);
    	return sum;
    }
    
    /**
     * �����û�ID��ѯ���û���ע���˵�����
     * @param userId
     * @return
     */
    public int getFollowersSum(int userId){
    	int sum = userDAO.getFollowersSum(userId);
    	return sum;
    }
    /**
     * �Ƴ���˿
     * @param userId �û�Id
     * @param guestId ��˿ID
     * @return
     */
    public int removeFan(int userId,int guestId){
    	int result = userDAO.removeFan(userId, guestId);
    	return result;
    }
    
    /**
     * �Ƴ���ע
     * @param userId �û�Id
     * @param hostId ��follow�û�ID
     * @return
     */
    public int removeFollow(int userId,int hostId){
    	int result = userDAO.removeFollow(userId, hostId);
    	return result;
    }
    /**
     * ��ӹ�ע
     * @param userId �û�Id
     * @param hostId ����ע�û�ID
     * @return
     */
    public int addFollow(int userId,int hostId){
    	int result = userDAO.addFollow(userId, hostId);
    	return result;
    }
    /**
     * ��ѯ�Ƿ���ڹ�ע��ϵ
     * @param hostId ��������ע���û�ID
     * @param guestId userId
     * @return
     */
    public boolean checkRelation(int hostId,int guestId){
    	boolean result = userDAO.checkRelation(hostId, guestId);
    	return result;
    }
    /**
     * ����tagId ��ȡurl�б�
     * @param tagId
     * @return
     */
    public List<User> getUserByTagId(int tagId, int pageNum,int viewerId){
    	List<User> users = userDAO.getUserByTagId(tagId, pageNum);
    	for(User user : users){
    		int userId = user.getUserId();
    		List<UserTag> userTags = userTagDAO.getUserTagsByUserId(userId);
    		user.setUserTags(userTags);

    		boolean result = userDAO.checkRelation(user.getUserId(), viewerId);
    		if(result){
    			//viewer�û��Ѿ� ��ע���û�
    			user.setRelationType(1);
    		}else{
    			//viewer�û�δ��ע���û�
    			user.setRelationType(0);
    		}
    		//���۲�ķ�˿�����û��Լ�
    		if(user.getUserId() == viewerId){
    			user.setRelationType(1);
    		}
    	}
    	return users;
    }
    /**
     * ��ȡ��Ӧ��ǩ����ǩ����
     * @param tagId
     * @return
     */
    public int getUserByTagIdSum(int tagId){
    	int sum = userDAO.getUserByTagIdSum(tagId);
    	return sum;
    }
    /**
     * ����������û��˺�,�������һ�.
     * @param username �û�������
     * @return
     */
    public String getPwdByUserName(String username){
    	String result = userDAO.getPwdByUserName(username);
    	return result;
    }
    /**
     * ��������
     * @param username
     * @param exPwd
     * @param newPwd
     * @return
     */
    public int resetPwd(String username,String newPwd){
    	int result = userDAO.resetPwd(username, newPwd);
    	return result;
    }
}
