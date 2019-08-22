package com.jp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.UserinfoMapper;
import com.jp.dao.WorshipAncestorDictMapper;
import com.jp.dao.WorshipAnnexMapper;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.entity.GenUserOtherVO;
import com.jp.entity.WorshipAncestorDict;
import com.jp.service.WorshipAncestorDictService;

/**
 * <p>
 * 家谱祭祖世系字典表 服务实现类
 * </p>
 *
 * @author SongMingWei
 * @since 2019-07-04
 */
@Service
public class WorshipAncestorDictServiceImpl implements WorshipAncestorDictService {

	private final Logger log_ = LogManager.getLogger(WorshipAncestorDictServiceImpl.class);
	@Resource
	private WorshipAncestorDictMapper worshipAncestorDictMapper;
	@Resource
	private WorshipOblationMapper oblationMapper;
	@Resource
	private WorshipOblationTypeMapper oblationTypeMapper;
	@Resource
	private WorshipAnnexMapper annexMapper;
	@Resource
	private UserDao userMapper;
	@Resource
	private UserinfoMapper userinfoMapper;

	@Override
	public JsonResponse getWorshipAncestorDictList(String familyid) {
		Result result = null;
		JsonResponse res = null;
		List<WorshipAncestorDict> selectList = null;
		Integer genlevel = null;
		try {
			if (familyid == null || "".equals(familyid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数familyid为空！");
				res = new JsonResponse(result);
				return res;
			}
			genlevel = userMapper.getUserFamilyid(familyid);
			if(genlevel == 0 ) {
				result = new Result(ConstantUtils.RESULT_SUCCESS,"暂无数据");
				res = new JsonResponse(result);
				return res;
			}
			//System.out.println("genlevel=="+genlevel);
			Integer last = null;
			//5世为一个世图 计算总共有几个世图
			if (genlevel % 5 == 0) {
				int end = genlevel / 5;
				last = end;
			} else {
				int end = genlevel / 5 + 1;
				last = end;
			}
			selectList = worshipAncestorDictMapper.selectList(last);
		} catch (Exception e) {
			log_.error("[getWorshipAncestorDictList方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(selectList);
		res.setData1(genlevel);//当前家族共有多少世
		return res;
	}

	@Override
	public JsonResponse getWorshipAncestorList(String familyid, Integer genlevel, String familyname, Integer pagesize,
			Integer pageNo) {
		Result result = null;
		JsonResponse res = null;
		List<GenUserOtherVO> list = new ArrayList<GenUserOtherVO>();
		Integer userByAncestorCount = 0;
		try {
			if (familyid == null || "".equals(familyid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数familyid为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (genlevel == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数genlevel为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (familyname == null || "".equals(familyname)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数familyid为空！");
				res = new JsonResponse(result);
				return res;
			}
			if (pagesize == null) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数pagesize为空！");
				res = new JsonResponse(result);
				return res;
			}
			//			if(pageNo == null ){
			//				result=new Result(MsgConstants.RESUL_FAIL);
			//				result.setMsg("参数pageNo为空！");
			//				return res;
			//			}
			if (pageNo == null) {
				pageNo = 1;
			}

			System.out.println(familyname);
			String substringBefore = StringUtils.substringBefore(familyname, "氏");
			familyname = substringBefore + "%";
			System.out.println(familyname);
			userByAncestorCount = userMapper.getUserByAncestorCount(familyid, genlevel);
			System.out.println("userByAncestorCount===" + userByAncestorCount);
			if (userByAncestorCount == 0) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("该世系没有人死亡！");
				res = new JsonResponse(result);
				return res;
			}
			//获取某世所有人及其配偶信息
			List<GenUserOtherVO> userByAncestor = userMapper.getUserByAncestor(familyid, familyname, genlevel);

			//筛选数据
			List<GenUserOtherVO> listSX = new ArrayList<GenUserOtherVO>();
			for (GenUserOtherVO user : userByAncestor) {
				if (user.getLivestatus() == 1) {
					listSX.add(user);
				} else {
					if (user.getMatelivestatus() != null && user.getMatelivestatus() == 1) {
						listSX.add(user);
					}
				}

			}
			//listSX数据分页
			//System.out.println("pageNo=start=="+pageNo);
			if (pageNo == 0) {
				pageNo = 1;
			}
			//System.out.println("pageNo=start=="+pageNo);
			//System.out.println("pagesize=count=="+pagesize);
			list = startPage(listSX, pageNo, pagesize);
			userByAncestorCount = listSX.size();

		} catch (Exception e) {
			log_.error("[getWorshipAncestorList方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}

		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		//res.setData1(userByAncestorCount);

		res.setData(list);
		return res;
	}

	/**
	 * 开始分页
	 *
	 * @param list
	 * @param pageNum  页码
	 * @param pageSize 每页多少条数据
	 * @return
	 */
	public static List<GenUserOtherVO> startPage(List<GenUserOtherVO> list, Integer pageNum, Integer pageSize) {
		if (list == null) {
			return null;
		}
		if (list.size() == 0) {
			return null;
		}

		Integer count = list.size(); //记录总数
		Integer pageCount = 0; //页数
		if (count % pageSize == 0) {
			pageCount = count / pageSize;
		} else {
			pageCount = count / pageSize + 1;
		}
		if (pageNum > pageCount) {
			return null;
		}
		int fromIndex = 0; //开始索引
		int toIndex = 0; //结束索引

		if (pageNum != pageCount) {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = fromIndex + pageSize;
		} else {
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = count;
		}

		List<GenUserOtherVO> pageList = list.subList(fromIndex, toIndex);

		return pageList;
	}

}
