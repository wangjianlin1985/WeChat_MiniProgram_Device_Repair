package com.client.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.chengxusheji.po.BreakInfo;
import com.chengxusheji.po.BreakType;
import com.chengxusheji.po.BuildingInfo;
import com.chengxusheji.po.RepairMan;
import com.chengxusheji.po.TaskInfoState;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.service.BreakInfoService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/breakInfo") 
public class ApiBreakInfoController {
	@Resource BreakInfoService breakInfoService;
	@Resource AuthService authService;

	@InitBinder("breakTypeObj")
	public void initBinderbreakTypeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("breakTypeObj.");
	}
	@InitBinder("buildingObj")
	public void initBinderbuildingObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("buildingObj.");
	}
	@InitBinder("repariManObj")
	public void initBinderrepariManObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("repariManObj.");
	}
	@InitBinder("taskStateObj")
	public void initBindertaskStateObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("taskStateObj.");
	}
	@InitBinder("studentObj")
	public void initBinderstudentObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("studentObj.");
	}
	@InitBinder("breakInfo")
	public void initBinderBreakInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("breakInfo.");
	}

	/*客户端ajax方式添加故障信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add( BreakInfo breakInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(userName);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		breakInfo.setCommitDate(sdf.format(new java.util.Date()));
		breakInfo.setHandResult("--");
		breakInfo.setStudentObj(userObj); 
		
        breakInfoService.addBreakInfo(breakInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新故障信息信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated BreakInfo breakInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        breakInfoService.updateBreakInfo(breakInfo);  //更新记录到数据库
        return JsonResultBuilder.ok(breakInfoService.getBreakInfo(breakInfo.getTaskId()));
	}

	/*ajax方式显示获取故障信息详细信息*/
	@RequestMapping(value="/get/{taskId}",method=RequestMethod.POST)
	public JsonResult getBreakInfo(@PathVariable int taskId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键taskId获取BreakInfo对象*/
        BreakInfo breakInfo = breakInfoService.getBreakInfo(taskId); 
        return JsonResultBuilder.ok(breakInfo);
	}

	/*ajax方式删除故障信息记录*/
	@RequestMapping(value="/delete/{taskId}",method=RequestMethod.POST)
	public JsonResult deleteBreakInfo(@PathVariable int taskId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			breakInfoService.deleteBreakInfo(taskId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询故障信息信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("taskStateObj") TaskInfoState taskStateObj,@ModelAttribute("repariManObj") RepairMan repariManObj,@ModelAttribute("breakTypeObj") BreakType breakTypeObj,@ModelAttribute("buildingObj") BuildingInfo buildingObj,String breakReason,@ModelAttribute("studentObj") UserInfo studentObj,String commitDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", breakInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	
	//客户端查询最新5条故障信息
	@RequestMapping(value="/zxList",method=RequestMethod.POST)
	public JsonResult zxList(@ModelAttribute("taskStateObj") TaskInfoState taskStateObj,@ModelAttribute("repariManObj") RepairMan repariManObj,@ModelAttribute("breakTypeObj") BreakType breakTypeObj,@ModelAttribute("buildingObj") BuildingInfo buildingObj,String breakReason,@ModelAttribute("studentObj") UserInfo studentObj,String commitDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		 
		List<BreakInfo> breakInfoList = breakInfoService.queryRecentBreakInfo();
	    
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    
	    resultMap.put("list", breakInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	
	//客户端查询故障信息信息
	@RequestMapping(value="/userlist",method=RequestMethod.POST)
	public JsonResult userlist(@ModelAttribute("taskStateObj") TaskInfoState taskStateObj,@ModelAttribute("repariManObj") RepairMan repariManObj,@ModelAttribute("breakTypeObj") BreakType breakTypeObj,@ModelAttribute("buildingObj") BuildingInfo buildingObj,String breakReason,@ModelAttribute("studentObj") UserInfo studentObj,String commitDate,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		studentObj = new UserInfo();
		studentObj.setUser_name(userName);
		
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
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", breakInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}
	
	

	//客户端ajax获取所有故障信息
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<BreakInfo> breakInfoList = breakInfoService.queryAllBreakInfo(); 
		return JsonResultBuilder.ok(breakInfoList);
	}
}

