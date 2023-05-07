package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.RepairMan;

public interface RepairManMapper {
	/*添加维修人员信息*/
	public void addRepairMan(RepairMan repairMan) throws Exception;

	/*按照查询条件分页查询维修人员记录*/
	public ArrayList<RepairMan> queryRepairMan(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有维修人员记录*/
	public ArrayList<RepairMan> queryRepairManList(@Param("where") String where) throws Exception;

	/*按照查询条件的维修人员记录数*/
	public int queryRepairManCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条维修人员记录*/
	public RepairMan getRepairMan(String repainManNumber) throws Exception;

	/*更新维修人员记录*/
	public void updateRepairMan(RepairMan repairMan) throws Exception;

	/*删除维修人员记录*/
	public void deleteRepairMan(String repainManNumber) throws Exception;

}
