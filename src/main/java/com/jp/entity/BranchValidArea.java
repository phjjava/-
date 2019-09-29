package com.jp.entity;

public class BranchValidArea {

	private String areaname;

	private String areacode;

	private String cityname;

	private String citycode;

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true; //自反性
		if (obj == null || this.getClass() != obj.getClass()) {  //传递性,非空性
			return false;
	    }
		BranchValidArea b = (BranchValidArea) obj;
		if(b.getAreacode().equals(this.areacode)) {
			return true;
		}else {
			return false;
		}
	}
}
