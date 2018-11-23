package com.tc.website.modules.sys.listener;

import com.tc.website.common.config.Global;
import com.tc.website.modules.sys.service.SystemService;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		// 检查初始化配置环境
		String activeSys = System.getenv("spring.profiles.active");
		if("development".equals(activeSys) || (null == activeSys)){
			// 开发环境
			System.out.println("当前配置为：开发环境 activeSys = " + activeSys);
			Global.initLoad("website.development.properties");
		}else if("test".equals(activeSys)){
			// 测试环境
			System.out.println("当前配置为：测试环境 activeSys = " + activeSys);
			Global.initLoad("website.test.properties");
		}else if("production".equals(activeSys)){
			// 生产环境
			System.out.println("当前配置为：生产环境 activeSys = " + activeSys);
			Global.initLoad("website.properties");
		}

		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
}
