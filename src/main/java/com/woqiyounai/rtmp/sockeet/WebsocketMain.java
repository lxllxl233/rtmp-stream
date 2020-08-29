package com.woqiyounai.rtmp.sockeet;

import com.alibaba.fastjson.JSON;
import com.woqiyounai.rtmp.bean.Aircraft;
import com.woqiyounai.rtmp.core.AircraftCenter;
import com.woqiyounai.rtmp.request.InfoRequest;
import com.woqiyounai.rtmp.response.ToClient;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Date;
import java.util.List;

public class WebsocketMain extends WebSocketClient {

    private AircraftCenter aircraftCenter;
    private String location;

    public WebsocketMain(URI serverUri,AircraftCenter aircraftCenter,String location) {
        super(serverUri);
        this.aircraftCenter = aircraftCenter;
        this.location = location;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("状态码 : "+serverHandshake.getHttpStatus());
    }

    @Override
    public void onMessage(String msg) {
        System.out.println(msg);
        ToClient toClient = JSON.parseObject(msg, ToClient.class);
        if (toClient.getType().equals("push")){
            System.out.println("开始推流");
            System.out.println(toClient.getList());
            List<Aircraft> list = aircraftCenter.pushStream(toClient.getList());
            this.send(JSON.toJSONString(new InfoRequest(new Date().getTime()+"","push",location,list)));
        }

        if (toClient.getType().equals("stop")){
            System.out.println("停止推流");
            System.out.println(toClient.getList());
            aircraftCenter.stopStream(toClient.getList());
            this.send(JSON.toJSONString(new InfoRequest(new Date().getTime()+"","stop",location,aircraftCenter.getAircraftList())));
        }

        if (toClient.getType().equals("response")){
            System.out.println("获取到了响应");
            System.out.println(msg);
            //this.send(JSON.toJSONString(new InfoRequest(new Date().getTime()+"","response",location,aircraftCenter.getAircraftList())));
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {
    }
}
