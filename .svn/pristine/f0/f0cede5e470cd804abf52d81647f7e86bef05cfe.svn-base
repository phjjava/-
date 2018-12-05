package com.jp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.CurrentUserContext;
import com.jp.common.LoginUserInfo;
import com.jp.common.PageModel;
import com.jp.dao.BranchDao;
import com.jp.dao.UserManagerMapper;
import com.jp.dao.UserbranchDao;
import com.jp.entity.Branch;
import com.jp.entity.BranchQuery;
import com.jp.entity.Role;
import com.jp.entity.User;
import com.jp.entity.UserManager;
import com.jp.entity.UserManagerExample;
import com.jp.entity.Userbranch;
import com.jp.entity.UserbranchQuery;
import com.jp.service.BranchService;
import com.jp.service.RoleService;
import com.jp.util.GsonUtil;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;

@Controller
@RequestMapping("branch")
public class BranchController {
	
	private final Logger log_ = LogManager.getLogger(BranchController.class);

	@Autowired
	private BranchService branchService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private UserbranchDao userBranchDao;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Branch branch, ModelMap model) {
		Integer result = null;
		try {
			if(StringTools.notEmpty(branch.getBranchid())){//修改
				branch.setUpdateid(CurrentUserContext.getCurrentUserId());
				branch.setUpdatetime(new Date());
				result = branchService.update(branch);
			}else{//新增
				branch.setStatus(0);// 使用中
				branch.setBranchid(UUIDUtils.getUUID());
				branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				branch.setCreateid(CurrentUserContext.getCurrentUserId());
				result = branchService.insert(branch);
				if(result!=0){
					//更新session中的分支信息
					LoginUserInfo userContext = CurrentUserContext.getUserContext();
					User user = userContext.getUser();
					//Role role = roleService.selectRoleByUserid(user.getFamilyid(), user.getUserid());
					List<Branch> branchList = null;
					//if(role.getIsmanager() == 1){
					//	branchList = branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), null);
					//}else{						
						branchList = branchService.selectBranchListByFamilyAndUserid(user.getFamilyid(), user.getUserid());
					//}
					if(branchList == null){
						branchList = new ArrayList<Branch>();
					}
					userContext.setBranchList(branchList);
				}
			}

		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return result + "";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(PageModel<Branch> pageModel,Branch branch, ModelMap model) {
		try {
			String userid = CurrentUserContext.getCurrentUserId();
			UserbranchQuery ex = new UserbranchQuery();
			ex.or().andUseridEqualTo(userid);
			List<Userbranch> list = userBranchDao.selectByExample(ex);
			Branch bran = new Branch();
			for(Userbranch b : list){
				bran.setBranchid(b.getBranchid());
				bran.setFamilyid(CurrentUserContext.getCurrentFamilyId());
				bran = branchDao.selectByPrimaryKey(bran);
				if(bran.getBranchid()!=null && !"".equals(bran.getBranchid()))
				branch.setBranchid(b.getBranchid());
				
			}
			branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			
			
	        UserManagerExample example = new UserManagerExample();
	        example.or().andUseridEqualTo(CurrentUserContext.getCurrentUserId());
	        example.setOrderByClause("ebtype desc,ismanager desc");
	        List<UserManager> managers = userManagerMapper.selectByExample(example);
//	        UserQuery ex = new UserQuery();
	        for(UserManager manager : managers) {
	        	if(manager.getEbtype() == 1 && manager.getIsmanager() == 1) {
	        		branchService.pageQuery(pageModel,branch);
	        		 
	        	}else {
	        		
	        		branchService.selectBranchListByFamilyAndUserid(pageModel,branch);
		        	
		        	 
	        	}
	             
	        }
			
			
			
//			branchService.pageQuery(pageModel,branch);
//			if (pageModel.getList() != null) {
//				if (pageModel.getList().size() == 0) {
//					if (pageModel.getPageNo() != null && !"1".equals(pageModel.getPageNo())) {
//						pageModel.setPageNo(pageModel.getPageNo() - 1);
//						branchService.pageQuery(pageModel,branch);
//					}
//				}
//			}
			model.put("pageModel", pageModel);
			model.put("branch", branch);
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "branch/branchList";
	}

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(HttpServletRequest request, ModelMap model) {
		try {

			String branchid = request.getParameter("branchid");
			Branch branch = branchService.get(branchid);
			model.put("branch", branch);
			
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return "branch/branch";
	}
	
	@ResponseBody
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public String changeStatus(Branch branch)  {
    	Integer result = null;
    	try {
    		result  = branchService.changeStatus(branch);
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
    	return result+"";
    }
	
	/**
	 * @描述 新增用户初始化父亲 和 配偶
	 * @作者 sj
	 * @时间 2017年5月5日上午11:03:44
	 * @参数 @param familyid
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/initBranch", method = RequestMethod.POST)
	public String selectBranch(String familyid) {
		String gsonStr = null;
		try {
			PageModel<Branch> pageModelBranch = new PageModel<Branch>();
			Branch branch = new Branch();
			branch.setFamilyid(CurrentUserContext.getCurrentFamilyId());
			branchService.initBranch(pageModelBranch,branch);
			gsonStr = GsonUtil.GsonString(pageModelBranch.getList());
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		return gsonStr;
	}

	@ResponseBody
	@RequestMapping(value = "/validateBranchname", method = RequestMethod.POST)
	public String validateBranchname(HttpServletRequest request) {
		boolean flag = true;//默认通过验证
	
		String branchname = StringTools.trimNotEmpty(request.getParameter("branchname")) ? request.getParameter("branchname").trim() : null;
		
		try {
			BranchQuery example=new BranchQuery();
			example.or().andBranchnameEqualTo(branchname)
				.andFamilyidEqualTo(CurrentUserContext.getCurrentFamilyId());
			List<Branch> selectRt = branchDao.selectByExample(example);
			if (selectRt!=null&&selectRt.size()>0) {
				flag=false;
			}
			
		} catch (Exception e) {
			log_.error("[PLMERROR:]", e);
		}
		return flag ? "true" : "false";
	}
	
	/**
	 * 
	 * @描述 对发起人做出提示(此人是否已经发起过其他分支)
	 * @作者 jinlizhi
	 * @时间 2017年6月28日下午4:50:25
	 * @参数 @param request
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/checkBeginer", method = RequestMethod.POST)
	public String checkBeginer(HttpServletRequest request) {
		boolean flag = true;//默认通过验证	
		String beginuserid = StringTools.trimNotEmpty(request.getParameter("beginuserid")) ? request.getParameter("beginuserid").trim() : null;
		if (beginuserid==null) {
			flag=false;
			return flag ? "true" : "false";
		}
		try {
			BranchQuery example=new BranchQuery();
			example.or().andBeginuseridEqualTo(beginuserid);
			List<Branch> selectRt = branchDao.selectByExample(example);
			if (selectRt!=null&&selectRt.size()>0) {
				flag=false;
			}	
		} catch (Exception e) {
			log_.error("[PLMERROR:]", e);
		}
		return flag ? "true" : "false";
	}
}
