package com.megacoffee.utils;

import jakarta.servlet.http.HttpServletRequest;

public class HttpServletUtil {
    
    public static String getOS(String userAgent){
        if(userAgent == null || userAgent.isBlank()) {
            return "Unknown OS";
        }

        String value = userAgent.toLowerCase();
        if(value.contains("windows")){
            return "Windows";
        } else if(value.contains("macintosh")){
            return "Mac";
        } else if(value.contains("linux")){
            return "Linux";
        }
        return value;
    }

    public static String getMacAddress(HttpServletRequest request) {
        String mac = request.getHeader("X-MAC-Address");
        if (mac != null && !mac.isBlank()) {
            return mac;
        }
        mac = request.getHeader("X-Device-MAC");
        if (mac != null && !mac.isBlank()) {
            return mac;
        }
        return "Unknown MAC";
    }

    public static String getClientIP(HttpServletRequest request) {
        if(request == null) {
            return "Unknown IP";
        }  

        String forwarded = request.getHeader("X-Forwarded-For");
        if(forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        String ip = request.getRemoteAddr();
        if(ip != null && !ip.isBlank()) {
            return ip;
        }
        return "Unknown IP";
    }

    public static String getSessionID(HttpServletRequest request) {
        if(request == null) {
            return "Unknown Session ID";
        }

        String sessionId = request.getRequestedSessionId();
        if(sessionId != null && !sessionId.isBlank()) {
            return sessionId;
        }
        if(request.getSession(false) != null) {
            return request.getSession(false).getId();
        }
        return "Unknown Session ID";
    }

    public static String getHeaderDetails(HttpServletRequest request) {
        if(request == null) {
            return "No Headers";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("User-Agent: ").append(request.getHeader("User-Agent")).append("\n");
        builder.append("X-MAC-Address: ").append(request.getHeader("X-MAC-Address")).append("\n");
        builder.append("X-Device-MAC: ").append(request.getHeader("X-Device-MAC")).append("\n");
        builder.append("X-Forwarded-For: ").append(request.getHeader("X-Forwarded-For")).append("\n");
        builder.append("Referer: ").append(request.getHeader("Referer")).append("\n");
        return builder.toString();
    }
    
}
