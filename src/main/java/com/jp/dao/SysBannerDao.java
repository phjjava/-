package com.jp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.entity.BannerHomePage;
import com.jp.entity.BannerQuery;
import com.jp.entity.InstructionTemplateQuery;

public interface SysBannerDao {

	List<BannerHomePage> selectByExample(BannerQuery bq);

	int updateByPrimaryKeySelective(BannerHomePage banner);

	Integer insertSelective(BannerHomePage banner);

	int batchDelete(@Param("array") String[] bannerids);
	
	int batchDeleteAll(@Param("array") String[] bannerids);

	void deleteByPrimaryKey(String bannerid);

	List<BannerHomePage> selectByExampleNew(BannerQuery bq);

}
