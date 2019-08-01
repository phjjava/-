package com.jp.service;

import java.util.List;


import com.jp.entity.SysNotice;
import com.jp.entity.SysNoticeType;

public interface SysNoticeService {

	List<SysNotice> selectNotice();

	List<SysNoticeType> selecttypelist();

	Integer update(SysNotice notice);

	Integer insert(SysNotice notice);

}
