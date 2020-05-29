package com.example.hp_awareness_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelCovidCase{


	@SerializedName("Himachal Pradesh")
	@Expose
	private HimachalPradesh himachalPradesh;

	public HimachalPradesh getHimachalPradesh() {
		return himachalPradesh;
	}

	public void setHimachalPradesh(HimachalPradesh himachalPradesh) {
		this.himachalPradesh = himachalPradesh;
	}

}
