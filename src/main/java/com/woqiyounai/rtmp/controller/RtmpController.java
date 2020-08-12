package com.woqiyounai.rtmp.controller;

import com.woqiyounai.rtmp.core.AircraftCenter;
import com.woqiyounai.rtmp.request.PullStreamRequest;
import com.woqiyounai.rtmp.request.StopStreamRequest;
import com.woqiyounai.rtmp.response.CommonResponse;
import com.woqiyounai.rtmp.response.data.PullStreamData;
import com.woqiyounai.rtmp.response.data.StopStreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RtmpController {
    //局域网飞行器总控中心
    @Autowired
    private AircraftCenter aircraftCenter;

    //接收请求，向腾讯云发起推流，返回推流地址
    @PostMapping("/api/stream/pull")
    public CommonResponse<List<PullStreamData>> pullStream(@RequestBody PullStreamRequest pullStreamRequest){
        List<String> markList = pullStreamRequest.getMarkList();
        List<PullStreamData> pullStreamData = aircraftCenter.pushStream(markList);
        return new CommonResponse<List<PullStreamData>>(200,"推流成功",pullStreamData);
    }

    //结束推流
    @PostMapping("/api/steam/stop")
    public CommonResponse<List<StopStreamData>> pullStream(@RequestBody StopStreamRequest stopStreamRequest){
        List<String> idList = stopStreamRequest.getIdList();
        List<StopStreamData> list = aircraftCenter.stopStream(idList);
        return new CommonResponse<>(200,"成功",list);
    }
}
