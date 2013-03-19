package com.url.bean;

public class UserTag {
    private int userTagId;
    private String userTagName;
    private int userId;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserTagId() {
		return userTagId;
	}
	public void setUserTagId(int userTagId) {
		this.userTagId = userTagId;
	}
	public String getUserTagName() {
		return userTagName;
	}
	public void setUserTagName(String userTagName) {
		this.userTagName = userTagName;
	}
}
