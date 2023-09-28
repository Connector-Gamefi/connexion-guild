package com.connexion.cps.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.connexion.cps.common.domain.AjaxResult;
import com.connexion.cps.common.domain.page.TableDataInfo;
import com.connexion.cps.domain.sys.SysUser;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import static com.connexion.cps.common.domain.page.TableSupport.CURRENT_PAGE;
import static com.connexion.cps.common.domain.page.TableSupport.PAGE_SIZE;

/**
 * web
 * 
 * 
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * fomat
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date format
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * start page
     */
    protected void startPage()
    {
        PageUtils.startPage();
    }

    /**
     * Set request paging data
     */
    protected void startPage(Object object)
    {
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(object));
        PageUtils.startPage(jsonObject.getInteger(CURRENT_PAGE),jsonObject.getInteger(PAGE_SIZE));
    }

    /**
     * Set request paging data
     */
    protected void startPage(Integer currentPage,Integer pageSize)
    {
        PageUtils.startPage(currentPage,pageSize);
    }

    /**
     * request
     */
    public HttpServletRequest getRequest()
    {
        return ServletUtils.getRequest();
    }

    /**
     * response
     */
    public HttpServletResponse getResponse()
    {
        return ServletUtils.getResponse();
    }

    /**
     * session
     */
    public HttpSession getSession()
    {
        return getRequest().getSession();
    }

    /**
     * Response to request paginated data
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setData(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * response return result
     * 
     * @param rows
     * @return
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * response return result
     * 
     * @param result
     * @return
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * success
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * fail
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * success
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * success
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("success", data);
    }

    /**
     * fail
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * error msg
     */
    public AjaxResult error(AjaxResult.Type type, String message)
    {
        return new AjaxResult(type, message);
    }

    /**
     * redirect
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * user cache
     */
    public SysUser getSysUser()
    {
        return ShiroUtils.getSysUser();
    }

    /**
     * set user cache
     */
    public void setSysUser(SysUser user)
    {
        ShiroUtils.setSysUser(user);
    }

    /**
     * cache login user
     */
    public Long getUserId()
    {
        return getSysUser().getUserId();
    }

    /**
     * get login username
     */
    public String getLoginName()
    {
        return getSysUser().getLoginName();
    }

    public ChannelEntity getChannelUser()
    {
        return ShiroUtils.getChannelUser();
    }
}
