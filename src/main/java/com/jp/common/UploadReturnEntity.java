package com.jp.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @功能 用于上传成功的图片(文件)json数据转换,方便拿值
 * @作者 jinlizhi
 * @时间 2017年6月2日上午11:14:32
 */
public class UploadReturnEntity implements Serializable{

	private Integer result;// 上传结果
	private String title;// 标题
	private String msg;// 信息
	private HashMap<String, String> data;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

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

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

}
