
package com.url.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "loginResponse", propOrder = {
    "result"
})
public class LoginResponse {

    @XmlElement(required = true)
    protected String result;


    public String getResult() {
        return result;
    }

    public void setResult(String value) {
        this.result = value;
    }

}
