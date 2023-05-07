var breakInfo_manage_tool = null; 
$(function () { 
	initBreakInfoManageTool(); //建立BreakInfo管理对象
	breakInfo_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#breakInfo_manage").datagrid({
		url : 'BreakInfo/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "taskId",
		sortOrder : "desc",
		toolbar : "#breakInfo_manage_tool",
		columns : [[
			{
				field : "taskId",
				title : "任务编号",
				width : 70,
			},
			{
				field : "breakTypeObj",
				title : "故障类型",
				width : 140,
			},
			{
				field : "buildingObj",
				title : "所在宿舍",
				width : 140,
			},
			{
				field : "breakPhoto",
				title : "故障图片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "breakReason",
				title : "故障信息",
				width : 140,
			},
			{
				field : "studentObj",
				title : "申报学生",
				width : 140,
			},
			{
				field : "commitDate",
				title : "报修时间",
				width : 140,
			},
			{
				field : "taskStateObj",
				title : "任务状态",
				width : 140,
			},
			{
				field : "repariManObj",
				title : "维修人员",
				width : 140,
			},
		]],
	});

	$("#breakInfoEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#breakInfoEditForm").form("validate")) {
					//验证表单 
					if(!$("#breakInfoEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#breakInfoEditForm").form({
						    url:"BreakInfo/" + $("#breakInfo_taskId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#breakInfoEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#breakInfoEditDiv").dialog("close");
			                        breakInfo_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#breakInfoEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#breakInfoEditDiv").dialog("close");
				$("#breakInfoEditForm").form("reset"); 
			},
		}],
	});
});

