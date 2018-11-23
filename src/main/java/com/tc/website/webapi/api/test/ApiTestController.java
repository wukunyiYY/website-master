package com.tc.website.webapi.api.test;

import com.tc.website.webapi.auth.Auth;
import com.tc.website.webapi.protocol.ApiResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Api测试接口")
@RestController
@RequestMapping("/api/test")
public class ApiTestController {

    @ApiOperation("校验apikey")
    @GetMapping("verify-apikey")
    @Auth(requiredAPIKey = true,requiredLicence = true,requiredAurhorized = true)
    public ApiResponseEntity verifyApikey(
            @RequestHeader(required = true)
            String ApiKey,
            @RequestHeader(required = true)
            String ApiSecret) {

        return ApiResponseEntity.buildOK("校验通过");
    }
}
