package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.entity.Nation;

public interface NationService {

	JsonResponse getNationDic(Nation entity);
}
