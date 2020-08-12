package com.woqiyounai.rtmp.request;

import java.io.Serializable;
import java.util.List;

public class PullStreamRequest implements Serializable {
    private List<String> markList;
    public PullStreamRequest() {
    }

    public PullStreamRequest(List<String> markList) {
        this.markList = markList;
    }

    public List<String> getMarkList() {
        return markList;
    }

    public void setMarkList(List<String> markList) {
        this.markList = markList;
    }
}
