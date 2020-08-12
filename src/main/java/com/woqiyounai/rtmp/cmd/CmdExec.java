package com.woqiyounai.rtmp.cmd;

import com.woqiyounai.rtmp.util.PatternUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class CmdExec {

    @Autowired
    private Runtime runtime;

    //管理运行中的 ffmpag 任务
    Map<String,Process> rtmpMap = new HashMap<>();

    //执行主机发现
    public List<String> getIpList(String baseIp) {
        List<String> ipList = new ArrayList<>();
        Process exec = null;
        try {
            exec = runtime.exec("nmap -sP "+baseIp+"/24");
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
        }
        return ipList;
    }
    //用来检查某个 ip 的某个端口是否可用
    public static boolean isCanUse(String ip,String port){
        //"ffmpeg -i rtsp://:8554/hello -vcodec copy -acodec copy -f flv rtmp://127.0.0.1:1935/stream/test"
        BufferedReader reader = null;
        Runtime runtime = Runtime.getRuntime();
        try {
            String id = null;
            String command = "telnet "+ip+" "+port;
            System.out.println(command);
            Process exec = runtime.exec(command);
            reader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            String str = null;
            while (null != (str= reader.readLine())){
                System.out.println(str);
                if (str.contains("Connected")){
                    exec.destroy();
                    return true;
                }
            }
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
        }
    }

    public static void main(String[] args) {
        System.out.println(
                isCanUse("192.168.3.55","5900")?"可以":"不可以"
        );

    }

    //运行推流
    public String execPushStream(String rtspUrl,String rtmpUrl){
        //"ffmpeg -i rtsp://:8554/hello -vcodec copy -acodec copy -f flv rtmp://127.0.0.1:1935/stream/test"
        try {
            String id = null;
            String command = "ffmpeg -i " + rtspUrl + " -vcodec copy -acodec copy -f flv " + rtmpUrl;
            System.out.println(command);
            Process exec = runtime.exec(command);
            if (null != exec){
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
    public void execStopStream(String id){
        Process process = rtmpMap.get(id);
        if (null != process){
            process.destroy();
        }
    }

    //查询是否正在运行推流
    public boolean isPullingStream(String id){
        Process process = rtmpMap.get(id);
        return process.isAlive();
    }

}

