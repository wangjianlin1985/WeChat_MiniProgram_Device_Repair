package com.chengxusheji.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.BreakInfo;

public interface BreakInfoMapper {
	/*添加故障信息信息*/
	public void addBreakInfo(BreakInfo breakInfo) throws Exception;

	/*按照查询条件分页查询故障信息记录*/
	public ArrayList<BreakInfo> queryBreakInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

  
	
	/*按照查询条件查询所有故障信息记录*/
	public ArrayList<BreakInfo> queryBreakInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的故障信息记录数*/
	public int queryBreakInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条故障信息记录*/
	public BreakInfo getBreakInfo(int taskId) throws Exception;

	/*更新故障信息记录*/
	public void updateBreakInfo(BreakInfo breakInfo) throws Exception;

	/*删除故障信息记录*/
	public void deleteBreakInfo(int taskId) throws Exception;

	/*查询最新5条故障记录*/
	public List<BreakInfo> queryRecentBreakInfo();

}
