<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>医院信息表管理</title>
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
		<li class="active"><a href="${ctx}/test/hospitalArchivesInfo/">医院信息表列表</a></li>
		<shiro:hasPermission name="test:hospitalArchivesInfo:edit"><li><a href="${ctx}/test/hospitalArchivesInfo/form">医院信息表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="hospitalArchivesInfo" action="${ctx}/test/hospitalArchivesInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>医院名称：</label>
				<form:input path="hospitalName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>医院代码：</label>
				<form:input path="hospitalCode" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>医院负责人：</label>
				<form:input path="hospitalPrincipal" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>医院联系：</label>
				<form:input path="hospitalContact" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>开始日期：</label>
				<form:input path="beginDate" id="beginDate" name="beginDate" readonly="readonly" maxlength="20" class="input-small Wdate" value="${hospitalArchivesInfo.beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li>
				<label>结束日期：</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate" value="${hospitalArchivesInfo.endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>&nbsp;&nbsp;
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>医院名称</th>
				<th>医院代码</th>
				<th>医院负责人</th>
				<th>医院联系</th>
				<th>医院邮箱</th>
				<th>医院地址</th>
				<th>办公电话</th>
				<th>创建时间</th>
				<shiro:hasPermission name="test:hospitalArchivesInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="hospitalArchivesInfo">
			<tr>
				<td>${hospitalArchivesInfo.hospitalName}</td>
				<td>${hospitalArchivesInfo.hospitalCode}</td>
				<td>${hospitalArchivesInfo.hospitalPrincipal}</td>
				<td>${hospitalArchivesInfo.hospitalContact}</td>
				<td>${hospitalArchivesInfo.hospitalEmail}</td>
				<td>${hospitalArchivesInfo.hospitalAddress}</td>
				<td>${hospitalArchivesInfo.officePhone}</td>
				<td>
					<fmt:formatDate value="${hospitalArchivesInfo.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="test:hospitalArchivesInfo:edit"><td>
    				<a href="${ctx}/test/hospitalArchivesInfo/form?id=${hospitalArchivesInfo.id}">修改</a>
					<a href="${ctx}/test/hospitalArchivesInfo/delete?id=${hospitalArchivesInfo.id}" onclick="return confirmx('确认要删除该医院信息表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>