package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BreakInfo {
    /*任务编号*/
    private Integer taskId;
    public Integer getTaskId(){
        return taskId;
    }
    public void setTaskId(Integer taskId){
        this.taskId = taskId;
    }

    /*故障类型*/
    private BreakType breakTypeObj;
    public BreakType getBreakTypeObj() {
        return breakTypeObj;
    }
    public void setBreakTypeObj(BreakType breakTypeObj) {
        this.breakTypeObj = breakTypeObj;
    }

    /*所在宿舍*/
    private BuildingInfo buildingObj;
    public BuildingInfo getBuildingObj() {
        return buildingObj;
    }
    public void setBuildingObj(BuildingInfo buildingObj) {
        this.buildingObj = buildingObj;
    }

    /*故障图片*/
    private String breakPhoto;
    public String getBreakPhoto() {
        return breakPhoto;
    }
    public void setBreakPhoto(String breakPhoto) {
        this.breakPhoto = breakPhoto;
    }

    private String breakPhotoUrl;
    public void setBreakPhotoUrl(String breakPhotoUrl) {
		this.breakPhotoUrl = breakPhotoUrl;
	}
	public String getBreakPhotoUrl() {
		return  SessionConsts.BASE_URL + breakPhoto;
	}
    /*故障信息*/
    @NotEmpty(message="故障信息不能为空")
    private String breakReason;
    public String getBreakReason() {
        return breakReason;
    }
    public void setBreakReason(String breakReason) {
        this.breakReason = breakReason;
    }

    /*申报学生*/
    private UserInfo studentObj;
    public UserInfo getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(UserInfo studentObj) {
        this.studentObj = studentObj;
    }

    /*报修时间*/
    @NotEmpty(message="报修时间不能为空")
    private String commitDate;
    public String getCommitDate() {
        return commitDate;
    }
    public void setCommitDate(String commitDate) {
        this.commitDate = commitDate;
    }

    /*任务状态*/
    private TaskInfoState taskStateObj;
    public TaskInfoState getTaskStateObj() {
        return taskStateObj;
    }
    public void setTaskStateObj(TaskInfoState taskStateObj) {
        this.taskStateObj = taskStateObj;
    }

    /*维修人员*/
    private RepairMan repariManObj;
    public RepairMan getRepariManObj() {
        return repariManObj;
    }
    public void setRepariManObj(RepairMan repariManObj) {
        this.repariManObj = repariManObj;
    }

    /*处理结果*/
    private String handResult;
    public String getHandResult() {
        return handResult;
    }
    public void setHandResult(String handResult) {
        this.handResult = handResult;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBreakInfo=new JSONObject(); 
		jsonBreakInfo.accumulate("taskId", this.getTaskId());
		jsonBreakInfo.accumulate("breakTypeObj", this.getBreakTypeObj().getBreakTypeName());
		jsonBreakInfo.accumulate("breakTypeObjPri", this.getBreakTypeObj().getBreakTypeId());
		jsonBreakInfo.accumulate("buildingObj", this.getBuildingObj().getBuildingName());
		jsonBreakInfo.accumulate("buildingObjPri", this.getBuildingObj().getBuildingId());
		jsonBreakInfo.accumulate("breakPhoto", this.getBreakPhoto());
		jsonBreakInfo.accumulate("breakReason", this.getBreakReason());
		jsonBreakInfo.accumulate("studentObj", this.getStudentObj().getName());
		jsonBreakInfo.accumulate("studentObjPri", this.getStudentObj().getUser_name());
		jsonBreakInfo.accumulate("commitDate", this.getCommitDate().length()>19?this.getCommitDate().substring(0,19):this.getCommitDate());
		jsonBreakInfo.accumulate("taskStateObj", this.getTaskStateObj().getStateName());
		jsonBreakInfo.accumulate("taskStateObjPri", this.getTaskStateObj().getStateId());
		jsonBreakInfo.accumulate("repariManObj", this.getRepariManObj().getRepairManName());
		jsonBreakInfo.accumulate("repariManObjPri", this.getRepariManObj().getRepainManNumber());
		jsonBreakInfo.accumulate("handResult", this.getHandResult());
		return jsonBreakInfo;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}