function initBreakInfoManageTool() {
	breakInfo_manage_tool = {
		init: function() {
			$.ajax({
				url : "BreakType/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#breakTypeObj_breakTypeId_query").combobox({ 
					    valueField:"breakTypeId",
					    textField:"breakTypeName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{breakTypeId:0,breakTypeName:"不限制"});
					$("#breakTypeObj_breakTypeId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "BuildingInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#buildingObj_buildingId_query").combobox({ 
					    valueField:"buildingId",
					    textField:"buildingName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{buildingId:0,buildingName:"不限制"});
					$("#buildingObj_buildingId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#studentObj_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",name:"不限制"});
					$("#studentObj_user_name_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "TaskInfoState/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#taskStateObj_stateId_query").combobox({ 
					    valueField:"stateId",
					    textField:"stateName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{stateId:0,stateName:"不限制"});
					$("#taskStateObj_stateId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "RepairMan/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#repariManObj_repainManNumber_query").combobox({ 
					    valueField:"repainManNumber",
					    textField:"repairManName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{repainManNumber:"",repairManName:"不限制"});
					$("#repariManObj_repainManNumber_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#breakInfo_manage").datagrid("reload");
		},
		redo : function () {
			$("#breakInfo_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#breakInfo_manage").datagrid("options").queryParams;
			queryParams["taskStateObj.stateId"] = $("#taskStateObj_stateId_query").combobox("getValue");
			queryParams["repariManObj.repainManNumber"] = $("#repariManObj_repainManNumber_query").combobox("getValue");
			queryParams["breakTypeObj.breakTypeId"] = $("#breakTypeObj_breakTypeId_query").combobox("getValue");
			queryParams["buildingObj.buildingId"] = $("#buildingObj_buildingId_query").combobox("getValue");
			queryParams["breakReason"] = $("#breakReason").val();
			queryParams["studentObj.user_name"] = $("#studentObj_user_name_query").combobox("getValue");
			queryParams["commitDate"] = $("#commitDate").datebox("getValue"); 
			$("#breakInfo_manage").datagrid("options").queryParams=queryParams; 
			$("#breakInfo_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#breakInfoQueryForm").form({
			    url:"BreakInfo/OutToExcel",
			});
			//提交表单
			$("#breakInfoQueryForm").submit();
		},
		remove : function () {
			var rows = $("#breakInfo_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var taskIds = [];
						for (var i = 0; i < rows.length; i ++) {
							taskIds.push(rows[i].taskId);
						}
						$.ajax({
							type : "POST",
							url : "BreakInfo/deletes",
							data : {
								taskIds : taskIds.join(","),
							},
							beforeSend : function () {
								$("#breakInfo_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#breakInfo_manage").datagrid("loaded");
									$("#breakInfo_manage").datagrid("load");
									$("#breakInfo_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#breakInfo_manage").datagrid("loaded");
									$("#breakInfo_manage").datagrid("load");
									$("#breakInfo_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#breakInfo_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "BreakInfo/" + rows[0].taskId +  "/update",
					type : "get",
					data : {
						//taskId : rows[0].taskId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (breakInfo, response, status) {
						$.messager.progress("close");
						if (breakInfo) { 
							$("#breakInfoEditDiv").dialog("open");
							$("#breakInfo_taskId_edit").val(breakInfo.taskId);
							$("#breakInfo_taskId_edit").validatebox({
								required : true,
								missingMessage : "请输入任务编号",
								editable: false
							});
							$("#breakInfo_breakTypeObj_breakTypeId_edit").combobox({
								url:"BreakType/listAll",
							    valueField:"breakTypeId",
							    textField:"breakTypeName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#breakInfo_breakTypeObj_breakTypeId_edit").combobox("select", breakInfo.breakTypeObjPri);
									//var data = $("#breakInfo_breakTypeObj_breakTypeId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#breakInfo_breakTypeObj_breakTypeId_edit").combobox("select", data[0].breakTypeId);
						            //}
								}
							});
							$("#breakInfo_buildingObj_buildingId_edit").combobox({
								url:"BuildingInfo/listAll",
							    valueField:"buildingId",
							    textField:"buildingName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#breakInfo_buildingObj_buildingId_edit").combobox("select", breakInfo.buildingObjPri);
									//var data = $("#breakInfo_buildingObj_buildingId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#breakInfo_buildingObj_buildingId_edit").combobox("select", data[0].buildingId);
						            //}
								}
							});
							$("#breakInfo_breakPhoto").val(breakInfo.breakPhoto);
							$("#breakInfo_breakPhotoImg").attr("src", breakInfo.breakPhoto);
							$("#breakInfo_breakReason_edit").val(breakInfo.breakReason);
							$("#breakInfo_breakReason_edit").validatebox({
								required : true,
								missingMessage : "请输入故障信息",
							});
							$("#breakInfo_studentObj_user_name_edit").combobox({
								url:"UserInfo/listAll",
							    valueField:"user_name",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#breakInfo_studentObj_user_name_edit").combobox("select", breakInfo.studentObjPri);
									//var data = $("#breakInfo_studentObj_user_name_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#breakInfo_studentObj_user_name_edit").combobox("select", data[0].user_name);
						            //}
								}
							});
							$("#breakInfo_commitDate_edit").datetimebox({
								value: breakInfo.commitDate,
							    required: true,
							    showSeconds: true,
							});
							$("#breakInfo_taskStateObj_stateId_edit").combobox({
								url:"TaskInfoState/listAll",
							    valueField:"stateId",
							    textField:"stateName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#breakInfo_taskStateObj_stateId_edit").combobox("select", breakInfo.taskStateObjPri);
									//var data = $("#breakInfo_taskStateObj_stateId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#breakInfo_taskStateObj_stateId_edit").combobox("select", data[0].stateId);
						            //}
								}
							});
							$("#breakInfo_repariManObj_repainManNumber_edit").combobox({
								url:"RepairMan/listAll",
							    valueField:"repainManNumber",
							    textField:"repairManName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#breakInfo_repariManObj_repainManNumber_edit").combobox("select", breakInfo.repariManObjPri);
									//var data = $("#breakInfo_repariManObj_repainManNumber_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#breakInfo_repariManObj_repainManNumber_edit").combobox("select", data[0].repainManNumber);
						            //}
								}
							});
							$("#breakInfo_handResult_edit").val(breakInfo.handResult);
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
