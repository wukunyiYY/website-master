/**
 * 
 */
package com.tc.website.common.utils;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * 绕过jsp/freemaker直接输出文本的简化函数.
 * @author yyz
 *
 */
public class RenderUtil {

	private static Gson gson = new Gson();
	// -- header 常量定义 --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

	private static Logger logger = Logger.getLogger(RenderUtil.class);

	// -- 取得Request/Response/Session的简化函数 --//

	// -- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	private static void render(final String contentType, final String content, HttpServletResponse response,
			final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, HttpServletResponse response, final String... headers) {
		render(TEXT_TYPE, text, response, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, HttpServletResponse response, final String... headers) {
		render(HTML_TYPE, html, response, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, HttpServletResponse response, final String... headers) {
		render(XML_TYPE, xml, response, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString, HttpServletResponse response, final String... headers) {
		render(JSON_TYPE, jsonString, response, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Map map, HttpServletResponse response, final String... headers) {
		String jsonString = gson.toJson(map);
		render(JSON_TYPE, jsonString, response, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object, HttpServletResponse response, final String... headers) {
		String jsonString = gson.toJson(object); //JSONObject.fromObject(object).toString();
		render(JSON_TYPE, jsonString, response, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param collection
	 *            Java对象集合, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Collection<?> collection, HttpServletResponse response, final String... headers) {
		String jsonString = gson.toJson(collection);
		render(JSON_TYPE, jsonString, response, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param array
	 *            Java对象数组, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object[] array, HttpServletResponse response, final String... headers) {
		String jsonString = gson.toJson(array);
		render(JSON_TYPE, jsonString, response, headers);
	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName
	 *            callback函数名.
	 * @param contentMap
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJsonp(final String callbackName, final Map contentMap, HttpServletResponse response,
			final String... headers) {
		String jsonParam =gson.toJson(contentMap);

		StringBuilder result = new StringBuilder().append(callbackName).append("(").append(jsonParam).append(");");

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{content:'Hello World!!!'}");
		render(JS_TYPE, result.toString(), response, headers);
	}
	

	/**
	 * 返回成功信息 {'success':successInfo}
	 * 
	 * @param successInfo
	 */
	public static void renderSuccess(String successInfo, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", successInfo);
		RenderUtil.renderJson(map, response);
	}

	/**
	 * 返回失败信息 {'failure':failInfo}
	 * 
	 * @param failInfo
	 */
	public static void renderFailure(String failInfo, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("failure", failInfo);
		RenderUtil.renderJson(map, response);
	}
	
	public static void renderFailureEx(String failInfo, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("failure", failInfo);
		String jsonString = gson.toJson(map);
		jsonString = "<body><pre>" + jsonString + "</pre></body>";
		RenderUtil.renderText(jsonString, response);
	}
	
	public static void renderSuccessEx(String failInfo, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("success", failInfo);
		String jsonString = gson.toJson(map);
		jsonString = "<body><pre>" + jsonString + "</pre></body>";
		RenderUtil.renderText(jsonString, response);
	}
}
