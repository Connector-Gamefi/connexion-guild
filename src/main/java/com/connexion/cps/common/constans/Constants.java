package com.connexion.cps.common.constans;

/**
 * contants
 * 
 * 
 */
public class Constants
{
    /**
     * UTF-8
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK
     */
    public static final String GBK = "GBK";

    /**
     * http
     */
    public static final String HTTP = "http://";

    /**
     * https
     */
    public static final String HTTPS = "https://";

    /**
     * success
     */
    public static final Integer SUCCESS = 200;

    /**
     * fail
     */
    public static final Integer FAIL = 500;

    public static final String LOGIN_SUCCESS_STATE = "1";

    public static final String LOGIN_FAIL_STATE ="0";

    /**
     * success
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * logout
     */
    public static final String LOGOUT = "Logout";

    /**
     * register
     */
    public static final String REGISTER = "Register";

    /**
     * login fail
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * param manger cache name
     */
    public static final String SYS_CONFIG_CACHE = "sys-config";

    /**
     * param manger cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * dict manager cache name
     */
    public static final String SYS_DICT_CACHE = "sys-dict";

    /**
     * dict manager cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * resouce prefix
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /** golbal config */
    public static final String GLOBAL_CONFIG = "ug.app.global.config";
    /** app start time */
    public static final String APP_START_TIME = "ug.app.startTime";
    /** sdk url */
    public static final String GLOBAL_BASE_URL = "p_base_url";

    /**
     * header token
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * chain stat num
     */
    public static final int CHAIN_STAT_NUM = 24;

    /**
     * default page
     */
    public static final int STAT_DEFAULT_PAGE = 1;

    /**
     * response code
     */
    public final class RespCode {

        /**
         * server exception
         */
        public static final int EXCEPTION = -1;

        /**
         * success
         */
        public static final int SUCCESS = 0;

        /**
         * fail
         */
        public static final int FAILURE = 1;

        /**
         * login fail
         *
         */
        public static final int RELOGIN = 2;

        /**
         * no auth
         */
        public static final int NOPERMISSION = 3;


    }
}