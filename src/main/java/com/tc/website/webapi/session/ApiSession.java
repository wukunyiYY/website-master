package com.tc.website.webapi.session;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户端信息
 * @author yyz
 *
 */
public class ApiSession implements Serializable {

	private static final long serialVersionUID = 1L;

	private String account;	//帐号
	
	private String accessToken;	//令牌
	
	private String ip; //IP
	
	private int port; //端口
	
	@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
	private Date heartBeatTime;		//最后在线心跳时间

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Date getHeartBeatTime() {
		return heartBeatTime;
	}

	public void setHeartBeatTime(Date heartBeatTime) {
		this.heartBeatTime = heartBeatTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
