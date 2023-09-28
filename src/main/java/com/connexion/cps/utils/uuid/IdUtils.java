package com.connexion.cps.utils.uuid;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.UUID;

/**
 * ID generator
 * 
 * 
 */
public class IdUtils
{
    /**
     * random UUID
     * 
     * @return random UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * simple uuid
     * 
     * @return
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * random uuid
     * 
     * @return random UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    public static String orderUUID(){
        DateTime dateTime = DateTime.now();
        return dateTime.year()+""+dateTime.monthBaseOne()+""+dateTime.dayOfMonth()+""+simpleUUID();
    }

    /**
     * simple uuid
     * 
     * @return
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }
}
