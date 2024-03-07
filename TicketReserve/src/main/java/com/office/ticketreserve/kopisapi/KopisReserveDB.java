package com.office.ticketreserve.kopisapi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class KopisReserveDB {
	private String prfplcnm;
    private int seatcnt;
    private int rnum;
    private String poster;
    private String prfpd;
    private String mt20id;
    private String prfnm;
    private String cate;
    private int prfdtcnt;
    private String area;

    @XmlElement(name = "prfplcnm")
    public String getPrfplcnm() {
        return prfplcnm;
    }

    public void setPrfplcnm(String prfplcnm) {
        this.prfplcnm = prfplcnm;
    }

    @XmlElement(name = "seatcnt")
    public int getSeatcnt() {
        return seatcnt;
    }

    public void setSeatcnt(int seatcnt) {
        this.seatcnt = seatcnt;
    }

    @XmlElement(name = "rnum")
    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }

    @XmlElement(name = "poster")
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @XmlElement(name = "prfpd")
    public String getPrfpd() {
        return prfpd;
    }

    public void setPrfpd(String prfpd) {
        this.prfpd = prfpd;
    }

    @XmlElement(name = "mt20id")
    public String getMt20id() {
        return mt20id;
    }

    public void setMt20id(String mt20id) {
        this.mt20id = mt20id;
    }

    @XmlElement(name = "prfnm")
    public String getPrfnm() {
        return prfnm;
    }

    public void setPrfnm(String prfnm) {
        this.prfnm = prfnm;
    }

    @XmlElement(name = "cate")
    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    @XmlElement(name = "prfdtcnt")
    public int getPrfdtcnt() {
        return prfdtcnt;
    }

    public void setPrfdtcnt(int prfdtcnt) {
        this.prfdtcnt = prfdtcnt;
    }

    @XmlElement(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "ConcertInfo{" +
                "prfplcnm='" + prfplcnm + '\'' +
                ", seatcnt=" + seatcnt +
                ", rnum=" + rnum +
                ", poster='" + poster + '\'' +
                ", prfpd='" + prfpd + '\'' +
                ", mt20id='" + mt20id + '\'' +
                ", prfnm='" + prfnm + '\'' +
                ", cate='" + cate + '\'' +
                ", prfdtcnt=" + prfdtcnt +
                ", area='" + area + '\'' +
                '}';
    }
}