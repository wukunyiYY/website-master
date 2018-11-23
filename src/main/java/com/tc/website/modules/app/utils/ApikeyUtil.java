package com.tc.website.modules.app.utils;

import com.tc.website.common.utils.CacheUtils;
import com.tc.website.modules.app.entity.ApiKey;
import com.tc.website.modules.app.service.ApiKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * FaceApiKey 工具类
 * @author Hogman
 *
 */
@Lazy(value = false)
@Component
public class ApikeyUtil {

	private static final Logger logger = LoggerFactory.getLogger(ApikeyUtil.class);

	public static final String CACHE_APIKEY = "CACHE_APIKEY";

	public static Map<String, ApiKey> apiKeyMap = new HashMap<String, ApiKey>();

	@Autowired
	public ApikeyUtil(ApiKeyService apiKeyService) {
		logger.info("初始化Apikey缓存数据");
		ApikeyUtil.refreshApiKey(apiKeyService);
	}		
	
	/**
	 * 校验ApiKey
	 * @param apiKey
	 * @param apiSecret
	 */
	public static ApiKey checkApikey(String apiKey, String apiSecret){
		String key = apiKey + apiSecret;
		ApiKey faceApiKey = ApikeyUtil.apiKeyMap.get(key);
		return faceApiKey;
	}
	
	/**
	 * 重新获取apikey列表
	 * @param faceApiKeyService
	 */
	public static void refreshApiKey(ApiKeyService faceApiKeyService){
		List<ApiKey> list = faceApiKeyService.findList(null);
		Map<String, ApiKey> apiKeyMap = new HashMap<String, ApiKey>();
		if(list.size() !=0){
			for (ApiKey faceApiKey : list) {
				String key = faceApiKey.getApiKey() + faceApiKey.getApiSecret();
				apiKeyMap.put(key, faceApiKey);
			}			
		}		
		CacheUtils.put(CACHE_APIKEY, apiKeyMap);
		ApikeyUtil.apiKeyMap.clear();
		ApikeyUtil.apiKeyMap.putAll(apiKeyMap);
	}
}
