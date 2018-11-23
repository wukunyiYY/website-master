package com.tc.website.configuration;

import com.tc.website.common.utils.StringUtils;
import com.tc.website.modules.app.utils.ConfigUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 应用配置文件
 * @author yyz
 *
 */
@Component
@Lazy(false)
public class ApplicationConfig {
   
	/*
	 * #根路徑
	 */
    private String diskPath = "D:/";
    
    /*
     * #缓存文件夹路径
     */
    private String tempPath = "temp/";
    
	/*
	 * #是否删除缓存文件
	 */
    private Integer isClearTemp = 0;

	public String getDiskPath() {
		if(ConfigUtil.getConfig() != null && StringUtils.isNotBlank(ConfigUtil.getConfig().getDiskPath())){
			diskPath = ConfigUtil.getConfig().getDiskPath();
		}
		return diskPath;
	}


	public String getTempPath() {
		if(ConfigUtil.getConfig() != null && StringUtils.isNotBlank(ConfigUtil.getConfig().getTempPath())){
			tempPath = ConfigUtil.getConfig().getTempPath();
		}
		return tempPath;
	}


	public Integer getIsClearTemp() {
		if(ConfigUtil.getConfig() != null && ConfigUtil.getConfig().getIsClearTemp() != null){
			isClearTemp = ConfigUtil.getConfig().getIsClearTemp();
		}
		return isClearTemp;
	}

}
