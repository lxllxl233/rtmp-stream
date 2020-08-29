package com.woqiyounai.rtmp;

import com.alibaba.fastjson.JSON;
import com.woqiyounai.rtmp.bean.Aircraft;
import com.woqiyounai.rtmp.core.AircraftCenter;
import com.woqiyounai.rtmp.request.InfoRequest;
import com.woqiyounai.rtmp.sockeet.WebsocketMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RtmpMain {

    public static void main(String[] args) {
        //设置运行命令行的对象
        ConfigurableApplicationContext run = SpringApplication.run(RtmpMain.class, args);
        //初始化
        init(run);
        //启动 webSocket
        startSocket(run);
    }

    //初始化应用程序
    public static void init(ConfigurableApplicationContext run){
        String baseIp = run.getEnvironment().getProperty("base.ip");
        boolean usenmap = run.getEnvironment().getProperty("base.usenmap").equals("true");
        System.out.println("开始初始化");
        AircraftCenter aircraftCenter = run.getBean(AircraftCenter.class);
        aircraftCenter.discovery(baseIp,usenmap);
        //启动定时发现主机任务
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
            aircraftCenter.discovery(baseIp,usenmap);
            ExecutorService service = run.getBean(ExecutorService.class);
            System.out.println(service);
        },1,1, TimeUnit.MINUTES);
        System.out.println("初始化完成");

        //推自己的服务器
//        String s = CmdExec.execPushStream("rtsp://192.168.3.16:8554/hello", "rtmp://120.79.196.97:1935/stream/test");
//        System.out.println("====="+s+"============");
    }

    public static void startSocket(ConfigurableApplicationContext run){
        try {
            String location = run.getEnvironment().getProperty("base.location");
            String socketUrl = run.getEnvironment().getProperty("base.socket.url");
            AircraftCenter center = run.getBean(AircraftCenter.class);
            //120.79.196.97
            URI uri = new URI(socketUrl+location);
            WebsocketMain websocketMain = new WebsocketMain(uri,center,location);
            boolean open = false;
            open = websocketMain.connectBlocking();
            if (open){
                System.out.println("连接中心成功");
            }else {
                System.out.println("连接中心失败");
            }
            if (open){
                String timeStamp = new Date().getTime() + "";
                System.out.println("连接成功，当前时间戳："+timeStamp);
                String jstr = JSON.toJSONString(new InfoRequest(timeStamp, "info", location, center.getAircraftList()));
                websocketMain.send(jstr);
                System.out.println(jstr);
                //执行定时任务，每分钟更新设备信息
                Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
                    List<Aircraft> aircraftList = center.getAircraftList();
                    String str = JSON.toJSONString(new InfoRequest(new Date().getTime() + "", "info", location, aircraftList));
                    websocketMain.send(str);
                    System.out.println(str);
                },90,90, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}