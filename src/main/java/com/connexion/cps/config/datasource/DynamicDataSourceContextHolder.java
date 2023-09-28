package com.connexion.cps.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Data source switching processing
 * 
 * 
 */
public class DynamicDataSourceContextHolder
{
    public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * Set data source variables
     */
    public static void setDataSourceType(String dsType)
    {
        log.info("Switch to {} data source", dsType);
        CONTEXT_HOLDER.set(dsType);
    }

    /**
     * Get the variables of the data source
     */
    public static String getDataSourceType()
    {
        return CONTEXT_HOLDER.get();
    }

    /**
     * Clear data source variables
     */
    public static void clearDataSourceType()
    {
        CONTEXT_HOLDER.remove();
    }
}
