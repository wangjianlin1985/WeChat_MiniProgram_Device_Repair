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
import com.chengxusheji.po.RepairMan;
import com.chengxusheji.service.RepairManService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/repairMan") 
public class ApiRepairManController {
	@Resource RepairManService repairManService;
	@Resource AuthService authService;

	@InitBinder("repairMan")
	public void initBinderRepairMan(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("repairMan.");
	}

	/*客户端ajax方式添加维修人员信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated RepairMan repairMan, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
		if(repairManService.getRepairMan(repairMan.getRepainManNumber()) != null) //验证主键是否重复
			return JsonResultBuilder.error(ReturnCode.PRIMARY_EXIST_ERROR);
        repairManService.addRepairMan(repairMan); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新维修人员信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated RepairMan repairMan, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        repairManService.updateRepairMan(repairMan);  //更新记录到数据库
        return JsonResultBuilder.ok(repairManService.getRepairMan(repairMan.getRepainManNumber()));
	}

	/*ajax方式显示获取维修人员详细信息*/
	@RequestMapping(value="/get/{repainManNumber}",method=RequestMethod.POST)
	public JsonResult getRepairMan(@PathVariable String repainManNumber,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键repainManNumber获取RepairMan对象*/
        RepairMan repairMan = repairManService.getRepairMan(repainManNumber); 
        return JsonResultBuilder.ok(repairMan);
	}

	/*ajax方式删除维修人员记录*/
	@RequestMapping(value="/delete/{repainManNumber}",method=RequestMethod.POST)
	public JsonResult deleteRepairMan(@PathVariable String repainManNumber,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			repairManService.deleteRepairMan(repainManNumber);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询维修人员信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(String repainManNumber,String repairManName,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (repainManNumber == null) repainManNumber = "";
		if (repairManName == null) repairManName = "";
		if(rows != 0)repairManService.setRows(rows);
		List<RepairMan> repairManList = repairManService.queryRepairMan(repainManNumber, repairManName, page);
	    /*计算总的页数和总的记录数*/
	    repairManService.queryTotalPageAndRecordNumber(repainManNumber, repairManName);
	    /*获取到总的页码数目*/
	    int totalPage = repairManService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = repairManService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", repairManList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有维修人员
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<RepairMan> repairManList = repairManService.queryAllRepairMan(); 
		return JsonResultBuilder.ok(repairManList);
	}
}

