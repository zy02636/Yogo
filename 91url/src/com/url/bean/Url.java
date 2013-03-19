package com.url.bean;

import java.util.List;

/*
 * 自定义的Url类,用于封装Url数据表中的信息
 */
public class Url {
	private int urlId;
    private String url;
	private String urlTitle;
    private String addDate;
    private String urlOwner;
    private int urlOwnerId;
    private int urlType;
    private String urlUserImage;
    private int shareId;
    private int shareSum;
    private String firstOwner;
    private int firstOwnerId;
	private List<Tag> tags;
    private int commentSum;
    private String featureOne;
    private String scoreOne;
    private int indexed;
    private int shareIdInfoId;
    private String urlTag;
    
    
	public String getUrlTag() {
		return urlTag;
	}
	public void setUrlTag(String urlTag) {
		this.urlTag = urlTag;
	}
	public int getShareIdInfoId() {
		return shareIdInfoId;
	}
	public void setShareIdInfoId(int shareIdInfoId) {
		this.shareIdInfoId = shareIdInfoId;
	}
	public int getIndexed() {
		return indexed;
	}
	public void setIndexed(int indexed) {
		this.indexed = indexed;
	}
	public String getFeatureOne() {
		return featureOne;
	}
	public void setFeatureOne(String featureOne) {
		this.featureOne = featureOne;
	}
	public String getScoreOne() {
		return scoreOne;
	}
	public void setScoreOne(String scoreOne) {
		this.scoreOne = scoreOne;
	}
	public int getCommentSum() {
		return commentSum;
	}
	public void setCommentSum(int commentSum) {
		this.commentSum = commentSum;
	}
	public int getFirstOwnerId() {
		return firstOwnerId;
	}
	public void setFirstOwnerId(int firstOwnerId) {
		this.firstOwnerId = firstOwnerId;
	}
	public String getFirstOwner() {
		return firstOwner;
	}
	public void setFirstOwner(String firstOwner) {
		this.firstOwner = firstOwner;
	}
	public int getShareSum() {
		return shareSum;
	}
	public void setShareSum(int shareSum) {
		this.shareSum = shareSum;
	}
	public int getShareId() {
		return shareId;
	}
	public void setShareId(int shareId) {
		this.shareId = shareId;
	}
    public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
    public String getUrlUserImage() {
		return urlUserImage;
	}
	public void setUrlUserImage(String urlUserImage) {
		this.urlUserImage = urlUserImage;
	}
	public int getUrlType() {
		return urlType;
	}
	public void setUrlType(int urlType) {
		this.urlType = urlType;
	}
	public int getUrlOwnerId() {
		return urlOwnerId;
	}
	public void setUrlOwnerId(int urlOwnerId) {
		this.urlOwnerId = urlOwnerId;
	}
	public int getUrlId() {
		return urlId;
	}
	public void setUrlId(int urlId) {
		this.urlId = urlId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlTitle() {
		return urlTitle;
	}
	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getUrlOwner() {
		return urlOwner;
	}
	public void setUrlOwner(String urlOwner) {
		this.urlOwner = urlOwner;
	}
 
    public static void main(String[] args){
    	String names = null;
    	System.out.println((names == null));
    } 
}
