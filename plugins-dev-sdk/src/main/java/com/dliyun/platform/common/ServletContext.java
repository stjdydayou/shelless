package com.dliyun.platform.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2015年09月24
 * <p>
 * Title: Servlet上下文
 * </p>
 * <p>
 * Description: 获取请求上下文的request,response等信息
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015
 * </p>
 *
 * @author jtoms
 * @version 1.0
 */
@Slf4j
public class ServletContext {

    private final static String[] PROXY_REMOTE_IP_ADDRESS = {"x-forwarded-for", "x-real-ip", "proxy-client-ip", "wl-proxy-client-ip"};


    private final static ThreadLocal<ServletContext> CONTROLLER_CONTEXT = new ThreadLocal<>();

    private final static String REQUEST = "____REQUEST____";
    private final static String RESPONSE = "____RESPONSE____";
    private final static String IS_MOBILE = "____IS_MOBILE____";
    private final static String REMOTE_IP_ADDRESS = "____REMOTE_IP_ADDRESS____";
    private final static String ACCESS_TOKEN = "____ACCESS_TOKEN____";

    private Map<String, Object> context;

    public ServletContext(Map<String, Object> context) {
        this.context = context;
    }

    /**
     * Created on 2015-09-24
     * <p>
     * Description:初始化 该方法在ControllerFilter中调用其他地方不能调用
     * </p>
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        CONTROLLER_CONTEXT.set(new ServletContext(new HashMap<String, Object>()));
        CONTROLLER_CONTEXT.get().getContext().put(REQUEST, request);
        CONTROLLER_CONTEXT.get().getContext().put(RESPONSE, response);
        CONTROLLER_CONTEXT.get().getContext().put(IS_MOBILE, isMobile(request));
        CONTROLLER_CONTEXT.get().getContext().put(REMOTE_IP_ADDRESS, getRemoteIPAddress(request));
        CONTROLLER_CONTEXT.get().getContext().put(ACCESS_TOKEN, getAccessToken(request, response));
    }

    public static void clean() {
        CONTROLLER_CONTEXT.set(null);
        CONTROLLER_CONTEXT.remove();
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) CONTROLLER_CONTEXT.get().getContext().get(REQUEST);
    }

    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) CONTROLLER_CONTEXT.get().getContext().get(RESPONSE);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(String key) {
        return (T) CONTROLLER_CONTEXT.get().getContext().get(key);
    }

    public static void set(String key, Object value) {
        CONTROLLER_CONTEXT.get().getContext().put(key, value);
    }

    public static boolean isMobile() {
        return getValue(IS_MOBILE);
    }

    public static String getRemoteIPAddress() {
        return getValue(REMOTE_IP_ADDRESS);
    }

    public static String getAccessToken() {
        return getValue(ACCESS_TOKEN);
    }

    public static Map<String, String> getRequestParamMap() {
        HttpServletRequest request = getRequest();
        Enumeration<String> parameterNames = request.getParameterNames();

        Map<String, String> dataMap = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = request.getParameter(key);
            dataMap.put(key, value);
        }
        return dataMap;
    }

    private static String getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String accessToken = "";
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }

        if (StringUtils.isBlank(accessToken) || !Token.checkToken(accessToken)) {
            accessToken = Token.init(ServletContext.getRemoteIPAddress()).toTokenString();

            Cookie cookie = new Cookie("accessToken", accessToken);
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return accessToken;
    }

    private static String getRemoteIPAddress(HttpServletRequest request) {
        for (int i = 0; i < PROXY_REMOTE_IP_ADDRESS.length; i++) {
            String ip = request.getHeader(PROXY_REMOTE_IP_ADDRESS[i]);
            if (StringUtils.isNotBlank(ip)) {
                return getRemoteIpFromForward(ip);
            }
        }
        return request.getRemoteHost();
    }

    /**
     * 判断访问来源是否为手机
     *
     * @param request
     * @return
     */
    private static Boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT").toLowerCase();
        if (StringUtils.isBlank(userAgent)) {
            userAgent = "";
        }

        Boolean isMobile = false;

        // 在通过正则表达式检测是否手机浏览器
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry"
                + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]"
                + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
        Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
        // 匹配
        Matcher matcherPhone = phonePat.matcher(userAgent);
        Matcher matcherTable = tablePat.matcher(userAgent);
        if (matcherPhone.find() || matcherTable.find()) {
            isMobile = true;
        }

        return isMobile;
    }


    /**
     * <p>
     * 从 HTTP Header 的 X-Forward-IP 头中截取客户端连接 IP 地址。如果经过多次反向代理， 在 X-Forward-IP
     * 中获得的是以“,&lt;SP&gt;”分隔 IP 地址链，第一段为客户端 IP 地址。
     * </p>
     *
     * @param xforwardIp
     * @return
     */
    private static String getRemoteIpFromForward(String xforwardIp) {
        int commaOffset = xforwardIp.indexOf(',');
        if (commaOffset < 0) {
            return xforwardIp;
        }
        return xforwardIp.substring(0, commaOffset);
    }

    private Map<String, Object> getContext() {
        return context;
    }

}
