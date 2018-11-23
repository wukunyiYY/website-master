/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.web;

import com.google.gson.Gson;
import com.tc.website.common.utils.*;
import com.tc.website.common.web.BaseController;
import com.tc.website.modules.app.entity.Licence;
import com.tc.website.modules.app.service.LicenceService;
import com.tc.website.modules.app.utils.LicenceUtil;
import com.tc.website.modules.app.utils.SysUtil;
import com.tc.website.modules.app.web.licence.LicenceValue;
import com.tc.website.modules.app.web.licence.MachineValue;
import com.tc.website.modules.app.web.licence.LicenceTimeOutThread;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * 许可授权Controller
 * @author Jack Yu
 * @version 2018-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/app/licence")
public class LicenceController extends BaseController {

	@Autowired
	private LicenceService licenceService;

	@Autowired
	private LicenceTimeOutThread licenseTimeOutThread;

	/**
	 * 启动授权界面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("app:licence:view")
	@RequestMapping(value = "form")
	public String form(Model model){
		boolean result = false;
		int licenceStatus = 0; // 授权状态 :  0 - 未授权或授权异常，1 - 授权成功， 2 - 授权过期或授权异常
		// 计算机器码
		MachineValue machineValue = new MachineValue(SysUtil.getCPUSerial(), SysUtil.getDiskSerial(), SysUtil.getMotherboardSerial());
		String machineValueJson = new Gson().toJson(machineValue);
		byte[] machineValueEnc = AesUtil.encrypt(machineValueJson, AesUtil.PASSWORD);
		String machineValueCode = ByteUtil.byte2Hex(machineValueEnc).toUpperCase();
		// 授权码
		LicenceValue licenceValue = new LicenceValue();
		// 查询是否已经添加许可授权，未添加预先创建
		Licence licenceSearch = new Licence();
		licenceSearch.setMachineCode(machineValueCode);
		Licence licence = licenceService.findOneByMachineCode(licenceSearch);
		if(licence == null){
			licence = new Licence();
			licence.setId(IdGen.uuid());
			licence.setIsNewRecord(true);
			licence.setMachineCode(machineValueCode);
			licenceService.save(licence);
			result = false;
		}else {
			//计算授权码
			String licenceValueCode = licence.getLicenceValue();
			if(StringUtils.isNotBlank(licenceValueCode)){
				byte[] licenceValueData = ByteUtil.hex2byte(licenceValueCode.toLowerCase());
				byte[] plainData = AesUtil.decrypt(licenceValueData, AesUtil.PASSWORD);
				String licenceValueJson = new String(plainData, Charset.forName("utf-8"));
				licenceValue = new Gson().fromJson(licenceValueJson, LicenceValue.class);
				result = true;
			}else{
				result = false;
			}
		}
		int verifyFlag = LicenceUtil.verifyLicense(null);
		if(verifyFlag == -1){
			// 无效或未授权
			licenceStatus = 0;
		}else if(verifyFlag == -2){
			// 过期
			licenceStatus = 2;
		}else if(verifyFlag == 0){
			// 正常
			licenceStatus = 1;
		}
		model.addAttribute("licenceStatus", licenceStatus);
		model.addAttribute("result", result);
		model.addAttribute("machineValue", machineValue);
		model.addAttribute("machineValueCode", machineValueCode);
		model.addAttribute("licenceValue", licenceValue);
		model.addAttribute("licenceValueCode", licence.getLicenceValue());
		return "modules/app/licenceForm";
	}

	/**
	 * licence 未授权
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("app:licence:view")
	@RequestMapping(value = "unLicence")
	public String unLicense() {
		return "modules/app/unLicence";
	}

	/**
	 * 导出机器码
	 * @param machineValueCode
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("app:licence:edit")
	@RequestMapping(value = "exportMachineCode")
	public void exportMachineCode(String machineValueCode, HttpServletResponse response) throws Exception {
		if(StringUtils.isNotBlank(machineValueCode)){
			String fileName = "machine.cert";
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ Encodes.urlEncode(fileName));
			OutputStream outputStream = null;
			try {
				outputStream = response.getOutputStream();
				outputStream.write(machineValueCode.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(outputStream != null){
					try {
						outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}


	/**
	 * 导入授权秘钥
	 * @param file
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("app:licence:edit")
	@RequestMapping(value = "upLoadLicenceCode")
	public void upLoadLicenceCode(@RequestParam("licenceCodeFile") MultipartFile file,  HttpServletResponse response) throws Exception {
		boolean result = false;
		if (file.getSize() != 0) {
			// 读取上传文件内容
			File fileTemp = File.createTempFile("realhowto", ".vbs");
			fileTemp.deleteOnExit();
			InputStream inputStream = new ByteArrayInputStream(file.getBytes());
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuilder stringBuilder = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
			String tempStr = "";
			while (StringUtils.isNotBlank(tempStr = bufferedReader.readLine())) {//逐行读取文件内容，不读取换行符和末尾的空格
				stringBuilder.append(tempStr + "\n");//将读取的字符串添加换行符后累加存放在缓存中
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			// 开始解密
			String licenceValueCode = stringBuilder.toString();
			byte[] data = ByteUtil.hex2byte(licenceValueCode.toLowerCase());
			byte[] plainData = AesUtil.decrypt(data, AesUtil.PASSWORD);
			String licenceJson = new String(plainData, Charset.forName("utf-8"));
			LicenceValue licenceValue = new Gson().fromJson(licenceJson, LicenceValue.class); // 授权码
			String licenceValueStr = licenceValue.getCpuSn() + licenceValue.getDiskSn() + licenceValue.getMac(); // 授权校验码
			MachineValue machineValue = new MachineValue(SysUtil.getCPUSerial(), SysUtil.getDiskSerial(), SysUtil.getMotherboardSerial()); // 计算机器码
			String machineValueStr = machineValue.getCpuSn() + machineValue.getDiskSn() + machineValue.getMac(); // 机器校验码

			if(machineValueStr.equals(licenceValueStr)){
				// 校验成功
				result = true;
				// 更新许可授权
				String nowDateStr = DateUtils.formatDate(new Date(),"yyyy-MM-dd");
				byte[] nowDateStrEnc = AesUtil.encrypt(nowDateStr, AesUtil.PASSWORD);
				String nowDateStrEncStr = ByteUtil.byte2Hex(nowDateStrEnc).toUpperCase();

				String machineCodeJson = new Gson().toJson(machineValue);
				byte[] machineCodeJsonEnc = AesUtil.encrypt(machineCodeJson, AesUtil.PASSWORD);
				String machineCodeJsonEncStr = ByteUtil.byte2Hex(machineCodeJsonEnc).toUpperCase();
				Licence licenceSearch = new Licence();
				licenceSearch.setMachineCode(machineCodeJsonEncStr);
				Licence licence = licenceService.findOneByMachineCode(licenceSearch);
				if(licence == null){
					licence = new Licence();
					licence.setId(IdGen.uuid());
					licence.setIsNewRecord(true);
					licence.setMachineCode(machineCodeJsonEncStr);
				}
				licence.setLicenceReg(nowDateStrEncStr); // 注册时间
				licence.setLicenceVerify(nowDateStrEncStr); // 校验时间
				licence.setLicenceValue(licenceValueCode);
				licenceService.save(licence); // 保存
				licenseTimeOutThread.refreshLicense(); // 刷新授权校验
			}
		}
		RenderUtil.renderJson(result, response);
	}

	/**
	 * 导出秘钥文件
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("app:licence:edit")
	@RequestMapping(value = "exportLicenceCode")
	public void exportLicenceCode(HttpServletResponse response) throws Exception {
		// 计算机器码
		MachineValue machineValue = new MachineValue(SysUtil.getCPUSerial(), SysUtil.getDiskSerial(), SysUtil.getMotherboardSerial());
		String machineValueJson = new Gson().toJson(machineValue);
		byte[] machineValueJsonEnc = AesUtil.encrypt(machineValueJson, AesUtil.PASSWORD);
		String machineValueCode = ByteUtil.byte2Hex(machineValueJsonEnc).toUpperCase();
		// 查询当前机器码是否存在授权秘钥
		Licence licenceSearch = new Licence();
		licenceSearch.setMachineCode(machineValueCode);
		Licence licence = licenceService.findOneByMachineCode(licenceSearch);
		if(licence != null && StringUtils.isNotBlank(licence.getLicenceValue())){
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=license.cert");
			InputStream inputStream = new ByteArrayInputStream(licence.getLicenceValue().getBytes());
			IOUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
		}
	}
}