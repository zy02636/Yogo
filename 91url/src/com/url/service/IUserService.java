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
     * �����û�ID��ѯfollow���û�����
     * @param userId �û�ID
     * @return
     */
    public List<User> getFans(int ownerId,int viewerId,int pageNum);
    
    /**
     * �����û�ID��ѯ���û�����ע����
     * @param userId �û�ID
     * @return
     */
    public List<User> getFollowers(int ownerId,int viewerId,int pageNum);
    /**
     * �����û�ID��ѯ��ע���û���������
     * @param userId
     * @return
     */
    public int getFansSum(int userId);
    
    /**
     * �����û�ID��ѯ���û���ע���˵�����
     * @param userId
     * @return
     */
    public int getFollowersSum(int userId);
    /**
     * �Ƴ���˿
     * @param userId �û�Id
     * @param guestId ��˿ID
     * @return
     */
    public int removeFan(int userId,int guestId);
    
    /**
     * �Ƴ���ע
     * @param userId �û�Id
     * @param hostId ��follow�û�ID
     * @return
     */
    public int removeFollow(int userId,int hostId);
    /**
     * ��ӹ�ע
     * @param userId �û�Id
     * @param hostId ����ע�û�ID
     * @return
     */
    public int addFollow(int userId,int hostId);
    /**
     * ��ѯ�Ƿ���ڹ�ע��ϵ
     * @param hostId ��������ע���û�ID
     * @param guestId userId
     * @return
     */
    public boolean checkRelation(int hostId,int guestId);
    /**
     * ����tagId ��ȡurl�б�
     * @param tagId
     * @return
     */
    public List<User> getUserByTagId(int tagId, int pageNum,int viewerId);
    /**
     * ��ȡ��Ӧ��ǩ����ǩ����
     * @param tagId
     * @return
     */
    public int getUserByTagIdSum(int tagId);
    /**
     * ����������û��˺�,�������һ�.
     * @param username �û�������
     * @return
     */
    public String getPwdByUserName(String username);
    /**
     * ��������
     * @param username
     * @param exPwd
     * @param newPwd
     * @return
     */
    public int resetPwd(String username,String newPwd);
}
