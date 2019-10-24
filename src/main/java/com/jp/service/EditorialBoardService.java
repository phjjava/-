package com.jp.service;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.entity.EditorialBoard;

public interface EditorialBoardService {

	JsonResponse pageQuery(PageModel<EditorialBoard> pageModel, EditorialBoard role);

	JsonResponse getEditorialBoard(String id);

	JsonResponse del(String id);

	JsonResponse selecteditorialBoardList();

	JsonResponse save(EditorialBoard eb);

}
