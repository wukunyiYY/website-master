/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.test.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.tc.website.common.persistence.DataEntity;

/**
 * 医院信息Entity
 * @author cyh
 * @version 2018-11-19
 */
public class HospitalArchivesInfo extends DataEntity<HospitalArchivesInfo> {
	
	private static final long serialVersionUID = 1L;
	private Date createdTime;		// 创建时间
	private Date updatedTime;		// 修改时间
	private String dataState;		// 状态
	private String hospitalName;		// 医院名称
	private String hospitalCode;		// 医院代码
	private String hospitalPrincipal;		// 医院负责人
	private String hospitalContact;		// 医院联系
	private String hospitalEmail;		// 医院邮箱
	private String hospitalAddress;		// 医院地址
	private String officePhone;		// 办公电话

	private String beginDate;	// 开始时间

	private String endDate;	// 结束时间

	public HospitalArchivesInfo() {
		super();
	}

	public HospitalArchivesInfo(String id){
		super(id);
	}

	@NotNull(message="创建时间不能为空")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@NotNull(message="修改时间不能为空")
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	@Length(min=1, max=50, message="状态长度必须介于 1 和 50 之间")
	public String getDataState() {
		return dataState;
	}

	public void setDataState(String dataState) {
		this.dataState = dataState;
	}
	
	@Length(min=1, max=100, message="医院名称长度必须介于 1 和 100 之间")
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	
	@Length(min=1, max=50, message="医院代码长度必须介于 1 和 50 之间")
	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	
	@Length(min=1, max=50, message="医院负责人长度必须介于 1 和 50 之间")
	public String getHospitalPrincipal() {
		return hospitalPrincipal;
	}

	public void setHospitalPrincipal(String hospitalPrincipal) {
		this.hospitalPrincipal = hospitalPrincipal;
	}
	
	@Length(min=1, max=50, message="医院联系长度必须介于 1 和 50 之间")
	public String getHospitalContact() {
		return hospitalContact;
	}

	public void setHospitalContact(String hospitalContact) {
		this.hospitalContact = hospitalContact;
	}
	
	@Length(min=1, max=100, message="医院邮箱长度必须介于 1 和 100 之间")
	public String getHospitalEmail() {
		return hospitalEmail;
	}

	public void setHospitalEmail(String hospitalEmail) {
		this.hospitalEmail = hospitalEmail;
	}
	
	@Length(min=1, max=300, message="医院地址长度必须介于 1 和 300 之间")
	public String getHospitalAddress() {
		return hospitalAddress;
	}

	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}
	
	@Length(min=0, max=30, message="办公电话长度必须介于 0 和 30 之间")
	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}


	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}