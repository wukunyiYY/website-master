<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>许可授权管理</title>
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
		<li class="active"><a href="${ctx}/app/licence/">许可授权列表</a></li>
		<shiro:hasPermission name="app:licence:edit"><li><a href="${ctx}/app/licence/form">许可授权添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="licence" action="${ctx}/app/licence/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>machine_code：</label>
				<form:input path="machineCode" htmlEscape="false" maxlength="1000" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>machine_code</th>
				<th>licence_reg</th>
				<th>licence_verify</th>
				<shiro:hasPermission name="app:licence:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="licence">
			<tr>
				<td><a href="${ctx}/app/licence/form?id=${licence.id}">
					${licence.machineCode}
				</a></td>
				<td>
					${licence.licenceReg}
				</td>
				<td>
					${licence.licenceVerify}
				</td>
				<shiro:hasPermission name="app:licence:edit"><td>
    				<a href="${ctx}/app/licence/form?id=${licence.id}">修改</a>
					<a href="${ctx}/app/licence/delete?id=${licence.id}" onclick="return confirmx('确认要删除该许可授权吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>