package com.office.ticketreserve.kopisapi;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "boxofs")
public class KopisReserveDBs {
    private List<KopisReserveDB> dbList;

    @XmlElement(name = "boxof")
    public List<KopisReserveDB> getDbList() {
        return dbList;
    }

    public void setDbList(List<KopisReserveDB> dbList) {
        this.dbList = dbList;
    }
}