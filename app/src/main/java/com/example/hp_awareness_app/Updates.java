package com.example.hp_awareness_app;

public class Updates {
    private String district;
    private int confirmed,confirmedDelta,recovered, recoveredDelta,deceased,deceasedDelta;

    public Updates() {
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getConfirmedDelta() {
        return confirmedDelta;
    }

    public void setConfirmedDelta(int confirmedDelta) {
        this.confirmedDelta = confirmedDelta;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getRecoveredDelta() {
        return recoveredDelta;
    }

    public void setRecoveredDelta(int recoveredDelta) {
        this.recoveredDelta = recoveredDelta;
    }

    public int getDeceased() {
        return deceased;
    }

    public void setDeceased(int deceased) {
        this.deceased = deceased;
    }

    public int getDeceasedDelta() {
        return deceasedDelta;
    }

    public void setDeceasedDelta(int deceasedDelta) {
        this.deceasedDelta = deceasedDelta;
    }
}
