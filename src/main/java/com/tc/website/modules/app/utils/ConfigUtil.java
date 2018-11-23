package com.tc.website.modules.app.utils;


import com.tc.website.common.utils.CacheUtils;
import com.tc.website.modules.app.entity.Config;
import com.tc.website.modules.app.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Config 工具类
 * @author rds
 *
 */
@Lazy(value = false)
@Component
public class ConfigUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	
	public static final String CACHE_CONFIG = "CACHE_CONFIG";
	
	
	@Autowired
	public ConfigUtil(ConfigService configService) {
		logger.info("初始化Config缓存数据");
		ConfigUtil.refreshConfig(configService);
	}

	/**
	 * 获取系统参数配置
	 * @return
	 */
	public static Config getConfig(){
		Config config = (Config) CacheUtils.get(CACHE_CONFIG);
		return config;
	}
	
	/**
	 * 更新Config 状态
	 * @param configService
	 */
	public static void refreshConfig(ConfigService configService){
		List<Config> list = configService.findList(null);
		CacheUtils.put(CACHE_CONFIG, null);
		if (list.size() == 1) {
			Config config = list.get(0);
			CacheUtils.put(CACHE_CONFIG, config);
		}
	}
}
