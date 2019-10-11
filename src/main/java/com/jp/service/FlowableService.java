package com.jp.service;

import java.util.List;

import com.jp.entity.Notice;
import com.jp.entity.UserManager;

public interface FlowableService {
	//查询提交人对应的分编委会成员
	String deploynew(String noticeid);
	//查询对应的总编委会成员
	String deploynewTwo(String noticeid);
	//查询对应的总编委会主任
	String deploynewThree();
	//查询公告内容
	Notice selectNotice(String noticeid);
	UserManager deploynewFour(String string);
	

}
