package com.jp.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.BranchalbumMapper;
import com.jp.dao.DynamicMapper;
import com.jp.dao.EventDao;
import com.jp.dao.EventreadDao;
import com.jp.dao.UserDao;
import com.jp.dao.UsercontentDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchKey;
import com.jp.entity.Branchalbum;
import com.jp.entity.Dynamic;
import com.jp.entity.DynamicVO;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Event;
import com.jp.entity.EventreadQuery;
import com.jp.entity.GlobalSearch;
import com.jp.entity.User;
import com.jp.entity.UserContentVO;
import com.jp.service.DynamicService;
import com.jp.service.GlobalSearchService;

@Service
public class GlobalSearchServiceImpl implements GlobalSearchService {

	@Autowired
	private DynamicMapper dynamicMapper;
	@Autowired
	private EventDao eventDao;
	@Autowired
	private EventreadDao eventReadDao;
	@Autowired
	private BranchalbumMapper branchAlbumMapper;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UsercontentDao userContentDao;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private DynamicService dynamicService;
	
	@Override
	public JsonResponse searchAll(GlobalSearch entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String userid = request.getHeader("userid");
			String familyid = request.getHeader("familyid");
			if (entity.getContent() == null || "".equals(entity.getContent())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少查询关键字！");
				res = new JsonResponse(result);
				return res;
			}
			Branch branch = new Branch();
			branch.setParentid(userid);
			branch.setFamilyid(familyid);
			
			Map<String, Object> map = new HashMap<String, Object>();
			//根据编委会权限，可以查询的branchid集合
			Set<String> branchds = hasRights(branch);
			map.put("branchids", branchds);
			map.put("familyid", familyid);
			map.put("content", entity.getContent());
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}
			// 查询动态
			List<Dynamic> dynamics = dynamicMapper.searchAllDynamic(map);

			for (Dynamic dy : dynamics) {
				JsonResponse result2 = dynamicService.getDyDetailExt(dy);
				if (result2.getCode() == 0) {
					DynamicVO data = (DynamicVO) result2.getData();
					String content = data.getDynamic().getDycontent();
					content = content.replaceAll("</?[^>]+>", "");
					content = content.replaceAll("\\s*|\t|\r|\n", "");
					content = content.replaceAll("&nbsp;", " ");
					if (content != null && content.length() > 20) {
						// 去除html标签
						// 截取1/10长度
						content = content.substring(0, 20);

					}
					dy.setDycontent(content);
					dy.setCountReads(data.getCountReads());
					dy.setCountFiles(data.getCountFiles());
					dy.setCountComments(data.getCountComments());
					dy.setCountPrises(data.getCountPrises());
				}

			}

			// 查询大事记
			List<Event> events = eventDao.selectAllEvent(map);
			for (Event event : events) {
				EventreadQuery eventReadExample = new EventreadQuery();
				eventReadExample.or().andEventidEqualTo(event.getEventid());
				int count = eventReadDao.countByExample(eventReadExample);

				String content = event.getEventcontent();
				if (content != null && content.length() > 0) {
					// 去除html标签
					content = content.replaceAll("</?[^>]+>", "");
					content = content.replaceAll("\\s*|\t|\r|\n", "");
					content = content.replaceAll("&nbsp;", " ");
					// 截取1/10长度
					content = content.substring(0, (content.length() / 10));
					event.setEventcontent(content);
				}
				event.setReadcount(count);
			}

			// 查询相册
			List<Branchalbum> branchAlbums = branchAlbumMapper.selectAllAlbum(map);

			// 查询通讯录
			// 查询正式用户、未删除用户、在世用户组合查询的结果
			// 获取当前家族所有有效用户
			List<User> users = userDao.searchUser(map);

			// 查询人物志
			List<UserContentVO> userContentVOs = userContentDao.searchtUserContent(map);
			// 截取内容，保证预览的速度
			for (UserContentVO userContentVO : userContentVOs) {
				if (userContentVO.getContent() != null || !"".equals(userContentVO.getContent())) {
					String content = userContentVO.getContent();
					
					if (content != null && content.length() > 0) {
						// 去除html标签
						content = content.replaceAll("</?[^>]+>", "");
						content = content.replaceAll("\\s*|\t|\r|\n", "");
						content = content.replaceAll("&nbsp;", " ");
						// 截取1/10长度 截取70个字
						if (content.length() > 70)
							content = content.substring(0, 70);
						userContentVO.setContent(content);
					}
				}
			}

