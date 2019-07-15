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
import com.jp.common.PageModel;
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
	public String merge(@RequestParam(value = "file", required = false) MultipartFile file, Platform platform,
			HttpServletRequest request) {
		String result = null;
		try {
			String path = request.getSession().getServletContext().getRealPath("/upload");
			if (platform.getId() != null && !platform.getId().equals("")) {//编辑
				JsonResponse res = platformService.selectone(platform.getId());
				Platform p = (Platform) res.getData();

				if (!file.getOriginalFilename().equals("") && p.getFileType().equals("2")) {
					String fileName = file.getOriginalFilename();
					platform.setFileRealName(fileName);
					fileName = System.currentTimeMillis() / 1000 + "_" + fileName;
					File targetFile = new File(path, fileName);
					platform.setDownloadUrl(ConstantUtils.JIAPU_IP + fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					file.transferTo(targetFile);
				}
				platformService.update(platform);
				result = "1";
			} else {//新增
				if (platform.getFileType().equals("2")) {
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
				platformService.save(platform);
				result = "1";
			}
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}
		return result;
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
