package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.BreakType;

import com.chengxusheji.mapper.BreakTypeMapper;
@Service
public class BreakTypeService {

	@Resource BreakTypeMapper breakTypeMapper;
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

    /*添加故障类型记录*/
    public void addBreakType(BreakType breakType) throws Exception {
    	breakTypeMapper.addBreakType(breakType);
    }

    /*按照查询条件分页查询故障类型记录*/
    public ArrayList<BreakType> queryBreakType(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return breakTypeMapper.queryBreakType(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<BreakType> queryBreakType() throws Exception  { 
     	String where = "where 1=1";
    	return breakTypeMapper.queryBreakTypeList(where);
    }

    /*查询所有故障类型记录*/
    public ArrayList<BreakType> queryAllBreakType()  throws Exception {
        return breakTypeMapper.queryBreakTypeList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = breakTypeMapper.queryBreakTypeCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取故障类型记录*/
    public BreakType getBreakType(int breakTypeId) throws Exception  {
        BreakType breakType = breakTypeMapper.getBreakType(breakTypeId);
        return breakType;
    }

    /*更新故障类型记录*/
    public void updateBreakType(BreakType breakType) throws Exception {
        breakTypeMapper.updateBreakType(breakType);
    }

    /*删除一条故障类型记录*/
    public void deleteBreakType (int breakTypeId) throws Exception {
        breakTypeMapper.deleteBreakType(breakTypeId);
    }

    /*删除多条故障类型信息*/
    public int deleteBreakTypes (String breakTypeIds) throws Exception {
    	String _breakTypeIds[] = breakTypeIds.split(",");
    	for(String _breakTypeId: _breakTypeIds) {
    		breakTypeMapper.deleteBreakType(Integer.parseInt(_breakTypeId));
    	}
    	return _breakTypeIds.length;
    }
}
