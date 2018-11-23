/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.service;

import com.tc.website.common.persistence.Page;
import com.tc.website.common.service.CrudService;
import com.tc.website.common.utils.AesUtil;
import com.tc.website.common.utils.ByteUtil;
import com.tc.website.modules.app.dao.LicenceDao;
import com.tc.website.modules.app.entity.Licence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 许可授权Service
 * @author Jack Yu
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class LicenceService extends CrudService<LicenceDao, Licence> {

	@Autowired
	LicenceDao dao;

	private final static String PASSWORD = "itistcsfpassword";

	public Licence get(String id) {
		return super.get(id);
	}
	
	public List<Licence> findList(Licence licence) {
		return super.findList(licence);
	}
	
	public Page<Licence> findPage(Page<Licence> page, Licence licence) {
		return super.findPage(page, licence);
	}
	
	@Transactional(readOnly = false)
	public void save(Licence licence) {
		super.save(licence);
	}
	
	@Transactional(readOnly = false)
	public void delete(Licence licence) {
		super.delete(licence);
	}

	@Transactional(readOnly = false)
	public Licence findOneByMachineCode(Licence licence){
		return dao.findOneByMachineCode(licence);
	}

	// 加密
	public String encrypt(String str){
		byte[] data = AesUtil.encrypt(str, PASSWORD);
		String encryptStr = ByteUtil.byte2Hex(data).toUpperCase();
		return encryptStr;
	}

	// 解密
	public String decrypt(String str){
		byte[] data = ByteUtil.hex2byte(str.toLowerCase());
		byte[] plainData = AesUtil.decrypt(data, PASSWORD);
		String decryptStr = new String(plainData, Charset.forName("utf-8"));
		return decryptStr;
	}

	// 读取文件信息
	public String readFile(File file){
		String code = null;
		try {
			FileReader reader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(reader);
			StringBuilder sb = new StringBuilder();
			String s = "";
			while ((s = bReader.readLine()) != null) {
				sb.append(s + "\n");
			}
			bReader.close();
			code = sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
}