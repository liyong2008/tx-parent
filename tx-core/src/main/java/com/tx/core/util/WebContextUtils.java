/*
 * 描          述:  <描述>
 * 修  改   人:  brady
 * 修改时间:  2013-9-22
 * <修改描述:>
 */
package com.tx.core.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.springframework.boot.autoconfigure.security.SecurityProperties.Headers;
import org.springframework.http.HttpHeaders;

/**
 * web容器工具类<br/>
 * <功能详细描述>
 * 
 * @author  brady
 * @version  [版本号, 2013-9-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class WebContextUtils {
    
    /**
     * 是否ajax请求<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        //Header
        String requestType = request.getHeader("X-Requested-With");
        if (StringUtils.equalsIgnoreCase("XMLHttpRequest", requestType)) {
            return true;
        }
        
        //accept
        String accept = request.getHeader(HttpHeaders.ACCEPT);
        if (StringUtils.isEmpty(accept)) {
            accept = request.getHeader("accept");
        }
        if (accept != null && StringUtils.indexOfIgnoreCase(accept,
                "application/json") != -1) {
            return true;
        }
        
        String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);
        if (StringUtils.isEmpty(contentType)) {
            contentType = request.getHeader("content-type");
        }
        if (contentType != null && StringUtils.indexOfIgnoreCase(contentType,
                "application/json") != -1) {
            return true;
        }
        
        //String uri = request.getRequestURI();
        //if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
        //    return true;
        //}
        //String ajax = request.getParameter("__ajax");
        //if (StringUtils.inStringIgnoreCase(ajax, "json", "xml")) {
        //    return true;
        //}
        
        return false;
    }
    
    /**
      * 根据request获取请求客户端ip地址<br/>
      *<功能详细描述>
      * @param request
      * @return [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = getClientIpAddress(request);
        return ip;
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            ip = StringUtils.splitByWholeSeparator(ip, ",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getForwardedIpAddress(HttpServletRequest request) {
        String forwardedIpAddress = request.getHeader("x-forwarded-for");
        if (forwardedIpAddress == null || forwardedIpAddress.length() == 0
                || "unknown".equalsIgnoreCase(forwardedIpAddress)) {
            forwardedIpAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        //如果forwaredIpAddress长度超过512，则截断
        if (forwardedIpAddress != null && forwardedIpAddress.length() > 512) {
            forwardedIpAddress = forwardedIpAddress.substring(0, 512);
        }
        return forwardedIpAddress;
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getRealIpAddress(HttpServletRequest request) {
        String realIpAddress = request.getHeader("X-Real-IP");
        return realIpAddress;
    }
    
    /**
     * 根据request获取请求客户端ip地址<br/>
     * <功能详细描述>
     * @param request
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getRemoteIpAddress(HttpServletRequest request) {
        String remoteIpAddress = request.getRemoteAddr();
        return remoteIpAddress;
    }
}
