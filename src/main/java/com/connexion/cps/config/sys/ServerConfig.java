package com.connexion.cps.config.sys;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * server configuration
 * 
 * 
 *
 */
@Component
public class ServerConfig
{
    /**
     * get whole url
     * 
     * @return server url
     */
    public String getUrl()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}
