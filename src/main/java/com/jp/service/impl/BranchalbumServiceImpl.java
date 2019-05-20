package com.jp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jp.common.ConstantUtils;
import com.jp.common.CurrentUserContext;
import com.jp.common.PageModel;
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.BranchphotoMapper;
import com.jp.entity.Branchalbum;
import com.jp.entity.BranchalbumExample;
import com.jp.entity.Branchphoto;
import com.jp.entity.BranchphotoExample;
import com.jp.entity.UserManager;
import com.jp.service.BranchalbumService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Service
public class BranchalbumServiceImpl implements BranchalbumService {
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
}
