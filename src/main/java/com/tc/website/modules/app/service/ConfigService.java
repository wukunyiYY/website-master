/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tc.website.common.persistence.Page;
import com.tc.website.common.service.CrudService;
import com.tc.website.modules.app.entity.Config;
import com.tc.website.modules.app.dao.ConfigDao;

/**
 * 参数配置Service
 * @author Jack Yu
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class ConfigService extends CrudService<ConfigDao, Config> {

	public Config get(String id) {
		return super.get(id);
	}
	
	public List<Config> findList(Config config) {
		return super.findList(config);
	}
	
	public Page<Config> findPage(Page<Config> page, Config config) {
		return super.findPage(page, config);
	}
	
	@Transactional(readOnly = false)
	public void save(Config config) {
		super.save(config);
	}
	
	@Transactional(readOnly = false)
	public void delete(Config config) {
		super.delete(config);
	}
	
}