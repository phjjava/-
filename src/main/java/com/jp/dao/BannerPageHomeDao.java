package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.BannerHomePage;
import com.jp.entity.BannerQuery;

public interface BannerPageHomeDao {

	List<BannerHomePage> selectByExample(BannerQuery bq);

	int updateByPrimaryKeySelective(BannerHomePage banner);

	Integer insertSelective(BannerHomePage banner);

	int batchDelete(@Param("array") String[] bannerids);

}
