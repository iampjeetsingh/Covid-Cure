package com.example.hp_awareness_app;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ModelCovidUpdate{

	@SerializedName("rchanges")
	private int rchanges;

	@SerializedName("districtData")
	private List<DistrictDataItem> districtData;

	@SerializedName("dChanges")
	private int dChanges;

	@SerializedName("active")
	private int active;

	@SerializedName("confirmed")
	private int confirmed;

	@SerializedName("recovered")
	private int recovered;

	@SerializedName("aChanges")
	private int aChanges;

	@SerializedName("cChanges")
	private int cChanges;

	@SerializedName("rChanges")
	private int rChanges;

	@SerializedName("id")
	private String id;

	@SerializedName("state")
	private String state;

	@SerializedName("deaths")
	private int deaths;

	@SerializedName("achanges")
	private int achanges;

	@SerializedName("dchanges")
	private int dchanges;

	@SerializedName("cchanges")
	private int cchanges;

	public void setRchanges(int rchanges){
		this.rchanges = rchanges;
	}

	public int getRchanges(){
		return rchanges;
	}

	public void setDistrictData(List<DistrictDataItem> districtData){
		this.districtData = districtData;
	}

	public List<DistrictDataItem> getDistrictData(){
		return districtData;
	}

	public void setDChanges(int dChanges){
		this.dChanges = dChanges;
	}

	public int getDChanges(){
		return dChanges;
	}

	public void setActive(int active){
		this.active = active;
	}

	public int getActive(){
		return active;
	}

	public void setConfirmed(int confirmed){
		this.confirmed = confirmed;
	}

	public int getConfirmed(){
		return confirmed;
	}

	public void setRecovered(int recovered){
		this.recovered = recovered;
	}

	public int getRecovered(){
		return recovered;
	}

	public void setAChanges(int aChanges){
		this.aChanges = aChanges;
	}

	public int getAChanges(){
		return aChanges;
	}

	public void setCChanges(int cChanges){
		this.cChanges = cChanges;
	}

	public int getCChanges(){
		return cChanges;
	}

	public void setRChanges(int rChanges){
		this.rChanges = rChanges;
	}

	public int getRChanges(){
		return rChanges;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setDeaths(int deaths){
		this.deaths = deaths;
	}

	public int getDeaths(){
		return deaths;
	}

	public void setAchanges(int achanges){
		this.achanges = achanges;
	}

	public int getAchanges(){
		return achanges;
	}

	public void setDchanges(int dchanges){
		this.dchanges = dchanges;
	}

	public int getDchanges(){
		return dchanges;
	}

	public void setCchanges(int cchanges){
		this.cchanges = cchanges;
	}

	public int getCchanges(){
		return cchanges;
	}
}