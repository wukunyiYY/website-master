<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统配置管理</title>
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
		<li class="active"><a href="${ctx}/app/config/form?id=${config.id}">系统配置</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="config" action="${ctx}/app/config/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">根路径（磁盘）：</label>
			<div class="controls">
				<form:input path="diskPath" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
				<span class="help-inline">1-windows系统示例（ D:\ ）、 2-linux系统示例（ /usr/local/file ）、 3-windows系统Tomcat 8 （ D:\子目录\ ）</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缓存路径：</label>
			<div class="controls">
				<form:input path="tempPath" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开启清除缓存：</label>
			<div class="controls">
				<form:select path="isClearTemp" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('config_is_clear_temp')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="app:config:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>