			GlobalSearch globalSearch = new GlobalSearch();
			globalSearch.setDynamics(dynamics);
			globalSearch.setEvents(events);
			globalSearch.setBranchAlbums(branchAlbums);
			globalSearch.setUserContentVOs(userContentVOs);
			globalSearch.setUsers(users);

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(globalSearch);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public JsonResponse searchMoreUser(GlobalSearch entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String familyid = request.getHeader("familyid");
			if (entity.getContent() == null || "".equals(entity.getContent())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少查询关键字！");
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyid",familyid);
			map.put("content", entity.getContent());
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}

			// 查询通讯录
			// 查询正式用户、未删除用户、在世用户组合查询的结果
			// 获取当前家族所有有效用户
			List<User> users = userDao.searchUser(map);

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(users);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public JsonResponse searchMoreDynamics(GlobalSearch entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String familyid = request.getHeader("familyid");
			if (entity.getContent() == null || "".equals(entity.getContent())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少查询关键字！");
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyid", familyid);
			map.put("content", entity.getContent());
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}

			// 查询动态
			List<Dynamic> dynamics = dynamicMapper.searchAllDynamic(map);

			for (Dynamic dy : dynamics) {
				JsonResponse result2 = dynamicService.getDyDetailExt(dy);
				if (result2.getCode() == 0) {
					DynamicVO data = (DynamicVO) result2.getData();
					String content = data.getDynamic().getDycontent();
					// System.out.println("原动态内容============="+content);
					content = content.replaceAll("</?[^>]+>", "");
					content = content.replaceAll("\\s*|\t|\r|\n", "");
					content = content.replaceAll("&nbsp;", " ");
					if (content != null && content.length() > 20) {
						// 去除html标签
						// 截取1/10长度
						content = content.substring(0, 20);

					}
					dy.setDycontent(content);
					dy.setCountReads(data.getCountReads());
					dy.setCountFiles(data.getCountFiles());
					dy.setCountComments(data.getCountComments());
					dy.setCountPrises(data.getCountPrises());
				}
			}
			
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(dynamics);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public JsonResponse searchMoreEvents(GlobalSearch entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String familyid = request.getHeader("familyid");
			if (entity.getContent() == null || "".equals(entity.getContent())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少查询关键字！");
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyid", familyid);
			map.put("content", entity.getContent());
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}

			List<Event> events = eventDao.selectAllEvent(map);
			for (Event event : events) {
				EventreadQuery eventReadExample = new EventreadQuery();
				eventReadExample.or().andEventidEqualTo(event.getEventid());
				int count = eventReadDao.countByExample(eventReadExample);

				String content = event.getEventcontent();
				if (content != null && content.length() > 0) {
					// 去除html标签
					content = content.replaceAll("</?[^>]+>", "");
					content = content.replaceAll("\\s*|\t|\r|\n", "");
					content = content.replaceAll("&nbsp;", " ");
					// 截取1/10长度
					content = content.substring(0, (content.length() / 10));
					event.setEventcontent(content);
				}
				event.setReadcount(count);
			}
			
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(events);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public JsonResponse searchMoreUserContentVOs(GlobalSearch entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String familyid = request.getHeader("familyid");
			if (entity.getContent() == null || "".equals(entity.getContent())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少查询关键字！");
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyid", familyid);
			map.put("content", entity.getContent());
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}
			// 查询人物志
			List<UserContentVO> userContentVOs = userContentDao.searchtUserContent(map);
			// 截取内容，保证预览的速度
			for (UserContentVO userContentVO : userContentVOs) {
				if (userContentVO.getContent() != null || !"".equals(userContentVO.getContent())) {
					String content = userContentVO.getContent();
					if (content != null && content.length() > 0) {
						// 去除html标签
						content = content.replaceAll("</?[^>]+>", "");
						content = content.replaceAll("\\s*|\t|\r|\n", "");
						content = content.replaceAll("&nbsp;", " ");
						// 截取1/10长度 截取70个字
						if (content.length() > 70)
							content = content.substring(0, 70);
						userContentVO.setContent(content);
					}
				}
			}

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userContentVOs);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public JsonResponse searchMoreBranchAlbums(GlobalSearch entity,HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		try {
			String familyid = request.getHeader("familyid");
			if (entity.getContent() == null || "".equals(entity.getContent())) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少查询关键字！");
				res = new JsonResponse(result);
				return res;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyid", familyid);
			map.put("content", entity.getContent());
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}

			// 查询相册
			List<Branchalbum> branchAlbums = branchAlbumMapper.selectAllAlbum(map);
			
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(branchAlbums);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}
	
	private Set<String> hasRights(Branch entity) {

		// 查询是否具备当前分支查看权限
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", entity.getParentid());
		map.put("familyid", entity.getFamilyid());
		List<EditorialBoard> editorialBoards = userDao.selectManagerBranchidsByUserid(map);

		// 定义当前用户具备权限的分支列表
		Set<String> branchids = new HashSet<String>();
		for (EditorialBoard editorialBoard : editorialBoards) {
			if (editorialBoard.getCodetype().equals("0"))
				return branchids;
			else {
				if ("1".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
				{
					List<String> areaBranchids = branchDao.selectBranchidsByAreacode(editorialBoard.getCode(),
							entity.getFamilyid());
					branchids.addAll(areaBranchids);
				}
				if ("2".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
				{
					List<String> areaBranchids = branchDao.selectBranchidsByCitycode(editorialBoard.getCode(),
							entity.getFamilyid());
					branchids.addAll(areaBranchids);
				}
				if ("3".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
				{
					List<String> areaBranchids = branchDao.selectBranchidsByQxcode(editorialBoard.getCode(),
							entity.getFamilyid());
					branchids.addAll(areaBranchids);
				}
				if ("4".equals(editorialBoard.getCodetype())) // 0总1省2市3区4分支
					branchids.add(editorialBoard.getCode());
			}
		}
		//查询当前用户所属分支
		User user = userDao.selectByPrimaryKey(entity.getParentid());
		if(user.getBranchid() !=null && !"".equals(user.getBranchid())) {
			BranchKey key = new BranchKey();
			key.setBranchid(user.getBranchid());
			key.setFamilyid(user.getFamilyid());
			Branch branch = branchDao.selectByPrimaryKey(key);
			branchids.add(branch.getBranchid());
		}
		branchids.add("0");	
		
		// 如果既不是总编委也不在管理范围内并且个人不在当前分支的分支列表，则认为没有权限,
		return branchids;
		
	}
	
	@Override
	public JsonResponse searchMoreUserContentVOsNew(GlobalSearch entity, HttpServletRequest request, String username) {
		// TODO Auto-generated method stub
		Result result = null;
		JsonResponse res = null;
		try {
			String familyid = request.getHeader("familyid");
			/*if (username == null || "".equals(username)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("缺少查询关键字！");
				res = new JsonResponse(result);
				return res;
			}*/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("familyid", familyid);
			map.put("username", username);
			if (entity.getStart() != null && entity.getCount() != null) {
				map.put("start", entity.getStart().toString());
				map.put("count", entity.getCount().toString());
			}
			// 查询人物志
			List<UserContentVO> userContentVOs = userContentDao.searchtUserContentNew(map);
			// 截取内容，保证预览的速度
			for (UserContentVO userContentVO : userContentVOs) {
				if (userContentVO.getContent() != null || !"".equals(userContentVO.getContent())) {
					String content = userContentVO.getContent();
					if (content != null && content.length() > 0) {
						// 去除html标签
						content = content.replaceAll("</?[^>]+>", "");
						content = content.replaceAll("\\s*|\t|\r|\n", "");
						content = content.replaceAll("&nbsp;", " ");
						// 截取1/10长度 截取70个字
						if (content.length() > 70)
							content = content.substring(0, 70);
						userContentVO.setContent(content);
					}
				}
			}

			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(userContentVOs);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
		}
		return res;
	}

}
