package com.jp.entity;

import java.util.List;

import com.jp.entity.Dycomment;
import com.jp.entity.Dyprise;
import com.jp.entity.Dyread;
import com.jp.entity.Dynamic;
import com.jp.entity.Dynamicfile;

public class DynamicDetailVO {
    Dynamic dynamic;

    List<Dynamicfile> dynamicFiles;
    List<Dycomment> dyComments;
    List<Dyread> dyReads;
    List<Dyprise> dyPrises;

    int countComments;
    int countReads;
    int countPrises;
    int countFiles;

    public Dynamic getDynamic() {
        return dynamic;
    }

    public void setDynamic(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    public int getCountComments() {
        return countComments;
    }

    public void setCountComments(int countComments) {
        this.countComments = countComments;
    }

    public int getCountReads() {
        return countReads;
    }

    public void setCountReads(int countReads) {
        this.countReads = countReads;
    }

    public int getCountPrises() {
        return countPrises;
    }

    public void setCountPrises(int countPrises) {
        this.countPrises = countPrises;
    }

    public int getCountFiles() {
        return countFiles;
    }

    public void setCountFiles(int countFiles) {
        this.countFiles = countFiles;
    }

	public List<Dynamicfile> getDynamicFiles() {
		return dynamicFiles;
	}

	public void setDynamicFiles(List<Dynamicfile> dynamicFiles) {
		this.dynamicFiles = dynamicFiles;
	}

	public List<Dycomment> getDyComments() {
		return dyComments;
	}

	public void setDyComments(List<Dycomment> dyComments) {
		this.dyComments = dyComments;
	}

	public List<Dyread> getDyReads() {
		return dyReads;
	}

	public void setDyReads(List<Dyread> dyReads) {
		this.dyReads = dyReads;
	}

	public List<Dyprise> getDyPrises() {
		return dyPrises;
	}

	public void setDyPrises(List<Dyprise> dyPrises) {
		this.dyPrises = dyPrises;
	}



}
