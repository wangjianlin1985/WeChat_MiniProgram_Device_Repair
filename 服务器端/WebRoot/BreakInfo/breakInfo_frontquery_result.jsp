<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.BreakInfo" %>
<%@ page import="com.chengxusheji.po.BreakType" %>
<%@ page import="com.chengxusheji.po.BuildingInfo" %>
<%@ page import="com.chengxusheji.po.RepairMan" %>
<%@ page import="com.chengxusheji.po.TaskInfoState" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<BreakInfo> breakInfoList = (List<BreakInfo>)request.getAttribute("breakInfoList");
    //获取所有的breakTypeObj信息
    List<BreakType> breakTypeList = (List<BreakType>)request.getAttribute("breakTypeList");
    //获取所有的buildingObj信息
    List<BuildingInfo> buildingInfoList = (List<BuildingInfo>)request.getAttribute("buildingInfoList");
    //获取所有的repariManObj信息
    List<RepairMan> repairManList = (List<RepairMan>)request.getAttribute("repairManList");
    //获取所有的taskStateObj信息
    List<TaskInfoState> taskInfoStateList = (List<TaskInfoState>)request.getAttribute("taskInfoStateList");
    //获取所有的studentObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    TaskInfoState taskStateObj = (TaskInfoState)request.getAttribute("taskStateObj");
    RepairMan repariManObj = (RepairMan)request.getAttribute("repariManObj");
    BreakType breakTypeObj = (BreakType)request.getAttribute("breakTypeObj");
    BuildingInfo buildingObj = (BuildingInfo)request.getAttribute("buildingObj");
    String breakReason = (String)request.getAttribute("breakReason"); //故障信息查询关键字
    UserInfo studentObj = (UserInfo)request.getAttribute("studentObj");
    String commitDate = (String)request.getAttribute("commitDate"); //报修时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>故障信息查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>BreakInfo/frontlist">故障信息信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>BreakInfo/breakInfo_frontAdd.jsp" style="display:none;">添加故障信息</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<breakInfoList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		BreakInfo breakInfo = breakInfoList.get(i); //获取到故障信息对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>BreakInfo/<%=breakInfo.getTaskId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=breakInfo.getBreakPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		任务编号:<%=breakInfo.getTaskId() %>
			     	</div>
			     	<div class="field">
	            		故障类型:<%=breakInfo.getBreakTypeObj().getBreakTypeName() %>
			     	</div>
			     	<div class="field">
	            		所在宿舍:<%=breakInfo.getBuildingObj().getBuildingName() %>
			     	</div>
			     	<div class="field">
	            		故障信息:<%=breakInfo.getBreakReason() %>
			     	</div>
			     	<div class="field">
	            		申报学生:<%=breakInfo.getStudentObj().getName() %>
			     	</div>
			     	<div class="field">
	            		报修时间:<%=breakInfo.getCommitDate() %>
			     	</div>
			     	<div class="field">
	            		任务状态:<%=breakInfo.getTaskStateObj().getStateName() %>
			     	</div>
			     	<div class="field">
	            		维修人员:<%=breakInfo.getRepariManObj().getRepairManName() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>BreakInfo/<%=breakInfo.getTaskId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="breakInfoEdit('<%=breakInfo.getTaskId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="breakInfoDelete('<%=breakInfo.getTaskId() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

			<div class="row">
				<div class="col-md-12">
					<nav class="pull-left">
						<ul class="pagination">
							<li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
							<%
								int startPage = currentPage - 5;
								int endPage = currentPage + 5;
								if(startPage < 1) startPage=1;
								if(endPage > totalPage) endPage = totalPage;
								for(int i=startPage;i<=endPage;i++) {
							%>
							<li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
							<%  } %> 
							<li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						</ul>
					</nav>
					<div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>故障信息查询</h1>
		</div>
		<form name="breakInfoQueryForm" id="breakInfoQueryForm" action="<%=basePath %>BreakInfo/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="taskStateObj_stateId">任务状态：</label>
                <select id="taskStateObj_stateId" name="taskStateObj.stateId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(TaskInfoState taskInfoStateTemp:taskInfoStateList) {
	 					String selected = "";
 					if(taskStateObj!=null && taskStateObj.getStateId()!=null && taskStateObj.getStateId().intValue()==taskInfoStateTemp.getStateId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=taskInfoStateTemp.getStateId() %>" <%=selected %>><%=taskInfoStateTemp.getStateName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="repariManObj_repainManNumber">维修人员：</label>
                <select id="repariManObj_repainManNumber" name="repariManObj.repainManNumber" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(RepairMan repairManTemp:repairManList) {
	 					String selected = "";
 					if(repariManObj!=null && repariManObj.getRepainManNumber()!=null && repariManObj.getRepainManNumber().equals(repairManTemp.getRepainManNumber()))
 						selected = "selected";
	 				%>
 				 <option value="<%=repairManTemp.getRepainManNumber() %>" <%=selected %>><%=repairManTemp.getRepairManName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="breakTypeObj_breakTypeId">故障类型：</label>
                <select id="breakTypeObj_breakTypeId" name="breakTypeObj.breakTypeId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(BreakType breakTypeTemp:breakTypeList) {
	 					String selected = "";
 					if(breakTypeObj!=null && breakTypeObj.getBreakTypeId()!=null && breakTypeObj.getBreakTypeId().intValue()==breakTypeTemp.getBreakTypeId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=breakTypeTemp.getBreakTypeId() %>" <%=selected %>><%=breakTypeTemp.getBreakTypeName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="buildingObj_buildingId">所在宿舍：</label>
                <select id="buildingObj_buildingId" name="buildingObj.buildingId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(BuildingInfo buildingInfoTemp:buildingInfoList) {
	 					String selected = "";
 					if(buildingObj!=null && buildingObj.getBuildingId()!=null && buildingObj.getBuildingId().intValue()==buildingInfoTemp.getBuildingId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=buildingInfoTemp.getBuildingId() %>" <%=selected %>><%=buildingInfoTemp.getBuildingName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="breakReason">故障信息:</label>
				<input type="text" id="breakReason" name="breakReason" value="<%=breakReason %>" class="form-control" placeholder="请输入故障信息">
			</div>
            <div class="form-group">
            	<label for="studentObj_user_name">申报学生：</label>
                <select id="studentObj_user_name" name="studentObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(studentObj!=null && studentObj.getUser_name()!=null && studentObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="commitDate">报修时间:</label>
				<input type="text" id="commitDate" name="commitDate" class="form-control"  placeholder="请选择报修时间" value="<%=commitDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="breakInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;故障信息信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="breakInfoEditForm" id="breakInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="breakInfo_taskId_edit" class="col-md-3 text-right">任务编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="breakInfo_taskId_edit" name="breakInfo.taskId" class="form-control" placeholder="请输入任务编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="breakInfo_breakTypeObj_breakTypeId_edit" class="col-md-3 text-right">故障类型:</label>
		  	 <div class="col-md-9">
			    <select id="breakInfo_breakTypeObj_breakTypeId_edit" name="breakInfo.breakTypeObj.breakTypeId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_buildingObj_buildingId_edit" class="col-md-3 text-right">所在宿舍:</label>
		  	 <div class="col-md-9">
			    <select id="breakInfo_buildingObj_buildingId_edit" name="breakInfo.buildingObj.buildingId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_breakPhoto_edit" class="col-md-3 text-right">故障图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="breakInfo_breakPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="breakInfo_breakPhoto" name="breakInfo.breakPhoto"/>
			    <input id="breakPhotoFile" name="breakPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_breakReason_edit" class="col-md-3 text-right">故障信息:</label>
		  	 <div class="col-md-9">
			    <textarea id="breakInfo_breakReason_edit" name="breakInfo.breakReason" rows="8" class="form-control" placeholder="请输入故障信息"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_studentObj_user_name_edit" class="col-md-3 text-right">申报学生:</label>
		  	 <div class="col-md-9">
			    <select id="breakInfo_studentObj_user_name_edit" name="breakInfo.studentObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_commitDate_edit" class="col-md-3 text-right">报修时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date breakInfo_commitDate_edit col-md-12" data-link-field="breakInfo_commitDate_edit">
                    <input class="form-control" id="breakInfo_commitDate_edit" name="breakInfo.commitDate" size="16" type="text" value="" placeholder="请选择报修时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_taskStateObj_stateId_edit" class="col-md-3 text-right">任务状态:</label>
		  	 <div class="col-md-9">
			    <select id="breakInfo_taskStateObj_stateId_edit" name="breakInfo.taskStateObj.stateId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_repariManObj_repainManNumber_edit" class="col-md-3 text-right">维修人员:</label>
		  	 <div class="col-md-9">
			    <select id="breakInfo_repariManObj_repainManNumber_edit" name="breakInfo.repariManObj.repainManNumber" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="breakInfo_handResult_edit" class="col-md-3 text-right">处理结果:</label>
		  	 <div class="col-md-9">
			    <textarea id="breakInfo_handResult_edit" name="breakInfo.handResult" rows="8" class="form-control" placeholder="请输入处理结果"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#breakInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxBreakInfoModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.breakInfoQueryForm.currentPage.value = currentPage;
    document.breakInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.breakInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.breakInfoQueryForm.currentPage.value = pageValue;
    documentbreakInfoQueryForm.submit();
}

/*弹出修改故障信息界面并初始化数据*/
function breakInfoEdit(taskId) {
	$.ajax({
		url :  basePath + "BreakInfo/" + taskId + "/update",
		type : "get",
		dataType: "json",
		success : function (breakInfo, response, status) {
			if (breakInfo) {
				$("#breakInfo_taskId_edit").val(breakInfo.taskId);
				$.ajax({
					url: basePath + "BreakType/listAll",
					type: "get",
					success: function(breakTypes,response,status) { 
						$("#breakInfo_breakTypeObj_breakTypeId_edit").empty();
						var html="";
		        		$(breakTypes).each(function(i,breakType){
		        			html += "<option value='" + breakType.breakTypeId + "'>" + breakType.breakTypeName + "</option>";
		        		});
		        		$("#breakInfo_breakTypeObj_breakTypeId_edit").html(html);
		        		$("#breakInfo_breakTypeObj_breakTypeId_edit").val(breakInfo.breakTypeObjPri);
					}
				});
				$.ajax({
					url: basePath + "BuildingInfo/listAll",
					type: "get",
					success: function(buildingInfos,response,status) { 
						$("#breakInfo_buildingObj_buildingId_edit").empty();
						var html="";
		        		$(buildingInfos).each(function(i,buildingInfo){
		        			html += "<option value='" + buildingInfo.buildingId + "'>" + buildingInfo.buildingName + "</option>";
		        		});
		        		$("#breakInfo_buildingObj_buildingId_edit").html(html);
		        		$("#breakInfo_buildingObj_buildingId_edit").val(breakInfo.buildingObjPri);
					}
				});
				$("#breakInfo_breakPhoto").val(breakInfo.breakPhoto);
				$("#breakInfo_breakPhotoImg").attr("src", basePath +　breakInfo.breakPhoto);
				$("#breakInfo_breakReason_edit").val(breakInfo.breakReason);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#breakInfo_studentObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#breakInfo_studentObj_user_name_edit").html(html);
		        		$("#breakInfo_studentObj_user_name_edit").val(breakInfo.studentObjPri);
					}
				});
				$("#breakInfo_commitDate_edit").val(breakInfo.commitDate);
				$.ajax({
					url: basePath + "TaskInfoState/listAll",
					type: "get",
					success: function(taskInfoStates,response,status) { 
						$("#breakInfo_taskStateObj_stateId_edit").empty();
						var html="";
		        		$(taskInfoStates).each(function(i,taskInfoState){
		        			html += "<option value='" + taskInfoState.stateId + "'>" + taskInfoState.stateName + "</option>";
		        		});
		        		$("#breakInfo_taskStateObj_stateId_edit").html(html);
		        		$("#breakInfo_taskStateObj_stateId_edit").val(breakInfo.taskStateObjPri);
					}
				});
				$.ajax({
					url: basePath + "RepairMan/listAll",
					type: "get",
					success: function(repairMans,response,status) { 
						$("#breakInfo_repariManObj_repainManNumber_edit").empty();
						var html="";
		        		$(repairMans).each(function(i,repairMan){
		        			html += "<option value='" + repairMan.repainManNumber + "'>" + repairMan.repairManName + "</option>";
		        		});
		        		$("#breakInfo_repariManObj_repainManNumber_edit").html(html);
		        		$("#breakInfo_repariManObj_repainManNumber_edit").val(breakInfo.repariManObjPri);
					}
				});
				$("#breakInfo_handResult_edit").val(breakInfo.handResult);
				$('#breakInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除故障信息信息*/
function breakInfoDelete(taskId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "BreakInfo/deletes",
			data : {
				taskIds : taskId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#breakInfoQueryForm").submit();
					//location.href= basePath + "BreakInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交故障信息信息表单给服务器端修改*/
function ajaxBreakInfoModify() {
	$.ajax({
		url :  basePath + "BreakInfo/" + $("#breakInfo_taskId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#breakInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#breakInfoQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse > a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*报修时间组件*/
    $('.breakInfo_commitDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

