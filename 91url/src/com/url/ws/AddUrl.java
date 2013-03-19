
package com.url.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addUrl", propOrder = {
    "url",
    "urlTitle",
    "urlOwner",
    "urlOwnerId",
    "urlAddDate",
    "urlType",
    "urlTag"
})
public class AddUrl {

    @XmlElement(required = true)
    protected String url;
    @XmlElement(required = true)
    protected String urlTitle;
    @XmlElement(required = true)
    protected String urlOwner;
    protected int urlOwnerId;
    @XmlElement(required = true)
    protected String urlAddDate;
    @XmlElement(required = true)
    protected int urlType;
    @XmlElement(required = true, nillable = true)
    protected String urlTag;



	public String getUrlTag() {
		return urlTag;
	}

	public void setUrlTag(String urlTag) {
		this.urlTag = urlTag;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String value) {
        this.url = value;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String value) {
        this.urlTitle = value;
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

    public String getUrlAddDate() {
        return urlAddDate;
    }

    public void setUrlAddDate(String value) {
        this.urlAddDate = value;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int value) {
        this.urlType = value;
    }
}
