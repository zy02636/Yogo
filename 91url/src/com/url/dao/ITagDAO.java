package com.url.dao;

import java.util.List;

import com.url.bean.Tag;

public interface ITagDAO {
	/**
	 * ע��һ������,����ԭʼ��url_Tag����������ͬ��Tag������ôֻҪ�ڹ�ϵ
	 * �������һ����ϵ����,��һ���µ�Url��Tag ��ô ������Ӽ�¼��ͬʱ
	 * ��ӹ�ϵ,�˴��ô洢���̽��
	 * @param tag
	 * @return ������Ӱ�������
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
