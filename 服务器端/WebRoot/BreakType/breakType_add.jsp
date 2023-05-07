<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/breakType.css" />
<div id="breakTypeAddDiv">
	<form id="breakTypeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">类型名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="breakType_breakTypeName" name="breakType.breakTypeName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="breakTypeAddButton" class="easyui-linkbutton">添加</a>
			<a id="breakTypeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/BreakType/js/breakType_add.js"></script> 
