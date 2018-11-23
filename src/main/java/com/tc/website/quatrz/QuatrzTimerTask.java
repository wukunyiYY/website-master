package com.tc.website.quatrz;

import com.tc.website.configuration.ApplicationConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuatrzTimerTask {
	
	private Logger log = Logger.getLogger(QuatrzTimerTask.class);
	
	@Autowired
	private ApplicationConfig applicationConfig;

	/**
	 * 清除缓存文件
	 */
	public void deleteTemp(){
		System.out.println("开始，清除缓存文件");
		log.info("开始，清除缓存文件");
		if("1".equals(applicationConfig.getIsClearTemp())){

		}
	}
}
