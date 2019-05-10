package com.jp.common;

import java.io.Serializable;

public class Result implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code = 0;
	private String msg = "成功";
	
	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public Result(Result result) {
		this.code = result.code;
		this.msg = result.msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public boolean equals(Object object) {
		boolean flag = false;
		if(object instanceof Result) {
			flag = this.getCode() == ((Result)object).getCode();
		}else {
			flag = super.equals(object);
		}
		return flag;
		
	}
	
}
