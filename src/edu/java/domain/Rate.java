package edu.java.domain;

public class Rate {

	private String crn;
	private String crc;
	private String title;

	public Rate() {}
	public Rate(String crn,String crc) {
		this.crn = crn;
		this.crc = crc;
	}
	public Rate(String crn,String crc, String title) {
		this.crn = crn;
		this.crc = crc;
		this.title = title;
	}

	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
