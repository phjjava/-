package com.jp.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jp.entity.HomeState;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author phj123
 * @since 2019-08-19
 */
public interface HomeStateMapper extends BaseMapper<HomeState> {

	@Select("SELECT * FROM `jp_home_state` WHERE userid = #{userid}")
	HomeState selectByUserid(@Param("userid") String userid);

	@Update("UPDATE jp_home_state SET #{param1} = #{param2} WHERE userid = #{param3}")
	int updateByUserid(String code, int status, String userid);

	int updateByUseridSelective(HomeState homeState);

	@Insert("INSERT INTO jp_home_state VALUES(#{userid},#{bannerStatus},#{mationStatus},#{noticeStatus},#{xingStatus},#{synopsisStatus});")
	int insertHomeState(HomeState homeState);
}
