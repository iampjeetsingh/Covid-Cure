package com.example.hp_awareness_app;

import android.widget.Button;

public class DistrictCases {

    String districtName;

    Integer active , confrm, recvr , death;
    private  boolean expanded;

    public DistrictCases(String districtName, Integer active, Integer confrm, Integer recvr, Integer death) {
        this.districtName = districtName;

        this.active = active;
        this.confrm = confrm;
        this.recvr = recvr;
        this.death = death;
        this.expanded = false;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }


    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getConfrm() {
        return confrm;
    }

    public void setConfrm(Integer confrm) {
        this.confrm = confrm;
    }

    public Integer getRecvr() {
        return recvr;
    }

    public void setRecvr(Integer recvr) {
        this.recvr = recvr;
    }

    public Integer getDeath() {
        return death;
    }

    public void setDeath(Integer death) {
        this.death = death;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
