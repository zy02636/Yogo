package com.url.service;

import com.url.bean.Url;

import java.util.List;

public interface IUrlService {
	/*
	 * ����һ��Url��¼,����Ϊ��װ�õ�Url����
	 */
    public int addUrl(Url url);
    /**
     * ����Url 
     * @param url ���ְ�װ��url
     * @return
     */
    public int updateUrl(Url url);
    /*
     * ��ȡĿ���û�������Url,����Ϊ�û�Id
     */
    public List<Url> getUrlsByUserId(int userId,int pageNum,int queryType);
    
    /*
     * ��ȡĿ���û��ĺ��ѵ�����Url,����Ϊ�û�Id
     */
    public List<Url> getGuestUrlsByUserId(int userId,int pageNum);
    /*
     * ��ȡ��Ӧ�����û�������Url��¼,����Ϊ�û���Id�Լ�ҳ��
     */
    public List<Url> getUrlsByUserIdAndIndex(int userId,int index);
    /*
     * ɾ��һ��Url��¼�Ľӿڷ���,����Ϊ��ӦUrl��ID
     */
    public int deleteUrl(int urlId);
    /**
     * ����url
     * @param shareId ����ID
     * @param url ���ְ�װ�õ�url
     * @return
     */
    public int[] shareUrl(int shareId,Url url);
	 /**
     * �����û�ID��ѯ�û��������ǩ��Ŀ
     * @param userId
     * @return
     */
    public int getUrlSumByUserId(int userId);
    /**
     * ��ȡ��ע�û�������url��Ŀ
     * @param userId
     * @return
     */
    public int getFollowsUrlSum(int userId);
    
    /**
     * ����tagId��ȡUrl
     * @param tagId 
     * @param pageNum
     * @param userId ���û���ѯ�Լ�����ǩʱ ֵ��Ҫ���� 0
     * @return
     */
    public List<Url> getUrlByTagId(int tagId, int pageNum,int userId);
    /**
     * ��ȡ��Ӧ��ǩ����ǩ����
     * @param tagId
     * @return
     */
    public int getUrlByTagIdSum(int tagId);
    /**
     * ����urlId ��ѯ�涨��С��url��
     * @param groundCount
     * @param startUrlId
     * @return
     */
    public List<com.url.ws.bean.Url> getUrlGroup(int groupCount,String username);
    /**
     * url ���������һ��url��shareId
     * @param urlId
     * @return
     */
    public int updateShareState(int urlId);
    /**
     * ���ϴ�������url��Ϣ�ɹ���,����shareId״̬
     * @param id shareId���identitier
     * @return
     */
    public int updateShareIdInfo(int id);
}
