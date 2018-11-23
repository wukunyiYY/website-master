/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.test.dao;

import com.tc.website.common.persistence.CrudDao;
import com.tc.website.common.persistence.annotation.MyBatisDao;
import com.tc.website.modules.test.entity.HospitalArchivesInfo;

/**
 * 医院信息DAO接口
 * @author cyh
 * @version 2018-11-19
 */
@MyBatisDao
public interface HospitalArchivesInfoDao extends CrudDao<HospitalArchivesInfo> {
	
}