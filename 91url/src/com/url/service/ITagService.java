package com.url.service;

import java.util.List;

import com.url.bean.Tag;
import com.url.bean.UserTag;

public interface ITagService {
	/*
	 * ���һ��Tag��¼, ����Ϊ��װ��com.url.bean.Tag
	 */
    public int[] addTag(Tag tag);
    /*
     * ����UrlId,��ȡ����url�ı�ǩ,����Ϊ��װ�õ�urlId
     */
    public List<Tag> getTagsByUrlId(int urlId);
    /*
     * ����userId,��ȡ���û�����ǩ�����б�ǩ
     */
    public List<Tag> getTagsByUserId(int userId);
    /*
     * ɾ��Tag,����Ϊtag��id
     */
    public int delTagByTagUrlId(int tagId,int urlId);
    /**
     * ���ݱ�ǩǰ׺��ѯtag
     * @param tagPrefix
     * @return
     */
    public List<Tag> getUrlTagsByTagPrefix(String tagPrefix);
    /**
     * ���ݱ�ǩID��ȡ��ǩ��
     * @param userTagId
     * @return
     */
    public String getTagNameById(int tagId);
}
