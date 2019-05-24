package com.jp.dao;

import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BranchphotoMapper {
    int countByExample(BranchphotoExample example);

    int deleteByExample(BranchphotoExample example);

    int deleteByPrimaryKey(String imgid);

    int insert(Branchphoto record);

    int insertSelective(Branchphoto record);

    List<Branchphoto> selectByExample(BranchphotoExample example);

    Branchphoto selectByPrimaryKey(String imgid);

    int updateByExampleSelective(@Param("record") Branchphoto record, @Param("example") BranchphotoExample example);

    int updateByExample(@Param("record") Branchphoto record, @Param("example") BranchphotoExample example);

    int updateByPrimaryKeySelective(Branchphoto record);

    int updateByPrimaryKey(Branchphoto record);
    
    /**
     * @描述 保存照片
     * @作者 sj
     * @时间 2017年5月21日下午11:43:28
     * @参数 @param branchphotoList
     * @参数 @return
     * @return int
     */
    int insertBranchPhoto(@Param("list")List<Branchphoto> branchphotoList);
    /**
     * @描述 根据所管理分支查看所有相片的url
     * @作者 sj
     * @时间 2017年12月7日下午2:58:41
     * @参数 @param branchids
     * @参数 @return
     * @return List<Branchphoto>
     */
    List<Branchphoto> selectByBranch(@Param("list")List<String> branchids);

	List<Branchphoto> selectByFamilyid(String familyid);
	
	List<Branchphoto> selectPhotosByAlbumid(Map<String, String> map);
}