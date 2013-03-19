package com.url.bean;

public class Comment {
	private int commentId;
	private String commentContent;
	private int urlId;
	private int userId;
	private String userNickName;
    private int replyToUserId;
    private String replyToUserName;
	private String replyDate;
    
    public String getReplyToUserName() {
		return replyToUserName;
	}
	public void setReplyToUserName(String replyToUserName) {
		this.replyToUserName = replyToUserName;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
	public int getReplyToUserId() {
		return replyToUserId;
	}
	public void setReplyToUserId(int replyToUserId) {
		this.replyToUserId = replyToUserId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public int getUrlId() {
		return urlId;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserCmpImg() {
		return userCmpImg;
	}
	public void setUserCmpImg(String userCmpImg) {
		this.userCmpImg = userCmpImg;
	}
	private String userCmpImg;
}
