package com.jp.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.GenealogyMapper;
import com.jp.entity.Genealogy;
import com.jp.service.GenealogyService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author panhuajie123
 * @since 2019-08-19
 */
@Service
public class GenealogyServiceImpl extends ServiceImpl<GenealogyMapper, Genealogy> implements GenealogyService {
	private final Logger log_ = LogManager.getLogger(GenealogyServiceImpl.class);

	@Autowired
	private GenealogyMapper GenealogyMapper;

	@Override
	public JsonResponse queryGenealogy(Genealogy genealogy) {
		Result result = null;
		JsonResponse res = null;
		if (genealogy.getLoginType() == null || "".equals(genealogy.getLoginType())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数loginType不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			String jsonStr = GenealogyMapper.selectJsonByType(genealogy.getLoginType());
			result = new Result(MsgConstants.RESUL_SUCCESS);
			res = new JsonResponse(result);
			if ("ios".equals(genealogy.getLoginType())) {
				JSONObject parseObject = JSON.parseObject(jsonStr);
				//	GenUserVO genUserVO = JacksonUtil.fromJsonToObject(jsonStr, GenUserVO.class);
				res.setData(parseObject);
				return res;
			} else {
				//		List<GenUserOther> list = JacksonUtil.fromJsonToList(jsonStr, GenUserOther.class);
				JSONArray parseArray = JSON.parseArray(jsonStr);
				res.setData(parseArray);
				return res;
			}
		} catch (Exception e) {
			log_.error("[queryGenealogy方法---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
	}

}
