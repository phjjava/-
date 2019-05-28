package com.jp.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jp.common.JsonResponse;
import com.jp.entity.MomentComment;
import com.jp.service.MomentCommentService;

@Controller
@RequestMapping("momentComment")
public class MomentCommentController {
	
	@Autowired
	private MomentCommentService momentCommentService;

	/**
     * 评论族圈
     * 
     * @author 潘华杰 
     * @throws IOException
     */
	@ResponseBody
	@RequestMapping(value = "/createMomentComment", method = RequestMethod.POST)
    public JsonResponse createMomentComment(MomentComment entity) {
        return momentCommentService.createMomentComment(entity);
    }

	/**
	 * 删除族圈评论
	 * @author 潘华杰
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/delMomentComment", method = RequestMethod.POST)
	public JsonResponse delMomentComment(MomentComment entity) {
		return momentCommentService.delMomentComment(entity);
	}
	
	/**
	 * 查询剩余族圈评论
	 * @author 潘华杰
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllMomentComment", method = RequestMethod.POST)
	public JsonResponse getAllMomentComment(MomentComment entity) {
		return momentCommentService.getAllMomentComment(entity);
	}
}
