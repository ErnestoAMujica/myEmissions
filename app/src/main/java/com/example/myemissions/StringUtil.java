package com.example.myemissions;

public class StringUtil {
    public static String getBetween(String source, String start, String end){
        if(source.contains(start) && source.contains(end) && (source.indexOf(start) < source.lastIndexOf(end))){
            int endOfStart = source.indexOf(start) + start.length();
            int startOfEnd = source.substring(endOfStart, source.length()).indexOf(end) + endOfStart;
            return source.substring(endOfStart, startOfEnd);
        }
        else
            return "";
    }
}
