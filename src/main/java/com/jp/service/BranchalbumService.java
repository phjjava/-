package com.jp.service;

import java.util.List;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.Branchalbum;
import com.jp.entity.Branchphoto;

public interface BranchalbumService {

	JsonResponse pageQuery(PageModel<Branchalbum> pageModel, Branchalbum ranchalbum);

	JsonResponse get(String albumid);

	JsonResponse mergeBranchAlbum(Branchalbum branchalbum);

	JsonResponse insertBranchPhoto(List<Branchphoto> userPhotoList);

	JsonResponse getAlbumAndPhotos(String albumid, String branchid);

	JsonResponse updateByPrimaryKeySelective(Branchphoto record);

	JsonResponse selectByPrimaryKey(String imgid);

	JsonResponse batchDelete(String albumids);

	/**
	 * 修改相册的状态字段
	 * 
	 * @描述 TODO
	 * @作者 jinlizhi
	 * @时间 2017年6月5日下午3:15:33
	 * @参数 @param branchAlbum
	 * @参数 @return
	 * @return int
	 */
	JsonResponse changeStatus(Branchalbum branchAlbum);

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
