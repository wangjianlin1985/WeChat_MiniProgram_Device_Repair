package com.chengxusheji.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.BreakType;
import com.chengxusheji.po.BuildingInfo;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.TaskInfoState;
import com.chengxusheji.po.RepairMan;
import com.chengxusheji.po.BreakInfo;

import com.chengxusheji.mapper.BreakInfoMapper;
@Service
public class BreakInfoService {

	@Resource BreakInfoMapper breakInfoMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加故障信息记录*/
    public void addBreakInfo(BreakInfo breakInfo) throws Exception {
    	breakInfoMapper.addBreakInfo(breakInfo);
    }
    
    
    //查询最新5条报修信息
	public List<BreakInfo> queryRecentBreakInfo() { 
		return breakInfoMapper.queryRecentBreakInfo();
	}
	

    /*按照查询条件分页查询故障信息记录*/
    public ArrayList<BreakInfo> queryBreakInfo(TaskInfoState taskStateObj,RepairMan repariManObj,BreakType breakTypeObj,BuildingInfo buildingObj,String breakReason,UserInfo studentObj,String commitDate,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != taskStateObj && taskStateObj.getStateId()!= null && taskStateObj.getStateId()!= 0)  where += " and t_breakInfo.taskStateObj=" + taskStateObj.getStateId();
    	if(null != repariManObj &&  repariManObj.getRepainManNumber() != null  && !repariManObj.getRepainManNumber().equals(""))  where += " and t_breakInfo.repariManObj='" + repariManObj.getRepainManNumber() + "'";
    	if(null != breakTypeObj && breakTypeObj.getBreakTypeId()!= null && breakTypeObj.getBreakTypeId()!= 0)  where += " and t_breakInfo.breakTypeObj=" + breakTypeObj.getBreakTypeId();
    	if(null != buildingObj && buildingObj.getBuildingId()!= null && buildingObj.getBuildingId()!= 0)  where += " and t_breakInfo.buildingObj=" + buildingObj.getBuildingId();
    	if(!breakReason.equals("")) where = where + " and t_breakInfo.breakReason like '%" + breakReason + "%'";
    	if(null != studentObj &&  studentObj.getUser_name() != null  && !studentObj.getUser_name().equals(""))  where += " and t_breakInfo.studentObj='" + studentObj.getUser_name() + "'";
    	if(!commitDate.equals("")) where = where + " and t_breakInfo.commitDate like '%" + commitDate + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return breakInfoMapper.queryBreakInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<BreakInfo> queryBreakInfo(TaskInfoState taskStateObj,RepairMan repariManObj,BreakType breakTypeObj,BuildingInfo buildingObj,String breakReason,UserInfo studentObj,String commitDate) throws Exception  { 
     	String where = "where 1=1";
    	if(null != taskStateObj && taskStateObj.getStateId()!= null && taskStateObj.getStateId()!= 0)  where += " and t_breakInfo.taskStateObj=" + taskStateObj.getStateId();
    	if(null != repariManObj &&  repariManObj.getRepainManNumber() != null && !repariManObj.getRepainManNumber().equals(""))  where += " and t_breakInfo.repariManObj='" + repariManObj.getRepainManNumber() + "'";
    	if(null != breakTypeObj && breakTypeObj.getBreakTypeId()!= null && breakTypeObj.getBreakTypeId()!= 0)  where += " and t_breakInfo.breakTypeObj=" + breakTypeObj.getBreakTypeId();
    	if(null != buildingObj && buildingObj.getBuildingId()!= null && buildingObj.getBuildingId()!= 0)  where += " and t_breakInfo.buildingObj=" + buildingObj.getBuildingId();
    	if(!breakReason.equals("")) where = where + " and t_breakInfo.breakReason like '%" + breakReason + "%'";
    	if(null != studentObj &&  studentObj.getUser_name() != null && !studentObj.getUser_name().equals(""))  where += " and t_breakInfo.studentObj='" + studentObj.getUser_name() + "'";
    	if(!commitDate.equals("")) where = where + " and t_breakInfo.commitDate like '%" + commitDate + "%'";
    	return breakInfoMapper.queryBreakInfoList(where);
    }

    /*查询所有故障信息记录*/
    public ArrayList<BreakInfo> queryAllBreakInfo()  throws Exception {
        return breakInfoMapper.queryBreakInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(TaskInfoState taskStateObj,RepairMan repariManObj,BreakType breakTypeObj,BuildingInfo buildingObj,String breakReason,UserInfo studentObj,String commitDate) throws Exception {
     	String where = "where 1=1";
    	if(null != taskStateObj && taskStateObj.getStateId()!= null && taskStateObj.getStateId()!= 0)  where += " and t_breakInfo.taskStateObj=" + taskStateObj.getStateId();
    	if(null != repariManObj &&  repariManObj.getRepainManNumber() != null && !repariManObj.getRepainManNumber().equals(""))  where += " and t_breakInfo.repariManObj='" + repariManObj.getRepainManNumber() + "'";
    	if(null != breakTypeObj && breakTypeObj.getBreakTypeId()!= null && breakTypeObj.getBreakTypeId()!= 0)  where += " and t_breakInfo.breakTypeObj=" + breakTypeObj.getBreakTypeId();
    	if(null != buildingObj && buildingObj.getBuildingId()!= null && buildingObj.getBuildingId()!= 0)  where += " and t_breakInfo.buildingObj=" + buildingObj.getBuildingId();
    	if(!breakReason.equals("")) where = where + " and t_breakInfo.breakReason like '%" + breakReason + "%'";
    	if(null != studentObj &&  studentObj.getUser_name() != null && !studentObj.getUser_name().equals(""))  where += " and t_breakInfo.studentObj='" + studentObj.getUser_name() + "'";
    	if(!commitDate.equals("")) where = where + " and t_breakInfo.commitDate like '%" + commitDate + "%'";
        recordNumber = breakInfoMapper.queryBreakInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取故障信息记录*/
    public BreakInfo getBreakInfo(int taskId) throws Exception  {
        BreakInfo breakInfo = breakInfoMapper.getBreakInfo(taskId);
        return breakInfo;
    }

    /*更新故障信息记录*/
    public void updateBreakInfo(BreakInfo breakInfo) throws Exception {
        breakInfoMapper.updateBreakInfo(breakInfo);
    }

    /*删除一条故障信息记录*/
    public void deleteBreakInfo (int taskId) throws Exception {
        breakInfoMapper.deleteBreakInfo(taskId);
    }

    /*删除多条故障信息信息*/
    public int deleteBreakInfos (String taskIds) throws Exception {
    	String _taskIds[] = taskIds.split(",");
    	for(String _taskId: _taskIds) {
    		breakInfoMapper.deleteBreakInfo(Integer.parseInt(_taskId));
    	}
    	return _taskIds.length;
    }
    
   
}
