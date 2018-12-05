package com.jp.dao;

import com.jp.entity.Branchalbum;
import com.jp.entity.BranchalbumExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BranchalbumMapper {
    int countByExample(BranchalbumExample example);

    int deleteByExample(BranchalbumExample example);

    int deleteByPrimaryKey(String albumid);

    int insert(Branchalbum record);

    int insertSelective(Branchalbum record);

    List<Branchalbum> selectByExample(BranchalbumExample example);

    Branchalbum selectByPrimaryKey(String albumid);

    int updateByExampleSelective(@Param("record") Branchalbum record, @Param("example") BranchalbumExample example);

    int updateByExample(@Param("record") Branchalbum record, @Param("example") BranchalbumExample example);

    int updateByPrimaryKeySelective(Branchalbum record);

    int updateByPrimaryKey(Branchalbum record);
    
    /**
     * @描述 相册列表的查询
     * @作者 sj
     * @时间 2017年5月22日下午4:30:22
     * @参数 @param branchids
     * @参数 @return
     * @return List<Branchalbum>
     */
    List<Branchalbum> selectByBranchIds(List<String> branchids);
    /**
     * 带条件,带相片数量统计的List查询
     * @描述 TODO
     * @作者 jinlizhi
     * @时间 2017年6月5日上午11:16:14
     * @参数 @param example
     * @参数 @return
     * @return List<Branchalbum>
     */
	List<Branchalbum> selectBranchAlbumMangeList(BranchalbumExample example);
	/**
	 * 相册批量删除(逻辑删除)
	 * @描述 TODO
	 * @作者 jinlizhi
	 * @时间 2017年6月5日上午11:56:39
	 * @参数 @param albumidArray
	 * @参数 @return
	 * @return int
	 */
	int batchDelete(String[] albumidArray);
}