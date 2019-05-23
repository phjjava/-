package com.jp.entity;

import java.util.List;

import com.jp.entity.Branch;
import com.jp.entity.BranchValidArea;

public class BranchCityBranch extends BranchValidArea {
    List<Branch> branchs;

    public List<Branch> getBranchs() {
        return branchs;
    }

    public void setBranchs(List<Branch> branchs) {
        this.branchs = branchs;
    }


}
