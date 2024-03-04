package com.office.worldcup.kopisapi;

import javax.xml.bind.annotation.XmlElement;


public class KopisDB {
	private String mt20id;
    private String prfnm;
    private String prfpdfrom;
    private String prfpdto;
    private String fcltynm;
    private String poster;
    private String area;
    private String genrenm;
    private String openrun;
    private String prfstate;
    
    @XmlElement
	public String getMt20id() {
		return mt20id;
	}
	public void setMt20id(String mt20id) {
		this.mt20id = mt20id;
	}
	
    @XmlElement
	public String getPrfnm() {
		return prfnm;
	}
	public void setPrfnm(String prfnm) {
		this.prfnm = prfnm;
	}
	
    @XmlElement
	public String getPrfpdfrom() {
		return prfpdfrom;
	}
	public void setPrfpdfrom(String prfpdfrom) {
		this.prfpdfrom = prfpdfrom;
	}
	
    @XmlElement
	public String getPrfpdto() {
		return prfpdto;
	}
	public void setPrfpdto(String prfpdto) {
		this.prfpdto = prfpdto;
	}
	
    @XmlElement
	public String getFcltynm() {
		return fcltynm;
	}
	public void setFcltynm(String fcltynm) {
		this.fcltynm = fcltynm;
	}
	
	@XmlElement
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	@XmlElement
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	@XmlElement
	public String getGenrenm() {
		return genrenm;
	}
	public void setGenrenm(String genrenm) {
		this.genrenm = genrenm;
	}
	
	@XmlElement
	public String getOpenrun() {
		return openrun;
	}
	public void setOpenrun(String openrun) {
		this.openrun = openrun;
	}
	
	@XmlElement
	public String getPrfstate() {
		return prfstate;
	}
	public void setPrfstate(String prfstate) {
		this.prfstate = prfstate;
	}
    
    
    @Override
    public String toString() {
        return "KopisDB{" +
                "mt20id='" + mt20id + '\'' +
                ", prfnm='" + prfnm + '\'' +
                ", prfpdfrom='" + prfpdfrom + '\'' +
                ", prfpdto='" + prfpdto + '\'' +
                ", fcltynm='" + fcltynm + '\'' +
                ", poster='" + poster + '\'' +
                ", area='" + area + '\'' +
                ", genrenm='" + genrenm + '\'' +
                ", openrun='" + openrun + '\'' +
                ", prfstate='" + prfstate + '\'' +
                '}';
    }

    
    
}
