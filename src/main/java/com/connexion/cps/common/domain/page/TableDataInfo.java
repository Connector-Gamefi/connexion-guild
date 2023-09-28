package com.connexion.cps.common.domain.page;

import java.io.Serializable;
import java.util.List;

/**
 * page helpaer
 * 
 * 
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** total */
    private long total;

    /** list data */
    private List<?> data;

    /** message code */
    private int code;

    /** msg */
    private String msg;

    /**
     * table data info
     */
    public TableDataInfo()
    {
    }

    /**
     * page
     * 
     * @param list list data
     * @param total total record
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.data = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getData()
    {
        return data;
    }

    public void setData(List<?> data)
    {
        this.data = data;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}