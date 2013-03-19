
package com.url.ws.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "url", propOrder = {
    "addDate",
    "shareId",
    "url",
    "urlFeatures",
    "featureScores",
    "urlId",
    "urlOwner",
    "urlOwnerId",
    "urlTitle",
    "shareIdInfoId",
})
public class Url {
	public Url() {
		super();
	}
	public Url(int urlId, String url, String urlTitle, String urlOwner,
			String addDate, String[] urlFeatures, int urlOwnerId,
			int shareId,int shareIdInfoId,int[] featureScores) {
		super();
		this.urlId = urlId;
		this.url = url;
		this.urlTitle = urlTitle;
		this.urlOwner = urlOwner;
		this.addDate = addDate;
		this.urlFeatures = urlFeatures;
		this.urlOwnerId = urlOwnerId;
		this.shareId = shareId;
		this.featureScores = featureScores;
		this.shareIdInfoId = shareIdInfoId;
	}
    protected String addDate;
    protected int shareId;
    protected String url;
    @XmlElement(nillable = true)
    protected String[] urlFeatures;
	@XmlElement(nillable = true)
    protected int[] featureScores;
	protected int urlId;
    protected String urlOwner;
    protected int urlOwnerId;
    protected String urlTitle;
    protected int shareIdInfoId;
   

    public int getShareIdInfoId() {
		return shareIdInfoId;
	}
	public void setShareIdInfoId(int shareIdInfoId) {
		this.shareIdInfoId = shareIdInfoId;
	}
	public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String value) {
        this.addDate = value;
    }

 
    public int getShareId() {
        return shareId;
    }

    public void setShareId(int value) {
        this.shareId = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        this.url = value;
    }
    public void setUrlFeatures(String[] urlFeatures) {
		this.urlFeatures = urlFeatures;
	}
    public String[] getUrlFeatures() {
        if (urlFeatures == null) {
            urlFeatures = new String[5];
        }
        return this.urlFeatures;
    }
    public int[] getFeatureScores() {
		return featureScores;
	}
	public void setFeatureScores(int[] featureScores) {
		this.featureScores = featureScores;
	}
    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int value) {
        this.urlId = value;
    }

    public String getUrlOwner() {
        return urlOwner;
    }

    public void setUrlOwner(String value) {
        this.urlOwner = value;
    }

    public int getUrlOwnerId() {
        return urlOwnerId;
    }

    public void setUrlOwnerId(int value) {
        this.urlOwnerId = value;
    }


    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String value) {
        this.urlTitle = value;
    }

}
