package com.example.hp_awareness_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictData {

	@SerializedName("Bilaspur")
	@Expose
	private Bilaspur bilaspur;
	@SerializedName("Chamba")
	@Expose
	private Chamba chamba;
	@SerializedName("Hamirpur")
	@Expose
	private Hamirpur hamirpur;
	@SerializedName("Kangra")
	@Expose
	private Kangra kangra;
	@SerializedName("Kinnaur")
	@Expose
	private Kinnaur kinnaur;
	@SerializedName("Kullu")
	@Expose
	private Kullu kullu;
	@SerializedName("Lahaul and Spiti")
	@Expose
	private LahaulAndSpiti lahaulAndSpiti;
	@SerializedName("Mandi")
	@Expose
	private Mandi mandi;
	@SerializedName("Shimla")
	@Expose
	private Shimla shimla;
	@SerializedName("Sirmaur")
	@Expose
	private Sirmaur sirmaur;
	@SerializedName("Solan")
	@Expose
	private Solan solan;
	@SerializedName("Una")
	@Expose
	private Una una;

	public Bilaspur getBilaspur() {
		return bilaspur;
	}

	public void setBilaspur(Bilaspur bilaspur) {
		this.bilaspur = bilaspur;
	}

	public Chamba getChamba() {
		return chamba;
	}

	public void setChamba(Chamba chamba) {
		this.chamba = chamba;
	}

	public Hamirpur getHamirpur() {
		return hamirpur;
	}

	public void setHamirpur(Hamirpur hamirpur) {
		this.hamirpur = hamirpur;
	}

	public Kangra getKangra() {
		return kangra;
	}

	public void setKangra(Kangra kangra) {
		this.kangra = kangra;
	}

	public Kinnaur getKinnaur() {
		return kinnaur;
	}

	public void setKinnaur(Kinnaur kinnaur) {
		this.kinnaur = kinnaur;
	}

	public Kullu getKullu() {
		return kullu;
	}

	public void setKullu(Kullu kullu) {
		this.kullu = kullu;
	}

	public LahaulAndSpiti getLahaulAndSpiti() {
		return lahaulAndSpiti;
	}

	public void setLahaulAndSpiti(LahaulAndSpiti lahaulAndSpiti) {
		this.lahaulAndSpiti = lahaulAndSpiti;
	}

	public Mandi getMandi() {
		return mandi;
	}

	public void setMandi(Mandi mandi) {
		this.mandi = mandi;
	}

	public Shimla getShimla() {
		return shimla;
	}

	public void setShimla(Shimla shimla) {
		this.shimla = shimla;
	}

	public Sirmaur getSirmaur() {
		return sirmaur;
	}

	public void setSirmaur(Sirmaur sirmaur) {
		this.sirmaur = sirmaur;
	}

	public Solan getSolan() {
		return solan;
	}

	public void setSolan(Solan solan) {
		this.solan = solan;
	}

	public Una getUna() {
		return una;
	}

	public void setUna(Una una) {
		this.una = una;
	}

}
