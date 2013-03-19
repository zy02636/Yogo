package com.url.bean;

import java.util.List;

public class User {
	private int userId;
	private String username;
    private String pwd;
    private int age;
    private String sex;
    private String province;
    private String city;
    private String nickName;
    private String bigImagePath;
    private String cmpImagePath;
    private int relationType;
    private List<UserTag> userTags;
    private String regDate;
    private int userType;
	

	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public List<UserTag> getUserTags() {
		return userTags;
	}
	public void setUserTags(List<UserTag> userTags) {
		this.userTags = userTags;
	}
	public int getRelationType() {
		return relationType;
	}
	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}
	public String getBigImagePath() {
		return bigImagePath;
	}
	public void setBigImagePath(String bigImagePath) {
		this.bigImagePath = bigImagePath;
	}
	public String getCmpImagePath() {
		return cmpImagePath;
	}
	public void setCmpImagePath(String cmpImagePath) {
		this.cmpImagePath = cmpImagePath;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	public static void main(String[] args){
		System.out.println((float)11/3);
	}
}
