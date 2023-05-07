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
import com.chengxusheji.po.BreakType;
import com.chengxusheji.service.BreakTypeService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/breakType") 
public class ApiBreakTypeController {
	@Resource BreakTypeService breakTypeService;
	@Resource AuthService authService;

	@InitBinder("breakType")
	public void initBinderBreakType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("breakType.");
	}

	/*客户端ajax方式添加故障类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated BreakType breakType, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        breakTypeService.addBreakType(breakType); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新故障类型信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated BreakType breakType, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        breakTypeService.updateBreakType(breakType);  //更新记录到数据库
        return JsonResultBuilder.ok(breakTypeService.getBreakType(breakType.getBreakTypeId()));
	}

	/*ajax方式显示获取故障类型详细信息*/
	@RequestMapping(value="/get/{breakTypeId}",method=RequestMethod.POST)
	public JsonResult getBreakType(@PathVariable int breakTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键breakTypeId获取BreakType对象*/
        BreakType breakType = breakTypeService.getBreakType(breakTypeId); 
        return JsonResultBuilder.ok(breakType);
	}

	/*ajax方式删除故障类型记录*/
	@RequestMapping(value="/delete/{breakTypeId}",method=RequestMethod.POST)
	public JsonResult deleteBreakType(@PathVariable int breakTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			breakTypeService.deleteBreakType(breakTypeId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询故障类型信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)breakTypeService.setRows(rows);
		List<BreakType> breakTypeList = breakTypeService.queryBreakType(page);
	    /*计算总的页数和总的记录数*/
	    breakTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = breakTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = breakTypeService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", breakTypeList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有故障类型
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<BreakType> breakTypeList = breakTypeService.queryAllBreakType(); 
		return JsonResultBuilder.ok(breakTypeList);
	}
}

