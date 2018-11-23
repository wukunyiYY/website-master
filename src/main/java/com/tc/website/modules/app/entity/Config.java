/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.entity;

import org.hibernate.validator.constraints.Length;

import com.tc.website.common.persistence.DataEntity;

/**
 * 参数配置Entity
 * @author Jack Yu
 * @version 2018-08-13
 */
public class Config extends DataEntity<Config> {
	
	private static final long serialVersionUID = 1L;
	private String diskPath;		// 磁盘根路径
	private String tempPath;		// 缓存文件路径，相对磁盘根路径
	private Integer isClearTemp;		// 是否清除缓存，定时23:00 ： 1-开启，0-禁止
	
	public Config() {
		super();
	}

	public Config(String id){
		super(id);
	}

	@Length(min=0, max=255, message="磁盘根路径长度必须介于 0 和 255 之间")
	public String getDiskPath() {
		return diskPath;
	}

	public void setDiskPath(String diskPath) {
		this.diskPath = diskPath;
	}
	
	@Length(min=0, max=255, message="缓存文件路径，相对磁盘根路径长度必须介于 0 和 255 之间")
	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
	
	public Integer getIsClearTemp() {
		return isClearTemp;
	}

	public void setIsClearTemp(Integer isClearTemp) {
		this.isClearTemp = isClearTemp;
	}
	
}