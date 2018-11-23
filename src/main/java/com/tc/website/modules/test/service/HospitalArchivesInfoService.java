/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tc.website.common.persistence.Page;
import com.tc.website.common.service.CrudService;
import com.tc.website.common.utils.StringUtils;
import com.tc.website.modules.test.entity.HospitalArchivesInfo;
import com.tc.website.modules.test.dao.HospitalArchivesInfoDao;

/**
 * 医院信息Service
 * @author cyh
 * @version 2018-11-19
 */
@Service
@Transactional(readOnly = true)
public class HospitalArchivesInfoService extends CrudService<HospitalArchivesInfoDao, HospitalArchivesInfo> {

	
	public HospitalArchivesInfo get(String id) {
		HospitalArchivesInfo hospitalArchivesInfo = super.get(id);
		return hospitalArchivesInfo;
	}
	
	public List<HospitalArchivesInfo> findList(HospitalArchivesInfo hospitalArchivesInfo) {
		return super.findList(hospitalArchivesInfo);
	}
	
	public Page<HospitalArchivesInfo> findPage(Page<HospitalArchivesInfo> page, HospitalArchivesInfo hospitalArchivesInfo) {
		return super.findPage(page, hospitalArchivesInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(HospitalArchivesInfo hospitalArchivesInfo) {
		super.save(hospitalArchivesInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(HospitalArchivesInfo hospitalArchivesInfo) {
		super.delete(hospitalArchivesInfo);
	}
	
}