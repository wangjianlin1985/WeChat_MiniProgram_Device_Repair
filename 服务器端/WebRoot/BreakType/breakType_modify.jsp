<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/breakType.css" />
<div id="breakType_editDiv">
	<form id="breakTypeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类型编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="breakType_breakTypeId_edit" name="breakType.breakTypeId" value="<%=request.getParameter("breakTypeId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="breakType_breakTypeName_edit" name="breakType.breakTypeName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="breakTypeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/BreakType/js/breakType_modify.js"></script> 
