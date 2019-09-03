package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.BranchphotoMapper;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.Branchalbum;
import com.jp.entity.BranchalbumExample;
import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;
import com.jp.entity.PhotosVO;
import com.jp.entity.UserManager;
import com.jp.service.BranchalbumService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class BranchalbumServiceImpl implements BranchalbumService {

	private final Logger log_ = LogManager.getLogger(BranchalbumServiceImpl.class);

	@Autowired
	private BranchalbumMapper badao;
	@Autowired
	private BranchphotoMapper photodao;
	@Autowired
	private BranchDao branchDao;

	@Override
	public JsonResponse pageQuery(PageModel<Branchalbum> pageModel, Branchalbum branchalbum) {
		Result result = null;
		JsonResponse res = null;
		if (pageModel.getPageNo() == null || "".equals(pageModel.getPageNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (pageModel.getPageSize() == null || "".equals(pageModel.getPageSize() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("分页参数pageSize不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			// 当前登录人所管理的branchids
			List<UserManager> managers = CurrentUserContext.getCurrentUserManager();
			String familyid = CurrentUserContext.getCurrentFamilyId();
			List<String> branchIds = CurrentUserContext.getCurrentBranchIds();
			List<Branchalbum> list = new ArrayList<Branchalbum>();
			for (UserManager m : managers) {
				BranchalbumExample example = new BranchalbumExample();
				example.clear();
				BranchalbumExample.Criteria criteria = example.createCriteria();
				if (StringTools.trimNotEmpty(branchalbum.getBranchid())) {
					criteria.andBranchidEqualTo(branchalbum.getBranchid());
				}
				if (StringTools.trimNotEmpty(branchalbum.getDeleteflag())) {
					criteria.andDeleteflagEqualTo(branchalbum.getDeleteflag());
				}
				example.setOrderByClause("createtime DESC");
				PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
				if (m.getEbtype() == 1) {
					criteria.andFamilyidEqualTo(familyid);
					list = badao.selectByExample(example);
					break;
				} else {
					if (branchIds.size() < 1) {
						result = new Result(MsgConstants.RESUL_FAIL);
						result.setMsg("您的账号当前没有分支");
						res = new JsonResponse(result);
						return res;
					}
					criteria.andBranchidIn(branchIds);
					list = badao.selectBranchAlbumMangeList(example);
					break;
				}
			}
			// if (branchList!=null&&branchList.size()>0) {
			// criteria.andBranchidIn(branchList);
			// }else{
			// return pageModel;
			// }

			// List<Branchalbum> list = badao.selectByBranchIds(branchList);
			// badao.selectBranchAlbumMangeList()\
			BranchphotoExample example1 = new BranchphotoExample();
			BranchKey key = new BranchKey();
			for (Branchalbum al : list) {
				example1.clear();
				example1.or().andAlbumidEqualTo(al.getAlbumid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
				al.setAlbumNum(photodao.countByExample(example1));
				if (!"0".equals(al.getBranchid())) {
					key.setBranchid(al.getBranchid());
					key.setFamilyid(familyid);
					Branch branch = branchDao.selectByPrimaryKey(key);
					String area = "";
					if (branch.getArea() != null)
						area += branch.getArea();
					if (branch.getCityname() != null)
						area += "_" + branch.getCityname();
					if (branch.getXname() != null)
						area += "_" + branch.getXname();
					if (branch.getAddress() != null)
						area += "_" + branch.getAddress();
					area += " " + branch.getBranchname();
					al.setBranchname(area);
				}

			}
			if (list != null) {
				pageModel.setList(list);
				pageModel.setPageInfo(new PageInfo<Branchalbum>(list));
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(pageModel);
				return res;
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse get(String albumid) {
		Result result = null;
		JsonResponse res = null;
		Branchalbum branchalbum = null;
		try {
			if (StringTools.trimNotEmpty(albumid)) {
				branchalbum = badao.selectByPrimaryKey(albumid);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPGL]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		if (branchalbum == null) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(branchalbum);
		return res;
	}

	@Override
	public JsonResponse mergeBranchAlbum(Branchalbum branchalbum) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		String ablumId = "";
		try {
			if (StringTools.trimNotEmpty(branchalbum.getAlbumid())) {
				branchalbum.setUpdatetime(new Date());
				branchalbum.setUpdateid(CurrentUserContext.getCurrentUserId());
				BranchalbumExample query = new BranchalbumExample();
				query.or().andAlbumidEqualTo(branchalbum.getAlbumid());
				if (!StringTools.trimNotEmpty(branchalbum.getBranchid())) {
					branchalbum.setBranchid("0");
				}
				branchalbum.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				status = badao.updateByExampleSelective(branchalbum, query);
				// badao.updateByPrimaryKeySelective(branchalbum);
			} else {
				if (!StringTools.trimNotEmpty(branchalbum.getBranchid())) {
					branchalbum.setBranchid("0");
				}
				ablumId = UUIDUtils.getUUID();
				branchalbum.setAlbumid(ablumId);
				branchalbum.setCreatetime(new Date());
				branchalbum.setCreateid(CurrentUserContext.getCurrentUserId());
				branchalbum.setUpdatetime(new Date());
				branchalbum.setUpdateid(CurrentUserContext.getCurrentUserId());
				branchalbum.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				// 0未删除
				branchalbum.setDeleteflag(0);
				status = badao.insertSelective(branchalbum);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(ablumId);
				return res;
			}
		} catch (Exception e) {
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse insertBranchPhoto(List<Branchphoto> userPhotoList) {
		Result result = null;
		JsonResponse res = null;
		try {
			for (Branchphoto bp : userPhotoList) {
				if (bp.getImgid() == null || "".equals(bp.getImgid())) {
					bp.setImgid(UUIDUtils.getUUID());
				}
				if (bp.getAlbumid() == null || "".equals(bp.getAlbumid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数albumid不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (bp.getBranchid() == null || "".equals(bp.getBranchid())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数branchid不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (bp.getImgurl() == null || "".equals(bp.getImgurl())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数imgurl不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (bp.getSmallimgurl() == null || "".equals(bp.getSmallimgurl())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数smallimgurl不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				if (bp.getDescription() == null || "".equals(bp.getDescription())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					result.setMsg("参数description不能为空！");
					res = new JsonResponse(result);
					return res;
				}
				bp.setCreatetime(new Date());
				bp.setCreateid(CurrentUserContext.getCurrentUserId());
				bp.setDeleteflag(0);
			}
			int status = photodao.insertBranchPhoto(userPhotoList);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[insertBranchPhoto方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse updateByPrimaryKeySelective(Branchphoto record) {
		Result result = null;
		JsonResponse res = null;
		if (record.getImgid() == null || "".equals(record.getImgid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数imgid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			int status = photodao.updateByPrimaryKeySelective(record);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse selectByPrimaryKey(String imgid) {
		Result result = null;
		JsonResponse res = null;
		if (imgid == null || "".equals(imgid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数imgid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			Branchphoto branchphoto = photodao.selectByPrimaryKey(imgid);
			if (branchphoto != null) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				res.setData(branchphoto);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse batchDelete(String albumids) {
		Result result = null;
		JsonResponse res = null;
		if (albumids == null || "".equals(albumids)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数albumids不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			String albumid = albumids.substring(0, albumids.length());
			String albumidArray[] = albumid.split(",");
			int status = badao.batchDelete(albumidArray);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public JsonResponse changeStatus(Branchalbum branchAlbum) {
		Result result = null;
		JsonResponse res = null;
		if (branchAlbum.getAlbumid() == null || "".equals(branchAlbum.getAlbumid())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数albumid不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (branchAlbum.getDeleteflag() == null || "".equals(branchAlbum.getDeleteflag())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数deleteflag不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			int status = badao.updateByPrimaryKeySelective(branchAlbum);
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	@Override
	public List<Branchphoto> selectByBranch(List<String> branchids) {
		return null;
	}

	/**
	 * 以下方法用于api
	 */

	@Override
	public JsonResponse getAlbum(Branchalbum entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getFamilyid() == null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if (entity.getMeautype() == null) {
				result = new Result(MsgConstants.MEAUTYPE_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}

			List<Branchalbum> albums = new ArrayList<>();
			BranchalbumExample example = new BranchalbumExample();
			example.setOrderByClause("sort asc,createtime desc");
			if (entity.getStart() != null && entity.getCount() != null) {
				example.setPageNo(entity.getStart().intValue() + 1);
				example.setPageSize(entity.getCount().intValue());
			}

			if (0 == entity.getMeautype()) {
				// 获取家族得相册列表
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE).andFamilyidEqualTo(entity.getFamilyid())
						.andTypeEqualTo(0);
				albums = badao.selectByExample(example);

			} else if (1 == entity.getMeautype()) {
				// 获取全部相册
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE).andFamilyidEqualTo(entity.getFamilyid());
				albums = badao.selectByExample(example);
			} else if (2 == entity.getMeautype()) {
				if (entity.getBranchid() == null || "".equals(entity.getBranchid())) {
					result = new Result(MsgConstants.BRANCHID_IS_NULL);
					res = new JsonResponse(result);
					return res;
				}

				// 按照城市编码获取相册列表
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("startRow", entity.getStart());
				params.put("pageSize", entity.getCount());
				params.put("cityCode", entity.getBranchid());
				params.put("familyid", entity.getFamilyid());

				albums = badao.selectByCityCode(params);
			} else if (3 == entity.getMeautype()) {
				if (entity.getBranchid() == null || "".equals(entity.getBranchid())) {
					result = new Result(MsgConstants.BRANCHID_IS_NULL);
					res = new JsonResponse(result);
					return res;
				}
				// 按照分支id获取相册列表
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE).andBranchidEqualTo(entity.getBranchid());
				albums = badao.selectByExample(example);
			}

			addAlbumNumAndPreImg(albums);

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(albums);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchalbumServiceImpl---Error:]", e);
		}
		return res;
	}

	/**
	 * 填充头图与图片数量
	 * 
	 * @描述
	 * @作者 jinlizhi
	 * @时间 2017年9月6日下午5:05:16
	 * @参数 @param branchAlbumList
	 * @return void
	 */
	private void addAlbumNumAndPreImg(List<Branchalbum> branchAlbumList) {
		// 填充图片数量
		BranchphotoExample photoEp = new BranchphotoExample();
		for (Branchalbum branchAlbum : branchAlbumList) {
			photoEp.clear();
			photoEp.setOrderByClause("createtime asc");
			photoEp.or().andAlbumidEqualTo(branchAlbum.getAlbumid()).andBranchidEqualTo(branchAlbum.getBranchid())
					.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<Branchphoto> photeList = photodao.selectByExample(photoEp);
			if (photeList != null) {
				// 填充头图
				branchAlbum.setPhotoNum(photeList.size());
				if (photeList.size() > 0) {
					String smallimgurl = photeList.get(0).getSmallimgurl();
					branchAlbum.setPrePhotoImg(smallimgurl);
				}
			}

		}
	}

	@Override
	public JsonResponse getPhotolistOfBranchAlbum(Branchphoto entity) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (entity.getAlbumid() == null || "".equals(entity.getAlbumid())) {
				result = new Result(MsgConstants.ALBUMID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			Map<String, String> map = new HashMap<String, String>();
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}
			map.put("albumid", entity.getAlbumid());
			List<Branchphoto> branchPhotos = photodao.selectPhotosByAlbumid(map);
			PhotosVO vo = new PhotosVO();
			if (branchPhotos == null || branchPhotos.size() == 0) {
				result = new Result(result);
				result.setMsg("没有相片");
				res = new JsonResponse(result);
				res.setData(vo);
				return res;
			}
			Branchalbum album = badao.selectByPrimaryKey(entity.getAlbumid());
			// 构造返回

			vo.setAlbumname(album.getAlbumname());
			vo.setRemake(album.getRemark());
			vo.setCreatetime(album.getCreatetime());

			vo.setPhotos(branchPhotos);

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(vo);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[BranchalbumServiceImpl---Error:]", e);
		}
		return res;
	}

	@Override
	public JsonResponse getAlbumAndPhotos(String albumid, String branchid) {
		Result result = null;
		JsonResponse res = null;
		List<Branchphoto> photoList = null;
		Branchalbum branchalbum = null;
		try {
			BranchphotoExample example = new BranchphotoExample();
			com.jp.entity.BranchphotoExample.Criteria criteria = example.createCriteria();
			if (StringTools.trimNotEmpty(albumid)) {
				criteria.andAlbumidEqualTo(albumid);
			}
			criteria.andDeleteflagEqualTo(0);
			photoList = photodao.selectByExample(example);
			branchalbum = badao.selectByPrimaryKey(albumid);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(photoList);
		res.setEntity(branchalbum);
		return res;
	}
}
