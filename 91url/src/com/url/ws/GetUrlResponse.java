
package com.url.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.url.ws.bean.Url;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUrlResponse", propOrder = {
    "urls"
})
public class GetUrlResponse {

    @XmlElement(nillable = true)
    protected Url[] urls;

    public Url[] getUrls() {
        if (urls == null) {
            urls = new Url[10];
        }
        return this.urls;
    }

}
