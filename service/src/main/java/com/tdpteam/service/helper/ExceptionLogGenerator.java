package com.tdpteam.service.helper;

public class ExceptionLogGenerator {
    public static String getRequestedUrlMessage(String url){
        return "Requested URL=" + url;
    }

    public static String getExceptionName(Exception ex){
        return "Exception Raised=" + ex;
    }
}
