package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.TaskInfoState;

public interface TaskInfoStateMapper {
	/*添加任务状态信息*/
	public void addTaskInfoState(TaskInfoState taskInfoState) throws Exception;

	/*按照查询条件分页查询任务状态记录*/
	public ArrayList<TaskInfoState> queryTaskInfoState(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有任务状态记录*/
	public ArrayList<TaskInfoState> queryTaskInfoStateList(@Param("where") String where) throws Exception;

	/*按照查询条件的任务状态记录数*/
	public int queryTaskInfoStateCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条任务状态记录*/
	public TaskInfoState getTaskInfoState(int stateId) throws Exception;

	/*更新任务状态记录*/
	public void updateTaskInfoState(TaskInfoState taskInfoState) throws Exception;

	/*删除任务状态记录*/
	public void deleteTaskInfoState(int stateId) throws Exception;

}
