package com.connexion.cps.config.filter;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnyRolesAuthorizationFilter extends AuthorizationFilter {

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response){
        request.setAttribute("anyRolesAuthFilter.FILTERED", true);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Boolean afterFiltered = (Boolean)(servletRequest.getAttribute("anyRolesAuthFilter.FILTERED"));
        if(BooleanUtils.isTrue(afterFiltered))
            return true;

        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) o;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (String role : rolesArray) {
            if (subject.hasRole(role))
                return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
        return false;
    }
}
