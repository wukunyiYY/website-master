package com.tc.website.webapi.auth;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

	/**
	 * 需要APIKey
	 * @return
	 */
	boolean requiredAPIKey() default true;

	/**
	 * 是否检验证书授权许可
	 *
	 * @return
	 */
	boolean requiredLicence() default true;

	/**
	 * 是否验证鉴权
	 *
	 * @return
	 */
	boolean requiredAurhorized() default true;

}
