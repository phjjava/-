package com.jp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.BranchDao;
import com.jp.dao.UserDao;
import com.jp.dao.UserroleDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchEditorBoard;
import com.jp.entity.BranchQuery;
import com.jp.entity.Person;
import com.jp.entity.PersonBranch;
import com.jp.entity.User;
import com.jp.entity.UserQuery;
import com.jp.service.PersonBranchService;
import com.jp.util.WebUtil;

@Service
public class PersonBranchServiceImpl implements PersonBranchService {

	@Resource
	private UserDao userMapper;
	@Resource
	private UserroleDao userRoleMapper;
	@Resource
	private BranchDao branchMapper;

	@Override
	public JsonResponse getPersonBranchData(PersonBranch entity) {
		Result result = null;
		JsonResponse res = null;
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (familyid == null || "".equals(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("家族不存在");
			res = new JsonResponse(result);
			return res;
		}
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		List<PersonBranch> provinceList = new ArrayList<PersonBranch>();
		List<PersonBranch> cityList = new ArrayList<PersonBranch>();
		PersonBranch personBranch = new PersonBranch();
		// private Integer totalPerson;//总人数
		UserQuery userExample = new UserQuery();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0);
		int totalPerson = userMapper.countByExample(userExample);
		List<BranchEditorBoard> allBranchs = branchMapper.selectCityByFamilyid(familyid);
		// 统计分支总数
		if (allBranchs != null) {
			personBranch.setTotalBranch(allBranchs.size());
		} else {
			personBranch.setTotalBranch(0);
		}
		personBranch.setTotalPerson(totalPerson);
		// private Integer totalBranch;//分支总数
		//
		// private Integer direct;//直系人数
		//
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andIsdirectEqualTo(1);
		int direct = userMapper.countByExample(userExample);
		personBranch.setDirect(direct);
		// private Integer nonDirect;//非直系人数
		//
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andIsdirectEqualTo(0);
		int nonDirect = userMapper.countByExample(userExample);
		personBranch.setNonDirect(nonDirect);
		//未知系人数
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andIsdirectIsNull();
		int unDirect = userMapper.countByExample(userExample);
		personBranch.setUnDirect(unDirect);

		// private Integer man;//男性成员
		//
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andSexEqualTo(1);
		int man = userMapper.countByExample(userExample);
		personBranch.setMan(man);
		// private Integer women;//女性成员
		//
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andSexEqualTo(0);
		int woman = userMapper.countByExample(userExample);
		personBranch.setWoman(woman);
		// private Person alive;//在世的人数
		//
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(0);
		int alive = userMapper.countByExample(userExample);
		// private Person depart;//离世人数
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(1);
		int depart = userMapper.countByExample(userExample);

		Person alivePerson = new Person();// 在世
		Map<String, String> totalparams = new HashMap<String, String>();
		totalparams.put("familyid", familyid);
		totalparams.put("livestatus", "0");
		int totalBranch = userMapper.countBranch(totalparams);
		alivePerson.setTotalPerson(alive);
		alivePerson.setTotalBranch(totalBranch);
		// 在世的直系
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(0)
				.andIsdirectEqualTo(1);
		int direct1 = userMapper.countByExample(userExample);
		alivePerson.setDirect(direct1);
		// 在世的非直系
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(0)
				.andIsdirectEqualTo(0);
		int nonDirect1 = userMapper.countByExample(userExample);
		alivePerson.setNonDirect(nonDirect1);
		// 在世的未知系
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(0)
				.andIsdirectIsNull();
		int unDirect1 = userMapper.countByExample(userExample);
		alivePerson.setUnDirect(unDirect1);
		;
		// 在世的男性
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(0).andSexEqualTo(1);
		int man1 = userMapper.countByExample(userExample);
		alivePerson.setMan(man1);
		// 在世的女性
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(0).andSexEqualTo(0);
		int woman1 = userMapper.countByExample(userExample);
		alivePerson.setWoman(woman1);
		personBranch.setAlive(alivePerson);

		Person departPerson = new Person();// 离世
		departPerson.setTotalPerson(depart);
		totalparams.put("livestatus", "1");
		totalBranch = userMapper.countBranch(totalparams);
		departPerson.setTotalBranch(totalBranch);
		// 离世的直系
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(1)
				.andIsdirectEqualTo(1);
		int direct2 = userMapper.countByExample(userExample);
		departPerson.setDirect(direct2);
		// 离世的非直系
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(1)
				.andIsdirectEqualTo(0);
		int nonDirect2 = userMapper.countByExample(userExample);
		departPerson.setNonDirect(nonDirect2);
		// 离世的未知系
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(1)
				.andIsdirectIsNull();
		int unDirect2 = userMapper.countByExample(userExample);
		departPerson.setUnDirect(unDirect2);
		// 离世的男性
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(1).andSexEqualTo(1);
		int man2 = userMapper.countByExample(userExample);
		departPerson.setMan(man2);
		// 离世的女性
		userExample.clear();
		userExample.or().andFamilyidEqualTo(familyid).andDeleteflagEqualTo(0).andLivestatusEqualTo(1).andSexEqualTo(0);
		int woman2 = userMapper.countByExample(userExample);
		departPerson.setWoman(woman2);
		personBranch.setDepart(departPerson);
		rtnMap.put("total", personBranch);

		// 查询地区
		List<Branch> areas = branchMapper.selectArea(familyid);
		// 查询城市
		List<Branch> cities = branchMapper.selectCity(familyid);
		// 地区
		PersonBranch province = null;
		// 城市
		PersonBranch city = null;
		/*
		 * totalPerson;//总人数 direct;//直系人数 nonDirect;//非直系人数 man;//男性成员 woman;//女性成员
		 * alive;//在世的人数 depart;//离世人数
		 */
		// 遍历地区
		List<User> alives = null;// 在世的
		List<User> departs = null;// 离世的
		List<String> branchs = null;
		for (Branch branch : areas) {
			totalPerson = 0;
			direct = 0;
			nonDirect = 0;
			unDirect = 0;
			man = 0;
			woman = 0;
			alives = new ArrayList<User>();
			departs = new ArrayList<User>();
			branchs = new ArrayList<String>();
			province = new PersonBranch();
			province.setName(branch.getArea());
			// 根据地区获取用户信息
			Map<String, String> params = new HashMap<String, String>();
			params.put("areacode", branch.getAreacode());
			params.put("familyid", familyid);
			List<User> users = userMapper.selectUserByAreaCode(params);
			for (User user : users) {
				if (user.getIsdirect() != null) {
					if (user.getIsdirect() == 1) {
						direct++;
					} else {
						nonDirect++;
					}
				} else {
					unDirect++;
				}
				if (user.getSex() == 1) {
					man++;
				} else {
					woman++;
				}
				if (user.getLivestatus() == 0) {
					alives.add(user);
				} else {
					departs.add(user);
				}
				if (!branchs.contains(user.getBranchid())) {
					branchs.add(user.getBranchid());
				}
			}
			province.setTotalPerson(users.size());
			province.setDirect(direct);
			province.setNonDirect(nonDirect);
			province.setUnDirect(unDirect);
			province.setMan(man);
			province.setWoman(woman);
			province.setTotalBranch(branchs.size());
			// 重置
			direct = 0;
			nonDirect = 0;
			unDirect = 0;
			man = 0;
			woman = 0;
			branchs = new ArrayList<String>();
			if (alives.size() > 0) {
				for (User u : alives) {
					if (u.getIsdirect() != null) {
						if (u.getIsdirect() == 1) {
							direct++;
						} else {
							nonDirect++;
						}
					} else {
						unDirect++;
					}
					if (u.getSex() == 1) {
						man++;
					} else {
						woman++;
					}
					if (!branchs.contains(u.getBranchid())) {
						branchs.add(u.getBranchid());
					}
				}
			}
			Person alivePerson1 = new Person();
			alivePerson1.setTotalPerson(alives.size());
			alivePerson1.setTotalBranch(branchs.size());
			alivePerson1.setDirect(direct);
			alivePerson1.setNonDirect(nonDirect);
			alivePerson1.setUnDirect(unDirect);
			alivePerson1.setMan(man);
			alivePerson1.setWoman(woman);
			province.setAlive(alivePerson1);

			// 重置
			direct = 0;
			nonDirect = 0;
			unDirect = 0;
			man = 0;
			woman = 0;
			branchs = new ArrayList<String>();
			if (departs.size() > 0) {
				for (User u : departs) {
					if (u.getIsdirect() != null) {
						if (u.getIsdirect() == 1) {
							direct++;
						} else {
							nonDirect++;
						}
					} else {
						unDirect++;
					}
					if (u.getSex() == 1) {
						man++;
					} else {
						woman++;
					}
					if (!branchs.contains(u.getBranchid())) {
						branchs.add(u.getBranchid());
					}
				}
			}
			Person departPerson1 = new Person();
			departPerson1.setTotalPerson(departs.size());
			departPerson1.setTotalBranch(branchs.size());
			departPerson1.setDirect(direct);
			departPerson1.setNonDirect(nonDirect);
			departPerson1.setUnDirect(unDirect);
			departPerson1.setMan(man);
			departPerson1.setWoman(woman);
			province.setDepart(departPerson1);
			provinceList.add(province);
		}

		rtnMap.put("totalProvince", provinceList);
		// 城市统计
		for (Branch branch : cities) {
			totalPerson = 0;
			direct = 0;
			nonDirect = 0;
			unDirect = 0;
			man = 0;
			woman = 0;
			alives = new ArrayList<User>();
			departs = new ArrayList<User>();
			branchs = new ArrayList<String>();
			city = new PersonBranch();
			city.setName(branch.getCityname());
			// 根据城市获取用户信息
			Map<String, String> params = new HashMap<String, String>();
			params.put("citycode", branch.getCitycode());
			params.put("familyid", familyid);
			List<User> users = userMapper.selectUserByAreaCode(params);
			for (User user : users) {
				if (user.getIsdirect() != null) {
					if (user.getIsdirect() != null) {
						if (user.getIsdirect() == 1) {
							direct++;
						} else {
							nonDirect++;
						}
					}
				} else {
					unDirect++;
				}
				if (user.getSex() == 1) {
					man++;
				} else {
					woman++;
				}
				if (user.getLivestatus() == 0) {
					alives.add(user);
				} else {
					departs.add(user);
				}
				if (!branchs.contains(user.getBranchid())) {
					branchs.add(user.getBranchid());
				}
			}
			city.setTotalPerson(users.size());
			city.setDirect(direct);
			city.setNonDirect(nonDirect);
			city.setUnDirect(unDirect);
			city.setMan(man);
			city.setWoman(woman);
			city.setTotalBranch(branchs.size());
			// 重置
			direct = 0;
			nonDirect = 0;
			unDirect = 0;
			man = 0;
			woman = 0;
			branchs = new ArrayList<String>();
			if (alives.size() > 0) {
				for (User u : alives) {
					if (u.getIsdirect() != null) {
						if (u.getIsdirect() == 1) {
							direct++;
						} else {
							nonDirect++;
						}
					} else {
						unDirect++;
					}
					if (u.getSex() == 1) {
						man++;
					} else {
						woman++;
					}
					if (!branchs.contains(u.getBranchid())) {
						branchs.add(u.getBranchid());
					}
				}
			}
			Person alivePerson1 = new Person();
			alivePerson1.setTotalPerson(alives.size());
			alivePerson1.setTotalBranch(branchs.size());
			alivePerson1.setDirect(direct);
			alivePerson1.setNonDirect(nonDirect);
			alivePerson1.setUnDirect(unDirect);
			alivePerson1.setMan(man);
			alivePerson1.setWoman(woman);
			city.setAlive(alivePerson1);

			// 重置
			direct = 0;
			nonDirect = 0;
			unDirect = 0;
			man = 0;
			woman = 0;
			branchs = new ArrayList<String>();
			if (departs.size() > 0) {
				for (User u : departs) {
					if (u.getIsdirect() != null) {
						if (u.getIsdirect() == 1) {
							direct++;
						} else {
							nonDirect++;
						}
					} else {
						unDirect++;
					}
					if (u.getSex() == 1) {
						man++;
					} else {
						woman++;
					}
					if (!branchs.contains(u.getBranchid())) {
						branchs.add(u.getBranchid());
					}
				}
			}
			Person departPerson1 = new Person();
			departPerson1.setTotalPerson(departs.size());
			departPerson1.setTotalBranch(branchs.size());
			departPerson1.setDirect(direct);
			departPerson1.setNonDirect(nonDirect);
			departPerson1.setUnDirect(unDirect);
			departPerson1.setMan(man);
			departPerson1.setWoman(woman);
			city.setDepart(departPerson1);
			cityList.add(city);
		}
		rtnMap.put("totalCity", cityList);
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(rtnMap);
		return res;
	}

