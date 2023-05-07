package com.client.controller;

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
import com.chengxusheji.po.TaskInfoState;
import com.chengxusheji.service.TaskInfoStateService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/taskInfoState") 
public class ApiTaskInfoStateController {
	@Resource TaskInfoStateService taskInfoStateService;
	@Resource AuthService authService;

	@InitBinder("taskInfoState")
	public void initBinderTaskInfoState(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("taskInfoState.");
	}

	/*客户端ajax方式添加任务状态信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated TaskInfoState taskInfoState, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        taskInfoStateService.addTaskInfoState(taskInfoState); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新任务状态信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated TaskInfoState taskInfoState, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        taskInfoStateService.updateTaskInfoState(taskInfoState);  //更新记录到数据库
        return JsonResultBuilder.ok(taskInfoStateService.getTaskInfoState(taskInfoState.getStateId()));
	}

	/*ajax方式显示获取任务状态详细信息*/
	@RequestMapping(value="/get/{stateId}",method=RequestMethod.POST)
	public JsonResult getTaskInfoState(@PathVariable int stateId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键stateId获取TaskInfoState对象*/
        TaskInfoState taskInfoState = taskInfoStateService.getTaskInfoState(stateId); 
        return JsonResultBuilder.ok(taskInfoState);
	}

	/*ajax方式删除任务状态记录*/
	@RequestMapping(value="/delete/{stateId}",method=RequestMethod.POST)
	public JsonResult deleteTaskInfoState(@PathVariable int stateId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			taskInfoStateService.deleteTaskInfoState(stateId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询任务状态信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)taskInfoStateService.setRows(rows);
		List<TaskInfoState> taskInfoStateList = taskInfoStateService.queryTaskInfoState(page);
	    /*计算总的页数和总的记录数*/
	    taskInfoStateService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = taskInfoStateService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = taskInfoStateService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", taskInfoStateList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有任务状态
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<TaskInfoState> taskInfoStateList = taskInfoStateService.queryAllTaskInfoState(); 
		return JsonResultBuilder.ok(taskInfoStateList);
	}
}

