package com.woqiyounai.rtmp;

import com.woqiyounai.rtmp.core.AircraftCenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class RtmpMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RtmpMain.class, args);
        init(run);
    }

    //初始化应用程序
    public static void init(ConfigurableApplicationContext run){
        String baseIp = run.getEnvironment().getProperty("base.ip");
        System.out.println("开始初始化");
        AircraftCenter aircraftCenter = run.getBean(AircraftCenter.class);
        aircraftCenter.discovery(baseIp);
        String marlTest = UUID.randomUUID().toString().substring(0, 8);
        System.out.println("===========================测试地址为：("+marlTest+")=================================");
        aircraftCenter.addAircraft("127.0.0.1", UUID.randomUUID().toString().substring(0,6),marlTest);
        //启动定时发现主机任务
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
            aircraftCenter.discovery(baseIp);
        },1,1, TimeUnit.MINUTES);
        System.out.println("初始化完成");
    }
}









