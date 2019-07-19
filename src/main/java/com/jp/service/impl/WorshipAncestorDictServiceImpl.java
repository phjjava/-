package com.jp.service.impl;

import com.jp.entity.GenUserOther;
import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.entity.WorshipAncestorDict;
import com.github.pagehelper.PageHelper;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.UserDao;
import com.jp.dao.WorshipAncestorDictMapper;
import com.jp.dao.WorshipAnnexMapper;
import com.jp.dao.WorshipOblationMapper;
import com.jp.dao.WorshipOblationTypeMapper;
import com.jp.service.WorshipAncestorDictService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

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
	@Override
	public JsonResponse getWorshipAncestorDictList(String familyid) {
		Result result = null;
		JsonResponse res = null;
		List<WorshipAncestorDict> selectList =null;
		Integer genlevel=null;
		try {
			if(familyid == null || "".equals(familyid)){
				result=new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数familyid为空！");
				res = new JsonResponse(result);
				return res;
			}
		    genlevel = userMapper.getUserFamilyid(familyid);
			//System.out.println("genlevel=="+genlevel);
			Integer last=null;
			//5世为一个世图 计算总共有几个世图
			if(genlevel%5==0) {
				int end=genlevel/5;
				last=end;
			}else {
				int end=genlevel/5+1;
				last=end;
			}
			selectList = worshipAncestorDictMapper.selectList(last);
		}catch (Exception e) {
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
	public JsonResponse getWorshipAncestorList(String familyid, Integer genlevel,String familyname,Integer pagesize,Integer pageNo) {
		Result result = null;
		JsonResponse res = null;
		List<GenUserOther> list=new ArrayList<GenUserOther>();
		Integer userByAncestorCount = 0;
		try {
			if(familyid == null || "".equals(familyid)){
				result=new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数familyid为空！");
				res = new JsonResponse(result);
				return res;
			}
			if(genlevel == null ){
				result=new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数genlevel为空！");
				res = new JsonResponse(result);
				return res;
			}
			if(familyname == null || "".equals(familyname)){
				result=new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数familyid为空！");
				res = new JsonResponse(result);
				return res;
			}
			if(pagesize == null ){
				result=new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("参数pagesize为空！");
				res = new JsonResponse(result);
				return res;
			}
//			if(pageNo == null ){
//				result=new Result(MsgConstants.RESUL_FAIL);
//				result.setMsg("参数pageNo为空！");
//				return res;
//			}
			if(pageNo == null) {
				pageNo=1;
			}
			
			System.out.println(familyname);
			String substringBefore = StringUtils.substringBefore(familyname, "氏"); 
			familyname=substringBefore+"%";
			System.out.println(familyname);
			userByAncestorCount = userMapper.getUserByAncestorCount(familyid, familyname, genlevel);
			System.out.println("userByAncestorCount==="+userByAncestorCount);
			if(userByAncestorCount==0) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("该世没有插入数据！");
				res = new JsonResponse(result);
				return res;
			}
			PageHelper.startPage(pageNo, pagesize);
			List<User> userByAncestor = userMapper.getUserByAncestor(familyid, familyname, genlevel);
			for (User user : userByAncestor) {
				// 初始化起始人实例
				GenUserOther genUserOther = new GenUserOther();
				genUserOther.setGenlevel(user.getGenlevel());
				genUserOther.setImgurl(user.getImgurl());
				genUserOther.setSex(user.getSex());
				genUserOther.setUserid(user.getUserid());
				genUserOther.setUsername(user.getUsername());
				genUserOther.setPid(user.getPid());

				User mate_user = new User();
				// 初始化配偶实例
				if (user.getMateid() == null || "".equals(user.getMateid())) {
					// 不存在配偶的情况
				} else {
					UserQuery userExample = new UserQuery();
					userExample.or().andUseridEqualTo(user.getMateid()).andDeleteflagEqualTo(0).andStatusEqualTo(0);
					List<User> users2 = userMapper.selectByExample(userExample);
					if (users2.size() > 0) {
						mate_user = users2.get(0);
					}
				}
				
				GenUserOther mateuser = new GenUserOther();
				mateuser.setGenlevel(mate_user.getGenlevel());
				mateuser.setImgurl(mate_user.getImgurl());
				mateuser.setSex(mate_user.getSex());
				mateuser.setUserid(mate_user.getUserid());
				mateuser.setUsername(mate_user.getUsername());
				genUserOther.setMate(mateuser);
				list.add(genUserOther);
			}
			
		} catch (Exception e) {
			log_.error("[getWorshipAncestorList方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData1(userByAncestorCount);
		
		res.setData(list);
		return res;
	}

}
