package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.BreakType;

public interface BreakTypeMapper {
	/*添加故障类型信息*/
	public void addBreakType(BreakType breakType) throws Exception;

	/*按照查询条件分页查询故障类型记录*/
	public ArrayList<BreakType> queryBreakType(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有故障类型记录*/
	public ArrayList<BreakType> queryBreakTypeList(@Param("where") String where) throws Exception;

	/*按照查询条件的故障类型记录数*/
	public int queryBreakTypeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条故障类型记录*/
	public BreakType getBreakType(int breakTypeId) throws Exception;

	/*更新故障类型记录*/
	public void updateBreakType(BreakType breakType) throws Exception;

	/*删除故障类型记录*/
	public void deleteBreakType(int breakTypeId) throws Exception;

}
