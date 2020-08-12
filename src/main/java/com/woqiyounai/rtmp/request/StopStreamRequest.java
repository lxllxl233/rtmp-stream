package com.woqiyounai.rtmp.request;

import java.io.Serializable;
import java.util.List;

public class StopStreamRequest implements Serializable {
    private List<String> idList;
    public StopStreamRequest() {
    }

    public StopStreamRequest(List<String> idList) {
        this.idList = idList;
    }

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }
}
