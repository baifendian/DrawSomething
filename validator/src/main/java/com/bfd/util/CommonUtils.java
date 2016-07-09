package com.bfd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CommonUtils {

    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static Date parseToDate(String str, String formats) {

        SimpleDateFormat format = new SimpleDateFormat(formats);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        return request;
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static void setSessionAttribute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(String key) {
        return getSession().getAttribute(key);
    }

    public static void removeSessionAttribute(String key) {
        Object sessionAttribute = getSessionAttribute(key);
        if (sessionAttribute != null) {
            getSession().removeAttribute(key);
        }
    }
}
