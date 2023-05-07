<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/breakInfo.css" /> 

<div id="breakInfo_manage"></div>
<div id="breakInfo_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="breakInfo_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="breakInfo_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="breakInfo_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="breakInfo_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="breakInfo_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="breakInfoQueryForm" method="post">
			任务状态：<input class="textbox" type="text" id="taskStateObj_stateId_query" name="taskStateObj.stateId" style="width: auto"/>
			维修人员：<input class="textbox" type="text" id="repariManObj_repainManNumber_query" name="repariManObj.repainManNumber" style="width: auto"/>
			故障类型：<input class="textbox" type="text" id="breakTypeObj_breakTypeId_query" name="breakTypeObj.breakTypeId" style="width: auto"/>
			所在宿舍：<input class="textbox" type="text" id="buildingObj_buildingId_query" name="buildingObj.buildingId" style="width: auto"/>
			故障信息：<input type="text" class="textbox" id="breakReason" name="breakReason" style="width:110px" />
			申报学生：<input class="textbox" type="text" id="studentObj_user_name_query" name="studentObj.user_name" style="width: auto"/>
			报修时间：<input type="text" id="commitDate" name="commitDate" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="breakInfo_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="breakInfoEditDiv">
	<form id="breakInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">任务编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="breakInfo_taskId_edit" name="breakInfo.taskId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">故障类型:</span>
			<span class="inputControl">
				<input class="textbox"  id="breakInfo_breakTypeObj_breakTypeId_edit" name="breakInfo.breakTypeObj.breakTypeId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">所在宿舍:</span>
			<span class="inputControl">
				<input class="textbox"  id="breakInfo_buildingObj_buildingId_edit" name="breakInfo.buildingObj.buildingId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">故障图片:</span>
			<span class="inputControl">
				<img id="breakInfo_breakPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="breakInfo_breakPhoto" name="breakInfo.breakPhoto"/>
				<input id="breakPhotoFile" name="breakPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">故障信息:</span>
			<span class="inputControl">
				<textarea id="breakInfo_breakReason_edit" name="breakInfo.breakReason" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">申报学生:</span>
			<span class="inputControl">
				<input class="textbox"  id="breakInfo_studentObj_user_name_edit" name="breakInfo.studentObj.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">报修时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="breakInfo_commitDate_edit" name="breakInfo.commitDate" />

			</span>

		</div>
		<div>
			<span class="label">任务状态:</span>
			<span class="inputControl">
				<input class="textbox"  id="breakInfo_taskStateObj_stateId_edit" name="breakInfo.taskStateObj.stateId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">维修人员:</span>
			<span class="inputControl">
				<input class="textbox"  id="breakInfo_repariManObj_repainManNumber_edit" name="breakInfo.repariManObj.repainManNumber" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">处理结果:</span>
			<span class="inputControl">
				<textarea id="breakInfo_handResult_edit" name="breakInfo.handResult" rows="8" cols="60"></textarea>

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="BreakInfo/js/breakInfo_manage.js"></script> 
