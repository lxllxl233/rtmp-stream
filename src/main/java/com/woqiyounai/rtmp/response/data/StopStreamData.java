package com.woqiyounai.rtmp.response.data;

import java.io.Serializable;

public class StopStreamData implements Serializable {
    private String id;
    private String msg;
    private boolean status;
    public StopStreamData() {
    }

    public StopStreamData(String id, String msg, boolean status) {
        this.id = id;
        this.msg = msg;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StopStreamData{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
