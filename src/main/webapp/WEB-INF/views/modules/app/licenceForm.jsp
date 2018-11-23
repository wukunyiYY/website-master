<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>许可授权</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#exportMachineCode").click(function(){
				window.location.href = "${ctx}/app/licence/exportMachineCode?machineValueCode=${machineValueCode}";
			});
			
			$("#exportLicenceCode").click(function(){
				var result = "${result}";
				if(result == "false"){
					top.$.jBox.tip('请先上传密钥文件', 'error');
					return;
				}
				window.location.href = "${ctx}/app/licence/exportLicenceCode";
			});
			
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
		
		function upLoadFile(obj){
			top.$.jBox.tip("正在提交中，请稍后…",'loading',{persistent:true,opacity:0}); 
			var url = "${ctx}/app/licence/upLoadLicenceCode";
			var options = {      
				url: url,      
				type: 'post',
				dataType: 'json',
				clearForm: true ,
				resetForm: true,
				timeout:   1000 * 60 *5,
				async: true,
				success: function(data){
					if(data == true){
	                	top.$.jBox.tip('上传成功', 'success');
                	}else{
                		top.$.jBox.tip('上传失败', 'error');
                	}
					window.location.href = "${ctx}/app/licence/form";
				}
			};      
			$("#inputForm").ajaxSubmit(options);
		}		
	</script>
	<style type="text/css">
		.upload{
		   	position: relative;
			width: 80px;
			height: 30px;
			line-height: 30px;
			transition: all .3s ease 0s;
			cursor: pointer;
			display: inline-block;
			text-align: center;
			color: #4c4c4c;
			border: 1px solid #cacaca;
			background-color: #fff;
			font-size: 14px;
		}
		.change{
		    position: absolute;
		    overflow: hidden;
		    right: 0;
		    top: 0;
		    opacity: 0;
		}
    </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/app/licence/form">许可授权</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="machineValue" action="${ctx}/app/licence/" method="post" enctype="multipart/form-data" class="form-horizontal">
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">CPU序列号：</label>
			<div class="controls">
				${machineValue.cpuSn}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">硬盘序列号：</label>
			<div class="controls">
				${machineValue.diskSn}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网卡地址：</label>
			<div class="controls">
				${machineValue.mac}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机器码：</label>
			<div class="controls">				
				<textarea name="machineValueCode" readonly="readonly" style="width:650px;height:40px;resize:none;">${machineValueCode}</textarea>
				<input id="exportMachineCode" class="btn btn-primary" type="button" value="导出">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密钥：</label>
			<div class="controls">
				<textarea name="licenceValueCode" readonly="readonly" style="width:650px;height:40px;resize:none;">${licenceValueCode}</textarea>
				<a class="upload"><span>选择文件</span>
					<input id="upLoadLicenceCode" name="licenceCodeFile" type="file" accept=".cert" style="width: 80px;" onchange="upLoadFile(this)" class="change">
				</a>
				<input id="exportLicenceCode" class="btn btn-primary" type="button" value="导出">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权状态：</label>
			<div class="controls">
				<c:if test="${licenceStatus == 0}"><span><font color="red">未授权或授权异常</font></span></c:if>
				<c:if test="${licenceStatus == 1}"><span>授权成功</span></c:if>
				<c:if test="${licenceStatus == 2}"><span></span><font color="red">授权过期或授权异常</font></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授权到期类型：</label>
			<div class="controls">
				<c:if test="${licenceValue.type == 'TIME_POINT'}"><span>到达时间点即过期</span></c:if>
				<c:if test="${licenceValue.type == 'TIME_LENGTH'}"><span>到达时长即过期</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">到期时间：</label>
			<div class="controls">
				<c:if test="${licenceValue.type == 'TIME_POINT'}">
					<fmt:formatDate value="${licenceValue.expiredTime}" pattern="yyyy-MM-dd"/>
				</c:if>
				<c:if test="${licenceValue.type == 'TIME_LENGTH'}">
					无
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效时长(天)：</label>
			<div class="controls">
				<c:if test="${licenceValue.type == 'TIME_POINT'}">
					无
				</c:if>
				<c:if test="${licenceValue.type == 'TIME_LENGTH'}">
					${licenceValue.effectiveTimeLength}
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否显示试用信息：</label>
			<div class="controls">
				<c:if test="${licenceValue.showTrial == true}"><span>是</span></c:if>
				<c:if test="${licenceValue.showTrial == false}"><span>否</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否绑定CPU：</label>
			<div class="controls">
				<c:if test="${licenceValue.bindCpu == true}"><span>是</span></c:if>
				<c:if test="${licenceValue.bindCpu == false}"><span>否</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否绑定硬盘：</label>
			<div class="controls">
				<c:if test="${licenceValue.bindDisk == true}"><span>是</span></c:if>
				<c:if test="${licenceValue.bindDisk == false}"><span>否</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否绑定网卡：</label>
			<div class="controls">
				<c:if test="${licenceValue.bindMac == true}"><span>是</span></c:if>
				<c:if test="${licenceValue.bindMac == false}"><span>否</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">功能是否可用：</label>
			<div class="controls">
				${licenceValue.functions}
			</div>
		</div>
	</form:form>
</body>
</html>