/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.cms.dao;

import com.tc.website.common.persistence.CrudDao;
import com.tc.website.common.persistence.annotation.MyBatisDao;
import com.tc.website.common.persistence.CrudDao;
import com.tc.website.common.persistence.annotation.MyBatisDao;
import com.tc.website.modules.cms.entity.ArticleData;

/**
 * 文章DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface ArticleDataDao extends CrudDao<ArticleData> {
	
}
