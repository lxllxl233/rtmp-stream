package com.woqiyounai.rtmp.response.data;

import java.io.Serializable;

public class PullStreamData implements Serializable {
    private String id;
    private String mark;
    private String rtmpUrl;

    public PullStreamData( ) {
    }

    public PullStreamData(String id, String mark, String rtmpUrl) {
        this.id = id;
        this.mark = mark;
        this.rtmpUrl = rtmpUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    @Override
    public String toString() {
        return "PullStreamData{" +
                "id='" + id + '\'' +
                ", mark='" + mark + '\'' +
                ", rtmpUrl='" + rtmpUrl + '\'' +
                '}';
    }
}
