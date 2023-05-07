<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/taskInfoState.css" />
<div id="taskInfoState_editDiv">
	<form id="taskInfoStateEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">状态编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="taskInfoState_stateId_edit" name="taskInfoState.stateId" value="<%=request.getParameter("stateId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">状态名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="taskInfoState_stateName_edit" name="taskInfoState.stateName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="taskInfoStateModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/TaskInfoState/js/taskInfoState_modify.js"></script> 
