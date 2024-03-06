package com.office.ticketreserve.kopisapi;

import javax.xml.bind.annotation.XmlElement;


public class KopisPlaceDB {
    private String fcltynm;
    private String mt10id;
    private String mt13cnt;
    private String fcltychartr;
    private String opende;
    private String seatscale;
    private String telno;
    private String relateurl;
    private String adres;
    private String la;
    private String lo;
    private String restaurant;
    private String cafe;
    private String store;
    private String nolibang;
    private String suyu;
    private String parkbarrier;
    private String restbarrier;
    private String runwbarrier;
    private String elevbarrier;
    private String parkinglot;

    @XmlElement
    public String getFcltynm() {
        return fcltynm;
    }

    public void setFcltynm(String fcltynm) {
        this.fcltynm = fcltynm;
    }

    @XmlElement
    public String getMt10id() {
        return mt10id;
    }

    public void setMt10id(String mt10id) {
        this.mt10id = mt10id;
    }

    @XmlElement
    public String getMt13cnt() {
        return mt13cnt;
    }

    public void setMt13cnt(String mt13cnt) {
        this.mt13cnt = mt13cnt;
    }

    @XmlElement
    public String getFcltychartr() {
        return fcltychartr;
    }

    public void setFcltychartr(String fcltychartr) {
        this.fcltychartr = fcltychartr;
    }

    @XmlElement
    public String getOpende() {
        return opende;
    }

    public void setOpende(String opende) {
        this.opende = opende;
    }

    @XmlElement
    public String getSeatscale() {
        return seatscale;
    }

    public void setSeatscale(String seatscale) {
        this.seatscale = seatscale;
    }

    @XmlElement
    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    @XmlElement
    public String getRelateurl() {
        return relateurl;
    }

    public void setRelateurl(String relateurl) {
        this.relateurl = relateurl;
    }

    @XmlElement
    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @XmlElement
    public String getLa() {
        return la;
    }

    public void setLa(String la) {
        this.la = la;
    }

    @XmlElement
    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    @XmlElement
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    @XmlElement
    public String getCafe() {
        return cafe;
    }

    public void setCafe(String cafe) {
        this.cafe = cafe;
    }

    @XmlElement
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @XmlElement
    public String getNolibang() {
        return nolibang;
    }

    public void setNolibang(String nolibang) {
        this.nolibang = nolibang;
    }

    @XmlElement
    public String getSuyu() {
        return suyu;
    }

    public void setSuyu(String suyu) {
        this.suyu = suyu;
    }

    @XmlElement
    public String getParkbarrier() {
        return parkbarrier;
    }

    public void setParkbarrier(String parkbarrier) {
        this.parkbarrier = parkbarrier;
    }

    @XmlElement
    public String getRestbarrier() {
        return restbarrier;
    }

    public void setRestbarrier(String restbarrier) {
        this.restbarrier = restbarrier;
    }

    @XmlElement
    public String getRunwbarrier() {
        return runwbarrier;
    }

    public void setRunwbarrier(String runwbarrier) {
        this.runwbarrier = runwbarrier;
    }

    @XmlElement
    public String getElevbarrier() {
        return elevbarrier;
    }

    public void setElevbarrier(String elevbarrier) {
        this.elevbarrier = elevbarrier;
    }

    @XmlElement
    public String getParkinglot() {
        return parkinglot;
    }

    public void setParkinglot(String parkinglot) {
        this.parkinglot = parkinglot;
    }

    @Override
    public String toString() {
        return "OlympicPark{" +
                "fcltynm='" + fcltynm + '\'' +
                ", mt10id='" + mt10id + '\'' +
                ", mt13cnt='" + mt13cnt + '\'' +
                ", fcltychartr='" + fcltychartr + '\'' +
                ", opende='" + opende + '\'' +
                ", seatscale='" + seatscale + '\'' +
                ", telno='" + telno + '\'' +
                ", relateurl='" + relateurl + '\'' +
                ", adres='" + adres + '\'' +
                ", la='" + la + '\'' +
                ", lo='" + lo + '\'' +
                ", restaurant='" + restaurant + '\'' +
                ", cafe='" + cafe + '\'' +
                ", store='" + store + '\'' +
                ", nolibang='" + nolibang + '\'' +
                ", suyu='" + suyu + '\'' +
                ", parkbarrier='" + parkbarrier + '\'' +
                ", restbarrier='" + restbarrier + '\'' +
                ", runwbarrier='" + runwbarrier + '\'' +
                ", elevbarrier='" + elevbarrier + '\'' +
                ", parkinglot='" + parkinglot + '\'' +
                '}';
    }
}
