package com.url.service;

import java.util.List;

import com.url.bean.UserTag;

public interface IUserTagService {
	/**
	 * ע��һ������,����ԭʼ��url_UserTag����������ͬ��Tag������ôֻҪ�ڹ�ϵ
	 * �������һ����ϵ����,��һ���µ�����������UserTag ��ô ������Ӽ�¼��ͬʱ
	 * ��ӹ�ϵ,�˴��ô洢���̽��
	 * @param userTag
	 * @return
	 */
	public int[] addUserTag(UserTag userTag);
    /**
     * �����û�id��ȡ���û����������������ı�ǩ
     * @param userId
     * @return
     */
    public List<UserTag> getUserTagsByUserId(int userId);
    /**
     * �������������ı�ǩ��ǰ׺��ѯuserTag
     * @param userId
     * @return
     */
    public List<UserTag> getUserTagsByTagPrefix(String tagPrefix);
    /**
     * ���ݱ�ǩID,�û�IDɾ����Ӧ�û���ǩ
     * @param userId
     * @return
     */
    public int deleteUserTagByUserId(int userTagId,int userId);
    /**
     * �����û���ǩID��ȡ��ǩ��
     * @param userTagId
     * @return
     */
    public String getUserTagNameById(int userTagId);
}
