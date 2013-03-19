package com.url.service.impl;

import java.util.List;

import com.url.bean.Tag;
import com.url.bean.UserTag;
import com.url.dao.ITagDAO;
import com.url.service.ITagService;

public class TagService implements ITagService {
    private ITagDAO tagDAO;
	public void setTagDAO(ITagDAO tagDAO) {
		this.tagDAO = tagDAO;
	}

	@Override
	public int[] addTag(Tag tag) {
		return tagDAO.addTag(tag);
	}

	@Override
	public int delTagByTagUrlId(int tagId,int urlId) {
		return tagDAO.delTagByTagUrlId(tagId,urlId);
	}

	@Override
	public List<Tag> getTagsByUrlId(int urlId) {
		return tagDAO.getTagsByUrlId(urlId);
	}
	
	@Override
	public List<Tag> getTagsByUserId(int userId) {
		return tagDAO.getTagsByUserId(userId);
	}
    /**
     * 根据标签前缀查询tag
     * @param tagPrefix
     * @return
     */
    public List<Tag> getUrlTagsByTagPrefix(String tagPrefix){
    	return tagDAO.getUrlTagsByTagPrefix(tagPrefix);
    }
    /**
     * 根据标签ID获取标签名
     * @param userTagId
     * @return
     */
    public String getTagNameById(int tagId){
    	String result = tagDAO.getTagNameById(tagId);
    	return result;
    }
}
