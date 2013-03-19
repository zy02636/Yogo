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
     * 根据用户ID查询follow该用户的人
     * @param ownerId 被查询的用户ID
     * @param viewerId 查询的用户ID
     * @return
     */
    public List<User> getFans(int ownerId,int viewerId,int pageNum){
    	List<User> users = userDAO.getFansByUserId(ownerId,pageNum);
    	/*
    	 * 这个循环是用来处理粉丝的显示问题,用户自己访问自己的粉丝列表时
    	 * 不需要对relationtype进行再次检验,而当别的用户访问时需要对relationType
    	 * 进行再次的判断,当然这时候的relationType只是程序里面修改,数据库里面的relationType值还是要
    	 * 根据具体的操作来实现
    	 */
    	if(ownerId != viewerId){
	    	for(User user : users){
        		boolean result = userDAO.checkRelation(user.getUserId(), viewerId);
        		if(result){
        			//viewer用户已经 关注该用户
        			user.setRelationType(1);
        		}else{
        			user.setRelationType(0);
        		}
        		//被观察的粉丝中有用户自己
        		if(user.getUserId() == viewerId){
        			user.setRelationType(1);
        		}
	        }
    	}
    	return users;
    }
    
    /**
     * 根据用户ID查询该用户所关注的人
     * @param ownerId 被查询的用户ID
     * @param viewerId 查询的用户ID
     * @return
     */
    public List<User> getFollowers(int ownerId,int viewerId,int pageNum){
    	List<User> users = userDAO.getFollowersByUserId(ownerId,pageNum);
    	/*
    	 * 这个循环是用来处理粉丝的显示问题,用户自己访问自己的粉丝列表时
    	 * 不需要对relationtype进行再次检验,而当别的用户访问时需要对relationType
    	 * 进行再次的判断,当然这时候的relationType只是程序里面修改,数据库里面的relationType值还是要
    	 * 根据具体的操作来实现
    	 */
    	if(ownerId != viewerId){
	    	for(User user : users){
        		boolean result = userDAO.checkRelation(user.getUserId(), viewerId);
        		if(result){
        			//viewer用户已经 关注该用户
        			user.setRelationType(1);
        		}else{
        			user.setRelationType(0);
        		}
        		//被观察的粉丝中有用户自己
        		if(user.getUserId() == viewerId){
        			user.setRelationType(1);
        		}
	        }
    	}
    	return users;
    }
    /**
     * 根据用户ID查询关注该用户的人总数
     * @param userId
     * @return
     */
    public int getFansSum(int userId){
    	int sum = userDAO.getFansSum(userId);
    	return sum;
    }
    
    /**
     * 根据用户ID查询该用户关注的人的总数
     * @param userId
     * @return
     */
    public int getFollowersSum(int userId){
    	int sum = userDAO.getFollowersSum(userId);
    	return sum;
    }
    /**
     * 移除粉丝
     * @param userId 用户Id
     * @param guestId 粉丝ID
     * @return
     */
    public int removeFan(int userId,int guestId){
    	int result = userDAO.removeFan(userId, guestId);
    	return result;
    }
    
    /**
     * 移除关注
     * @param userId 用户Id
     * @param hostId 被follow用户ID
     * @return
     */
    public int removeFollow(int userId,int hostId){
    	int result = userDAO.removeFollow(userId, hostId);
    	return result;
    }
    /**
     * 添加关注
     * @param userId 用户Id
     * @param hostId 被关注用户ID
     * @return
     */
    public int addFollow(int userId,int hostId){
    	int result = userDAO.addFollow(userId, hostId);
    	return result;
    }
    /**
     * 查询是否存在关注关系
     * @param hostId 即将被关注的用户ID
     * @param guestId userId
     * @return
     */
    public boolean checkRelation(int hostId,int guestId){
    	boolean result = userDAO.checkRelation(hostId, guestId);
    	return result;
    }
    /**
     * 根据tagId 获取url列表
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
    			//viewer用户已经 关注该用户
    			user.setRelationType(1);
    		}else{
    			//viewer用户未关注该用户
    			user.setRelationType(0);
    		}
    		//被观察的粉丝中有用户自己
    		if(user.getUserId() == viewerId){
    			user.setRelationType(1);
    		}
    	}
    	return users;
    }
    /**
     * 获取对应标签的书签数量
     * @param tagId
     * @return
     */
    public int getUserByTagIdSum(int tagId){
    	int sum = userDAO.getUserByTagIdSum(tagId);
    	return sum;
    }
    /**
     * 根据输入的用户账号,将密码找回.
     * @param username 用户的邮箱
     * @return
     */
    public String getPwdByUserName(String username){
    	String result = userDAO.getPwdByUserName(username);
    	return result;
    }
    /**
     * 更改密码
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
