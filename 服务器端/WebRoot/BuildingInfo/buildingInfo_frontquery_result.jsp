<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.BuildingInfo" %>
<%@ page import="com.chengxusheji.po.AreaInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<BuildingInfo> buildingInfoList = (List<BuildingInfo>)request.getAttribute("buildingInfoList");
    //获取所有的areaObj信息
    List<AreaInfo> areaInfoList = (List<AreaInfo>)request.getAttribute("areaInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    AreaInfo areaObj = (AreaInfo)request.getAttribute("areaObj");
    String buildingName = (String)request.getAttribute("buildingName"); //宿舍名称查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>宿舍信息查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#buildingInfoListPanel" aria-controls="buildingInfoListPanel" role="tab" data-toggle="tab">宿舍信息列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>BuildingInfo/buildingInfo_frontAdd.jsp" style="display:none;">添加宿舍信息</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="buildingInfoListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>记录编号</td><td>所在区域</td><td>宿舍名称</td><td>管理员</td><td>门卫电话</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<buildingInfoList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		BuildingInfo buildingInfo = buildingInfoList.get(i); //获取到宿舍信息对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=buildingInfo.getBuildingId() %></td>
 											<td><%=buildingInfo.getAreaObj().getAreaName() %></td>
 											<td><%=buildingInfo.getBuildingName() %></td>
 											<td><%=buildingInfo.getManageMan() %></td>
 											<td><%=buildingInfo.getTelephone() %></td>
 											<td>
 												<a href="<%=basePath  %>BuildingInfo/<%=buildingInfo.getBuildingId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="buildingInfoEdit('<%=buildingInfo.getBuildingId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="buildingInfoDelete('<%=buildingInfo.getBuildingId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

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
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>宿舍信息查询</h1>
		</div>
		<form name="buildingInfoQueryForm" id="buildingInfoQueryForm" action="<%=basePath %>BuildingInfo/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="areaObj_areaId">所在区域：</label>
                <select id="areaObj_areaId" name="areaObj.areaId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(AreaInfo areaInfoTemp:areaInfoList) {
	 					String selected = "";
 					if(areaObj!=null && areaObj.getAreaId()!=null && areaObj.getAreaId().intValue()==areaInfoTemp.getAreaId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=areaInfoTemp.getAreaId() %>" <%=selected %>><%=areaInfoTemp.getAreaName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="buildingName">宿舍名称:</label>
				<input type="text" id="buildingName" name="buildingName" value="<%=buildingName %>" class="form-control" placeholder="请输入宿舍名称">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="buildingInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;宿舍信息信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="buildingInfoEditForm" id="buildingInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="buildingInfo_buildingId_edit" class="col-md-3 text-right">记录编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="buildingInfo_buildingId_edit" name="buildingInfo.buildingId" class="form-control" placeholder="请输入记录编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="buildingInfo_areaObj_areaId_edit" class="col-md-3 text-right">所在区域:</label>
		  	 <div class="col-md-9">
			    <select id="buildingInfo_areaObj_areaId_edit" name="buildingInfo.areaObj.areaId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buildingInfo_buildingName_edit" class="col-md-3 text-right">宿舍名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buildingInfo_buildingName_edit" name="buildingInfo.buildingName" class="form-control" placeholder="请输入宿舍名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buildingInfo_manageMan_edit" class="col-md-3 text-right">管理员:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buildingInfo_manageMan_edit" name="buildingInfo.manageMan" class="form-control" placeholder="请输入管理员">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="buildingInfo_telephone_edit" class="col-md-3 text-right">门卫电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="buildingInfo_telephone_edit" name="buildingInfo.telephone" class="form-control" placeholder="请输入门卫电话">
			 </div>
		  </div>
		</form> 
	    <style>#buildingInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxBuildingInfoModify();">提交</button>
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
    document.buildingInfoQueryForm.currentPage.value = currentPage;
    document.buildingInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.buildingInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.buildingInfoQueryForm.currentPage.value = pageValue;
    documentbuildingInfoQueryForm.submit();
}

/*弹出修改宿舍信息界面并初始化数据*/
function buildingInfoEdit(buildingId) {
	$.ajax({
		url :  basePath + "BuildingInfo/" + buildingId + "/update",
		type : "get",
		dataType: "json",
		success : function (buildingInfo, response, status) {
			if (buildingInfo) {
				$("#buildingInfo_buildingId_edit").val(buildingInfo.buildingId);
				$.ajax({
					url: basePath + "AreaInfo/listAll",
					type: "get",
					success: function(areaInfos,response,status) { 
						$("#buildingInfo_areaObj_areaId_edit").empty();
						var html="";
		        		$(areaInfos).each(function(i,areaInfo){
		        			html += "<option value='" + areaInfo.areaId + "'>" + areaInfo.areaName + "</option>";
		        		});
		        		$("#buildingInfo_areaObj_areaId_edit").html(html);
		        		$("#buildingInfo_areaObj_areaId_edit").val(buildingInfo.areaObjPri);
					}
				});
				$("#buildingInfo_buildingName_edit").val(buildingInfo.buildingName);
				$("#buildingInfo_manageMan_edit").val(buildingInfo.manageMan);
				$("#buildingInfo_telephone_edit").val(buildingInfo.telephone);
				$('#buildingInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除宿舍信息信息*/
function buildingInfoDelete(buildingId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "BuildingInfo/deletes",
			data : {
				buildingIds : buildingId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#buildingInfoQueryForm").submit();
					//location.href= basePath + "BuildingInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交宿舍信息信息表单给服务器端修改*/
function ajaxBuildingInfoModify() {
	$.ajax({
		url :  basePath + "BuildingInfo/" + $("#buildingInfo_buildingId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#buildingInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#buildingInfoQueryForm").submit();
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

})
</script>
</body>
</html>

