package com.url.dao;

import java.util.List;

import com.url.bean.User;

public interface IUserDAO {
	/*
	 * 增加一条用户信息,参数为包装好后的com.url.bean.User对象
	 */
    public int addUser(User user);
    /*
     * 删除一条用户信息,参数为对应的用户Id
     */
    public int deleteUser(int userId);
    /*
     * 更新一条用户信息,参数为包装好后的com.url.bean.User对象
     */
    public int updateUser(User updatedUser);
    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    public User getUserByUserId(int userId);
    /*
     * 查询用户信息,参数为对应用的账号跟密码
     */
    public User getUserByNamePwd(String userName,String pwd);
    /*
     * 获取所有用户信息
     */
    public List<User> getUserListByTagId(int tagId,String tagType);
    /*
     * 验证用户是否存在,参数为包装好后的com.url.bean.User对象
     */
    public boolean checkUserName(String username);
    /**
     * 根据用户ID查询follow该用户的人
     * @param userId 用户ID
     * @return
     */
    public List<User> getFansByUserId(int userId,int pageNum);
    
    /**
     * 根据用户ID查询该用户所关注的人
     * @param userId 用户ID
     * @return
     */
    public List<User> getFollowersByUserId(int userId,int pageNum);
    /**
     * 根据用户ID查询关注该用户的人总数
     * @param userId
     * @return
     */
    public int getFansSum(int userId);
    
    /**
     * 根据用户ID查询该用户关注的人的总数
     * @param userId
     * @return
     */
    public int getFollowersSum(int userId);
    /**
     * 移除粉丝
     * @param userId 用户Id
     * @param guestId 粉丝ID
     * @return
     */
    public int removeFan(int userId,int guestId);
    
    /**
     * 移除关注
     * @param userId 用户Id
     * @param hostId 被follow用户ID
     * @return
     */
    public int removeFollow(int userId,int hostId);
    /**
     * 添加关注
     * @param userId 用户Id
     * @param hostId 被follow用户ID
     * @return
     */
    public int addFollow(int userId,int hostId);
    /**
     * 查询是否存在关注关系
     * @param hostId 即将被关注的用户ID
     * @param guestId userId
     * @return
     */
    public boolean checkRelation(int hostId,int guestId);
    /**
     * 根据tagId 获取url列表
     * @param tagId
     * @return
     */
    public List<User> getUserByTagId(int tagId, int pageNum);
    /**
     * 获取对应标签的书签数量
     * @param tagId
     * @return
     */
    public int getUserByTagIdSum(int tagId);
    /**
     * 根据输入的用户账号,将密码找回.
     * @param username 用户的邮箱
     * @return
     */
    public String getPwdByUserName(String username);
    /**
     * 更改密码
     * @param username
     * @param exPwd
     * @param newPwd
     * @return
     */
    public int resetPwd(String username,String newPwd);
}
