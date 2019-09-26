package com.jp.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jp.entity.Branch;
import com.jp.entity.BranchEditorBoard;
import com.jp.entity.BranchKey;
import com.jp.entity.BranchQuery;
import com.jp.entity.BranchValidArea;

public interface BranchDao {
	int countByExample(BranchQuery example);

	int deleteByExample(BranchQuery example);

	int deleteByPrimaryKey(BranchKey key);

	int insert(Branch record);

	int insertSelective(Branch record);

	List<Branch> selectByExample(BranchQuery example);

	Branch selectByPrimaryKey(BranchKey key);

	@Select("SELECT branchid cityCode, 3 `level` ,branchname cityName FROM jp_branch WHERE branchid = #{branchid}")
	Map<String, Object> selectByBranchid(@Param("branchid") String branchid);

	int updateByExampleSelective(@Param("record") Branch record, @Param("example") BranchQuery example);

	int updateByExample(@Param("record") Branch record, @Param("example") BranchQuery example);

	int updateByPrimaryKeySelective(Branch record);

	int updateByPrimaryKey(Branch record);

	List<Branch> selectBranchList(@Param("branch") Branch branch, @Param("list") List<String> branchids);

	List<Branch> selectBranchListByFamily(@Param("familyid") String familyid);

	List<Branch> selectBranchListByFamilyAndUserid(@Param("status") Integer status, @Param("familyid") String familyid,
			@Param("branchname") String branchname);

	int updateByBranchidSelective(Branch record);

	int selectByFamilyid(String familyid);

	/**
	 * 修改前的分编委会分支权限
	 * @param familyid
	 * @param userid
	 * @param branchname
	 * @return
	 */
	List<Branch> getBranchsByFamilyAndUserid(@Param("familyid") String familyid, @Param("userid") String userid,
			@Param("branchname") String branchname);

	List<Branch> getBranchListByFamilyAndCodes(@Param("familyid") String familyid, @Param("codeList") String[] codeList,
			@Param("branchname") String branchname);

	List<Branch> getBranchListByFamilyAndCode(@Param("familyid") String familyid, @Param("code") String code,
			@Param("branchname") String branchname);

	List<BranchValidArea> selectValidArea(String familyid);

	List<BranchValidArea> selectValidCity(Map<String, String> map);

	List<BranchValidArea> selectValidXQ(Map<String, String> map);

	List<Branch> selectBranchByXQ(Map<String, String> map);

	List<Branch> selectBranchByCitycode(Map<String, String> map);

	List<String> selectBranchidsByAreacode(@Param("code") String code, @Param("familyid") String familyid);

	List<String> selectBranchidsByCitycode(@Param("code") String code, @Param("familyid") String familyid);

	List<String> selectBranchidsByQxcode(@Param("code") String code, @Param("familyid") String familyid);

	List<BranchEditorBoard> selectCityByFamilyid(@Param("familyid") String familyid);

	List<String> selectBranchByCity(Map<String, String> param);

	List<Branch> selectBranchByUser(Map<String, String> map2);

	List<Branch> getBranchAdminCityList(Map<String, String> map);

	List<Branch> selectArea(String familyid);

	List<Branch> selectCity(String familyid);
}