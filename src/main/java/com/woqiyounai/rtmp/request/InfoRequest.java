package com.woqiyounai.rtmp.request;

import com.woqiyounai.rtmp.bean.Aircraft;

import java.io.Serializable;
import java.util.List;

public class InfoRequest implements Serializable {

    private String id;
    private String type;
    private String location;
    private List<Aircraft> aircraftList;

    public InfoRequest() {
    }

    public InfoRequest(String type,String location, List<Aircraft> aircraftList) {
        this.type = type;
        this.location = location;
        this.aircraftList = aircraftList;
    }

    public InfoRequest(String id, String type, String location, List<Aircraft> aircraftList) {
        this.id = id;
        this.type = type;
        this.location = location;
        this.aircraftList = aircraftList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "InfoRequest{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", location='" + location + '\'' +
                ", aircraftList=" + aircraftList +
                '}';
    }
}
