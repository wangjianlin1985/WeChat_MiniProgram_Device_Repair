package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.BreakInfoService;
import com.chengxusheji.po.BreakInfo;
import com.chengxusheji.service.BreakTypeService;
import com.chengxusheji.po.BreakType;
import com.chengxusheji.service.BuildingInfoService;
import com.chengxusheji.po.BuildingInfo;
import com.chengxusheji.service.RepairManService;
import com.chengxusheji.po.RepairMan;
import com.chengxusheji.service.TaskInfoStateService;
import com.chengxusheji.po.TaskInfoState;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//BreakInfo管理控制层
@Controller
@RequestMapping("/BreakInfo")
public class BreakInfoController extends BaseController {

    /*业务层对象*/
    @Resource BreakInfoService breakInfoService;

    @Resource BreakTypeService breakTypeService;
    @Resource BuildingInfoService buildingInfoService;
    @Resource RepairManService repairManService;
    @Resource TaskInfoStateService taskInfoStateService;
    @Resource UserInfoService userInfoService;
	@InitBinder("breakTypeObj")
	public void initBinderbreakTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("breakTypeObj.");
	}
	@InitBinder("buildingObj")
	public void initBinderbuildingObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("buildingObj.");
	}
	@InitBinder("studentObj")
	public void initBinderstudentObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("studentObj.");
	}
	@InitBinder("taskStateObj")
	public void initBindertaskStateObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("taskStateObj.");
	}
	@InitBinder("repariManObj")
	public void initBinderrepariManObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("repariManObj.");
	}
	@InitBinder("breakInfo")
	public void initBinderBreakInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("breakInfo.");
	}
	/*跳转到添加BreakInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new BreakInfo());
		/*查询所有的BreakType信息*/
		List<BreakType> breakTypeList = breakTypeService.queryAllBreakType();
		request.setAttribute("breakTypeList", breakTypeList);
		/*查询所有的BuildingInfo信息*/
		List<BuildingInfo> buildingInfoList = buildingInfoService.queryAllBuildingInfo();
		request.setAttribute("buildingInfoList", buildingInfoList);
		/*查询所有的RepairMan信息*/
		List<RepairMan> repairManList = repairManService.queryAllRepairMan();
		request.setAttribute("repairManList", repairManList);
		/*查询所有的TaskInfoState信息*/
		List<TaskInfoState> taskInfoStateList = taskInfoStateService.queryAllTaskInfoState();
		request.setAttribute("taskInfoStateList", taskInfoStateList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "BreakInfo_add";
	}

	/*客户端ajax方式提交添加故障信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated BreakInfo breakInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			breakInfo.setBreakPhoto(this.handlePhotoUpload(request, "breakPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        breakInfoService.addBreakInfo(breakInfo);
        message = "故障信息添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询故障信息信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("taskStateObj") TaskInfoState taskStateObj,@ModelAttribute("repariManObj") RepairMan repariManObj,@ModelAttribute("breakTypeObj") BreakType breakTypeObj,@ModelAttribute("buildingObj") BuildingInfo buildingObj,String breakReason,@ModelAttribute("studentObj") UserInfo studentObj,String commitDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (breakReason == null) breakReason = "";
		if (commitDate == null) commitDate = "";
		if(rows != 0)breakInfoService.setRows(rows);
		List<BreakInfo> breakInfoList = breakInfoService.queryBreakInfo(taskStateObj, repariManObj, breakTypeObj, buildingObj, breakReason, studentObj, commitDate, page);
	    /*计算总的页数和总的记录数*/
	    breakInfoService.queryTotalPageAndRecordNumber(taskStateObj, repariManObj, breakTypeObj, buildingObj, breakReason, studentObj, commitDate);
	    /*获取到总的页码数目*/
	    int totalPage = breakInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = breakInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(BreakInfo breakInfo:breakInfoList) {
			JSONObject jsonBreakInfo = breakInfo.getJsonObject();
			jsonArray.put(jsonBreakInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询故障信息信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<BreakInfo> breakInfoList = breakInfoService.queryAllBreakInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(BreakInfo breakInfo:breakInfoList) {
			JSONObject jsonBreakInfo = new JSONObject();
			jsonBreakInfo.accumulate("taskId", breakInfo.getTaskId());
			jsonBreakInfo.accumulate("breakReason", breakInfo.getBreakReason());
			jsonArray.put(jsonBreakInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询故障信息信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("taskStateObj") TaskInfoState taskStateObj,@ModelAttribute("repariManObj") RepairMan repariManObj,@ModelAttribute("breakTypeObj") BreakType breakTypeObj,@ModelAttribute("buildingObj") BuildingInfo buildingObj,String breakReason,@ModelAttribute("studentObj") UserInfo studentObj,String commitDate,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (breakReason == null) breakReason = "";
		if (commitDate == null) commitDate = "";
		List<BreakInfo> breakInfoList = breakInfoService.queryBreakInfo(taskStateObj, repariManObj, breakTypeObj, buildingObj, breakReason, studentObj, commitDate, currentPage);
	    /*计算总的页数和总的记录数*/
	    breakInfoService.queryTotalPageAndRecordNumber(taskStateObj, repariManObj, breakTypeObj, buildingObj, breakReason, studentObj, commitDate);
	    /*获取到总的页码数目*/
	    int totalPage = breakInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = breakInfoService.getRecordNumber();
	    request.setAttribute("breakInfoList",  breakInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("taskStateObj", taskStateObj);
	    request.setAttribute("repariManObj", repariManObj);
	    request.setAttribute("breakTypeObj", breakTypeObj);
	    request.setAttribute("buildingObj", buildingObj);
	    request.setAttribute("breakReason", breakReason);
	    request.setAttribute("studentObj", studentObj);
	    request.setAttribute("commitDate", commitDate);
	    List<BreakType> breakTypeList = breakTypeService.queryAllBreakType();
	    request.setAttribute("breakTypeList", breakTypeList);
	    List<BuildingInfo> buildingInfoList = buildingInfoService.queryAllBuildingInfo();
	    request.setAttribute("buildingInfoList", buildingInfoList);
	    List<RepairMan> repairManList = repairManService.queryAllRepairMan();
	    request.setAttribute("repairManList", repairManList);
	    List<TaskInfoState> taskInfoStateList = taskInfoStateService.queryAllTaskInfoState();
	    request.setAttribute("taskInfoStateList", taskInfoStateList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "BreakInfo/breakInfo_frontquery_result"; 
	}

     /*前台查询BreakInfo信息*/
	@RequestMapping(value="/{taskId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer taskId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键taskId获取BreakInfo对象*/
        BreakInfo breakInfo = breakInfoService.getBreakInfo(taskId);

        List<BreakType> breakTypeList = breakTypeService.queryAllBreakType();
        request.setAttribute("breakTypeList", breakTypeList);
        List<BuildingInfo> buildingInfoList = buildingInfoService.queryAllBuildingInfo();
        request.setAttribute("buildingInfoList", buildingInfoList);
        List<RepairMan> repairManList = repairManService.queryAllRepairMan();
        request.setAttribute("repairManList", repairManList);
        List<TaskInfoState> taskInfoStateList = taskInfoStateService.queryAllTaskInfoState();
        request.setAttribute("taskInfoStateList", taskInfoStateList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("breakInfo",  breakInfo);
        return "BreakInfo/breakInfo_frontshow";
	}

	/*ajax方式显示故障信息修改jsp视图页*/
	@RequestMapping(value="/{taskId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer taskId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键taskId获取BreakInfo对象*/
        BreakInfo breakInfo = breakInfoService.getBreakInfo(taskId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonBreakInfo = breakInfo.getJsonObject();
		out.println(jsonBreakInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新故障信息信息*/
	@RequestMapping(value = "/{taskId}/update", method = RequestMethod.POST)
	public void update(@Validated BreakInfo breakInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String breakPhotoFileName = this.handlePhotoUpload(request, "breakPhotoFile");
		if(!breakPhotoFileName.equals("upload/NoImage.jpg"))breakInfo.setBreakPhoto(breakPhotoFileName); 


		try {
			breakInfoService.updateBreakInfo(breakInfo);
			message = "故障信息更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "故障信息更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除故障信息信息*/
	@RequestMapping(value="/{taskId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer taskId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  breakInfoService.deleteBreakInfo(taskId);
	            request.setAttribute("message", "故障信息删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "故障信息删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条故障信息记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String taskIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = breakInfoService.deleteBreakInfos(taskIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出故障信息信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("taskStateObj") TaskInfoState taskStateObj,@ModelAttribute("repariManObj") RepairMan repariManObj,@ModelAttribute("breakTypeObj") BreakType breakTypeObj,@ModelAttribute("buildingObj") BuildingInfo buildingObj,String breakReason,@ModelAttribute("studentObj") UserInfo studentObj,String commitDate, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(breakReason == null) breakReason = "";
        if(commitDate == null) commitDate = "";
        List<BreakInfo> breakInfoList = breakInfoService.queryBreakInfo(taskStateObj,repariManObj,breakTypeObj,buildingObj,breakReason,studentObj,commitDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "BreakInfo信息记录"; 
        String[] headers = { "任务编号","故障类型","所在宿舍","故障图片","故障信息","申报学生","报修时间","任务状态","维修人员"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<breakInfoList.size();i++) {
        	BreakInfo breakInfo = breakInfoList.get(i); 
        	dataset.add(new String[]{breakInfo.getTaskId() + "",breakInfo.getBreakTypeObj().getBreakTypeName(),breakInfo.getBuildingObj().getBuildingName(),breakInfo.getBreakPhoto(),breakInfo.getBreakReason(),breakInfo.getStudentObj().getName(),breakInfo.getCommitDate(),breakInfo.getTaskStateObj().getStateName(),breakInfo.getRepariManObj().getRepairManName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"BreakInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
