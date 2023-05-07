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
import com.chengxusheji.po.BuildingInfo;
import com.chengxusheji.po.AreaInfo;
import com.chengxusheji.service.BuildingInfoService;
import com.client.service.AuthService;
import com.client.utils.JsonResult;
import com.client.utils.JsonResultBuilder;
import com.client.utils.ReturnCode;

@RestController
@RequestMapping("/api/buildingInfo") 
public class ApiBuildingInfoController {
	@Resource BuildingInfoService buildingInfoService;
	@Resource AuthService authService;

	@InitBinder("areaObj")
	public void initBinderareaObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("areaObj.");
	}
	@InitBinder("buildingInfo")
	public void initBinderBuildingInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("buildingInfo.");
	}

	/*客户端ajax方式添加宿舍信息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JsonResult add(@Validated BuildingInfo buildingInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors()) //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR);
        buildingInfoService.addBuildingInfo(buildingInfo); //添加到数据库
        return JsonResultBuilder.ok();
	}

	/*客户端ajax更新宿舍信息信息*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public JsonResult update(@Validated BuildingInfo buildingInfo, BindingResult br, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		if (br.hasErrors())  //验证输入参数
			return JsonResultBuilder.error(ReturnCode.INPUT_PARAM_ERROR); 
        buildingInfoService.updateBuildingInfo(buildingInfo);  //更新记录到数据库
        return JsonResultBuilder.ok(buildingInfoService.getBuildingInfo(buildingInfo.getBuildingId()));
	}

	/*ajax方式显示获取宿舍信息详细信息*/
	@RequestMapping(value="/get/{buildingId}",method=RequestMethod.POST)
	public JsonResult getBuildingInfo(@PathVariable int buildingId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键buildingId获取BuildingInfo对象*/
        BuildingInfo buildingInfo = buildingInfoService.getBuildingInfo(buildingId); 
        return JsonResultBuilder.ok(buildingInfo);
	}

	/*ajax方式删除宿舍信息记录*/
	@RequestMapping(value="/delete/{buildingId}",method=RequestMethod.POST)
	public JsonResult deleteBuildingInfo(@PathVariable int buildingId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		//通过accessToken获取到用户信息 
		String userName = authService.getUserName(request);
		if(userName == null) return JsonResultBuilder.error(ReturnCode.TOKEN_VALID_ERROR);
		try {
			buildingInfoService.deleteBuildingInfo(buildingId);
			return JsonResultBuilder.ok();
		} catch (Exception ex) {
			return JsonResultBuilder.error(ReturnCode.FOREIGN_KEY_CONSTRAINT_ERROR);
		}
	}

	//客户端查询宿舍信息信息
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public JsonResult list(@ModelAttribute("areaObj") AreaInfo areaObj,String buildingName,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (buildingName == null) buildingName = "";
		if(rows != 0)buildingInfoService.setRows(rows);
		List<BuildingInfo> buildingInfoList = buildingInfoService.queryBuildingInfo(areaObj, buildingName, page);
	    /*计算总的页数和总的记录数*/
	    buildingInfoService.queryTotalPageAndRecordNumber(areaObj, buildingName);
	    /*获取到总的页码数目*/
	    int totalPage = buildingInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = buildingInfoService.getRecordNumber();
	    HashMap<String, Object> resultMap = new HashMap<String, Object>();
	    resultMap.put("totalPage", totalPage);
	    resultMap.put("list", buildingInfoList);
	    return JsonResultBuilder.ok(resultMap);
	}

	//客户端ajax获取所有宿舍信息
	@RequestMapping(value="/listAll",method=RequestMethod.POST)
	public JsonResult listAll() throws Exception{
		List<BuildingInfo> buildingInfoList = buildingInfoService.queryAllBuildingInfo(); 
		return JsonResultBuilder.ok(buildingInfoList);
	}
}

