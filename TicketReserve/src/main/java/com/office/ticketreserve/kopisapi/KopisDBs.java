package com.office.ticketreserve.kopisapi;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dbs")
public class KopisDBs {
    private List<KopisDB> dbList;

    @XmlElement(name = "db")
    public List<KopisDB> getDbList() {
        return dbList;
    }

    public void setDbList(List<KopisDB> dbList) {
        this.dbList = dbList;
    }
}