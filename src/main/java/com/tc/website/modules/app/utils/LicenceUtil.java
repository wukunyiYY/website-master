package com.tc.website.modules.app.utils;

import com.tc.website.common.utils.CacheUtils;
import com.tc.website.common.utils.StringUtils;
import com.tc.website.modules.app.web.licence.LicenceValue;

/**
 * License 授权校验工具类
 * @author Jack Yu
 *
 */
public class LicenceUtil {

	public static final String CACHE_LICENSE = "CACHE_LICENSE";
	
	/**
	 * 校验授权
	 * @param func
	 * @return -1 License文件不存在或授权无效 , -2 License 过期  , -3 该功能未授权 , 0  授权成功
	 */
	public static int verifyLicense(String func){
		LicenceValue licence = (LicenceValue) CacheUtils.get(CACHE_LICENSE);
		// 校验是否License文件不存在或授权无效
		if(licence == null){
			return -1;
		}
		
		// 校验是否过期
		if(licence.isTimeOut){
			return -2;
		}
		if(StringUtils.isNotBlank(func)){
			// 校验功能是否授权
			if(!licence.getFunctions().containsKey(func)){
				return -3;
			}else if(!licence.getFunctions().get(func)){
				return -3;
			}
		}
		return 0;
	}
	
	/**
	 * 更新License 状态
	 * @param licence
	 */
	public static void refreshLicense(LicenceValue licence){
		CacheUtils.put(CACHE_LICENSE, licence);
	}
}
