package com.url.dao;

import java.util.List;

import com.url.bean.User;

public interface IUserDAO {
	/*
	 * ����һ���û���Ϣ,����Ϊ��װ�ú��com.url.bean.User����
	 */
    public int addUser(User user);
    /*
     * ɾ��һ���û���Ϣ,����Ϊ��Ӧ���û�Id
     */
    public int deleteUser(int userId);
    /*
     * ����һ���û���Ϣ,����Ϊ��װ�ú��com.url.bean.User����
     */
    public int updateUser(User updatedUser);
    /**
     * �����û�ID��ȡ�û���Ϣ
     * @param userId
     * @return
     */
    public User getUserByUserId(int userId);
    /*
     * ��ѯ�û���Ϣ,����Ϊ��Ӧ�õ��˺Ÿ�����
     */
    public User getUserByNamePwd(String userName,String pwd);
    /*
     * ��ȡ�����û���Ϣ
     */
    public List<User> getUserListByTagId(int tagId,String tagType);
    /*
     * ��֤�û��Ƿ����,����Ϊ��װ�ú��com.url.bean.User����
     */
    public boolean checkUserName(String username);
    /**
     * �����û�ID��ѯfollow���û�����
     * @param userId �û�ID
     * @return
     */
    public List<User> getFansByUserId(int userId,int pageNum);
    
    /**
     * �����û�ID��ѯ���û�����ע����
     * @param userId �û�ID
     * @return
     */
    public List<User> getFollowersByUserId(int userId,int pageNum);
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
     * @param hostId ��follow�û�ID
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
    public List<User> getUserByTagId(int tagId, int pageNum);
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
