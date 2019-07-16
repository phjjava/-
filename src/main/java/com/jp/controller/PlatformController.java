package com.jp.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jp.common.ConstantUtils;
import com.jp.common.JsonResponse;
import com.jp.common.MsgConstants;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.Platform;
import com.jp.service.PlatformService;

@Controller
@RequestMapping("system/platform")
public class PlatformController {
	private final Logger log_ = LogManager.getLogger(PlatformController.class);
	@Autowired
	private PlatformService platformService;

	/**
	 * @描述 安卓或ios升级版本列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse list(PageModel<Platform> pageModel, Platform platform, ModelMap model) {
		return platformService.selectPlatformList(pageModel, platform);
	}

	/**
	 * @描述 删除版本
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteVersion", method = RequestMethod.POST)
	public JsonResponse deleteVersion(Integer id) {
		return platformService.deleteVersion(id);
	}

	/**
	 * @描述 增加或编辑版本
	 * @作者 sj
	 * @时间 2017年11月28日下午5:28:01
	 * @参数 @param file
	 * @参数 @param platform
	 * @参数 @param request
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/merge", method = RequestMethod.POST)
	public JsonResponse merge(@RequestParam(value = "file", required = false) MultipartFile file, Platform platform,
			HttpServletRequest request) {
		Result result = null;
		JsonResponse res = null;
		int status = 0;
		if (platform.getVersionName() == null || "".equals(platform.getVersionName())) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionName不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (platform.getVersionNo() == null || "".equals(platform.getVersionNo() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数versionNo不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		if (platform.getMinUpdateVersion() == null || "".equals(platform.getMinUpdateVersion() + "")) {
			result = new Result(MsgConstants.RESUL_FAIL);
			result.setMsg("参数minUpdateVersion不能为空！");
			res = new JsonResponse(result);
			return res;
		}
		try {
			String path = request.getSession().getServletContext().getRealPath("/upload");
			if (platform.getId() != null && !"".equals(platform.getId() + "")) {//编辑
				res = platformService.selectone(platform.getId());
				Platform p = (Platform) res.getData();

				if (p.getFileType() == 2 && file != null) {
					String fileName = file.getOriginalFilename();
					if ("".equals(fileName)) {
						result = new Result(MsgConstants.RESUL_FAIL);
						result.setMsg("file文件名字不能为空，请检查后重试！");
						res = new JsonResponse(result);
						return res;
					}
					platform.setFileRealName(fileName);
					fileName = System.currentTimeMillis() / 1000 + "_" + fileName;
					File targetFile = new File(path, fileName);
					platform.setDownloadUrl(ConstantUtils.JIAPU_IP + fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					file.transferTo(targetFile);
				}
				status = platformService.update(platform);
			} else {//新增
				if (platform.getFileType() == 2) {
					if (file == null || "".equals(file.getOriginalFilename())) {
						result = new Result(MsgConstants.RESUL_FAIL);
						result.setMsg("参数file为空或文件名字为空，请检查后重试！");
						res = new JsonResponse(result);
						return res;
					}
					String fileName = file.getOriginalFilename();
					platform.setFileRealName(fileName);
					fileName = System.currentTimeMillis() / 1000 + "_" + fileName;
					File targetFile = new File(path, fileName);
					platform.setDownloadUrl(ConstantUtils.JIAPU_IP + fileName);
					file.transferTo(targetFile);
				}
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				platform.setUploadTime(formatter.format(new Date()));
				if (platform.getFileRealName() == null) {
					String[] urls = platform.getDownloadUrl().split("/");
					if (urls.length != 0) {
						platform.setFileRealName(urls[urls.length - 1]);
					}
				}
				status = platformService.save(platform);
			}
			if (status > 0) {
				result = new Result(MsgConstants.RESUL_SUCCESS);
				res = new JsonResponse(result);
				return res;
			}
		} catch (Exception e) {
			log_.error("[merge---异常:]", e);
			result = new Result(MsgConstants.SYS_ERROR);
			res = new JsonResponse(result);
			return res;
		}
		result = new Result(MsgConstants.RESUL_FAIL);
		res = new JsonResponse(result);
		return res;
	}

	/**
	 * 编辑回显
	 * 
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse edit(Integer id) {
		return platformService.selectone(id);
	}

	/**
	 * @描述 开启或关闭版本
	 * @作者 sj
	 * @时间 2017年11月29日上午9:28:35
	 * @参数 @param request
	 * @参数 @param platform
	 * @参数 @param model
	 * @参数 @return
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/isOpen", method = RequestMethod.POST)
	public JsonResponse isOpen(Integer id) {
		return platformService.isOpen(id);
	}

	/**
	 * api方法分割线--------------------------------------------------------
	 */

	/**
	 * 获取在用的版本号内容
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/getPlatform", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getPlatform(Platform entity) {
		return platformService.getPlatform(entity.getFileType());
	}

	/**
	 * 返回当前系统时间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCurrentTime", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getCurrentTime() {
		return platformService.getCurrentTime();
	}

}
