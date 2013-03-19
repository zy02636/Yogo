package com.url.service;

import java.util.List;

import com.url.bean.Tag;
import com.url.bean.UserTag;

public interface ITagService {
	/*
	 * 添加一条Tag记录, 参数为包装好com.url.bean.Tag
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
