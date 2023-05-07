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
import com.chengxusheji.service.RepairManService;
import com.chengxusheji.po.RepairMan;

//RepairMan管理控制层
@Controller
@RequestMapping("/RepairMan")
public class RepairManController extends BaseController {

    /*业务层对象*/
    @Resource RepairManService repairManService;

	@InitBinder("repairMan")
	public void initBinderRepairMan(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("repairMan.");
	}
	/*跳转到添加RepairMan视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new RepairMan());
		return "RepairMan_add";
	}

	/*客户端ajax方式提交添加维修人员信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated RepairMan repairMan, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(repairManService.getRepairMan(repairMan.getRepainManNumber()) != null) {
			message = "人员编号已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			repairMan.setMyPhoto(this.handlePhotoUpload(request, "myPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        repairManService.addRepairMan(repairMan);
        message = "维修人员添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询维修人员信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String repainManNumber,String repairManName,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
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
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(RepairMan repairMan:repairManList) {
			JSONObject jsonRepairMan = repairMan.getJsonObject();
			jsonArray.put(jsonRepairMan);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询维修人员信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<RepairMan> repairManList = repairManService.queryAllRepairMan();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(RepairMan repairMan:repairManList) {
			JSONObject jsonRepairMan = new JSONObject();
			jsonRepairMan.accumulate("repainManNumber", repairMan.getRepainManNumber());
			jsonRepairMan.accumulate("repairManName", repairMan.getRepairManName());
			jsonArray.put(jsonRepairMan);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询维修人员信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String repainManNumber,String repairManName,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (repainManNumber == null) repainManNumber = "";
		if (repairManName == null) repairManName = "";
		List<RepairMan> repairManList = repairManService.queryRepairMan(repainManNumber, repairManName, currentPage);
	    /*计算总的页数和总的记录数*/
	    repairManService.queryTotalPageAndRecordNumber(repainManNumber, repairManName);
	    /*获取到总的页码数目*/
	    int totalPage = repairManService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = repairManService.getRecordNumber();
	    request.setAttribute("repairManList",  repairManList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("repainManNumber", repainManNumber);
	    request.setAttribute("repairManName", repairManName);
		return "RepairMan/repairMan_frontquery_result"; 
	}

     /*前台查询RepairMan信息*/
	@RequestMapping(value="/{repainManNumber}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String repainManNumber,Model model,HttpServletRequest request) throws Exception {
		/*根据主键repainManNumber获取RepairMan对象*/
        RepairMan repairMan = repairManService.getRepairMan(repainManNumber);

        request.setAttribute("repairMan",  repairMan);
        return "RepairMan/repairMan_frontshow";
	}

	/*ajax方式显示维修人员修改jsp视图页*/
	@RequestMapping(value="/{repainManNumber}/update",method=RequestMethod.GET)
	public void update(@PathVariable String repainManNumber,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键repainManNumber获取RepairMan对象*/
        RepairMan repairMan = repairManService.getRepairMan(repainManNumber);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonRepairMan = repairMan.getJsonObject();
		out.println(jsonRepairMan.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新维修人员信息*/
	@RequestMapping(value = "/{repainManNumber}/update", method = RequestMethod.POST)
	public void update(@Validated RepairMan repairMan, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String myPhotoFileName = this.handlePhotoUpload(request, "myPhotoFile");
		if(!myPhotoFileName.equals("upload/NoImage.jpg"))repairMan.setMyPhoto(myPhotoFileName); 


		try {
			repairManService.updateRepairMan(repairMan);
			message = "维修人员更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "维修人员更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除维修人员信息*/
	@RequestMapping(value="/{repainManNumber}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String repainManNumber,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  repairManService.deleteRepairMan(repainManNumber);
	            request.setAttribute("message", "维修人员删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "维修人员删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条维修人员记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String repainManNumbers,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = repairManService.deleteRepairMans(repainManNumbers);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出维修人员信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String repainManNumber,String repairManName, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(repainManNumber == null) repainManNumber = "";
        if(repairManName == null) repairManName = "";
        List<RepairMan> repairManList = repairManService.queryRepairMan(repainManNumber,repairManName);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "RepairMan信息记录"; 
        String[] headers = { "人员编号","人员姓名","性别","个人头像","工作年限","联系电话"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<repairManList.size();i++) {
        	RepairMan repairMan = repairManList.get(i); 
        	dataset.add(new String[]{repairMan.getRepainManNumber(),repairMan.getRepairManName(),repairMan.getRepairManSex(),repairMan.getMyPhoto(),repairMan.getWorkYear(),repairMan.getTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"RepairMan.xls");//filename是下载的xls的名，建议最好用英文 
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
