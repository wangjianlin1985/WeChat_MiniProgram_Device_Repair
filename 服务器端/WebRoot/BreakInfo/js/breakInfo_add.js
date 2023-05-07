$(function () {
	$("#breakInfo_breakTypeObj_breakTypeId").combobox({
	    url:'BreakType/listAll',
	    valueField: "breakTypeId",
	    textField: "breakTypeName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#breakInfo_breakTypeObj_breakTypeId").combobox("getData"); 
            if (data.length > 0) {
                $("#breakInfo_breakTypeObj_breakTypeId").combobox("select", data[0].breakTypeId);
            }
        }
	});
	$("#breakInfo_buildingObj_buildingId").combobox({
	    url:'BuildingInfo/listAll',
	    valueField: "buildingId",
	    textField: "buildingName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#breakInfo_buildingObj_buildingId").combobox("getData"); 
            if (data.length > 0) {
                $("#breakInfo_buildingObj_buildingId").combobox("select", data[0].buildingId);
            }
        }
	});
	$("#breakInfo_breakReason").validatebox({
		required : true, 
		missingMessage : '请输入故障信息',
	});

	$("#breakInfo_studentObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#breakInfo_studentObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#breakInfo_studentObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#breakInfo_commitDate").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#breakInfo_taskStateObj_stateId").combobox({
	    url:'TaskInfoState/listAll',
	    valueField: "stateId",
	    textField: "stateName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#breakInfo_taskStateObj_stateId").combobox("getData"); 
            if (data.length > 0) {
                $("#breakInfo_taskStateObj_stateId").combobox("select", data[0].stateId);
            }
        }
	});
	$("#breakInfo_repariManObj_repainManNumber").combobox({
	    url:'RepairMan/listAll',
	    valueField: "repainManNumber",
	    textField: "repairManName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#breakInfo_repariManObj_repainManNumber").combobox("getData"); 
            if (data.length > 0) {
                $("#breakInfo_repariManObj_repainManNumber").combobox("select", data[0].repainManNumber);
            }
        }
	});
	//单击添加按钮
	$("#breakInfoAddButton").click(function () {
		//验证表单 
		if(!$("#breakInfoAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#breakInfoAddForm").form({
			    url:"BreakInfo/add",
			    onSubmit: function(){
					if($("#breakInfoAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#breakInfoAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#breakInfoAddForm").submit();
		}
	});

	//单击清空按钮
	$("#breakInfoClearButton").click(function () { 
		$("#breakInfoAddForm").form("clear"); 
	});
});
