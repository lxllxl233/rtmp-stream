package com.woqiyounai.rtmp.response;

import java.io.Serializable;
import java.util.List;

public class ToClient implements Serializable {
    private String type;
    private String msg;
    private String location;
    private List<String> list;

    public ToClient() {
    }

    public ToClient(String type, String msg, String location, List<String> list) {
        this.type = type;
        this.msg = msg;
        this.location = location;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ToClient{" +
                "type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                ", location='" + location + '\'' +
                ", list=" + list +
                '}';
    }
}
