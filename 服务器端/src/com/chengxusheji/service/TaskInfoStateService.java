package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.TaskInfoState;

import com.chengxusheji.mapper.TaskInfoStateMapper;
@Service
public class TaskInfoStateService {

	@Resource TaskInfoStateMapper taskInfoStateMapper;
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

    /*添加任务状态记录*/
    public void addTaskInfoState(TaskInfoState taskInfoState) throws Exception {
    	taskInfoStateMapper.addTaskInfoState(taskInfoState);
    }

    /*按照查询条件分页查询任务状态记录*/
    public ArrayList<TaskInfoState> queryTaskInfoState(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return taskInfoStateMapper.queryTaskInfoState(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<TaskInfoState> queryTaskInfoState() throws Exception  { 
     	String where = "where 1=1";
    	return taskInfoStateMapper.queryTaskInfoStateList(where);
    }

    /*查询所有任务状态记录*/
    public ArrayList<TaskInfoState> queryAllTaskInfoState()  throws Exception {
        return taskInfoStateMapper.queryTaskInfoStateList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = taskInfoStateMapper.queryTaskInfoStateCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取任务状态记录*/
    public TaskInfoState getTaskInfoState(int stateId) throws Exception  {
        TaskInfoState taskInfoState = taskInfoStateMapper.getTaskInfoState(stateId);
        return taskInfoState;
    }

    /*更新任务状态记录*/
    public void updateTaskInfoState(TaskInfoState taskInfoState) throws Exception {
        taskInfoStateMapper.updateTaskInfoState(taskInfoState);
    }

    /*删除一条任务状态记录*/
    public void deleteTaskInfoState (int stateId) throws Exception {
        taskInfoStateMapper.deleteTaskInfoState(stateId);
    }

    /*删除多条任务状态信息*/
    public int deleteTaskInfoStates (String stateIds) throws Exception {
    	String _stateIds[] = stateIds.split(",");
    	for(String _stateId: _stateIds) {
    		taskInfoStateMapper.deleteTaskInfoState(Integer.parseInt(_stateId));
    	}
    	return _stateIds.length;
    }
}
