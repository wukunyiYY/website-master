/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.gen.dao;

import com.tc.website.common.persistence.CrudDao;
import com.tc.website.common.persistence.annotation.MyBatisDao;
import com.tc.website.common.persistence.CrudDao;
import com.tc.website.common.persistence.annotation.MyBatisDao;
import com.tc.website.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author ThinkGem
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
