package com.office.ticketreserve.kopisapi;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;



public class StyUrl {
    private List<String> url = new ArrayList<>();

    @XmlElement(name = "styurl")
    public List<String> getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url.add(url);
    }

    @Override
    public String toString() {
        return "StyUrl{" +
                "url='" + url + '\'' +
                '}';
    }
}