package com.jp.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AddressBook {

	List<User> usersadd;

	List<User> userdel;

	@JsonIgnore
	List<String> ids_del;

	String updatetime;

	public List<User> getUsersadd() {
		return usersadd;
	}

	public void setUsersadd(List<User> usersadd) {
		this.usersadd = usersadd;
	}

	public List<User> getUserdel() {
		return userdel;
	}

	public void setUserdel(List<User> userdel) {
		this.userdel = userdel;
	}

	public List<String> getIds_del() {
		return ids_del;
	}

	public void setIds_del(List<String> ids_del) {
		this.ids_del = ids_del;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

}