	@Override
	public JsonResponse getPersonBranchDataByBranch(PersonBranch entity) {
		Result result = null;
		JsonResponse res = null;
		String familyid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		if (familyid == null || "".equals(familyid)) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("家族不存在");
			res = new JsonResponse(result);
			return res;
		}
		BranchQuery example = new BranchQuery();
		example.or().andFamilyidEqualTo(familyid);
		if (entity.getStart() != null && entity.getCount() != null) {
			example.setPageNo(entity.getStart());
			example.setPageSize(entity.getCount());
		}
		List<PersonBranch> rtnList = new ArrayList<PersonBranch>();
		// 分页查询分支
		List<Branch> branchs = branchMapper.selectByExample(example);
		UserQuery userexample = new UserQuery();
		int direct;
		int nonDirect;
		int unDirect;
		int man;
		int woman;
		int alives;
		int departs;

		PersonBranch pb;
		for (Branch branch : branchs) {
			direct = 0;
			nonDirect = 0;
			unDirect = 0;
			man = 0;
			woman = 0;
			alives = 0;
			departs = 0;

			pb = new PersonBranch();
			pb.setName(branch.getBranchname());
			userexample.clear();
			userexample.or().andBranchidEqualTo(branch.getBranchid()).andDeleteflagEqualTo(0);
			List<User> users = userMapper.selectByExample(userexample);
			// 统计
			for (User user : users) {
				if (user.getIsdirect() != null) {
					if (user.getIsdirect() == 1) {
						direct++;
					} else {
						nonDirect++;
					}
				} else {
					unDirect++;
				}
				if (user.getSex() == 1) {
					man++;
				} else {
					woman++;
				}
				if (user.getLivestatus() == 0) {
					alives++;
				} else {
					departs++;
				}

			}
			pb.setTotalPerson(users.size());

			pb.setDirect(direct);
			pb.setNonDirect(nonDirect);
			pb.setUnDirect(unDirect);
			pb.setMan(man);
			pb.setWoman(woman);
			Person person = new Person();
			person.setTotalPerson(alives);
			pb.setAlive(person);
			Person person1 = new Person();
			person1.setTotalPerson(departs);
			pb.setDepart(person1);
			rtnList.add(pb);
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		res = new JsonResponse(result);
		res.setData(rtnList);
		return res;
	}

}
