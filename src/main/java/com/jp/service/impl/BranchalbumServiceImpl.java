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
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.BranchphotoMapper;
import com.jp.entity.Branchalbum;
import com.jp.entity.BranchalbumExample;
import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;
import com.jp.entity.PhotosVO;
import com.jp.entity.UserManager;
import com.jp.service.BranchalbumService;
import com.jp.util.DateUtils;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class BranchalbumServiceImpl implements BranchalbumService {
	
	private final Logger log_ = LogManager.getLogger(BranchalbumServiceImpl.class);
	
	@Autowired
	private BranchalbumMapper badao;
	@Autowired
	private BranchphotoMapper photodao;

	@Override
	public PageModel<Branchalbum> pageQuery(PageModel<Branchalbum> pageModel, Branchalbum branchalbum)
			throws Exception {
		// 当前登录人所管理的branchids

		PageHelper.startPage(pageModel.getPageNo(), pageModel.getPageSize());
		List<UserManager> managers = CurrentUserContext.getCurrentUserManager();
		String familyid = CurrentUserContext.getCurrentFamilyId();
		List<String> branchList = CurrentUserContext.getCurrentBranchIds();
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
			if (m.getEbtype() == 1) {
				criteria.andFamilyidEqualTo(familyid);
				list = badao.selectByExample(example);
				break;
			} else {
				criteria.andBranchidIn(branchList);
				list = badao.selectBranchAlbumMangeList(example);
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

		for (Branchalbum al : list) {
			example1.clear();
			example1.or().andAlbumidEqualTo(al.getAlbumid()).andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			al.setAlbumNum(photodao.countByExample(example1));
		}
		pageModel.setList(list);
		pageModel.setPageInfo(new PageInfo<Branchalbum>(list));
		return pageModel;
	}

	@Override
	public Branchalbum get(String albumid, String branchid) throws Exception {
		Branchalbum branchalbum = badao.selectByPrimaryKey(albumid);
		return branchalbum;
	}

	@Override
	public String mergeBranchAlbum(Branchalbum branchalbum) {
		String ablumId = "";
		if (StringTools.trimNotEmpty(branchalbum.getAlbumid())) {
			branchalbum.setUpdatetime(new Date());
			branchalbum.setUpdateid(CurrentUserContext.getCurrentUserId());
			BranchalbumExample query = new BranchalbumExample();
			query.or().andAlbumidEqualTo(branchalbum.getAlbumid());
			if (!StringTools.trimNotEmpty(branchalbum.getBranchid())) {
				branchalbum.setBranchid("0");
			}
			branchalbum.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			badao.updateByExampleSelective(branchalbum, query);
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
			badao.insertSelective(branchalbum);
		}
		return ablumId;
	}

	@Override
	public int insertBranchPhoto(List<Branchphoto> userPhotoList) {
		return photodao.insertBranchPhoto(userPhotoList);
	}

	@Override
	public List<Branchphoto> selectByExample(BranchphotoExample example) {
		return photodao.selectByExample(example);
	}

	@Override
	public int updateByPrimaryKeySelective(Branchphoto record) {
		return photodao.updateByPrimaryKeySelective(record);
	}

	@Override
	public Branchphoto selectByPrimaryKey(String key) {
		return photodao.selectByPrimaryKey(key);
	}

	@Override
	public int batchDelete(String[] albumidArray) {
		return badao.batchDelete(albumidArray);
	}

	@Override
	public int changeStatus(Branchalbum branchAlbum) {
		return badao.updateByPrimaryKeySelective(branchAlbum);
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
			if(entity.getFamilyid()==null) {
				result = new Result(MsgConstants.FAMILYID_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			if(entity.getMeautype()==null) {
				result = new Result(MsgConstants.MEAUTYPE_IS_NULL);
				res = new JsonResponse(result);
				return res;
			}
			
			List<Branchalbum> albums = new ArrayList<>();
			BranchalbumExample example = new BranchalbumExample();
			example.setOrderByClause("sort asc,createtime desc");
			if(entity.getStart()!=null && entity.getCount()!=null) {
				example.setPageNo(entity.getStart().intValue()+1);
				example.setPageSize(entity.getCount().intValue());
			}
			
			if(0==entity.getMeautype()) {
				//获取家族得相册列表
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE)
									.andFamilyidEqualTo(entity.getFamilyid())
									.andTypeEqualTo(0);
				albums = badao.selectByExample(example);
				
			}else if(1==entity.getMeautype()) {
				//获取全部相册
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE)
								.andFamilyidEqualTo(entity.getFamilyid());
				albums = badao.selectByExample(example);
			}else if(2==entity.getMeautype()) {
				if(entity.getBranchid()==null || "".equals(entity.getBranchid())) {
					result = new Result(MsgConstants.BRANCHID_IS_NULL);
					res = new JsonResponse(result);
					return res;
				}
					
				//按照城市编码获取相册列表
				Map<String,Object> params  = new HashMap<String,Object>();
				params.put("startRow", entity.getStart());
				params.put("pageSize",entity.getCount());
				params.put("cityCode", entity.getBranchid());
				params.put("familyid", entity.getFamilyid());
				
				albums = badao.selectByCityCode(params);
			}else if(3==entity.getMeautype()) {
				if(entity.getBranchid()==null || "".equals(entity.getBranchid())) {
					result = new Result(MsgConstants.BRANCHID_IS_NULL);
					res = new JsonResponse(result);
					return res;
				}
				//按照分支id获取相册列表
				example.or().andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE)
							.andBranchidEqualTo(entity.getBranchid());
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
     * @描述 
     * @作者 jinlizhi
     * @时间 2017年9月6日下午5:05:16
     * @参数 @param branchAlbumList
     * @return void
     */
	private void addAlbumNumAndPreImg(List<Branchalbum> branchAlbumList) {
		//填充图片数量
        BranchphotoExample photoEp = new BranchphotoExample();
        for (Branchalbum branchAlbum : branchAlbumList) {		
			photoEp.clear();
			photoEp.setOrderByClause("createtime asc");
			photoEp.or().andAlbumidEqualTo(branchAlbum.getAlbumid())
				.andBranchidEqualTo(branchAlbum.getBranchid())
					.andDeleteflagEqualTo(ConstantUtils.DELETE_FALSE);
			List<Branchphoto> photeList = photodao.selectByExample(photoEp);
			if (photeList!=null) {
				//填充头图
				branchAlbum.setPhotoNum(photeList.size());
				if (photeList.size()>0) {
					String smallimgurl = photeList.get(0).getSmallimgurl();
					branchAlbum.setPrePhotoImg(smallimgurl);
				}
			}
			
		}
	}

	@Override
	public JsonResponse getPhotolistOfBranchAlbum(Branchphoto entity) {
		Result result  = null;
		JsonResponse res = null;
		try {
			if (entity.getAlbumid() == null || "".equals(entity.getAlbumid())) {
				result = new Result(MsgConstants.ALBUMID_IS_NULL);
		        res = new JsonResponse(result);
		        return res;
	        }
	     Map<String, String> map=new HashMap<String,String>();
	     if (entity.getStart() != null && entity.getCount() != null) {
	         map.put("start", entity.getStart().toString());
	         map.put("count", entity.getCount().toString());
	     }
	      map.put("albumid", entity.getAlbumid());
	        List<Branchphoto> branchPhotos = photodao.selectPhotosByAlbumid(map);
	        PhotosVO vo = new PhotosVO();
	        if(branchPhotos==null || branchPhotos.size()==0) {
	            result = new Result(result);
	            result.setMsg("没有相片");
	            res = new JsonResponse(result);
	            res.setData(vo);
	            return res;
	        }
	        Branchalbum album = badao.selectByPrimaryKey(entity.getAlbumid());
	        //构造返回
	        
	        vo.setAlbumname(album.getAlbumname());
	        vo.setRemake(album.getRemark());
	        vo.setCreatetime(DateUtils.date2shortStr(album.getCreatetime()));
	        
	       
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
}
