package com.revolve44.mosmetro2.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cells {

    @SerializedName("global_id")
    @Expose
    private Integer globalId;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Station")
    @Expose
    private String station;
    @SerializedName("Line")
    @Expose
    private String line;
    @SerializedName("AdmArea")
    @Expose
    private String admArea;
    @SerializedName("District")
    @Expose
    private String district;
    @SerializedName("ID")
    @Expose
    private Integer iD;

    public Integer getGlobalId() {
        return globalId;
    }

    public void setGlobalId(Integer globalId) {
        this.globalId = globalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getAdmArea() {
        return admArea;
    }

    public void setAdmArea(String admArea) {
        this.admArea = admArea;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

}

