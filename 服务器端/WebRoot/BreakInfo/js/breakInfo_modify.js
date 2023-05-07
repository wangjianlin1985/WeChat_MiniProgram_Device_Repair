$(function () {
	$.ajax({
		url : "BreakInfo/" + $("#breakInfo_taskId_edit").val() + "/update",
		type : "get",
		data : {
			//taskId : $("#breakInfo_taskId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (breakInfo, response, status) {
			$.messager.progress("close");
			if (breakInfo) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#breakInfoModifyButton").click(function(){ 
		if ($("#breakInfoEditForm").form("validate")) {
			$("#breakInfoEditForm").form({
			    url:"BreakInfo/" +  $("#breakInfo_taskId_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#breakInfoEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
