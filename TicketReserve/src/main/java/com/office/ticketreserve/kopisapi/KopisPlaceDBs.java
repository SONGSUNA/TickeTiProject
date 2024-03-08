package com.office.ticketreserve.kopisapi;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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