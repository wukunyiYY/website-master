package com.tc.website.webapi;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {
	
   ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("WebSite Framework Web API")
            .description("WebSite Admin Framework Web API "
            		+ "<div style='font-size:12px;'>"
                + "本文档是提供给第三方应用接入，用户调用的WEB API(V1)文档，此API需要您事先知道如下几点：<br/>"
                + "<ol>"
                + "<li>此API为每个功能接口使用一个URL，URL含V1或Vn代表了当前的api版本号。每个api接口都会详细描述实现的功能、"
                + "客户端所需的请求参数、服务端所响应的数据格式。并且本页面每个api都自带测试表单，可以通过本页面测试接口实现的准确性。</li>"
                + "<li>本api基于http+json制定，尽可能的利用http本身的资源实现restful。但根据现实情况，目前此api针对rest的区别如下："
	                + "<ol>"
		                + "<li>本api目前所有接口都使用http post请求完成</li>"
		                + "<li>本api并没有完全利用http自带响应码，而是在响应体中携带我们自定义的错误码和错误消息，但客户端调用时请务必先判断"
		                + "http常规响应码如400/401/404/500，然后统一处理错误码3000/3001/3002/3003/3004/3005/..../3201,以保证程序的健壮性。</li>"
	                + "</ol>"
                + "<li>客户端在执行请求时，若需要验证身份，请在http header加入API-Key 、 API-Secret头部(在Constant中定义)，该值通过创建API Key 获取。</li>"
                + "<li>服务端针对每个请求响应时一定会有必要字段（errCode和errMsg），这两个字段在RespBase类中，而针对本次请求的响应字段都在"
                + "继承于RespBase的具体类中定义。</li>"
                + "<li>服务端响应的错误码都在StatusCode中定义，包括对应的错误消息也在其中。在每个api接口中都会列出该接口可能返回的错误码。</li>"
            + "</ol>"
            + "<br/>"
            + "业务 级别返回代码(在StatusCode中定义)：<br/>"
            + "<ol>"
            + "<li>3000 - 处理成功</li>"
            + "<li>3001 - 处理失败</li>"
            + "<li>3002 - 未登入，请先登录</li>"
            + "<li>3003 - 账号或密码错误</li>"
            + "<li>3004 - 账号不存在</li>"
            + "<li>3005 - 验证码错误</li>"
            + "</ol>"
            + "API 级别返回代码(在StatusCode中定义)：<br/>"
            + "<ol>"
            + "<li>3100 - API Key 或 API Secret 错误</li>"
            + "<li>3101 - 权限不足,请联系管理员</li>"
            + "<li>3102 - 请求版本不支持</li>"
            + "<li>3103 - 请求参数不符合业务逻辑</li>"
            + "<li>3104 - 重复请求</li>"
            + "<li>3105 - 许可失效或未授权</li>"
            + "</ol>"
            + "系统 级别返回代码(在StatusCode中定义)：<br/>"
            + "<ol>"
            + "<li>3200 - 服务器内部发生错误</li>"
            + "<li>3201 - 操作失败，原因未知</li>"
            + "</ol>"
            + "最后，客户端开发者请向服务端开发者索要协议类定义，请不要根据协议自定义类，保证服务端和客户端协议类的同步。"
                + "</div>")
            .license("Copyright © LANXUM ALL Right Reserved")
            .licenseUrl("http://www.lanxum.com")
            .termsOfServiceUrl("http://www.lanxum.com")
            .version("1.0.0")
            .contact(new Contact("Jack Yu","http://www.lanxum.com","yuyongzhou@lanxum.com"))
            .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().paths(PathSelectors.regex("/api/.*")) 
                .build()
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
