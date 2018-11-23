/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.entity;

import com.tc.website.common.persistence.DataEntity;
import com.tc.website.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 应用信息Entity
 * @author Jack Yu
 * @version 2018-08-13
 */
public class ApiKey extends DataEntity<ApiKey> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户编号
	private String apiKey;		// API Key
	private String apiSecret;		// API Secret
	private Integer type;		// 应用类型：1-正式，0-试用
	private String name;		// 应用名称，小于100字符
	private Integer plat;		// 应用平台：1-Android平台、2-IOS平台、3-微信小程序、4-Web平台、5-其他
	private String depict;		// 应用描述，小于200字符
	private Integer status;		// 状态：0-禁用，1-启用
	private Integer isSaveRecord;		// 是否生成记录：0-否，1-是
	private Integer threshold;		// 阀值（人脸识别专用）
	
	public ApiKey() {
		super();
	}

	public ApiKey(String id){
		super(id);
	}

	@NotNull(message="用户编号不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=100, message="API Key长度必须介于 1 和 100 之间")
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Length(min=1, max=100, message="API Secret长度必须介于 1 和 100 之间")
	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}
	
	@NotNull(message="应用类型：1-正式，0-试用不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=1, max=100, message="应用名称，小于100字符长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="应用平台：1-Android平台、2-IOS平台、3-微信小程序、4-Web平台、5-其他不能为空")
	public Integer getPlat() {
		return plat;
	}

	public void setPlat(Integer plat) {
		this.plat = plat;
	}
	
	@Length(min=0, max=200, message="应用描述，小于200字符长度必须介于 0 和 200 之间")
	public String getDepict() {
		return depict;
	}

	public void setDepict(String depict) {
		this.depict = depict;
	}
	
	@NotNull(message="状态：0-禁用，1-启用不能为空")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@NotNull(message="是否生成记录：0-否，1-是不能为空")
	public Integer getIsSaveRecord() {
		return isSaveRecord;
	}

	public void setIsSaveRecord(Integer isSaveRecord) {
		this.isSaveRecord = isSaveRecord;
	}
	
	@NotNull(message="阀值（人脸识别专用）不能为空")
	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	
}