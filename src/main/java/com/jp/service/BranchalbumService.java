package com.jp.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Branchalbum;
import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;
public interface BranchalbumService {
	
	PageModel<Branchalbum> pageQuery(PageModel<Branchalbum> pageModel,Branchalbum ranchalbum) throws Exception;
	
	Branchalbum get(String albumid,String branchid) throws Exception;
	
	String mergeBranchAlbum(Branchalbum branchalbum);
	
	int insertBranchPhoto(@Param("list")List<Branchphoto> userPhotoList);
	
	List<Branchphoto> selectByExample(BranchphotoExample example);
	
	int updateByPrimaryKeySelective(Branchphoto record);
	
	Branchphoto selectByPrimaryKey(String key);
	/**
	 * 相册批量删除 (逻辑删除)
	 * @描述 TODO
	 * @作者 jinlizhi
	 * @时间 2017年6月5日上午11:54:14
	 * @参数 @param albumidArray
	 * @参数 @return
	 * @return int
	 */
	int batchDelete(String[] albumidArray);
	/**
	 * 修改相册的状态字段
	 * @描述 TODO
	 * @作者 jinlizhi
	 * @时间 2017年6月5日下午3:15:33
	 * @参数 @param branchAlbum
	 * @参数 @return
	 * @return int
	 */
	int changeStatus(Branchalbum branchAlbum);
	/**
	 * @描述 查询管理分支下的所有相册的url
	 * @作者 sj
	 * @时间 2017年12月7日下午2:55:22
	 * @参数 @return
	 * @return List<Branchphoto>
	 */
	List<Branchphoto> selectByBranch(List<String> branchids);
	
	/**
	* 以下方法用于api
	*/
	
	JsonResponse getAlbum(Branchalbum entity);
	
	JsonResponse getPhotolistOfBranchAlbum(Branchphoto entity);
}
