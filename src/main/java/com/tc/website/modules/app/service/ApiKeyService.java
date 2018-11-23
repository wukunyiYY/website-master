/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tc.website.modules.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tc.website.common.persistence.Page;
import com.tc.website.common.service.CrudService;
import com.tc.website.modules.app.entity.ApiKey;
import com.tc.website.modules.app.dao.ApiKeyDao;

/**
 * 应用信息Service
 * @author Jack Yu
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class ApiKeyService extends CrudService<ApiKeyDao, ApiKey> {

	public ApiKey get(String id) {
		return super.get(id);
	}
	
	public List<ApiKey> findList(ApiKey apiKey) {
		return super.findList(apiKey);
	}
	
	public Page<ApiKey> findPage(Page<ApiKey> page, ApiKey apiKey) {
		return super.findPage(page, apiKey);
	}
	
	@Transactional(readOnly = false)
	public void save(ApiKey apiKey) {
		super.save(apiKey);
	}
	
	@Transactional(readOnly = false)
	public void delete(ApiKey apiKey) {
		super.delete(apiKey);
	}
	
}