package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BuildingInfo {
    /*记录编号*/
    private Integer buildingId;
    public Integer getBuildingId(){
        return buildingId;
    }
    public void setBuildingId(Integer buildingId){
        this.buildingId = buildingId;
    }

    /*所在区域*/
    private AreaInfo areaObj;
    public AreaInfo getAreaObj() {
        return areaObj;
    }
    public void setAreaObj(AreaInfo areaObj) {
        this.areaObj = areaObj;
    }

    /*宿舍名称*/
    @NotEmpty(message="宿舍名称不能为空")
    private String buildingName;
    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /*管理员*/
    private String manageMan;
    public String getManageMan() {
        return manageMan;
    }
    public void setManageMan(String manageMan) {
        this.manageMan = manageMan;
    }

    /*门卫电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBuildingInfo=new JSONObject(); 
		jsonBuildingInfo.accumulate("buildingId", this.getBuildingId());
		jsonBuildingInfo.accumulate("areaObj", this.getAreaObj().getAreaName());
		jsonBuildingInfo.accumulate("areaObjPri", this.getAreaObj().getAreaId());
		jsonBuildingInfo.accumulate("buildingName", this.getBuildingName());
		jsonBuildingInfo.accumulate("manageMan", this.getManageMan());
		jsonBuildingInfo.accumulate("telephone", this.getTelephone());
		return jsonBuildingInfo;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}