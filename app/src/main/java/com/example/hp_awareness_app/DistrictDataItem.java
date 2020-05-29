package com.example.hp_awareness_app;

import com.google.gson.annotations.SerializedName;

public class DistrictDataItem{

	@SerializedName("recovered")
	private Object recovered;

	@SerializedName("zone")
	private String zone;

	@SerializedName("oldDeaths")
	private Object oldDeaths;

	@SerializedName("name")
	private String name;

	@SerializedName("oldRecovered")
	private Object oldRecovered;

	@SerializedName("id")
	private String id;

	@SerializedName("state")
	private Object state;

	@SerializedName("confirmed")
	private int confirmed;

	@SerializedName("deaths")
	private Object deaths;

	@SerializedName("oldConfirmed")
	private int oldConfirmed;

	public void setRecovered(Object recovered){
		this.recovered = recovered;
	}

	public Object getRecovered(){
		return recovered;
	}

	public void setZone(String zone){
		this.zone = zone;
	}

	public String getZone(){
		return zone;
	}

	public void setOldDeaths(Object oldDeaths){
		this.oldDeaths = oldDeaths;
	}

	public Object getOldDeaths(){
		return oldDeaths;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOldRecovered(Object oldRecovered){
		this.oldRecovered = oldRecovered;
	}

	public Object getOldRecovered(){
		return oldRecovered;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setState(Object state){
		this.state = state;
	}

	public Object getState(){
		return state;
	}

	public void setConfirmed(int confirmed){
		this.confirmed = confirmed;
	}

	public int getConfirmed(){
		return confirmed;
	}

	public void setDeaths(Object deaths){
		this.deaths = deaths;
	}

	public Object getDeaths(){
		return deaths;
	}

	public void setOldConfirmed(int oldConfirmed){
		this.oldConfirmed = oldConfirmed;
	}

	public int getOldConfirmed(){
		return oldConfirmed;
	}
}