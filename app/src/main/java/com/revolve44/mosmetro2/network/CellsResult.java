package com.revolve44.mosmetro2.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CellsResult {

    @SerializedName("global_id")
    @Expose
    private Integer globalId;
    @SerializedName("Number")
    @Expose
    private Integer number;
    @SerializedName("Cells")
    @Expose
    private Cells cells;

    public Integer getGlobalId() {
        return globalId;
    }

    public void setGlobalId(Integer globalId) {
        this.globalId = globalId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Cells getCells() {
        return cells;
    }

    public void setCells(Cells cells) {
        this.cells = cells;
    }

}