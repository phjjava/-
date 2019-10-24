package com.jp.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.entity.JpMember;
import com.jp.entity.JpMemberRecord;
import com.jp.entity.User;
import com.jp.service.MemberService;
import com.jp.util.StringTools;
import com.jp.util.UUIDUtils;
import com.jp.util.WebUtil;

@Controller
@RequestMapping("member")
public class MemberController {
	private final Logger log_ = LogManager.getLogger(MemberController.class);
	@Autowired
	private MemberService memberService;

	/**
	 * 查询该登录用户的会员级别
	 * 刘宇成
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse get(HttpServletRequest request, ModelMap model) {
		Result result = null;
		JsonResponse res = null;
		try {
			//String bannerid = request.getParameter("userid");
			//获取当前登录用户userid
			//当前登录人 userid
			String userid = WebUtil.getHeaderInfo(ConstantUtils.HEADER_USERID);
			if (StringTools.isEmpty(userid)) {
				result = new Result(MsgConstants.RESUL_FAIL);
				result.setMsg("用户非法！");
				res = new JsonResponse(result);
				return res;
			}
			User user = memberService.get(userid);
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			res.setData(user);
		} catch (Exception e) {
			result = new Result(MsgConstants.RESUL_FAIL);
			res = new JsonResponse(result);
			e.printStackTrace();
			log_.error("[JPGL]", e);
		}
		return res;
	}

	/**
	 * 充值记录添加充值数据
	 * 刘宇成
	 */
	@ResponseBody
	@RequestMapping(value = "/saverecord", method = RequestMethod.POST)
	public JsonResponse saverecord(User user, ModelMap model, String money) {
		Result result = new Result(MsgConstants.RESUL_FAIL);
		JsonResponse res = null;
		Integer count = 0;
		try {
			//更改历史充值记录状态
			count = memberService.updateterecord(user.getUserid());
			//添加充值记录(状态为0的记录为最新充值记录)
			JpMemberRecord record = new JpMemberRecord();
			record.setId(UUIDUtils.getUUID());
			record.setRecordid(user.getUserid());
			record.setRecordname(user.getUsername());
			record.setRecordmoney(money);
			record.setRecordtime(new Date());
			record.setRecordstatus(0);
			count = memberService.insert(record);

			if (count > 0) {
				//查询记录表付款金额,以及记录状态
				JpMemberRecord list = memberService.selectRecord(user.getUserid());
				if (list != null && list.getRecordstatus() == 0) {
					//获取时间加一年
					Date date = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);//设置起时间
					cal.add(Calendar.YEAR, 1);//增加一年
					//查询会员表中是否含有此用户记录,有记录则修改无记录则增加
					JpMember list1 = memberService.selectMember(user.getUserid());
					if (list1 == null) {
						//开通会员
						JpMember momber = new JpMember();
						momber.setMid(user.getUserid());
						momber.setMname(user.getUsername());
						momber.setMimgurl(user.getImgurl());
						momber.setStarttime(new Date());
						momber.setMemberstatus(0);
						//判断充值金额,添加会员级别
						if (list.getRecordmoney().equals("99")) {
							momber.setMember(2);
						}
						if (list.getRecordmoney().equals("299")) {
							momber.setMember(3);
						}
						//设置会员结束时间
						momber.setEndtime(cal.getTime());
						memberService.insert(momber);
						result = new Result(MsgConstants.RESUL_SUCCESS);
					} else {
						//更改会员级别
						JpMember momber = new JpMember();
						momber.setMid(user.getUserid());
						momber.setMname(user.getUsername());
						momber.setMimgurl(user.getImgurl());
						momber.setStarttime(new Date());
						momber.setMemberstatus(0);
						//判断充值金额,添加会员级别
						System.out.println("充值金额=" + list.getRecordmoney());
						if (list.getRecordmoney().equals("99")) {
							momber.setMember(2);
						}
						if (list.getRecordmoney().equals("299")) {
							momber.setMember(3);
						}
						//设置会员结束时间
						momber.setEndtime(cal.getTime());
						memberService.update(momber);
						result = new Result(MsgConstants.RESUL_SUCCESS);
					}

				} else {
					result = new Result(MsgConstants.MOMBER_RECORD);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			log_.error("[JPSYSTEM]", e);
		}
		res = new JsonResponse(result);
		return res;
	}
}
