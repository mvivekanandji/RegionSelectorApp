package com.enggvam.countryselectorapp.data.remote.response;

import com.enggvam.countryselectorapp.data.db.entity.Region;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * <h1>POJO class representing the country backend response</h1>
 * <p>
 * Copyright 2021 Vivekanand Mishra.
 * All rights reserved.
 *
 * @author Vivekanand Mishra
 * @version 1.0
 */
public class RegionResponse extends BackendResponse{

    @SerializedName("result")
    List<Region> regionList;

    public RegionResponse(int code, List<Region> regionList) {
        super(code);
        this.regionList = regionList;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RegionResponse that = (RegionResponse) o;
        return Objects.equals(regionList, that.regionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), regionList);
    }

    @Override
    public String toString() {
        return "RegionResponse{" +
                "regionList=" + regionList +
                '}';
    }
}
