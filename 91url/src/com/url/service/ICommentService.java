package com.url.service;

import java.util.List;

import com.url.bean.Comment;

public interface ICommentService{
	/**
	 * 根据urlId查询对应url的评论
	 * @param urlId
	 * @return
	 */
    public List<Comment> getCommentsByUrlId(int urlId,int comPage);
    
    /**
     * 根据urlId添加url的评论
     * @param urlId
     * @return
     */
    public int addComment(Comment comment);
    
    /**
     * 根据commentId删除评论
     * @param commentId
     * @return
     */
    public int delCommentByCommentId(int commentId);
    
    /**
     * 根据urlId获取相应评论数目
     * @param urlId
     * @return
     */
    public int getCommentSumByUrlId(int urlId);
}

