package com.connexion.cps.config.annotation;

import com.connexion.cps.common.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * customer datasources
 *
 *
 *
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource
{
    /**
     * exchange datasource
     */
    public DataSourceType value() default DataSourceType.MASTER;
}
