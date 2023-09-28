package com.connexion.cps.config.annotation;

import com.connexion.cps.common.enums.BusinessType;
import com.connexion.cps.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * customer logs
 * 
 * 
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * tital
     */
    public String title() default "";

    /**
     * business
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * operate type
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * Whether to save requested parameters
     */
    public boolean isSaveRequestData() default true;

    /**
     * Whether to save response parameters
     */
    public boolean isSaveResponseData() default true;
}
