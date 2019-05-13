package com.jp.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class JsonResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code = 0;
	private String msg = "成功";
	
	@JsonInclude(Include.NON_NULL)
	private Object data = null;
	
	@JsonInclude(Include.NON_NULL)
	private Object entity = null;
	
	@JsonInclude(Include.NON_NULL)
	private Object data1 = null;
	
	@JsonInclude(Include.NON_NULL)
	private Object data2 = null;
	
	public Object getData1() {
		return data1;
	}

	public void setData1(Object data1) {
		this.data1 = data1;
	}

	public Object getData2() {
		return data2;
	}

	public void setData2(Object data2) {
		this.data2 = data2;
	}

	public JsonResponse(int code , String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public JsonResponse(Result result) {
		this.code = result.getCode();
		this.msg = result.getMsg();
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}
		
}
