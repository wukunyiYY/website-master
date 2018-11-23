package com.tc.website.modules.app.web.licence;


import java.io.Serializable;

/**
 * 机器信息
 * @author Jack Yu
 *
 */
public class MachineValue implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cpuSn;

    private String diskSn;

    private String mac;

    public MachineValue() {}

    public MachineValue(String cpuSn, String diskSn, String mac) {
        this.cpuSn = cpuSn;
        this.diskSn = diskSn;
        this.mac = mac;
    }

	public String getCpuSn() {
		return cpuSn;
	}

	public void setCpuSn(String cpuSn) {
		this.cpuSn = cpuSn;
	}

	public String getDiskSn() {
		return diskSn;
	}

	public void setDiskSn(String diskSn) {
		this.diskSn = diskSn;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
    
}
