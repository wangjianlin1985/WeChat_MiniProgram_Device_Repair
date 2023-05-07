package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.RepairMan;

import com.chengxusheji.mapper.RepairManMapper;
@Service
public class RepairManService {

	@Resource RepairManMapper repairManMapper;
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

    /*添加维修人员记录*/
    public void addRepairMan(RepairMan repairMan) throws Exception {
    	repairManMapper.addRepairMan(repairMan);
    }

    /*按照查询条件分页查询维修人员记录*/
    public ArrayList<RepairMan> queryRepairMan(String repainManNumber,String repairManName,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!repainManNumber.equals("")) where = where + " and t_repairMan.repainManNumber like '%" + repainManNumber + "%'";
    	if(!repairManName.equals("")) where = where + " and t_repairMan.repairManName like '%" + repairManName + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return repairManMapper.queryRepairMan(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<RepairMan> queryRepairMan(String repainManNumber,String repairManName) throws Exception  { 
     	String where = "where 1=1";
    	if(!repainManNumber.equals("")) where = where + " and t_repairMan.repainManNumber like '%" + repainManNumber + "%'";
    	if(!repairManName.equals("")) where = where + " and t_repairMan.repairManName like '%" + repairManName + "%'";
    	return repairManMapper.queryRepairManList(where);
    }

    /*查询所有维修人员记录*/
    public ArrayList<RepairMan> queryAllRepairMan()  throws Exception {
        return repairManMapper.queryRepairManList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String repainManNumber,String repairManName) throws Exception {
     	String where = "where 1=1";
    	if(!repainManNumber.equals("")) where = where + " and t_repairMan.repainManNumber like '%" + repainManNumber + "%'";
    	if(!repairManName.equals("")) where = where + " and t_repairMan.repairManName like '%" + repairManName + "%'";
        recordNumber = repairManMapper.queryRepairManCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取维修人员记录*/
    public RepairMan getRepairMan(String repainManNumber) throws Exception  {
        RepairMan repairMan = repairManMapper.getRepairMan(repainManNumber);
        return repairMan;
    }

    /*更新维修人员记录*/
    public void updateRepairMan(RepairMan repairMan) throws Exception {
        repairManMapper.updateRepairMan(repairMan);
    }

    /*删除一条维修人员记录*/
    public void deleteRepairMan (String repainManNumber) throws Exception {
        repairManMapper.deleteRepairMan(repainManNumber);
    }

    /*删除多条维修人员信息*/
    public int deleteRepairMans (String repainManNumbers) throws Exception {
    	String _repainManNumbers[] = repainManNumbers.split(",");
    	for(String _repainManNumber: _repainManNumbers) {
    		repairManMapper.deleteRepairMan(_repainManNumber);
    	}
    	return _repainManNumbers.length;
    }
}
