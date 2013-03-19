package com.url.service.impl;

import java.util.List;

import com.url.bean.Comment;
import com.url.dao.ICommentDAO;
import com.url.dao.ITagDAO;
import com.url.service.ICommentService;

public class CommentService implements ICommentService {
    private ICommentDAO commentDAO;
	public void setCommentDAO(ICommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	@Override
	public int addComment(Comment comment) {
		int result = commentDAO.addComment(comment);
		return result;
	}

	@Override
	public int delCommentByCommentId(int commentId) {
		int result = commentDAO.delCommentByCommentId(commentId);
		return result;
	}

	@Override
	public List<Comment> getCommentsByUrlId(int urlId,int comPage) {
		List<Comment> comments = commentDAO.getCommentsByUrlId(urlId,comPage);
		return comments;
	}
	
    /**
     * 根据urlId获取相应评论数目
     * @param urlId
     * @return
     */
    public int getCommentSumByUrlId(int urlId){
    	int result = commentDAO.getCommentSumByUrlId(urlId);
		return result;
    }

}
