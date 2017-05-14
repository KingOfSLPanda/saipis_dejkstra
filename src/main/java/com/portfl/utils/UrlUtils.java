package com.portfl.utils;

import com.portfl.constants.Constants;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Vlad on 25.03.17.
 */
public class UrlUtils {

    public static String getAppUrl(WebRequest request) {
        HttpServletRequest httpServletRequest = ((ServletWebRequest) request).getRequest();
        String url = Constants.Common.EMPTY;
        url += httpServletRequest.getScheme();
        url += "://";
        url += httpServletRequest.getServerName();
        url += ":";
        url += httpServletRequest.getServerPort();
        if (!httpServletRequest.getContextPath().isEmpty()) {
            url += "/";
            url += httpServletRequest.getContextPath();
        }
        return url;
    }

}
