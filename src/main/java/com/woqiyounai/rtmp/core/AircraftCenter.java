package com.woqiyounai.rtmp.core;

import com.woqiyounai.rtmp.bean.Aircraft;
import com.woqiyounai.rtmp.bean.ResultUrl;
import com.woqiyounai.rtmp.cmd.CmdExec;
import com.woqiyounai.rtmp.response.data.StopStreamData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

//飞行器总控制对象
@Component
public class AircraftCenter {

    @Value("${base.aircraft.rtsp.suffix}")
    private String rtspSuffix;
    @Value("${base.aircraft.rtsp.port}")
    private String rtspPort;

    @Value("${base.url.rtmp}")
    private String pushRtmpUrl;
    @Value("${base.location}")
    private String location;

    @Value("${base.url.pull}")
    private String basePullUrl;

    @Value("${base.nmap.path}")
    private String nmapPath;
    @Value("${base.telnet.path}")
    private String telnetPath;
    @Value("${base.ffmpeg.command}")
    private String ffmpegCommand;

    @Autowired
    private ExecutorService executorService;

    private Map<String,Aircraft> aircraftMap = new ConcurrentHashMap<>();
    private Map<String,String>    ipToMark   = new ConcurrentHashMap<>();

    //添加飞行器
    public void addAircraft(String ip,String name,String mark){
        String suffix = location +"-"+ mark;
        Aircraft aircraft = new Aircraft();
        aircraft.setIp(ip);
        aircraft.setName(name);
        aircraft.setMark(mark);
        String rtspUrl = "rtsp://"+ip+":"+rtspPort+rtspSuffix;
        aircraft.setRtspUrl(rtspUrl);
        String rtmpUrl = pushRtmpUrl + suffix;
        aircraft.setRtmpUrl(rtmpUrl);
        aircraft.setResultUrl(new ResultUrl(
                "rtmp://"+basePullUrl+suffix,
                "http://"+basePullUrl+suffix+".flv",
                "http://"+basePullUrl+suffix+".m3u8"
        ));
        ipToMark.put(ip, mark);
        aircraftMap.put(mark,aircraft);
    }

    //移除飞行器
    public void removwAircraft(String mark){
        Aircraft remove = aircraftMap.remove(mark);
        ipToMark.remove(remove.getIp());
    }

    //发现飞行器
    public void discovery(String baseIp,boolean useNmap){
        List<String> ipList = null;
        if (useNmap) {
            System.out.println("nmap 扫描");
            ipList = CmdExec.getIpList(baseIp, nmapPath);
        }else {
            System.out.println("fping 扫描");
            ipList = CmdExec.getIpList(baseIp);
        }

        for (String ip : ipList) {
            //检查是否可用
            boolean flag = false;
            Future<Boolean> submit = executorService.submit(() -> CmdExec.isCanUse(ip,rtspPort,telnetPath));
            try {
                flag = submit.get(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println("无设备");
            }
            if (flag) {
                //创建飞行器
                //设置标示
                String mark = UUID.randomUUID().toString().substring(0, 8);
                String name = UUID.randomUUID().toString().substring(0, 6);
                String baseMark = ipToMark.get(ip);
                if (null == baseMark) {
                    addAircraft(ip, name, mark);
                }
            }
        }
    }

    //与总控中心通信

    //根据列表推流
    public List<Aircraft> pushStream(List<String> markList){
        List<Aircraft> list = new ArrayList<>();
        for (String mark : markList) {
            Aircraft aircraft = aircraftMap.get(mark);
            if (null != aircraft){
                Future<String> submit = executorService.submit(() -> {
                    String idInTd = CmdExec.execPushStream(aircraft.getRtspUrl(), aircraft.getRtmpUrl(),ffmpegCommand);
                    return idInTd;
                });
                String id = null;
                try {
                    id = submit.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                aircraft.setId(id);
                list.add(aircraft);
            }else {
                list.add(aircraft);
            }
        }
        return list;
    }

    //停止推流
    public List<StopStreamData> stopStream(List<String> idList){
        List<StopStreamData> list = new ArrayList<>();
        for (String id : idList) {
            CmdExec.execStopStream(id);
            boolean pullingStream = CmdExec.isPushingStream(id);
            list.add(new StopStreamData(id, "成功", pullingStream));
        }
        return list;
    }

    //看某个运行中的推流进程是否在运行
    public boolean isAlive(String id){
        return CmdExec.isPushingStream(id);
    }

    public List<Aircraft> getAircraftList() {
        Collection<Aircraft> values = aircraftMap.values();
        return values.parallelStream().collect(Collectors.toList());
    }
}