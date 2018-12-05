package com.jp.util;

public class Result {
	
	private Integer status;
	private String title;
	private String msg;
	private Object data;
	private Object data1;
	private Object data2;
	
	public Result(){
		
	}
	
	public Result(Integer status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	private Object entity;//有时候data放不下数据，就把部分放到这里来
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
