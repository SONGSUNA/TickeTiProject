package com.office.ticketreserve.kopisapi;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dbs")
public class KopisdetailDBs {
    private List<KopisdetailDB> dbList;

    @XmlElement(name = "db")
    public List<KopisdetailDB> getDbList() {
        return dbList;
    }

    public void setDbList(List<KopisdetailDB> dbList) {
        this.dbList = dbList;
    }
}