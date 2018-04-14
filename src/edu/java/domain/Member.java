package edu.java.domain;

public class Member {

	private String mname;
	private String memail;
	private char[] mpw;
	
	public Member() {}
	public Member(String mname, String memail,char[] mpw) {
		this.mname = mname;
		this.memail = memail;
		this.mpw = mpw;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}
	public char[] getMpw() {
		return mpw;
	}
	public void setMpw(char[] mpw) {
		this.mpw = mpw;
	}
	
	
}
