package com.jp.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.Result;
import com.jp.dao.NationMapper;
import com.jp.entity.Nation;
import com.jp.entity.NationExample;
import com.jp.service.NationService;

@Service
public class NationServiceImpl implements NationService {

	private final Logger log_ = LogManager.getLogger(NationServiceImpl.class);

	@Autowired
	private NationMapper nationMapper;

	@Override
	public JsonResponse getNationDic(Nation entity) {
		Result result = null;
		JsonResponse res = null;
		NationExample nationExample = new NationExample();
		List<Nation> nations = nationMapper.selectByExample(nationExample);
		if (nations == null || nations.size() == 0) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("没有民族信息");
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_SUCCESS);
		result.setMsg("获取成功");
		res = new JsonResponse(result);
		res.setData(nations);
		return res;
	}
}
