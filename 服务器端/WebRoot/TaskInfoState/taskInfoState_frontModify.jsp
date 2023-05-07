<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.TaskInfoState" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    TaskInfoState taskInfoState = (TaskInfoState)request.getAttribute("taskInfoState");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改任务状态信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">任务状态信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="taskInfoStateEditForm" id="taskInfoStateEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="taskInfoState_stateId_edit" class="col-md-3 text-right">状态编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="taskInfoState_stateId_edit" name="taskInfoState.stateId" class="form-control" placeholder="请输入状态编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="taskInfoState_stateName_edit" class="col-md-3 text-right">状态名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="taskInfoState_stateName_edit" name="taskInfoState.stateName" class="form-control" placeholder="请输入状态名称">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxTaskInfoStateModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#taskInfoStateEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改任务状态界面并初始化数据*/
function taskInfoStateEdit(stateId) {
	$.ajax({
		url :  basePath + "TaskInfoState/" + stateId + "/update",
		type : "get",
		dataType: "json",
		success : function (taskInfoState, response, status) {
			if (taskInfoState) {
				$("#taskInfoState_stateId_edit").val(taskInfoState.stateId);
				$("#taskInfoState_stateName_edit").val(taskInfoState.stateName);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交任务状态信息表单给服务器端修改*/
function ajaxTaskInfoStateModify() {
	$.ajax({
		url :  basePath + "TaskInfoState/" + $("#taskInfoState_stateId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#taskInfoStateEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                location.href= basePath + "TaskInfoState/frontlist";
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
    taskInfoStateEdit("<%=request.getParameter("stateId")%>");
 })
 </script> 
</body>
</html>

