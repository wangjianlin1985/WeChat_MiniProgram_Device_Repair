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
import com.chengxusheji.service.BreakTypeService;
import com.chengxusheji.po.BreakType;

//BreakType管理控制层
@Controller
@RequestMapping("/BreakType")
public class BreakTypeController extends BaseController {

    /*业务层对象*/
    @Resource BreakTypeService breakTypeService;

	@InitBinder("breakType")
	public void initBinderBreakType(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("breakType.");
	}
	/*跳转到添加BreakType视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new BreakType());
		return "BreakType_add";
	}

	/*客户端ajax方式提交添加故障类型信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated BreakType breakType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        breakTypeService.addBreakType(breakType);
        message = "故障类型添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询故障类型信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)breakTypeService.setRows(rows);
		List<BreakType> breakTypeList = breakTypeService.queryBreakType(page);
	    /*计算总的页数和总的记录数*/
	    breakTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = breakTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = breakTypeService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(BreakType breakType:breakTypeList) {
			JSONObject jsonBreakType = breakType.getJsonObject();
			jsonArray.put(jsonBreakType);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询故障类型信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<BreakType> breakTypeList = breakTypeService.queryAllBreakType();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(BreakType breakType:breakTypeList) {
			JSONObject jsonBreakType = new JSONObject();
			jsonBreakType.accumulate("breakTypeId", breakType.getBreakTypeId());
			jsonBreakType.accumulate("breakTypeName", breakType.getBreakTypeName());
			jsonArray.put(jsonBreakType);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询故障类型信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<BreakType> breakTypeList = breakTypeService.queryBreakType(currentPage);
	    /*计算总的页数和总的记录数*/
	    breakTypeService.queryTotalPageAndRecordNumber();
	    /*获取到总的页码数目*/
	    int totalPage = breakTypeService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = breakTypeService.getRecordNumber();
	    request.setAttribute("breakTypeList",  breakTypeList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
		return "BreakType/breakType_frontquery_result"; 
	}

     /*前台查询BreakType信息*/
	@RequestMapping(value="/{breakTypeId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer breakTypeId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键breakTypeId获取BreakType对象*/
        BreakType breakType = breakTypeService.getBreakType(breakTypeId);

        request.setAttribute("breakType",  breakType);
        return "BreakType/breakType_frontshow";
	}

	/*ajax方式显示故障类型修改jsp视图页*/
	@RequestMapping(value="/{breakTypeId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer breakTypeId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键breakTypeId获取BreakType对象*/
        BreakType breakType = breakTypeService.getBreakType(breakTypeId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonBreakType = breakType.getJsonObject();
		out.println(jsonBreakType.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新故障类型信息*/
	@RequestMapping(value = "/{breakTypeId}/update", method = RequestMethod.POST)
	public void update(@Validated BreakType breakType, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			breakTypeService.updateBreakType(breakType);
			message = "故障类型更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "故障类型更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除故障类型信息*/
	@RequestMapping(value="/{breakTypeId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer breakTypeId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  breakTypeService.deleteBreakType(breakTypeId);
	            request.setAttribute("message", "故障类型删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "故障类型删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条故障类型记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String breakTypeIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = breakTypeService.deleteBreakTypes(breakTypeIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出故障类型信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel( Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<BreakType> breakTypeList = breakTypeService.queryBreakType();
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "BreakType信息记录"; 
        String[] headers = { "类型编号","类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<breakTypeList.size();i++) {
        	BreakType breakType = breakTypeList.get(i); 
        	dataset.add(new String[]{breakType.getBreakTypeId() + "",breakType.getBreakTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"BreakType.xls");//filename是下载的xls的名，建议最好用英文 
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
