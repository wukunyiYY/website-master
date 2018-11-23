package com.tc.website.modules.app.web.licence;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Unicorn
 * @date 2017/5/9
 */
public class LicenceValue implements Serializable {

    /*到达指定时间节点即过期*/
    public static final String TYPE_TIME_POINT = "TIME_POINT";

    /*使用时长到达即过期*/
    public static final String TYPE_TIME_LENGTH = "TIME_LENGTH";

    /*授权到期类型*/
    private String type;

    /*到期时间，TIME_POINT有效*/
    private Date expiredTime;

    /*有效时长，TIME_LENGTH有效*/
    private long effectiveTimeLength;

    /*是否显示试用信息*/
    private Boolean showTrial;

    /*网卡地址*/
    private String mac;

    /*硬盘序列号*/
    private String diskSn;

    /*cpu序列号*/
    private String cpuSn;

    /*是否绑定网卡*/
    private Boolean bindMac;

    /*是否绑定硬盘*/
    private Boolean bindDisk;

    /*是否绑定CPU*/
    private Boolean bindCpu;

    /*功能是否可用*/
    private Map<String,Boolean> functions;
    
    /*是否过期，通过校验License定时任务计算*/
    public Boolean isTimeOut = false;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public long getEffectiveTimeLength() {
		return effectiveTimeLength;
	}

	public void setEffectiveTimeLength(long effectiveTimeLength) {
		this.effectiveTimeLength = effectiveTimeLength;
	}

	public Boolean getShowTrial() {
		return showTrial;
	}

	public void setShowTrial(Boolean showTrial) {
		this.showTrial = showTrial;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getDiskSn() {
		return diskSn;
	}

	public void setDiskSn(String diskSn) {
		this.diskSn = diskSn;
	}

	public String getCpuSn() {
		return cpuSn;
	}

	public void setCpuSn(String cpuSn) {
		this.cpuSn = cpuSn;
	}

	public Boolean getBindMac() {
		return bindMac;
	}

	public void setBindMac(Boolean bindMac) {
		this.bindMac = bindMac;
	}

	public Boolean getBindDisk() {
		return bindDisk;
	}

	public void setBindDisk(Boolean bindDisk) {
		this.bindDisk = bindDisk;
	}

	public Boolean getBindCpu() {
		return bindCpu;
	}

	public void setBindCpu(Boolean bindCpu) {
		this.bindCpu = bindCpu;
	}

	public Map<String, Boolean> getFunctions() {
		return functions;
	}

	public void setFunctions(Map<String, Boolean> functions) {
		this.functions = functions;
	}

	public static String getTypeTimePoint() {
		return TYPE_TIME_POINT;
	}

	public static String getTypeTimeLength() {
		return TYPE_TIME_LENGTH;
	}
}
