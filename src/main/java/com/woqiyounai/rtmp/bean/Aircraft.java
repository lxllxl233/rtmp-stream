package com.woqiyounai.rtmp.bean;

import java.io.Serializable;

public class Aircraft implements Serializable {
    private String name;
    private String ip;
    private String id;
    private String mark;
    private String rtspUrl;
    private String rtmpUrl;
    private ResultUrl resultUrl;

    public Aircraft() {
    }

    public Aircraft(String name, String ip, String mark, String rtspUrl, String rtmpUrl, ResultUrl resultUrl) {
        this.name = name;
        this.ip = ip;
        this.mark = mark;
        this.rtspUrl = rtspUrl;
        this.rtmpUrl = rtmpUrl;
        this.resultUrl = resultUrl;
        this.id = null;
    }

    public Aircraft(String name, String ip, String id, String mark, String rtspUrl, String rtmpUrl, ResultUrl resultUrl) {
        this.name = name;
        this.ip = ip;
        this.id = id;
        this.mark = mark;
        this.rtspUrl = rtspUrl;
        this.rtmpUrl = rtmpUrl;
        this.resultUrl = resultUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRtspUrl() {
        return rtspUrl;
    }

    public void setRtspUrl(String rtspUrl) {
        this.rtspUrl = rtspUrl;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public ResultUrl getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(ResultUrl resultUrl) {
        this.resultUrl = resultUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", mark='" + mark + '\'' +
                ", rtspUrl='" + rtspUrl + '\'' +
                ", rtmpUrl='" + rtmpUrl + '\'' +
                ", resultUrl=" + resultUrl +
                '}';
    }
}
