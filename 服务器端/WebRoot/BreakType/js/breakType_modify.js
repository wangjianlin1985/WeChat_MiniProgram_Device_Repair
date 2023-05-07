$(function () {
	$.ajax({
		url : "BreakType/" + $("#breakType_breakTypeId_edit").val() + "/update",
		type : "get",
		data : {
			//breakTypeId : $("#breakType_breakTypeId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (breakType, response, status) {
			$.messager.progress("close");
			if (breakType) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#breakTypeModifyButton").click(function(){ 
		if ($("#breakTypeEditForm").form("validate")) {
			$("#breakTypeEditForm").form({
			    url:"BreakType/" +  $("#breakType_breakTypeId_edit").val() + "/update",
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
			$("#breakTypeEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
