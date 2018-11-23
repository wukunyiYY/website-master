/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.dao;

import com.tc.website.common.persistence.CrudDao;
import com.tc.website.common.persistence.annotation.MyBatisDao;
import com.tc.website.modules.app.entity.Config;

/**
 * 参数配置DAO接口
 * @author Jack Yu
 * @version 2018-08-13
 */
@MyBatisDao
public interface ConfigDao extends CrudDao<Config> {
	
}