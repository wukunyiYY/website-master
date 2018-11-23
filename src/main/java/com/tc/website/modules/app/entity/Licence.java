/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.entity;

import org.hibernate.validator.constraints.Length;

import com.tc.website.common.persistence.DataEntity;

/**
 * 许可授权Entity
 * @author Jack Yu
 * @version 2018-08-13
 */
public class Licence extends DataEntity<Licence> {
	
	private static final long serialVersionUID = 1L;
	private String machineCode;		// machine_code
	private String licenceReg;		// licence_reg
	private String licenceVerify;		// licence_verify
	private String licenceValue;		// licence_value
	
	public Licence() {
		super();
	}

	public Licence(String id){
		super(id);
	}

	@Length(min=0, max=1000, message="machine_code长度必须介于 0 和 1000 之间")
	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	@Length(min=0, max=255, message="licence_reg长度必须介于 0 和 255 之间")
	public String getLicenceReg() {
		return licenceReg;
	}

	public void setLicenceReg(String licenceReg) {
		this.licenceReg = licenceReg;
	}
	
	@Length(min=0, max=255, message="licence_verify长度必须介于 0 和 255 之间")
	public String getLicenceVerify() {
		return licenceVerify;
	}

	public void setLicenceVerify(String licenceVerify) {
		this.licenceVerify = licenceVerify;
	}
	
	@Length(min=0, max=4096, message="licence_value长度必须介于 0 和 4096 之间")
	public String getLicenceValue() {
		return licenceValue;
	}

	public void setLicenceValue(String licenceValue) {
		this.licenceValue = licenceValue;
	}
	
}