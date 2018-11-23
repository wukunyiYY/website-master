<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>API Key管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/app/apiKey/">API Key列表</a></li>
		<li class="active"><a href="${ctx}/app/apiKey/form?id=${apiKey.id}">API Key<shiro:hasPermission name="app:apiKey:edit">${not empty apiKey.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="app:apiKey:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="apiKey" action="${ctx}/app/apiKey/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">应用名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:choose>
			<c:when test="${not empty apiKey.id? true : false}">
				<div class="control-group">
					<label class="control-label">API Key：</label>
					<div class="controls">
						<form:input path="apiKey" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="readonly"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">API Secret：</label>
					<div class="controls">
						<form:input path="apiSecret" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="readonly"/>
					</div>
				</div>
			</c:when>
		</c:choose>

		<div class="control-group">
			<label class="control-label">应用类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('api_key_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">应用平台：</label>
			<div class="controls">
				<form:select path="plat" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('api_key_plat')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">启用状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('api_key_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生成记录：</label>
			<div class="controls">
				<form:select path="isSaveRecord" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('api_key_is_save_record')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">比对阀值：</label>
			<div class="controls">
				<form:input path="threshold" htmlEscape="false" maxlength="11" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">应用描述：</label>
			<div class="controls">
				<form:input path="depict" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="app:apiKey:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>