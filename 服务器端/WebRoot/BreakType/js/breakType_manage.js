var breakType_manage_tool = null; 
$(function () { 
	initBreakTypeManageTool(); //建立BreakType管理对象
	breakType_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#breakType_manage").datagrid({
		url : 'BreakType/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "breakTypeId",
		sortOrder : "desc",
		toolbar : "#breakType_manage_tool",
		columns : [[
			{
				field : "breakTypeId",
				title : "类型编号",
				width : 70,
			},
			{
				field : "breakTypeName",
				title : "类型名称",
				width : 140,
			},
		]],
	});

	$("#breakTypeEditDiv").dialog({
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
				if ($("#breakTypeEditForm").form("validate")) {
					//验证表单 
					if(!$("#breakTypeEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#breakTypeEditForm").form({
						    url:"BreakType/" + $("#breakType_breakTypeId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#breakTypeEditForm").form("validate"))  {
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
			                        $("#breakTypeEditDiv").dialog("close");
			                        breakType_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#breakTypeEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#breakTypeEditDiv").dialog("close");
				$("#breakTypeEditForm").form("reset"); 
			},
		}],
	});
});

function initBreakTypeManageTool() {
	breakType_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#breakType_manage").datagrid("reload");
		},
		redo : function () {
			$("#breakType_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#breakType_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#breakTypeQueryForm").form({
			    url:"BreakType/OutToExcel",
			});
			//提交表单
			$("#breakTypeQueryForm").submit();
		},
		remove : function () {
			var rows = $("#breakType_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var breakTypeIds = [];
						for (var i = 0; i < rows.length; i ++) {
							breakTypeIds.push(rows[i].breakTypeId);
						}
						$.ajax({
							type : "POST",
							url : "BreakType/deletes",
							data : {
								breakTypeIds : breakTypeIds.join(","),
							},
							beforeSend : function () {
								$("#breakType_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#breakType_manage").datagrid("loaded");
									$("#breakType_manage").datagrid("load");
									$("#breakType_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#breakType_manage").datagrid("loaded");
									$("#breakType_manage").datagrid("load");
									$("#breakType_manage").datagrid("unselectAll");
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
			var rows = $("#breakType_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "BreakType/" + rows[0].breakTypeId +  "/update",
					type : "get",
					data : {
						//breakTypeId : rows[0].breakTypeId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (breakType, response, status) {
						$.messager.progress("close");
						if (breakType) { 
							$("#breakTypeEditDiv").dialog("open");
							$("#breakType_breakTypeId_edit").val(breakType.breakTypeId);
							$("#breakType_breakTypeId_edit").validatebox({
								required : true,
								missingMessage : "请输入类型编号",
								editable: false
							});
							$("#breakType_breakTypeName_edit").val(breakType.breakTypeName);
							$("#breakType_breakTypeName_edit").validatebox({
								required : true,
								missingMessage : "请输入类型名称",
							});
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
