<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>API Key管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/app/apiKey/">API Key列表</a></li>
		<shiro:hasPermission name="app:apiKey:edit"><li><a href="${ctx}/app/apiKey/form">API Key添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="apiKey" action="${ctx}/app/apiKey/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>应用名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>应用名称</th>
				<th>管理账号</th>
				<th>API Key</th>
				<th>API Secret</th>
				<th>应用类型</th>
				<th>应用平台</th>
				<th>启用状态</th>
				<th>生成记录</th>
				<th>比对阀值</th>
				<shiro:hasPermission name="app:apiKey:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="apiKey">
			<tr>
				<td><a href="${ctx}/app/apiKey/form?id=${apiKey.id}">
					${apiKey.name}
				</a></td>
				<td>
					${apiKey.user.name}
				</td>
				<td>
					${apiKey.apiKey}
				</td>
				<td>
					${apiKey.apiSecret}
				</td>
				<td>
						${fns:getDictLabel(apiKey.type, 'api_key_type', '')}
				</td>
				<td>
						${fns:getDictLabel(apiKey.plat, 'api_key_plat', '')}
				</td>
				<td>
						${fns:getDictLabel(apiKey.status, 'api_key_status', '')}
				</td>
				<td>
						${fns:getDictLabel(apiKey.isSaveRecord, 'api_key_is_save_record', '')}
				</td>
				<td>
					${apiKey.threshold}
				</td>
				<shiro:hasPermission name="app:apiKey:edit"><td>
    				<a href="${ctx}/app/apiKey/form?id=${apiKey.id}">修改</a>
					<a href="${ctx}/app/apiKey/delete?id=${apiKey.id}" onclick="return confirmx('确认要删除该API Key吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>