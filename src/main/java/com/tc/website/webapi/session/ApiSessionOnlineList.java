package com.tc.website.webapi.session;

import com.tc.website.common.utils.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 客户端在线列表缓存
 * @author yyz
 * @date 2014-12-22
 */

@Component
public class ApiSessionOnlineList {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private long TIME_OUT_MILLIS = 1000*60*60*24*360;
	
	private List<ApiSession> cacheClientList = Collections.synchronizedList(new ArrayList<ApiSession>());

	/**
	 * 通过token查找客户端信息
	 * @param accessToken
	 * @return
	 */
	public ApiSession getClientByToken(String accessToken){
		return findClientInfoByToken(accessToken);
	}
	
	/**
	 * 退出登录
	 * @param accessToken
	 */
	public void logoutByToken(String accessToken){
		ApiSession clientOld = findClientInfoByToken(accessToken);
		cacheClientList.remove(clientOld);
	}
	
	
	/**
	 * 通过token判断用户是否登陆
	 * @param accessToken
	 * @return
	 */
	public boolean isLoginByToken(String accessToken){
		return (findClientInfoByToken(accessToken) == null)? false : true;
	}
	
	
	public String login(String account , String ip , int port){

		ApiSession clientOld = findClientInfoByAccount(account);
		cacheClientList.remove(clientOld);
		
		ApiSession clientNew = new ApiSession();
		String accessToken = RandomUtils.generateMixString(20);//令牌
		clientNew.setAccessToken(accessToken);
		clientNew.setAccount(account);
		clientNew.setHeartBeatTime(new Date());
		clientNew.setIp(ip);
		clientNew.setPort(port);
		cacheClientList.add(clientNew);
		
		return accessToken;
	}
	
	/**
	 * 移除离线用户
	 */
	public void removeTimeOutLogin(){
		Iterator<ApiSession> iterator = cacheClientList.iterator();
		while(iterator.hasNext()){
			ApiSession client = iterator.next();
			Calendar currentTime = Calendar.getInstance();
            if(currentTime.getTimeInMillis() > (client.getHeartBeatTime().getTime() + TIME_OUT_MILLIS)){
            	logger.info("用户名："+client.getAccount()+",令牌失效！");
            	iterator.remove();
            }
		}
	}
	
	private ApiSession findClientInfoByToken(String accessToken){
		ApiSession client = null;
		for (ApiSession item : cacheClientList) {
			if(item.getAccessToken().equals(accessToken)){
				client = item;
				break;
			}
		}
		return client;
	}
	
	private ApiSession findClientInfoByAccount(String account){
		ApiSession client = null;
		for (ApiSession item : cacheClientList) {
			if(item.getAccount().equals(account)){
				client = item;
				break;
			}
		}
		return client;
	}
}
