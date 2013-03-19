package com.url.service;

import com.url.bean.Url;

import java.util.List;

public interface IUrlService {
	/*
	 * 增加一条Url记录,参数为包装好的Url对象
	 */
    public int addUrl(Url url);
    /**
     * 更新Url 
     * @param url 部分包装的url
     * @return
     */
    public int updateUrl(Url url);
    /*
     * 获取目标用户的所有Url,参数为用户Id
     */
    public List<Url> getUrlsByUserId(int userId,int pageNum,int queryType);
    
    /*
     * 获取目标用户的好友的所有Url,参数为用户Id
     */
    public List<Url> getGuestUrlsByUserId(int userId,int pageNum);
    /*
     * 获取对应单个用户的所有Url记录,参数为用户的Id以及页码
     */
    public List<Url> getUrlsByUserIdAndIndex(int userId,int index);
    /*
     * 删除一条Url记录的接口方法,参数为对应Url的ID
     */
    public int deleteUrl(int urlId);
    /**
     * 分享url
     * @param shareId 分享ID
     * @param url 部分包装好的url
     * @return
     */
    public int[] shareUrl(int shareId,Url url);
	 /**
     * 根据用户ID查询用户分享的书签数目
     * @param userId
     * @return
     */
    public int getUrlSumByUserId(int userId);
    /**
     * 获取关注用户的所发url数目
     * @param userId
     * @return
     */
    public int getFollowsUrlSum(int userId);
    
    /**
     * 根据tagId获取Url
     * @param tagId 
     * @param pageNum
     * @param userId 当用户查询自己的书签时 值需要大于 0
     * @return
     */
    public List<Url> getUrlByTagId(int tagId, int pageNum,int userId);
    /**
     * 获取对应标签的书签数量
     * @param tagId
     * @return
     */
    public int getUrlByTagIdSum(int tagId);
    /**
     * 根据urlId 查询规定大小的url组
     * @param groundCount
     * @param startUrlId
     * @return
     */
    public List<com.url.ws.bean.Url> getUrlGroup(int groupCount,String username);
    /**
     * url 组里面最后一条url的shareId
     * @param urlId
     * @return
     */
    public int updateShareState(int urlId);
    /**
     * 当上传处理后的url信息成功后,更新shareId状态
     * @param id shareId表的identitier
     * @return
     */
    public int updateShareIdInfo(int id);
}
