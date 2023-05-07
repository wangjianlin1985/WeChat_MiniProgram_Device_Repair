package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RepairMan {
    /*人员编号*/
    @NotEmpty(message="人员编号不能为空")
    private String repainManNumber;
    public String getRepainManNumber(){
        return repainManNumber;
    }
    public void setRepainManNumber(String repainManNumber){
        this.repainManNumber = repainManNumber;
    }

    /*人员姓名*/
    @NotEmpty(message="人员姓名不能为空")
    private String repairManName;
    public String getRepairManName() {
        return repairManName;
    }
    public void setRepairManName(String repairManName) {
        this.repairManName = repairManName;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String repairManSex;
    public String getRepairManSex() {
        return repairManSex;
    }
    public void setRepairManSex(String repairManSex) {
        this.repairManSex = repairManSex;
    }

    /*个人头像*/
    private String myPhoto;
    public String getMyPhoto() {
        return myPhoto;
    }
    public void setMyPhoto(String myPhoto) {
        this.myPhoto = myPhoto;
    }

    private String myPhotoUrl;
    public void setMyPhotoUrl(String myPhotoUrl) {
		this.myPhotoUrl = myPhotoUrl;
	}
	public String getMyPhotoUrl() {
		return  SessionConsts.BASE_URL + myPhoto;
	}
    /*工作年限*/
    @NotEmpty(message="工作年限不能为空")
    private String workYear;
    public String getWorkYear() {
        return workYear;
    }
    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*维修人员介绍*/
    private String repairMan;
    public String getRepairMan() {
        return repairMan;
    }
    public void setRepairMan(String repairMan) {
        this.repairMan = repairMan;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonRepairMan=new JSONObject(); 
		jsonRepairMan.accumulate("repainManNumber", this.getRepainManNumber());
		jsonRepairMan.accumulate("repairManName", this.getRepairManName());
		jsonRepairMan.accumulate("repairManSex", this.getRepairManSex());
		jsonRepairMan.accumulate("myPhoto", this.getMyPhoto());
		jsonRepairMan.accumulate("workYear", this.getWorkYear());
		jsonRepairMan.accumulate("telephone", this.getTelephone());
		jsonRepairMan.accumulate("repairMan", this.getRepairMan());
		return jsonRepairMan;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}