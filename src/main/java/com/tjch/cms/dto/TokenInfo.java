package com.tjch.cms.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 用户登录信息
 * @author ASUS
 *
 */
@Data
@ToString
public class TokenInfo {

	private String username;
	private String token;

	private String realname;
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}
