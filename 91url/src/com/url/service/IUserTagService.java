package com.url.service;

import java.util.List;

import com.url.bean.UserTag;

public interface IUserTagService {
	/**
	 * 注意一个问题,就是原始的url_UserTag表内若有相同的Tag名称那么只要在关系
	 * 表中添加一条关系即可,万一是新的自我描述的UserTag 那么 就在添加记录的同时
	 * 添加关系,此处用存储过程解决
	 * @param userTag
	 * @return
	 */
	public int[] addUserTag(UserTag userTag);
    /**
     * 根据用户id获取该用户的所有自我描述的标签
     * @param userId
     * @return
     */
    public List<UserTag> getUserTagsByUserId(int userId);
    /**
     * 根据自我描述的标签的前缀查询userTag
     * @param userId
     * @return
     */
    public List<UserTag> getUserTagsByTagPrefix(String tagPrefix);
    /**
     * 根据标签ID,用户ID删除对应用户标签
     * @param userId
     * @return
     */
    public int deleteUserTagByUserId(int userTagId,int userId);
    /**
     * 根据用户标签ID获取标签名
     * @param userTagId
     * @return
     */
    public String getUserTagNameById(int userTagId);
}
