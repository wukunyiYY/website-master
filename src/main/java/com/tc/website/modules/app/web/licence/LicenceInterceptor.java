package com.tc.website.modules.app.web.licence;


import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 许可授权验证拦截器
 * @author Hogman
 *
 */
public class LicenceInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
//		if (path.equals("/a/third/facePhotoIdphoto")||path.equals("/a/third/faceTwoPhoto")
//				||path.equals("/a/third/faceLibFeatureCode")||path.equals("/a/third/faceComparePicLib")) {
//			if (LicenceUtil.checkLicense(LicenceValue.FUNCTION_COMPARE) != 0) {
//				response.sendRedirect(request.getContextPath() + Global.getAdminPath() + "/sys/license/license/unLicense");
//				return false;
//			}
//		}
//
//		if (path.equals("/a/cashboard/balanceCashboard")||path.equals("/a/order/balanceOrder")
//				||path.equals("/a/pay/balancePay")) {
//			if (LicenceUtil.checkLicense(LicenceValue.FUNCTION_BALANCE) != 0) {
//				response.sendRedirect(request.getContextPath() + Global.getAdminPath() + "/sys/license/license/unLicense");
//				return false;
//			}
//		}
		
	  return super.preHandle(request, response, handler);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) throws Exception {		
		super.afterCompletion(request, response, handler, ex);
	}
	
}
