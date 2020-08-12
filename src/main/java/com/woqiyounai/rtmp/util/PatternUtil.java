package com.woqiyounai.rtmp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    public static String matcher(String pattern,String text){
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(text);
        if (matcher.find()){
            return matcher.group();
        }
        return null;
    }
}
