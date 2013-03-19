
package com.url.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.url.ws.bean.UrlCollection;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "setUrl", propOrder = {
    "urlCollection",
    "username",
    "password"
})
public class SetUrl {

	@XmlElement(nillable = true)
	protected UrlCollection urlCollection;
    protected String username;
    protected String password;
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public UrlCollection getUrls() {
	    if (urlCollection == null) {
	    	urlCollection = new UrlCollection();
	    }
	    return this.urlCollection;
	}
	public void setUrls(UrlCollection urlCollection){
		this.urlCollection = urlCollection;
	}
}
