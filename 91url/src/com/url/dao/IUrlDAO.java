package com.url.dao;
import java.util.HashMap;
import java.util.List;

import com.url.bean.Url;

public interface IUrlDAO {
	/*
	 * 增加一条Url记录的接口方法,参数为包装好后的com.url.bean.Url对象
	 */
    public int addUrl(Url url);
    /*
     * 删除一条Url记录的接口方法,参数为对应Url的ID
     */
    public int deleteUrl(int urlId);
    /*
     * 更新一条Url记录的接口方法,参数为包装好后的com.url.bean.Url对象
     */
    public int updateUrl(Url updateUrl);
    /*
     * 获取对应单个用户的所有Url记录,参数为用户的Id
     */
    public List<Url> getUrlsByUserId(int userId,int pageNum,int queryType);
    /*
     * 获取对应单个用户的所有好友的Url记录,参数为用户的Id
     */
    public List<Url> getGuestUrlsByUserId(int userId,int pageNum);
    /*
     * 获取对应单个用户的所有Url记录,参数为用户的Id以及页码
     */
    public List<Url> getUrlsByUserIdAndIndex(int userId,int index);
    
    /*
     * 根据分享ID 更新对应的url的分享数目
     */
    public int[] shareUrl(int shareId, Url url);
    
    /**
     * 获取url1的分享数目
     * @param shareId 分享ID
     * @return
     */
    public HashMap<String,Object> getUrlShareSum(int shareId);
    
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
    public List<Url> getUrlByTagId(int tagId, int pageNum, int userId);
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
     * @param shareIdInfoId
     * @return
     */
    public List<com.url.ws.bean.Url> getUrlGroupByStartUrlId(int groupCount,int firstShareId,int shareIdInfoId);
    /**
     * url 组里面最后一条url的shareId
     * @param urlId
     * @return
     */
    public int updateShareState(int urlId);
    /**
     * 获取share的indexed 状态
     * @param shareId
     * @return
     */
    public int getShareState(int shareId);
    /**
     * 每次有一个程序通过Web Service访问获取信息时
     * 会有相应的记录.
     * @param requestUser 请求的用户
     * @param requestTime 请求的时间
     * @return
     */
    public int addShareIdInfo(int firstShareId, int groupSum,String requestUser,String requestTime);
    /**
     * 当上传处理后的url信息成功后,更新shareId状态
     * @param id shareId表的identitier
     * @return
     */
    public int updateShareIdInfo(int id);
    /**
     * 获取最新一条用户通过web service获取url信息的记录
     * @return 返回最新一条记录的shareId值与groupCount的值,用于计算
     * 当前请求用户应该从多少shareId开始取得记录
     */
    public int[] getLastShareIdInfo();
}
