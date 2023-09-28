package com.connexion.cps.config.aspectj;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.connexion.cps.config.annotation.Log;
import com.connexion.cps.config.factory.AsyncFactory;
import com.connexion.cps.config.factory.AsyncManager;
import com.connexion.cps.domain.sys.SysOperLog;
import com.connexion.cps.domain.sys.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * operate log
 * 
 * 
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /** exclude properties */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };

    @Pointcut("@annotation(com.connexion.cps.config.annotation.Log)")
    public void logPointCut()
    {
    }

    /**
     * Execute after processing the request
     *
     * @param joinPoint point
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult)
    {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * Intercept abnormal operations
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e)
    {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult)
    {
        try
        {
            // get current user
            SysUser currentUser = ShiroUtils.getSysUser();

            // *========db log=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // req address
            String ip = ShiroUtils.getIp();
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (currentUser != null)
            {
                operLog.setOperName(currentUser.getLoginName());
                if (StringUtils.isNotNull(currentUser.getDept())
                        && StringUtils.isNotEmpty(currentUser.getDept().getDeptName()))
                {
                    operLog.setDeptName(currentUser.getDept().getDeptName());
                }
            }

            if (e != null)
            {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // set method name
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // set req method
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            AsyncManager.me().execute(AsyncFactory.recordOper(operLog));
        }
        catch (Exception exp)
        {
            log.error("==Pre-notification error==");
            log.error("error info:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * Get the description information of the method in the annotation for Controller layer annotation
     * 
     * @param log
     * @param operLog
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, SysOperLog operLog, Object jsonResult) throws Exception
    {
        // set action
        operLog.setBusinessType(log.businessType().ordinal());
        // set title
        operLog.setTitle(log.title());
        // set operator
        operLog.setOperatorType(log.operatorType().ordinal());
        if (log.isSaveRequestData())
        {
            setRequestValue(joinPoint, operLog);
        }
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult))
        {
            operLog.setJsonResult(StringUtils.substring(JSONObject.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * Get the requested parameters and put them in the log
     * 
     * @param operLog
     * @throws Exception
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperLog operLog) throws Exception
    {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        if (StringUtils.isNotEmpty(map))
        {
            String params = JSONObject.toJSONString(map, excludePropertyPreFilter());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        }
        else
        {
            Object args = joinPoint.getArgs();
            if (StringUtils.isNotNull(args))
            {
                String params = argsArrayToString(joinPoint.getArgs());
                operLog.setOperParam(StringUtils.substring(params, 0, 2000));
            }
        }
    }

    /**
     * Ignore sensitive attributes
     */
    public PropertyPreFilters.MySimplePropertyPreFilter excludePropertyPreFilter()
    {
        return new PropertyPreFilters().addFilter().addExcludes(EXCLUDE_PROPERTIES);
    }

    /**
     * Parameter assembly
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (Object o : paramsArray)
            {
                if (StringUtils.isNotNull(o) && !isFilterObject(o))
                {
                    try
                    {
                        Object jsonObj = JSONObject.toJSONString(o, excludePropertyPreFilter());
                        params += jsonObj.toString() + " ";
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
        return params.trim();
    }

    /**
     * Determine whether filtering is required.
     * 
     * @param o 。
     * @return
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o)
    {
        Class<?> clazz = o.getClass();
        if (clazz.isArray())
        {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz))
        {
            Collection collection = (Collection) o;
            for (Object value : collection)
            {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz))
        {
            Map map = (Map) o;
            for (Object value : map.entrySet())
            {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
