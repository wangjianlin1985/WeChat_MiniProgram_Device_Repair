$(function () {
	$.ajax({
		url : "TaskInfoState/" + $("#taskInfoState_stateId_edit").val() + "/update",
		type : "get",
		data : {
			//stateId : $("#taskInfoState_stateId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (taskInfoState, response, status) {
			$.messager.progress("close");
			if (taskInfoState) { 
				$("#taskInfoState_stateId_edit").val(taskInfoState.stateId);
				$("#taskInfoState_stateId_edit").validatebox({
					required : true,
					missingMessage : "请输入状态编号",
					editable: false
				});
				$("#taskInfoState_stateName_edit").val(taskInfoState.stateName);
				$("#taskInfoState_stateName_edit").validatebox({
					required : true,
					missingMessage : "请输入状态名称",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#taskInfoStateModifyButton").click(function(){ 
		if ($("#taskInfoStateEditForm").form("validate")) {
			$("#taskInfoStateEditForm").form({
			    url:"TaskInfoState/" +  $("#taskInfoState_stateId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#taskInfoStateEditForm").form("validate"))  {
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
			$("#taskInfoStateEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
