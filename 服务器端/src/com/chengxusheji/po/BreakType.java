package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;
import com.client.utils.JsonUtils;
import com.client.utils.SessionConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BreakType {
    /*类型编号*/
    private Integer breakTypeId;
    public Integer getBreakTypeId(){
        return breakTypeId;
    }
    public void setBreakTypeId(Integer breakTypeId){
        this.breakTypeId = breakTypeId;
    }

    /*类型名称*/
    @NotEmpty(message="类型名称不能为空")
    private String breakTypeName;
    public String getBreakTypeName() {
        return breakTypeName;
    }
    public void setBreakTypeName(String breakTypeName) {
        this.breakTypeName = breakTypeName;
    }

    @JsonIgnore
    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBreakType=new JSONObject(); 
		jsonBreakType.accumulate("breakTypeId", this.getBreakTypeId());
		jsonBreakType.accumulate("breakTypeName", this.getBreakTypeName());
		return jsonBreakType;
    }

    @Override
	public String toString() {
		return JsonUtils.toJson(this);
	}
}