package com.woqiyounai.rtmp.bean;

import java.io.Serializable;

public class ResultUrl implements Serializable {
    private String rtmpUrl;
    private String flvUrl;
    private String m3u8Url;

    public ResultUrl() {
    }

    public ResultUrl(String rtmpUrl, String flvUrl, String m3u8Url) {
        this.rtmpUrl = rtmpUrl;
        this.flvUrl = flvUrl;
        this.m3u8Url = m3u8Url;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getFlvUrl() {
        return flvUrl;
    }

    public void setFlvUrl(String flvUrl) {
        this.flvUrl = flvUrl;
    }

    public String getM3u8Url() {
        return m3u8Url;
    }

    public void setM3u8Url(String m3u8Url) {
        this.m3u8Url = m3u8Url;
    }

    @Override
    public String toString() {
        return "ResultUrl{" +
                "rtmpUrl='" + rtmpUrl + '\'' +
                ", flvUrl='" + flvUrl + '\'' +
                ", m3u8Url='" + m3u8Url + '\'' +
                '}';
    }
}
