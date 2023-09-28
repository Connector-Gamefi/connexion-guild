package com.connexion.cps.config.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ParamsFilter extends OncePerRequestFilter {

    private static final String SQL_REG_EXP = ".*(\\b(select|insert|into|update|delete|from|where|trancate" +
            "|drop|execute|grant|use|union)\\b).*";

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        CustomRequestWrapper requestWrapper = new CustomRequestWrapper(request);
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap = getParameterMap(parameterMap, request, requestWrapper);
        for (Object obj : parameterMap.entrySet()) {
            Map.Entry entry = (Map.Entry) obj;
            Object value = entry.getValue();
            if (value != null) {
                boolean isValid = isSqlInject(value.toString(), servletResponse);
                if (!isValid) {
                    return;
                }
            }
        }

        filterChain.doFilter(requestWrapper, servletResponse);
    }

    private Map<String, Object> getParameterMap(Map<String, Object> paramMap, HttpServletRequest request,CustomRequestWrapper customRequestWrapper) {
        // 1.POST get req
        if ("POST".equals(request.getMethod().toUpperCase())) {
            String body = customRequestWrapper.getBody();
            if(StringUtils.isNotEmpty(body)){
                boolean jsonType = getJSONType(body);
                if(jsonType==true){
                    paramMap = JSONObject.parseObject(body, HashMap.class);
                }else {
                    String[] split = body.split("&");
                    for (int i = 0; i < split.length; i++) {
                        String[] split1;
                        split1 = split[i].split("=");
                        paramMap.put(split1[0],split1[1]);
                        split1 = null;
                    }
                }
            }else {
                Map<String, String[]> parameterMap = customRequestWrapper.getParameterMap();
                if (parameterMap != null && parameterMap.size() > 0) {
                    Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
                    for (Map.Entry<String, String[]> next : entries) {
                        paramMap.put(next.getKey(), next.getValue()[0]);
                    }
                }
            }
        } else {
            Map<String, String[]> parameterMap = customRequestWrapper.getParameterMap();
            if (parameterMap != null && parameterMap.size() > 0) {
                Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
                for (Map.Entry<String, String[]> next : entries) {
                    paramMap.put(next.getKey(), next.getValue()[0]);
                }
            } else {
                String afterDecodeUrl = null;
                try {
                    afterDecodeUrl = URLDecoder.decode(request.getRequestURI(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                paramMap.put("pathVar", afterDecodeUrl);
            }
        }
        return paramMap;
    }
    private boolean isSqlInject(String value, ServletResponse servletResponse) throws IOException {
        if ((null != value && value.toLowerCase().matches(SQL_REG_EXP)) || value.contains("*") || value.contains("$") || value.contains("dataScope")) {
            log.info("valid params: " + value);
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("code", "999");
            responseMap.put("msg","input contains Illegal character");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(JSON.toJSONString(responseMap));
            response.getWriter().flush();
            response.getWriter().close();
            return false;
        }
        return true;
    }

    private boolean getJSONType(String str){
        boolean result = false;
        if (StringUtils.isNotBlank(str)) {
            str = str.trim();
            if (str.startsWith("{") && str.endsWith("}")) {
                result = true;
            } else if (str.startsWith("[") && str.endsWith("]")) {
                result = true;
            }
        }
        return result;
    }
}
