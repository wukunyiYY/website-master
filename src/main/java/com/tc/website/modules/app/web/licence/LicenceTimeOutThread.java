package com.tc.website.modules.app.web.licence;

import com.google.gson.Gson;
import com.tc.website.common.utils.AesUtil;
import com.tc.website.common.utils.ByteUtil;
import com.tc.website.common.utils.DateUtils;
import com.tc.website.common.utils.StringUtils;
import com.tc.website.modules.app.entity.Licence;
import com.tc.website.modules.app.service.LicenceService;
import com.tc.website.modules.app.utils.LicenceUtil;
import com.tc.website.modules.app.utils.SysUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * License 授权License校验
 * @author Jack Yu
 *
 */
@Lazy(value = false)
@Component
public class LicenceTimeOutThread {

	private static final Logger logger = LoggerFactory.getLogger(LicenceTimeOutThread.class);

	private boolean isRun = true;

	private LicenceService licenceService;

	@Autowired
	public LicenceTimeOutThread(final LicenceService licenceService) {

		this.licenceService = licenceService;

		new Thread(new Runnable() {

			@Override
			public void run() {
				logger.info("启动,授权License校验");

				while(isRun) {
					try {
						logger.info("执行,授权License校验");
						refreshLicense(licenceService);
						// 每30s校验一次
						Thread.sleep(1000 * 30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@PreDestroy
	public void destroy() {
		logger.info("停止,授权License校验");
		isRun = false;
	}

	private void refreshLicense(LicenceService licenceService){
		// 计算机器码
		MachineValue machineValue = new MachineValue(SysUtil.getCPUSerial(), SysUtil.getDiskSerial(), SysUtil.getMotherboardSerial());
		String machineValueJson = new Gson().toJson(machineValue);
		byte[] machineValueEnc = AesUtil.encrypt(machineValueJson, AesUtil.PASSWORD);
		String machineValueCode = ByteUtil.byte2Hex(machineValueEnc).toUpperCase();
		// 查询是否已经添加许可授权，未添加预先创建
		Licence licenceSearch = new Licence();
		licenceSearch.setMachineCode(machineValueCode);
		Licence licence = licenceService.findOneByMachineCode(licenceSearch);
		if(licence != null){
			String licenceValueCode = licence.getLicenceValue();
			if( !StringUtils.isNotBlank(licenceValueCode)){
				return;
			}
			byte[] licenceValueData = ByteUtil.hex2byte(licenceValueCode.toLowerCase());
			byte[] plainData = AesUtil.decrypt(licenceValueData, AesUtil.PASSWORD);
			String licenceValueJson = new String(plainData, Charset.forName("utf-8"));
			LicenceValue licenceValue = new Gson().fromJson(licenceValueJson, LicenceValue.class);
			if (licenceValue.getBindCpu() && !machineValue.getCpuSn().equals(licenceValue.getCpuSn())) {
				// cup 不匹配
				LicenceUtil.refreshLicense(null);
				return;
			}
			if (licenceValue.getBindDisk() && !machineValue.getDiskSn().equals(licenceValue.getDiskSn())) {
				// 硬盘不匹配
				LicenceUtil.refreshLicense(null);
				return;
			}
			if (licenceValue.getBindMac() && !machineValue.getMac().equals(licenceValue.getMac())) {
				// 系列号不匹配
				LicenceUtil.refreshLicense(null);
				return;
			}

			// 计算是否超时
			long currentTime = DateUtils.parseDate(DateUtils.getDate()).getTime(); // 当前时间
			long regTime = 0; // 注册时间
			long verifyTime = 0; // 校验时间
			if(StringUtils.isNotBlank(licence.getLicenceReg())){
				String regDateStr = licenceService.decrypt(licence.getLicenceReg());
				regTime = DateUtils.parseDate(regDateStr).getTime();
			}
			if(StringUtils.isNotBlank(licence.getLicenceVerify())){
				String verifyDateStr = licenceService.decrypt(licence.getLicenceVerify());
				verifyTime = DateUtils.parseDate(verifyDateStr).getTime();
			}
			// 如果系统时间小于最后一次校验时间，则用户修改过系统时间，直接计算授权失效
			if(verifyTime > currentTime){
				licenceValue.isTimeOut = true;
				LicenceUtil.refreshLicense(licenceValue);
				return;
			}
			// 校验License授权时间类型
			if(LicenceValue.TYPE_TIME_POINT.equals(licenceValue.getType())){
				// 到达指定时间节点即过期
				long expiredTime = licenceValue.getExpiredTime().getTime();
				if (expiredTime <= currentTime) {
					licenceValue.isTimeOut = true;
					LicenceUtil.refreshLicense(licenceValue);
					return;
				}
			}else{
				// 使用时长到达即过期
				long effectiveTime = licenceValue.getEffectiveTimeLength() * 24 * 60 * 60 * 1 * 1000;
				if((currentTime - regTime) >= effectiveTime){
					licenceValue.isTimeOut = true;
					LicenceUtil.refreshLicense(licenceValue);
					return;
				}
			}
			licenceValue.isTimeOut = false;
			LicenceUtil.refreshLicense(licenceValue);
			// 更新校验时间
			String nowDateStr = DateUtils.formatDate(new Date(),"yyyy-MM-dd");
			byte[] nowDateStrEnc = AesUtil.encrypt(nowDateStr, AesUtil.PASSWORD);
			String nowDateStrEncStr = ByteUtil.byte2Hex(nowDateStrEnc).toUpperCase();
			licence.setLicenceVerify(nowDateStrEncStr);
			licence.setUpdateDate(new Date());
			licenceService.save(licence);
		}
	}

	/**
	 * 手动执行，授权License校验
	 */
	public void refreshLicense(){
		logger.info("手动执行，授权License校验");
		refreshLicense(this.licenceService);
	}
}
