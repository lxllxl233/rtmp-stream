package com.woqiyounai.rtmp.cmd;

import com.woqiyounai.rtmp.util.PatternUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CmdExec {
    //管理运行中的 ffmpag 任务
    public static Map<String,Process> rtmpMap = new HashMap<>();

    //执行主机发现
    public static List<String> getIpList(String baseIp,String nmapPath) {
        Runtime runtime = Runtime.getRuntime();
        List<String> ipList = new ArrayList<>();
        Process exec = null;
        try {
            exec = runtime.exec(nmapPath+" -sP "+baseIp+"/24");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String read = null;
        //正则表达式匹配 ip 地址
        String pattern = "[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";
        //读取输入流
        try {
            String temp = null;
            while (null != (read=bufferedReader.readLine())){
                if(null != (temp=PatternUtil.matcher(pattern,read)) && (!read.contains("_gateway"))){
                    ipList.add(temp);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            exec.destroy();
        }
        return ipList;
    }
    //用来检查某个 ip 的某个端口是否可用
    public static Boolean isCanUse(String ip ,String rtspPort,String telnetPath){
        //"ffmpeg -i rtsp://:8554/hello -vcodec copy -acodec copy -f flv rtmp://127.0.0.1:1935/stream/test"
        Runtime runtime = Runtime.getRuntime();
        BufferedReader reader = null;
        String id = null;
        String command = telnetPath+" " + ip + " " + rtspPort;
        System.out.println(command);
        Process exec = null;
        try {
            exec = runtime.exec(command);
            reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String str = null;
            while (null != (str = reader.readLine())) {
                if (str.contains("Connected")) {
                    exec.destroy();
                    return true;
                }
            }
            exec.destroy();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally {
            if (null != reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            exec.destroy();
        }
    }

    //运行推流
    public static String execPushStream(String rtspUrl,String rtmpUrl,String ffmpegPath){
        //"ffmpeg -i rtsp://:8554/hello -vcodec copy -acodec copy -f flv rtmp://127.0.0.1:1935/stream/test"
        Runtime runtime = Runtime.getRuntime();
        try {
            String id = null;
            //ffmpeg -re -stream_loop -1 -i rtsp://192.168.3.16:8554/hello  -vcodec copy -acodec copy -f flv rtmp://109366.livepush.myqcloud.com/live/guilin7b77b1aa
            String command = ffmpegPath.replaceAll("#rtspUrl",rtspUrl).replaceAll("#rtmpUrl",rtmpUrl);
            System.out.println(command);
            Process exec = runtime.exec(command);
            if (null != exec){
                new Thread(()->{
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
                        String read = null;
                        while (null != (read = reader.readLine())){
                            reader.skip(read.getBytes().length);
                        }
                    }catch (Exception e){
                        System.out.println("摄像头连接关闭");
                    }finally {
                        if (null != reader){
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                id = UUID.randomUUID().toString().substring(0,8);
                rtmpMap.put(id,exec);
            }
            return id;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //停止推流
    public static void execStopStream(String id){
        Process process = rtmpMap.get(id);
        if (null != process){
            process.destroy();
        }
    }

    //查询是否正在运行推流
    public static boolean isPushingStream(String id){
        Process process = rtmpMap.get(id);
        if (null != process) {
            return process.isAlive();
        }
        return false;
    }

    public static List<String> getIpList(String baseIp) {
        List<String> ipList = new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        String read = null;
        String command = "fping -c1 -t50 192.168.3.";
        String pattern = "[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+";
        String temp = null;

        for (int i = 1; i < 255; i++) {
            long l = System.currentTimeMillis();
            try {
                Process exec = runtime.exec(command + i);
                InputStream inputStream = exec.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                while (null != (read = reader.readLine())) {
                    if (null != (temp = PatternUtil.matcher(pattern, read)) && (!read.contains("_gateway"))) {
                        ipList.add(temp);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ipList;
    }
}
