package com.url.service;

import java.util.List;

import com.url.bean.Url;
import com.url.bean.User;

public interface IUserService {
    public int addUser(User user);
    public int deleteUser(int userId);
    public User getUser(int userId);
    public User getUserByNamePwd(String username,String pwd);
    public int updateUser(User user);
    public boolean userNameExists(String username);
    /**
     * 根据用户ID查询follow该用户的人
     * @param userId 用户ID
     * @return
     */
    public List<User> getFans(int ownerId,int viewerId,int pageNum);
    
    /**
     * 根据用户ID查询该用户所关注的人
     * @param userId 用户ID
     * @return
     */
    public List<User> getFollowers(int ownerId,int viewerId,int pageNum);
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
     * @param hostId 被关注用户ID
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
    public List<User> getUserByTagId(int tagId, int pageNum,int viewerId);
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
