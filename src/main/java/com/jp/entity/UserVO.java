package com.jp.entity;

import java.util.List;

public class UserVO {
	private User user;
	private Userinfo userInfo;
	private List<Useredu> userEdu;
	private List<Userworkexp> userWorkexp;
	// private UserContent userContent;
	// private List<UserAlbum> userAlbum;

	public Userinfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Userinfo userInfo) {
		this.userInfo = userInfo;
	}
	// public List<UserAlbum> getUserAlbum() {
	// return userAlbum;
	// }
	//
	// public void setUserAlbum(List<UserAlbum> userAlbum) {
	// this.userAlbum = userAlbum;
	// }
	//
	//
	// public UserContent getUserContent() {
	// return userContent;
	// }
	//
	// public void setUserContent(UserContent userContent) {
	// this.userContent = userContent;
	// }

	public List<Useredu> getUserEdu() {
		return userEdu;
	}

	public void setUserEdu(List<Useredu> userEdu) {
		this.userEdu = userEdu;
	}

	public List<Userworkexp> getUserWorkexp() {
		return userWorkexp;
	}

	public void setUserWorkexp(List<Userworkexp> userWorkexp) {
		this.userWorkexp = userWorkexp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
