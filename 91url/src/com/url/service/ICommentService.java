package com.url.service;

import java.util.List;

import com.url.bean.Comment;

public interface ICommentService{
	/**
	 * ����urlId��ѯ��Ӧurl������
	 * @param urlId
	 * @return
	 */
    public List<Comment> getCommentsByUrlId(int urlId,int comPage);
    
    /**
     * ����urlId���url������
     * @param urlId
     * @return
     */
    public int addComment(Comment comment);
    
    /**
     * ����commentIdɾ������
     * @param commentId
     * @return
     */
    public int delCommentByCommentId(int commentId);
    
    /**
     * ����urlId��ȡ��Ӧ������Ŀ
     * @param urlId
     * @return
     */
    public int getCommentSumByUrlId(int urlId);
}

