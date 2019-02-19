package com.tdpteam.web.helper;

import javax.servlet.http.HttpServletRequest;

public class HttpHelper {
    public static String getGoBackRedirect(HttpServletRequest request) {
        return "redirect:" + request.getHeader("Referer");
    }
}
