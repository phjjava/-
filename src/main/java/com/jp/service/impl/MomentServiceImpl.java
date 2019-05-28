package com.jp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.MomentMapper;
import com.jp.dao.MomentTimelineMapper;
import com.jp.dao.MomentUserFilterMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Moment;
import com.jp.entity.MomentTimeline;
import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.service.MomentService;
import com.jp.util.JsonValidator;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Service
public class MomentServiceImpl implements MomentService {
	
	private final Logger log_ = LogManager.getLogger(MomentServiceImpl.class);
	
	//异步执行线程池
	private ExecutorService executor = Executors.newCachedThreadPool();
	
	@Resource
	private MomentMapper momentMapper;
	@Resource
	private MomentTimelineMapper momentTimelimeMapper;
	@Resource
	private MomentUserFilterMapper momentUserFilterMapper;
	@Resource
	private UserDao userMapper;
		

	@Override
	public JsonResponse createMoment(Moment entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			if(StringUtils.isBlank(entity.getShowType())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("参数showtype为空！");
				return res;
			}
			if(!StringUtils.isBlank(entity.getPicture())) {
				//上传图片需做图片url格式校验
				if(!checkJsonFormat(entity.getPicture())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					res = new JsonResponse(result);
					res.setMsg("参数picture格式不正确！");
					return res;
				}
			}
			String userid = WebUtil.getHeaderInfo(request.getHeader("userid"));
			String familyid = WebUtil.getHeaderInfo(request.getHeader("familyid"));
			//族圈主表
//			Moment moment = new Moment();
			String uuid = UUIDUtils.getUUID();
			entity.setUserid(userid);
			entity.setId(uuid);
			entity.setDeleteflag(ConstantUtils.DELETE_FALSE);
			entity.setCreateby(WebUtil.getHeaderInfo(request.getHeader("userid")));
			entity.setLikeNum(0);
			momentMapper.insert(entity);
			//族圈时间轴插入自己
			MomentTimeline timeline = new MomentTimeline();
			timeline.setId(UUIDUtils.getUUID());
			timeline.setDeleteflag(0);
			timeline.setIsOwn((byte)1);
			timeline.setMomentId(uuid);
			timeline.setUserid(userid);
			timeline.setCreateby(userid);
			momentTimelimeMapper.insert(timeline);
			
			// 插入时间轴
			inertMomentTimeline(uuid,familyid,userid,entity.getShowType(),entity.getTagid(),entity.getTagtype());
			
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			return res ;
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[MomentServiceImpl---Error:]", e);
			return res;
		}
	}

	@Override
	public JsonResponse getMomentList(Moment entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = request.getHeader("userid");
			if(userid == null || "".equals(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("不存在的用户！");
				return res;
			}
			Map<String,Object> params = new HashMap<String,Object>();
			if(entity.getStart()!=null && entity.getCount()!=null) {
				params.put("start", entity.getStart());
				params.put("count", entity.getCount());
			}
			params.put("userid", userid);
			//查询族圈时间轴获取对应族圈内容
			List<Moment> moments = momentTimelimeMapper.selectMomentByUserid(params);
			
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(moments);
			return res;
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[MomentServiceImpl---Error:]", e);
			return res;
		}
	}

	@Override
	public JsonResponse getPersonMomentList(Moment entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			if(StringUtils.isBlank(entity.getUserid())){
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("参数userid为空！");
				return res;
			}
			Map<String,Object> params = new HashMap<String,Object>();
			if(entity.getStart()!=null && entity.getCount()!=null) {
				params.put("start", entity.getStart());
				params.put("count", entity.getCount());
			}
			params.put("userid", entity.getUserid());
			List<Moment> moments = momentTimelimeMapper.selectPersonListByUserid(params);
			
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(moments);
			return res;
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[MomentServiceImpl---Error:]", e);
			return res;
		}
	}
	
	/**
	 * 校验图片地址是否符合规定格式
	 * @param json 被校验的字符串：图片url信息
	 * @return
	 */
	public boolean checkJsonFormat(String json) {
		boolean checkResult = false;
		if(JsonValidator.validate(json) && JsonValidator.isJsonObject(json)) {
			JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
			JsonArray imgArray = jsonObj.getAsJsonArray("imgUrl");
			if(imgArray != null && imgArray.size() > 0) {
				checkResult = true;
			}	
		}
		return checkResult;
	}

	/**
	 * 用于执行异步任务，异步插入倒族圈的时间轴表 
	 * @param id
	 * @param familyid
	 * @param userid
	 * @param showType
	 * @throws Exception
	 */
	public void inertMomentTimeline(final String id,final String familyid,
					final String userid,final String showType,
					final String tagid,final String tagtype) throws Exception {
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					//用于存放发布可见用户
					List<User> userList = new ArrayList<User>();
					List<MomentTimeline> lines = new ArrayList<MomentTimeline>();
					UserQuery example = new UserQuery();
					
					//查询所有的用户
					if(ConstantUtils.MOMENT_ALL.equals(showType)) {
						
						example.or().andDeleteflagEqualTo(0)
									.andStatusEqualTo(0)
									.andFamilyidEqualTo(familyid)
									.andUseridNotEqualTo(userid)
									.andLivestatusEqualTo(0)
									.andStatusEqualTo(0);
						userList = userMapper.selectByExample(example);
					}else if(ConstantUtils.MOMENT_SELECT.equals(showType)) {
						//选择分支branch    选择标签tag
						String[] strs = StringUtils.split(tagid, ",");
						if("branch".equals(tagtype)) {
							userList = userMapper.selectUserByBranchids(strs);
						}else if("tag".equals(tagtype)) {
							userList = userMapper.selectUserByTag(strs);
						}
					}
					lines = getMomentUser(userList,userid,id);
					momentTimelimeMapper.insertBatch(lines);
					System.out.println("插入完成！");
				}catch(Exception e) {

					e.printStackTrace();

				}
				
			}
		});
	}
	
	/**
	 * 获取插入的用户列表
	 */
	public List<MomentTimeline> getMomentUser(List<User> userList,String userid,String id){
		List<MomentTimeline> lines = new ArrayList<MomentTimeline>();
		//查询过滤得用户   不看谁的，不让谁看
		//List<String> momentusers =  momentUserFilterMapper.selectFilterUsers(userid);
		if(userList!=null && userList.size()>0) {
			for(User user : userList) {
				//是否包含不让我看和我不让看的，如果有，则不插入时间轴表
//				if(momentusers !=null && momentusers.size()>0 && momentusers.contains(user.getUserid())) {
//					
//				}else {
					MomentTimeline timeline = new MomentTimeline();
					timeline.setId(UUIDUtils.getUUID());
					timeline.setDeleteflag(0);
					timeline.setCreateby(userid);
					timeline.setIsOwn((byte)0);
					timeline.setMomentId(id);
					timeline.setUserid(user.getUserid());
					lines.add(timeline);
//				}
				
			}
		}
		return lines;
	}
}
