package com.jp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.jp.dao.MomentFilterMapper;
import com.jp.dao.MomentMapper;
import com.jp.dao.MomentTimelineMapper;
import com.jp.dao.MomentUserFilterMapper;
import com.jp.dao.UserDao;
import com.jp.entity.Moment;
import com.jp.entity.MomentComment;
import com.jp.entity.MomentFilter;
import com.jp.entity.MomentFilterExample;
import com.jp.entity.MomentLikeTimeline;
import com.jp.entity.MomentTimeline;
import com.jp.entity.MomentTimelineExample;
import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.service.MomentCommentService;
import com.jp.service.MomentLikeService;
import com.jp.service.MomentService;
import com.jp.service.UserService;
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
	@Resource
	private MomentFilterMapper momentFilterMapper;
	@Resource
	private MomentLikeService momentLikeService;
	@Resource
	private MomentCommentService momentCommentService;
	@Resource
	private UserService userService;

	@Override
	public JsonResponse createMoment(Moment entity, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			JsonResponse demoUser = userService.checkDemoUser();
			if (demoUser.getCode() == 1) {
				return demoUser;
			}
			if (StringUtils.isBlank(entity.getShowType())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("参数showtype为空！");
				return res;
			}
			if (!StringUtils.isBlank(entity.getPicture())) {
				//上传图片需做图片url格式校验
				if (!checkJsonFormat(entity.getPicture())) {
					result = new Result(MsgConstants.RESUL_FAIL);
					res = new JsonResponse(result);
					res.setMsg("参数picture格式不正确！");
					return res;
				}
			}
			String userid = WebUtil.getHeaderInfo("userid");
			String familyid = WebUtil.getHeaderInfo("familyid");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间
			Date createtime = df.parse(date);//将字符串日期转化为Date类型

			//族圈主表
			//			Moment moment = new Moment();
			String uuid = UUIDUtils.getUUID();
			entity.setUserid(userid);
			entity.setId(uuid);
			entity.setDeleteflag(ConstantUtils.DELETE_FALSE);
			entity.setCreateby(userid);
			entity.setLikeNum(0);
			entity.setCreatetime(createtime);
			momentMapper.insert(entity);
			//族圈时间轴插入自己
			MomentTimeline timeline = new MomentTimeline();
			timeline.setId(UUIDUtils.getUUID());
			timeline.setDeleteflag(0);
			timeline.setIsOwn((byte) 1);
			timeline.setMomentId(uuid);
			timeline.setUserid(userid);
			timeline.setCreateby(userid);
			timeline.setCreatetime(createtime);
			momentTimelimeMapper.insert(timeline);

			// 插入时间轴
			inertMomentTimeline(uuid, familyid, userid, entity.getShowType(), entity.getTagid(), entity.getTagtype());

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			return res;
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			log_.error("[MomentServiceImpl---Error:]", e);
			return res;
		}
	}

	@Override
	public JsonResponse getMomentList(Moment entity, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = request.getHeader("userid");
			if (userid == null || "".equals(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("不存在的用户！");
				return res;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			if (entity.getStart() != null && entity.getCount() != null) {
				params.put("start", entity.getStart());
				params.put("count", entity.getCount());
			}
			params.put("userid", userid);
			//查询族圈时间轴获取对应族圈内容
			List<Moment> moments = momentTimelimeMapper.selectMomentByUserid(params);
			//查询人是否可见动态的权限

			//添加点赞和评论
			getMomentTail(moments);
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
	public JsonResponse getPersonMomentList(Moment entity, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			if (StringUtils.isBlank(entity.getUserid())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				res = new JsonResponse(result);
				res.setMsg("参数userid为空！");
				return res;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			if (entity.getStart() != null && entity.getCount() != null) {
				params.put("start", entity.getStart());
				params.put("count", entity.getCount());
			}
			params.put("userid", entity.getUserid());
			List<Moment> moments = momentTimelimeMapper.selectPersonListByUserid(params);
			//添加点赞和评论
			getMomentTail(moments);
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

	private void getMomentTail(List<Moment> list) {
		//Result result = new Result();
		if (list != null && list.size() > 0) {
			MomentComment momentComment = new MomentComment();
			for (Moment moment : list) {
				if (moment.getUserid().equals(WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID))
						&& (ConstantUtils.MOMENT_ISIBLE.equals(moment.getShowType())
								|| ConstantUtils.MOMENT_INVISIBLE.equals(moment.getShowType()))) {
					MomentFilterExample example = new MomentFilterExample();
					example.or().andMomentidEqualTo(moment.getId());
					List<MomentFilter> filters = momentFilterMapper.selectByExample(example);

				}
				momentComment.setMomentId(moment.getId());
				User user = userMapper.selectByPrimaryKey(moment.getUserid());
				if (user != null) {
					moment.setImgurl(user.getImgurl());
					moment.setUsername(user.getUsername());
				}

				//获取点赞时间轴列表
				List<MomentLikeTimeline> likes = (List<MomentLikeTimeline>) momentLikeService
						.getMomentLikeInfo(moment.getId()).getData();
				moment.setLikeTimelines(likes);

				//获取评论列表
				List<MomentComment> comments = (List<MomentComment>) momentCommentService
						.getAllMomentComment(momentComment).getData();
				System.out.println("输出=" + comments);

				if (comments != null && comments.size() > 0)
					moment.setComments(comments);

				//获取不让谁看、让谁看的用户列表
				MomentFilterExample fex = new MomentFilterExample();
				fex.or().andMomentidEqualTo(moment.getId()).andFiltertypeEqualTo("person");
				List<MomentFilter> momentFilters = momentFilterMapper.selectByExample(fex);
				List<User> userList = new ArrayList<User>();
				for (MomentFilter momentFilter : momentFilters) {
					User u = userMapper.selectByPrimaryKey(momentFilter.getFilterid());
					userList.add(u);
				}
				moment.setFilterusers(userList);
			}
		}
	}

	/**
	 * 校验图片地址是否符合规定格式
	 * @param json 被校验的字符串：图片url信息
	 * @return
	 */
	public boolean checkJsonFormat(String json) {
		boolean checkResult = false;
		try {
			if (JsonValidator.validate(json) && JsonValidator.isJsonObject(json)) {
				JsonObject jsonObj = new JsonParser().parse(json).getAsJsonObject();
				JsonArray imgArray = jsonObj.getAsJsonArray("imgUrl");
				JsonArray soundArray = jsonObj.getAsJsonArray("sounds");
				JsonArray videoArray = jsonObj.getAsJsonArray("vedio");
				JsonObject url = jsonObj.getAsJsonObject("url");
				if (imgArray != null && imgArray.size() > 0) {
					checkResult = true;
				}
				if (soundArray != null && soundArray.size() > 0) {
					checkResult = true;
				}
				if (videoArray != null && videoArray.size() > 0) {
					checkResult = true;
				}
				if (url != null) {
					checkResult = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	public void inertMomentTimeline(final String id, final String familyid, final String userid, final String showType,
			final String tagid, final String tagtype) throws Exception {
		executor.submit(new Runnable() {

			@Override
			public void run() {
				try {

					//用于存放发布可见用户
					List<User> userList = new ArrayList<User>();
					List<MomentTimeline> lines = new ArrayList<MomentTimeline>();
					UserQuery example = new UserQuery();

					//查询所有的用户
					if (ConstantUtils.MOMENT_ALL.equals(showType)) {

						example.or().andDeleteflagEqualTo(0).andStatusEqualTo(0).andFamilyidEqualTo(familyid)
								.andUseridNotEqualTo(userid).andLivestatusEqualTo(0).andStatusEqualTo(0);
						userList = userMapper.selectByExample(example);
					} else if (ConstantUtils.MOMENT_ISIBLE.equals(showType)) {
						//可见
						//选择分支branch    选择标签tag   个人person
						//tagid为branchid tagid为标签id tagid为用户id
						String[] strs = StringUtils.split(tagid, ",");

						if ("branch".equals(tagtype)) {
							userList = userMapper.selectUserByBranchids(strs);
						} else if ("tag".equals(tagtype)) {
							userList = userMapper.selectUserByTag(strs);
						} else if ("person".equals(tagtype)) {
							userList = userMapper.selectByUserids(strs, familyid);
							inserFilter(userList, id, "preson");
						}
					} else if (ConstantUtils.MOMENT_INVISIBLE.equals(showType)) {
						//不可见
						//选择分支branch    选择标签tag   个人person
						String[] strs = StringUtils.split(tagid, ",");

						if ("branch".equals(tagtype)) {
							userList = userMapper.selectUserByNoBranchids(strs, familyid);
						} else if ("tag".equals(tagtype)) {
							userList = userMapper.selectUserByNoTag(strs, familyid);
						} else if ("person".equals(tagtype)) {

							List<String> resultList = new ArrayList<>(strs.length);
							Collections.addAll(resultList, strs);
							resultList.add(userid);
							userList = userMapper.selectByNoUserids(resultList, familyid);
							List<User> ulist = userMapper.selectByUserids(strs, familyid);
							inserFilter(ulist, id, "preson");
						}
					}
					lines = getMomentUser(userList, userid, id);
					momentTimelimeMapper.insertBatch(lines);
					System.out.println("插入完成！" + lines);
				} catch (Exception e) {

					e.printStackTrace();

				}

			}
		});
	}

	/**
	 * 选择性查看插入记录表
	 * @param userList 用户列表
	 * @param id	momentid
	 * @param type  选择类型
	 * @return
	 */
	private Integer inserFilter(List<User> userList, String id, String type) {
		List<MomentFilter> filters = new ArrayList<MomentFilter>();
		for (User u : userList) {
			MomentFilter f = new MomentFilter();
			f.setFilterid(u.getUserid());
			f.setFiltertype("person");
			f.setMomentid(id);
			filters.add(f);
		}
		return momentFilterMapper.insertBatch(filters);
	}

	/**
	 * 获取插入的用户列表
	 */
	public List<MomentTimeline> getMomentUser(List<User> userList, String userid, String id) {
		List<MomentTimeline> lines = new ArrayList<MomentTimeline>();
		//查询过滤得用户   不看谁的，不让谁看
		//List<String> momentusers =  momentUserFilterMapper.selectFilterUsers(userid);
		if (userList != null && userList.size() > 0) {
			Date date = new Date();

			for (User user : userList) {
				//是否包含不让我看和我不让看的，如果有，则不插入时间轴表
				//				if(momentusers !=null && momentusers.size()>0 && momentusers.contains(user.getUserid())) {
				//					
				//				}else {
				MomentTimeline timeline = new MomentTimeline();
				timeline.setId(UUIDUtils.getUUID());
				timeline.setDeleteflag(0);
				timeline.setCreateby(userid);
				timeline.setIsOwn((byte) 0);
				timeline.setMomentId(id);
				timeline.setCreatetime(date);
				timeline.setUserid(user.getUserid());
				lines.add(timeline);
				//				}

			}
		}
		return lines;
	}

	@Override
	public JsonResponse delMoment(Moment entity, HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		JsonResponse demoUser = userService.checkDemoUser();
		if (demoUser.getCode() == 1) {
			return demoUser;
		}
		if (StringUtils.isBlank(entity.getId())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数主体id不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		entity.setDeleteflag(1);
		int status = momentMapper.updateByPrimaryKeySelective(entity);
		if (status < 1) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("删除失败！不存在的动态！");
			res = new JsonResponse(result);
			return res;
		}
		MomentTimeline timeline = new MomentTimeline();
		timeline.setDeleteflag(1);
		//timeline.setMomentId(entity.getId());
		MomentTimelineExample ex = new MomentTimelineExample();
		ex.or().andMomentIdEqualTo(entity.getId());
		int count = momentTimelimeMapper.updateByExampleSelective(timeline, ex);
		if (count < 1) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("删除族圈时间轴失败！");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		return res;
	}
}
