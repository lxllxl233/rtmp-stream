package com.woqiyounai.rtmp.core;

import com.woqiyounai.rtmp.bean.Aircraft;
import com.woqiyounai.rtmp.cmd.CmdExec;
import com.woqiyounai.rtmp.response.data.PullStreamData;
import com.woqiyounai.rtmp.response.data.StopStreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//飞行器总控制对象
@Component
public class AircraftCenter {

    @Value("${base.aircraft.rtsp.suffix}")
    private String rtspSuffix;
    @Value("${base.aircraft.rtmp.suffix}")
    private String rtmpSuffix;
    @Value("${base.aircraft.rtsp.port}")
    private String rtspPort;
    @Value("${base.aircraft.rtmp.port}")
    private String rtmpPort;
    @Value("${base.aircraft.rtmp.ip}")
    private String rtmpIp;

    @Autowired
    private CmdExec cmdExec;

    private Map<String,Aircraft> aircraftMap = new ConcurrentHashMap<>();
    private Map<String,String> ipToMark = new ConcurrentHashMap<>();
    //添加飞行器
    public void addAircraft(String ip,String name,String mark){
        Aircraft aircraft = new Aircraft();
        aircraft.setIp(ip);
        aircraft.setName(name);
        aircraft.setMark(mark);
        String rtspUrl = "rtsp://"+ip+":"+rtspPort+rtspSuffix;
        aircraft.setRtspUrl(rtspUrl);
        String rtmpUrl = "rtmp://"+rtmpIp+":"+rtmpPort+rtmpSuffix+"-"+mark;
        aircraft.setRtmpUrl(rtmpUrl);
        aircraftMap.put(mark,aircraft);
    }

    //移除飞行器
    public void removwAircraft(String mark){
        Aircraft remove = aircraftMap.remove(mark);
        ipToMark.remove(remove.getIp());
    }

    //发现飞行器
    public void discovery(String baseIp){
        List<String> ipList = cmdExec.getIpList(baseIp);
        for (String ip : ipList) {
            //创建飞行器
            //设置标示
            String mark = UUID.randomUUID().toString().substring(0,8);
            String name = UUID.randomUUID().toString().substring(0,6);
            String baseMark = ipToMark.get(ip);
            if (null == baseMark){
                ipToMark.put(ip,mark);
                addAircraft(ip,name,mark);
            }
        }
    }

    //与总控中心通信

    //根据列表推流
    public List<PullStreamData> pushStream(List<String> markList){
        List<PullStreamData> list = new ArrayList<>();
        for (String mark : markList) {
            Aircraft aircraft = aircraftMap.get(mark);
            if (null != aircraft){
                String id = cmdExec.execPushStream(aircraft.getRtspUrl(), aircraft.getRtmpUrl());
                list.add(new PullStreamData(id, aircraft.getMark(), aircraft.getRtmpUrl()));
            }else {
                list.add(new PullStreamData(null, mark, null));
            }
        }
        return list;
    }

    //停止推流
    public List<StopStreamData> stopStream(List<String> idList){
        List<StopStreamData> list = new ArrayList<>();
        for (String id : idList) {
            cmdExec.execStopStream(id);
            boolean pullingStream = cmdExec.isPullingStream(id);
            list.add(new StopStreamData(id, "成功", pullingStream));
        }
        return list;
    }
}