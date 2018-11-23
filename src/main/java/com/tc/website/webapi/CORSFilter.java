package com.tc.website.webapi;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决跨域问题
 * 浏览器跨域请求将会发送两次请求
 * 第一次为head请求，得到http响应头，若头部限制不允许跨域请求，浏览器将不再进行第二次请求
 * 客户端http编程不会出现此问题
 * 本过滤器可在web.xml中配置指定匹配url的请求允许跨域
 * @author 大爱阳哥
 *
 */
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		resp.setHeader("Access-Control-Max-Age", "3600");
		//Content-Type, Authorization, Accept,X-Requested-With,Access-Token
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept,X-Requested-With,Access-Token");
	    chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
