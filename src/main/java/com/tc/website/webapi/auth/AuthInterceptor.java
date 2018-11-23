package com.tc.website.webapi.auth;

import com.tc.website.common.utils.RenderUtil;
import com.tc.website.modules.app.entity.ApiKey;
import com.tc.website.modules.app.utils.ApikeyUtil;
import com.tc.website.modules.app.utils.LicenceUtil;
import com.tc.website.modules.sys.entity.User;
import com.tc.website.modules.sys.utils.UserUtils;
import com.tc.website.webapi.Constant;
import com.tc.website.webapi.protocol.ApiResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 身份验证拦截器
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            Auth auth = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
            if(auth != null) {
				// 检验证书授权许可
				if(auth.requiredLicence()){
					if (LicenceUtil.verifyLicense(null) != 0) {
						respUnLicence(response);
        				return false;
					}
				}
				// 校验API Key授权许可
            	String apiKey = request.getHeader(Constant.HEADER_API_KEY);
            	String apiSecret = request.getHeader(Constant.HEADER_API_SECRET);
            	ApiKey faceApiKey = ApikeyUtil.checkApikey(apiKey, apiSecret);
            	if(auth.requiredAPIKey()) {
            		if(faceApiKey == null){
						respUnAPIKey(response);
            			return false;
            		}else if(faceApiKey.getStatus() == 0){
						respUnAurhorized(response);
            			return false;
            		}
            	}
				// 校验账号有效
            	if(auth.requiredAurhorized()){
					if(faceApiKey != null && faceApiKey.getUser() != null){
						User user = UserUtils.get(faceApiKey.getUser().getId());
						if(user == null || !"1".equals(user.getLoginFlag())){
							respUnAurhorized(response);
							return false;
						}
					}else {
						respUnAurhorized(response);
						return false;
					}
				}
            }
        }
        return super.preHandle(request, response, handler);
	}
	
	/**
	 * API Key 或 API Secret 错误
	 * @param response
	 */
	private void respUnAPIKey(HttpServletResponse response) {
		ApiResponseEntity apiResponseEntity = ApiResponseEntity.buildFailed("API Key 或 API Secret 错误");
		RenderUtil.renderJson(apiResponseEntity, response);
	}
	
	/**
	 * 权限不足
	 * @param response
	 */
	private void respUnAurhorized(HttpServletResponse response) {
		ApiResponseEntity apiResponseEntity = ApiResponseEntity.buildUnauthorized("权限不足");
		RenderUtil.renderJson(apiResponseEntity, response);
	}
	
	/**
	 * 许可失效或未授权
	 * @param response
	 */
	private void respUnLicence(HttpServletResponse response) {
		ApiResponseEntity apiResponseEntity = ApiResponseEntity.buildFailed("许可失效或未授权");
		RenderUtil.renderJson(apiResponseEntity, response);
	}
}
