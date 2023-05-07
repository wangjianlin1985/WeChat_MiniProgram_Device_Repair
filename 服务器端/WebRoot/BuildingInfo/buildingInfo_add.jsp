<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buildingInfo.css" />
<div id="buildingInfoAddDiv">
	<form id="buildingInfoAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">所在区域:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="buildingInfo_areaObj_areaId" name="buildingInfo.areaObj.areaId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">宿舍名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="buildingInfo_buildingName" name="buildingInfo.buildingName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">管理员:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="buildingInfo_manageMan" name="buildingInfo.manageMan" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">门卫电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="buildingInfo_telephone" name="buildingInfo.telephone" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="buildingInfoAddButton" class="easyui-linkbutton">添加</a>
			<a id="buildingInfoClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/BuildingInfo/js/buildingInfo_add.js"></script> 
