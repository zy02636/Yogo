package com.url.dao;

import java.util.List;

import com.url.bean.Tag;

public interface ITagDAO {
	/**
	 * 注意一个问题,就是原始的url_Tag表内若有相同的Tag名称那么只要在关系
	 * 表中添加一条关系即可,万一是新的Url的Tag 那么 就在添加记录的同时
	 * 添加关系,此处用存储过程解决
	 * @param tag
	 * @return 返回受影响的行数
	 */
    public int[] addTag(Tag tag);
    /*
     * 根据UrlId,获取单个url的标签,参数为包装好的urlId
     */
    public List<Tag> getTagsByUrlId(int urlId);
    /*
     * 根据userId,获取该用户的书签的所有标签
     */
    public List<Tag> getTagsByUserId(int userId);
    /*
     * 删除Tag,参数为tag的id
     */
    public int delTagByTagUrlId(int tagId,int urlId);
    /**
     * 根据标签前缀查询tag
     * @param tagPrefix
     * @return
     */
    public List<Tag> getUrlTagsByTagPrefix(String tagPrefix);
    /**
     * 根据标签ID获取标签名
     * @param userTagId
     * @return
     */
    public String getTagNameById(int tagId);
}
