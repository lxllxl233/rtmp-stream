package com.woqiyounai.rtmp.bean;

import java.io.Serializable;

public class Aircraft implements Serializable {
    private String name;
    private String ip;
    private String port;
    private String suffix;
    private String rtspUrl;
    private String rtmpUrl;
    private String mark;

    public Aircraft() {
    }

    public Aircraft(String name, String ip, String port, String suffix, String rtspUrl, String rtmpUrl, String mark) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.suffix = suffix;
        this.rtspUrl = rtspUrl;
        this.rtmpUrl = rtmpUrl;
        this.mark = mark;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", suffix='" + suffix + '\'' +
                ", rtspUrl='" + rtspUrl + '\'' +
                ", rtmpUrl='" + rtmpUrl + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
