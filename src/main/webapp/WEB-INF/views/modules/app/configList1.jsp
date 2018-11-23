<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>参数配置管理</title>
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
		<li class="active"><a href="${ctx}/app/config/">参数配置列表</a></li>
		<shiro:hasPermission name="app:config:edit"><li><a href="${ctx}/app/config/form">参数配置添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="config" action="${ctx}/app/config/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>更新时间：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${config.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>磁盘根路径：</label>
				<form:input path="diskPath" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>缓存文件路径，相对磁盘根路径：</label>
				<form:input path="tempPath" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>是否清除缓存，定时23:00：</label>
				<form:input path="isClearTemp" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>更新时间</th>
				<th>磁盘根路径</th>
				<th>缓存文件路径，相对磁盘根路径</th>
				<th>是否清除缓存，定时23:00</th>
				<shiro:hasPermission name="app:config:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="config">
			<tr>
				<td><a href="${ctx}/app/config/form?id=${config.id}">
					${config.updateDate}
				</a></td>
				<td>
					${config.diskPath}
				</td>
				<td>
					${config.tempPath}
				</td>
				<td>
					${config.isClearTemp}
				</td>
				<shiro:hasPermission name="app:config:edit"><td>
    				<a href="${ctx}/app/config/form?id=${config.id}">修改</a>
					<a href="${ctx}/app/config/delete?id=${config.id}" onclick="return confirmx('确认要删除该参数配置吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>