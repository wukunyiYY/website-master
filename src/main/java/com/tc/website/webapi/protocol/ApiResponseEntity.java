package com.tc.website.webapi.protocol;

/**
 * HTTP响应基础实体
 * @author Unicorn Lien
 * 2016年10月18日 下午11:15:19 创建
 */
public class ApiResponseEntity<T> {
    
    /*业务响应码*/
    private String code;

    /*错误码*/
    private Integer errCode;
    
    /*业务响应消息*/
    private String message;
    
    /*业务响应体*/
    private T body;
    
    public ApiResponseEntity() {
        super();
    }

    public ApiResponseEntity(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ApiResponseEntity(String code, String message, int errCode) {
        super();
        this.code = code;
        this.message = message;
        this.errCode = errCode;
    }
    
    public ApiResponseEntity(ApiStatusCode code, String message) {
        super();
        this.code = code.toString();
        this.message = message;
    }

    public ApiResponseEntity(ApiStatusCode code, String message, int errCode) {
        super();
        this.code = code.toString();
        this.message = message;
        this.errCode = errCode;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getCode() {
        return code;
    }

    public ApiResponseEntity<T> setCode(String code) {
        this.code = code;
        return this;
    }
    
    public ApiResponseEntity<T> setCode(ApiStatusCode apiStatusCode) {
        this.code = apiStatusCode.toString();
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResponseEntity<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    
    public T getBody() {
		return body;
	}

	public ApiResponseEntity<T> setBody(T body) {
		this.body = body;
		return this;
	}

	/*快速构建响应对象*/
	public static <T> ApiResponseEntity<T> buildOK() {
        return buildOK("成功");
    }
	
    public static <T> ApiResponseEntity<T> buildOK(String message) {
        return new ApiResponseEntity<>(ApiStatusCode.OK, message, ApiErrorCode.OK);
    }

    public static <T> ApiResponseEntity<T> buildOK(String message, T body) {
        ApiResponseEntity<T> apiResponseEntity = new ApiResponseEntity<>(ApiStatusCode.OK, message, ApiErrorCode.OK);
        apiResponseEntity.setBody(body);
        return apiResponseEntity;
    }

    public static <T> ApiResponseEntity<T> buildOK(T body) {
        ApiResponseEntity<T> apiResponseEntity = ApiResponseEntity.buildOK();
        apiResponseEntity.setBody(body);
        return apiResponseEntity;
    }

    public static <T> ApiResponseEntity<T> buildOccupy() {
	    return buildOccupy("请求资源已占用");
    }

    public static <T> ApiResponseEntity<T> buildOccupy(String message) {
	    return new ApiResponseEntity<>(ApiStatusCode.OCCUPY, message, ApiErrorCode.OCCUPY);
    }
    
    public static <T> ApiResponseEntity<T> buildFailed() {
        return buildFailed("失败");
    }
    
    public static <T> ApiResponseEntity<T> buildFailed(String message) {
        return new ApiResponseEntity<>(ApiStatusCode.FAILED, message, ApiErrorCode.FAILED);
    }
    
    public static <T> ApiResponseEntity<T> buildNotFound() {
        return buildNotFound("资源不存在");
    }
    
    public static <T> ApiResponseEntity<T> buildNotFound(String message) {
        return new ApiResponseEntity<>(ApiStatusCode.NOT_FOUND, message, ApiErrorCode.NOT_FOUND);
    }
    
    public static <T> ApiResponseEntity<T> buildUnauthorized() {
        return buildUnauthorized("未登录");
    }
    
    public static <T> ApiResponseEntity<T> buildUnauthorized(String message) {
        return new ApiResponseEntity<>(ApiStatusCode.UNAUTHORIZED, message, ApiErrorCode.UNAUTHORIZED);
    }
    
    public static <T> ApiResponseEntity<T> buildPermissionDenied() {
        return buildPermissionDenied("没有权限");
    }
    
    public static <T> ApiResponseEntity<T> buildPermissionDenied(String message) {
        return new ApiResponseEntity<>(ApiStatusCode.PERMISSION_DENIED, message, ApiErrorCode.PERMISSION_DENIED);
    }
    
    public static <T> ApiResponseEntity<T> buildIllegalArgument() {
        return buildIllegalArgument("请求参数错误");
    }
    
    public static <T> ApiResponseEntity<T> buildIllegalArgument(String message) {
        return new ApiResponseEntity<>(ApiStatusCode.ILLEGAL_ARGUMENT, message, ApiErrorCode.ILLEGAL_ARGUMENT);
    }


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
        result = prime * result + ((errCode == null) ? 0 : errCode.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiResponseEntity<?> other = (ApiResponseEntity<?>) obj;

		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;

		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;

        if (errCode == null) {
            if (other.errCode != null)
                return false;
        } else if (!errCode.equals(other.errCode))
            return false;

		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "ApiResponseEntity [code=" + code + ", errCode=" + errCode + ", message=" + message + ", body=" + body + "]";
	}

}
