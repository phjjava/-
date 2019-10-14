package com.jp.service.impl;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.common.ConstantUtils;
import com.jp.dao.BranchDao;
import com.jp.dao.EditorialBoardMapper;
import com.jp.dao.NoticeMapper;
import com.jp.dao.UserManagerMapper;
import com.jp.entity.Branch;
import com.jp.entity.EditorialBoard;
import com.jp.entity.Notice;
import com.jp.entity.UserManager;
import com.jp.service.FlowableService;
import com.jp.service.UserManagerService;
import com.jp.service.UserService;
import com.jp.util.WebUtil;

@Service
public class FlowableServiceImpl implements FlowableService {
	@Autowired
	private BranchDao branchDao;
	@Autowired
	private EditorialBoardMapper editorialBoardMapper;
	@Autowired
	private UserManagerMapper userManagerMapper;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private NoticeMapper noticeMapper;
	@Autowired
	private UserManagerService userManagerService;
	@Autowired
	private UserService userService;

	/**
	 * 分编委会成员查询
	 */
	public String deploynew(String noticeid) {
		// 查询对应的分编委会人员,设置审批人
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		String familyId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		//根据用户id查询分支id
		String branchid=userService.selectBranchId(userid);
		// 查询对应的分支
		Branch branch = branchDao.selectbyEditor(branchid);
		String areacode = branch.getAreacode();
		String citycode = branch.getCitycode();
		String xcode = branch.getXcode();
		// 通过对应的分支code值查询分编委会(code值分为省市县三级,通过不同code查询)
		 EditorialBoard editorialBoard = editorialBoardMapper.selectbyEditor(areacode, citycode, xcode, familyId);
		
		if (null != editorialBoard ) {
			// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
			String id = editorialBoard.getId();
			List<UserManager> list = userManagerMapper.selectbyEditor(id);
			String userIdNew = "";
			for (UserManager manger : list) {
				userIdNew = userIdNew + "," + manger.getUserid();
			}
			userIdNew.substring(1);
			return userIdNew;
		} else {
			EditorialBoard editorialBoardTwo = editorialBoardMapper.selectbyEditorTwo(areacode, citycode, xcode,
					familyId);
			if (null != editorialBoardTwo) {
				List<UserManager> list = userManagerMapper.selectbyEditor(editorialBoardTwo.getId());
				String userIdNew = "";
				for (UserManager manger : list) {
					userIdNew = userIdNew + "," + manger.getUserid();
				}
				userIdNew.substring(1);
				return userIdNew;
			} else {
				// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
				EditorialBoard editorialBoardThree = editorialBoardMapper.selectbyEditorThree(areacode, citycode, xcode,
						familyId);
				if (null != editorialBoardThree) {
					// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
					List<UserManager> list = userManagerMapper.selectbyEditor(editorialBoardThree.getId());
					String userIdNew = "";
					for (UserManager manger : list) {
						userIdNew = userIdNew + "," + manger.getUserid();
					}
					userIdNew.substring(1);
					return userIdNew;
				} else {
					//判断当前人是否为总编委会主任
					String postname="";
					List<UserManager> managers = userManagerService.selectManagerByUserid(userid, postname);
					for (UserManager userManager : managers) {
						postname=userManager.getPostname();
					}
					if(postname.equals("总编委会主任")) {
						String examinestatus="1";//更改审核状态
						noticeMapper.updateNoticeExamin(noticeid,examinestatus);
						return "跳过";
					}else {
						return "未找到对应的编委会";
					}
					
				}
			}
		}

	}
	/**
	 * 总编委会成员查询
	 */
	public String deploynewTwo(String noticeid) {
		// 查询对应的分编委会人员,设置审批人
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		String familyId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		//根据用户id查询分支id
		String branchid=userService.selectBranchId(userid);
		// 查询对应的分支
		Branch branch = branchDao.selectbyEditor(branchid);
		String areacode = branch.getAreacode();
		String citycode = branch.getCitycode();
		String xcode = branch.getXcode();
		// 通过对应的分支code值查询分编委会(code值分为省市县三级,通过不同code查询)
		EditorialBoard editorialBoard = editorialBoardMapper.selectbyEditor(areacode, citycode, xcode, familyId);
		
		if (null != editorialBoard ) {
			// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
						String id = editorialBoard.getId();
						List<UserManager> list = userManagerMapper.selectbyEditor(id);
						// 遍历取值
						String family = " ";
						for (UserManager manger : list) {
							family = manger.getFamilyid();
						}
						// 查询总编委会成员
						List<UserManager> listTwo = userManagerMapper.selectbyEditorOver(family);
						String userIdNew = "";
						for (UserManager manger : listTwo) {
							userIdNew = userIdNew + "," + manger.getUserid();
						}
						userIdNew.substring(1);
						return userIdNew;
		} else {
			EditorialBoard editorialBoardTwo = editorialBoardMapper.selectbyEditorTwo(areacode, citycode, xcode,
					familyId);
			if (null != editorialBoardTwo) {
				// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
				List<UserManager> list = userManagerMapper.selectbyEditor(editorialBoardTwo.getId());
				// 遍历取值
				String family = " ";
				for (UserManager manger : list) {
					family = manger.getFamilyid();
				}
				// 查询总编委会成员
				List<UserManager> listTwo = userManagerMapper.selectbyEditorOver(family);
				String userIdNew = "";
				for (UserManager manger : listTwo) {
					userIdNew = userIdNew + "," + manger.getUserid();
				}
				userIdNew.substring(1);
				return userIdNew;
			} else {
				// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
				EditorialBoard editorialBoardThree = editorialBoardMapper.selectbyEditorThree(areacode, citycode, xcode,
						familyId);
				if (null != editorialBoardThree) {
					// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
					List<UserManager> list = userManagerMapper.selectbyEditor(editorialBoardThree.getId());
					// 遍历取值
					String family = " ";
					for (UserManager manger : list) {
						family = manger.getFamilyid();
					}
					// 查询总编委会成员
					List<UserManager> listTwo = userManagerMapper.selectbyEditorOver(family);
					String userIdNew = "";
					for (UserManager manger : listTwo) {
						userIdNew = userIdNew + "," + manger.getUserid();
					}
					userIdNew.substring(1);
					return userIdNew;
				} else {
					//判断当前人是否为总编委会主任
					String postname="";
					List<UserManager> managers = userManagerService.selectManagerByUserid(userid, postname);
					for (UserManager userManager : managers) {
						postname=userManager.getPostname();
					}
					if(postname.equals("总编委会主任")) {
						String examinestatus="1";//更改审核状态
						noticeMapper.updateNoticeExamin(noticeid,examinestatus);
						return "跳过";
					}else {
						return "未找到对应的编委会";
					}
				}
			}
		}
		
	}

	
	@Override
	public String deploynewThree() {
		// 查询对应的分编委会人员,设置审批人
		String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
		String familyId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		//根据用户id查询分支id
		String branchid=userService.selectBranchId(userid);
		// 查询对应的分支
		Branch branch = branchDao.selectbyEditor(branchid);
		String areacode = branch.getAreacode();
		String citycode = branch.getCitycode();
		String xcode = branch.getXcode();
		// 通过对应的分支code值查询分编委会(code值分为省市县三级,通过不同code查询)
		EditorialBoard editorialBoard = editorialBoardMapper.selectbyEditor(areacode, citycode, xcode, familyId);
		if (editorialBoard.getId() == null) {
			EditorialBoard editorialBoardTwo = editorialBoardMapper.selectbyEditorTwo(areacode, citycode, xcode,
					familyId);
			if (editorialBoardTwo.getId() == null) {
				EditorialBoard editorialBoardThree = editorialBoardMapper.selectbyEditorThree(areacode, citycode, xcode,
						familyId);
				if (editorialBoardThree.getId() == null) {
					System.out.println("未找到对应的编委会");
					return "未找到对应的编委会";
				} else {
					// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
					List<UserManager> list = userManagerMapper.selectbyEditor(editorialBoardThree.getId());
					// 遍历取值
					String family = " ";
					for (UserManager manger : list) {
						family = manger.getFamilyid();
					}
					// 查询总编委会主任
					List<UserManager> listThree = userManagerMapper.selectbyEditorOverOne(family);
					String userIdNew = "";
					for (UserManager manger : listThree) {
						userIdNew = userIdNew + "," + manger.getUserid();
					}
					userIdNew.substring(1);
					return userIdNew;
				}
			} else {
				// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
				List<UserManager> list = userManagerMapper.selectbyEditor(editorialBoardTwo.getId());
				// 遍历取值
				String family = " ";
				for (UserManager manger : list) {
					family = manger.getFamilyid();
				}
				// 查询总编委会主任
				List<UserManager> listThree = userManagerMapper.selectbyEditorOverOne(family);
				String userIdNew = "";
				for (UserManager manger : listThree) {
					userIdNew = userIdNew + "," + manger.getUserid();
				}
				userIdNew.substring(1);
				return userIdNew;
			}

		} else {
			// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
			String id = editorialBoard.getId();
			List<UserManager> list = userManagerMapper.selectbyEditor(id);
			// 遍历取值
			String family = " ";
			for (UserManager manger : list) {
				family = manger.getFamilyid();
			}
			// 查询总编委会主任
			List<UserManager> listThree = userManagerMapper.selectbyEditorOverOne(family);
			String userIdNew = "";
			for (UserManager manger : listThree) {
				userIdNew = userIdNew + "," + manger.getUserid();
			}
			userIdNew.substring(1);
			return userIdNew;
		}
	}

