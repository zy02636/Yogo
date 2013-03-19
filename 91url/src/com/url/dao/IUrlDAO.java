package com.url.dao;
import java.util.HashMap;
import java.util.List;

import com.url.bean.Url;

public interface IUrlDAO {
	/*
	 * ����һ��Url��¼�Ľӿڷ���,����Ϊ��װ�ú��com.url.bean.Url����
	 */
    public int addUrl(Url url);
    /*
     * ɾ��һ��Url��¼�Ľӿڷ���,����Ϊ��ӦUrl��ID
     */
    public int deleteUrl(int urlId);
    /*
     * ����һ��Url��¼�Ľӿڷ���,����Ϊ��װ�ú��com.url.bean.Url����
     */
    public int updateUrl(Url updateUrl);
    /*
     * ��ȡ��Ӧ�����û�������Url��¼,����Ϊ�û���Id
     */
    public List<Url> getUrlsByUserId(int userId,int pageNum,int queryType);
    /*
     * ��ȡ��Ӧ�����û������к��ѵ�Url��¼,����Ϊ�û���Id
     */
    public List<Url> getGuestUrlsByUserId(int userId,int pageNum);
    /*
     * ��ȡ��Ӧ�����û�������Url��¼,����Ϊ�û���Id�Լ�ҳ��
     */
    public List<Url> getUrlsByUserIdAndIndex(int userId,int index);
    
    /*
     * ���ݷ���ID ���¶�Ӧ��url�ķ�����Ŀ
     */
    public int[] shareUrl(int shareId, Url url);
    
    /**
     * ��ȡurl1�ķ�����Ŀ
     * @param shareId ����ID
     * @return
     */
    public HashMap<String,Object> getUrlShareSum(int shareId);
    
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
    public List<Url> getUrlByTagId(int tagId, int pageNum, int userId);
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
     * @param shareIdInfoId
     * @return
     */
    public List<com.url.ws.bean.Url> getUrlGroupByStartUrlId(int groupCount,int firstShareId,int shareIdInfoId);
    /**
     * url ���������һ��url��shareId
     * @param urlId
     * @return
     */
    public int updateShareState(int urlId);
    /**
     * ��ȡshare��indexed ״̬
     * @param shareId
     * @return
     */
    public int getShareState(int shareId);
    /**
     * ÿ����һ������ͨ��Web Service���ʻ�ȡ��Ϣʱ
     * ������Ӧ�ļ�¼.
     * @param requestUser ������û�
     * @param requestTime �����ʱ��
     * @return
     */
    public int addShareIdInfo(int firstShareId, int groupSum,String requestUser,String requestTime);
    /**
     * ���ϴ�������url��Ϣ�ɹ���,����shareId״̬
     * @param id shareId���identitier
     * @return
     */
    public int updateShareIdInfo(int id);
    /**
     * ��ȡ����һ���û�ͨ��web service��ȡurl��Ϣ�ļ�¼
     * @return ��������һ����¼��shareIdֵ��groupCount��ֵ,���ڼ���
     * ��ǰ�����û�Ӧ�ôӶ���shareId��ʼȡ�ü�¼
     */
    public int[] getLastShareIdInfo();
}
