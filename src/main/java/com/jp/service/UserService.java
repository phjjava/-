package com.jp.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.jp.common.JsonResponse;
import com.jp.common.PageModel;
import com.jp.common.Result;
import com.jp.entity.User;
import com.jp.entity.Useralbum;
import com.jp.entity.Usercode;
import com.jp.entity.Userinfo;
import com.jp.entity.Userphoto;
//import com.jp.util.Result;

public interface UserService {

	int insert(User user) throws Exception;

	PageModel pageQuery(PageModel pageModel, User user) throws Exception;

	void deleteAndAll(User user) throws Exception;

	/**
	 * @描述 用户的保存
	 * @作者 sj
	 * @时间 2017年4月27日下午3:17:27
	 * @参数 @param user
	 * @参数 @param userinfo
	 * @参数 @param useredu
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	Result merge(User user) throws Exception;

	/**
	 * 
	 * @描述 用户列表分页
	 * @作者 sj
	 * @时间 2017年4月28日上午9:47:51
	 * @参数 @param pageModel
	 * @参数 @param user
	 * @参数 @param userInfo
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel
	 */
	PageModel<User> selectUserList(PageModel<User> pageModel, User user, List<String> branchList) throws Exception;

	/**
	 * 
	 * @描述 查询单个用户
	 * @作者 sj
	 * @时间 2017年5月1日下午2:28:11
	 * @参数 @param userid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return User
	 */
	User selectByPrimaryKey(String userid) throws Exception;

	/**
	 * 
	 * @描述 停用用户
	 * @作者 sj
	 * @时间 2017年5月3日下午5:42:10
	 * @参数 @param user
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	int changeStatus(User user) throws Exception;

	/**
	 * 
	 * @描述 用户的导入
	 * @作者 sj
	 * @时间 2017年5月4日上午9:14:56
	 * @参数 @param file
	 * @参数 @param request
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return String
	 */
	JsonResponse importUsers(MultipartFile file, HttpServletRequest request) throws Exception;

	/**
	 * 
	 * @描述 新增用户时初始化 父亲 和配偶
	 * @作者 sj
	 * @时间 2017年5月5日上午10:44:10
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<User>
	 */
	List<User> selectPnameAndMate(String familyid, List<String> branchList) throws Exception;

	/**
	 * 
	 * @描述 用户配偶的导入
	 * @作者 sj
	 * @时间 2017年5月8日上午11:59:52
	 * @参数 @param file
	 * @参数 @param request
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return String
	 */
	JsonResponse importUserMates(MultipartFile file, HttpServletRequest request) throws Exception;

	/**
	 * 
	 * @描述 查询待审核的用户
	 * @作者 sj
	 * @时间 2017年5月8日下午7:21:37
	 * @参数 @param pageModel
	 * @参数 @param user
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return PageModel
	 */
	PageModel selecUserListToReview(PageModel pageModel, User user) throws Exception;

	/**
	 * @描述 保存用户配偶
	 * @作者 sj
	 * @时间 2017年5月12日下午4:03:17
	 * @参数 @param user
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return String
	 */
	Result mergeMate(User user, Userinfo userInfo, String usernameBefore) throws Exception;

	/**
	 * @描述 登录
	 * @作者 wumin
	 * @时间 2017年5月13日下午2:37:50
	 * @参数 @param phone
	 * @参数 @param password
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<User>
	 */
	List<User> login(String phone, String password) throws Exception;

	boolean validatePhone(String familyid, String userid, String phone);

	/**
	 * 
	 * @描述 用户新增相册
	 * @作者 sj
	 * @时间 2017年5月17日上午10:33:15
	 * @参数 @param userAlbum
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return int
	 */
	String mergeUserAlbum(Useralbum userAlbum) throws Exception;

	/**
	 * 
	 * @描述 用户新增相册
	 * @作者 sj
	 * @时间 2017年5月17日下午4:08:11
	 * @参数 @param userPhoto
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return String
	 */
	String mergeUserPhoto(List<Userphoto> userPhotoList) throws Exception;

	/**
	 * 
	 * @描述 查询用户相册
	 * @作者 sj
	 * @时间 2017年5月17日下午4:42:26
	 * @参数 @param userid
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<Useralbum>
	 */
	List<Useralbum> selectUseralbum(String userid) throws Exception;

	/**
	 * @描述 新增或编辑用户验证手机号
	 * @作者 sj
	 * @时间 2017年5月19日上午9:49:47
	 * @参数 @param user
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<User>
	 */
	List<User> validatePhone(User user) throws Exception;

	/**
	 * 对应家族版本用户数量验证
	 */
	boolean limitUserNumber(String familyid, Integer addNum);

	/**
	 * @描述 用户下拉项数据
	 * @作者 wumin
	 * @时间 2017年5月22日下午4:39:00
	 * @参数 @param user
	 * @参数 @return
	 * @参数 @throws Exception
	 * @return List<User>
	 */
	List<User> selectUserItem(User user) throws Exception;

	/**
	 * 
	 * @描述 修改当前登陆用户原始密码
	 * @作者 jinlizhi
	 * @时间 2017年5月26日下午4:36:52
	 * @参数 @param string2md5
	 * @参数 @return
	 * @return Result
	 */
	Result editPwd(String string2md5);

	List<User> selectUserItemLive(User user);

	List<User> selectAllUser(User user);

	JsonResponse importUsersNew(MultipartFile file, HttpServletRequest request);

	Integer del(@Param("array") String[] userids);

	Result confirmImport(String excelid);

	List<User> validatePhoneForApi(@Param("familyid") String familyid, @Param("userid") String userid,
			@Param("phone") String phone);

	JsonResponse getAddressByUserid(User user);

	JsonResponse checkCodeForMT(Usercode usercode);

	JsonResponse regist(User user, String userCode);

	JsonResponse logout(ServletContext servletContext, User user);

	JsonResponse getOnlineUser(ServletContext servletContext);

	JsonResponse login(HttpServletRequest req, User entity, String loginType, String internetType, String version,
			String smscode);

}