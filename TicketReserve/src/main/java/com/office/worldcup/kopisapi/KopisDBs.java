package com.office.worldcup.kopisapi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

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