package com.tc.website.webapi.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 移除离线客户端
 * @author yyz
 * @date 2015-1-5
 */
@Component
public class ApiSessionTimeOutThread {
	
	private boolean isRun = true;
	
	@Autowired
	public ApiSessionTimeOutThread(final ApiSessionOnlineList list) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isRun) {
					try {
						Thread.sleep(1000*10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					list.removeTimeOutLogin();
				}
			}
		}).start();
	}
	
	@PreDestroy
	public void destroy() {
		isRun = false;
	}
}
