package com.example.hp_awareness_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HimachalPradesh {

	@SerializedName("districtData")
	@Expose
	private DistrictData districtData;
	@SerializedName("statecode")
	@Expose
	private String statecode;

	public DistrictData getDistrictData() {
		return districtData;
	}

	public void setDistrictData(DistrictData districtData) {
		this.districtData = districtData;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

}
