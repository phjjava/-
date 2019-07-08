package com.jp.entity;

import java.util.List;

public class BranchAreaCity extends BranchValidArea {

	List<BranchValidArea> citys;

	public List<BranchValidArea> getCitys() {
		return citys;
	}

	public void setCitys(List<BranchValidArea> citys) {
		this.citys = citys;
	}

}