	@Override
	public UserManager deploynewFour(String userid) {
		String familyId = WebUtil.getHeaderInfo(ConstantUtils.HEADER_FAMILYID);
		//根据用户id查询分支id
		String branchid=userService.selectBranchId(userid);
		// 查询对应的分支
		Branch branch = branchDao.selectbyEditor(branchid);
		String areacode = branch.getAreacode();
		String citycode = branch.getCitycode();
		String xcode = branch.getXcode();
		// 通过对应的分支code值查询分编委会(code值分为省市县三级,通过不同code查询)
		EditorialBoard editorialBoard = editorialBoardMapper.selectbyEditor(areacode, citycode, xcode, familyId);
		if (null!= editorialBoard ) {
			// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
						String id = editorialBoard.getId();
						List<UserManager> list = userManagerMapper.selectbyEditorEbname(id);
						return list.get(0);
					} else {
							EditorialBoard editorialBoardTwo = editorialBoardMapper.selectbyEditorTwo(areacode, citycode, xcode,
										familyId);
							if (null!= editorialBoardTwo) {
								// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
								List<UserManager> list = userManagerMapper.selectbyEditorEbname(editorialBoardTwo.getId());
								return list.get(0);
							}else {
								EditorialBoard editorialBoardThree = editorialBoardMapper.selectbyEditorThree(areacode, citycode, xcode,
										familyId);
								if (null!= editorialBoardThree) {
									// 根据分支code值和编委会code匹配查询出编委会id,根据编委会id查询编委会人员
									List<UserManager> list = userManagerMapper.selectbyEditorEbname(editorialBoardThree.getId());
									return list.get(0);
								} else {
									System.out.println("未找到对应的编委会");
									return null;
								}
							}
							
					}

	}

	/**
	 * 查询公告发起人
	 */
	@Override
	public Notice selectNotice(String noticeid) {
		// TODO Auto-generated method stub
		return noticeMapper.selectNotice(noticeid);
	}

}
