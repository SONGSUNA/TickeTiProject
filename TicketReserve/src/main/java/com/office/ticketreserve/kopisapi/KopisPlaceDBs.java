package com.office.ticketreserve.kopisapi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "dbs")
public class KopisPlaceDBs {
    private List<KopisPlaceDB> dbList;

    @XmlElement(name = "db")
    public List<KopisPlaceDB> getDbList() {
        return dbList;
    }

    public void setDbList(List<KopisPlaceDB> dbList) {
        this.dbList = dbList;
    }
}