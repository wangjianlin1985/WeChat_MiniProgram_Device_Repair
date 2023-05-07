<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/taskInfoState.css" />
<div id="taskInfoStateAddDiv">
	<form id="taskInfoStateAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">状态名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="taskInfoState_stateName" name="taskInfoState.stateName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="taskInfoStateAddButton" class="easyui-linkbutton">添加</a>
			<a id="taskInfoStateClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/TaskInfoState/js/taskInfoState_add.js"></script> 
