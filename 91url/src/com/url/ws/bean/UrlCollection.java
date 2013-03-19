
package com.url.ws.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "urlCollection", propOrder = {
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
public class UrlCollection {
	public UrlCollection() {
		super();
	}
	public UrlCollection(String urlId, String url, String urlTitle, String urlOwner,
			String addDate, String urlFeatures, String urlOwnerId,
			String shareId,String shareIdInfoId,String featureScores) {
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
    protected String shareId;
    protected String url;
    protected String urlFeatures;
    protected String featureScores;
	protected String urlId;
    protected String urlOwner;
    protected String urlOwnerId;
    protected String urlTitle;
    protected String shareIdInfoId;
    

    public String getShareIdInfoId() {
		return shareIdInfoId;
	}
	public void setShareIdInfoId(String shareIdInfoId) {
		this.shareIdInfoId = shareIdInfoId;
	}

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String value) {
        this.addDate = value;
    }

 
    public String getShareId() {
        return shareId;
    }

    public void setShareId(String value) {
        this.shareId = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        this.url = value;
    }
    public void setUrlFeatures(String urlFeatures) {
		this.urlFeatures = urlFeatures;
	}
    public String getUrlFeatures() {
        if (urlFeatures == null) {
            urlFeatures = new String();
        }
        return this.urlFeatures;
    }
    public String getFeatureScores() {
		return featureScores;
	}
	public void setFeatureScores(String featureScores) {
		this.featureScores = featureScores;
	}
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String value) {
        this.urlId = value;
    }

    public String getUrlOwner() {
        return urlOwner;
    }

    public void setUrlOwner(String value) {
        this.urlOwner = value;
    }

    public String getUrlOwnerId() {
        return urlOwnerId;
    }

    public void setUrlOwnerId(String value) {
        this.urlOwnerId = value;
    }


    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String value) {
        this.urlTitle = value;
    }

}
