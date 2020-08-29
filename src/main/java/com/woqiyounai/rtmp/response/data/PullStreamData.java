package com.woqiyounai.rtmp.response.data;

import com.woqiyounai.rtmp.bean.ResultUrl;

import java.io.Serializable;

public class PullStreamData implements Serializable {
    private String id;
    private String mark;
    private ResultUrl resultUrl;

    public PullStreamData() {
    }

    public PullStreamData(String id, String mark, ResultUrl resultUrl) {
        this.id = id;
        this.mark = mark;
        this.resultUrl = resultUrl;
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

    public ResultUrl getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(ResultUrl resultUrl) {
        this.resultUrl = resultUrl;
    }

    @Override
    public String toString() {
        return "PullStreamData{" +
                "id='" + id + '\'' +
                ", mark='" + mark + '\'' +
                ", resultUrl=" + resultUrl +
                '}';
    }
}
