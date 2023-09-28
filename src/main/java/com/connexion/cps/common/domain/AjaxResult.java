package com.connexion.cps.common.domain;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;

/**
 * return result
 *
 * 
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    public static final String CODE_TAG = "code";

    /**
     * msg
     */
    public static final String MSG_TAG = "msg";

    /**
     * Object
     */
    public static final String DATA_TAG = "data";

    /**
     * total
     */
    public static final String TOTAL_TAG = "total";

    /**
     * status type
     */
    public enum Type {
        /**
         * success
         */
        SUCCESS(0),
        /**
         * warn
         */
        WARN(301),
        /**
         * error
         */
        ERROR(500),
        /**
         * no auth
         */
        NO_AUTH(401),

        //go sdk error
        GO_SDK_ERROR(10),

        GAME_OPEN_API_ERROR(11),
        ;

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    /**
     * Initializes a newly created AjaxResult object to represent an empty message.
     */
    public AjaxResult() {
    }

    /**
     * Initializes a newly created AjaxResult object to represent an empty message.
     *
     * @param type
     * @param msg
     */
    public AjaxResult(Type type, String msg) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }

    /**
     * Initializes a newly created AjaxResult object to represent an empty message.
     *
     * @param type
     * @param msg
     * @param data
     */
    public AjaxResult(Type type, String msg, Object data) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            if (data instanceof Page){
                Page page = (Page) data;
                super.put(TOTAL_TAG,page.getTotal());
                super.put(DATA_TAG, page.getRecords());
            }else {
                super.put(DATA_TAG, data);
            }
        }
    }

    public AjaxResult(Type type, String msg, Object data, Object total) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
        if (StringUtils.isNotNull(total)) {
            super.put(TOTAL_TAG, total);
        }
    }

    public AjaxResult(Type type, String msg, HashMap<String, Object> pageData) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (MapUtils.isNotEmpty(pageData)){
            if (MapUtils.isNotEmpty(pageData)){
                super.put(DATA_TAG, pageData.getOrDefault("records", null));
            }
            if (StringUtils.isNotNull(pageData.getOrDefault("total", null))) {
                super.put(TOTAL_TAG, pageData.get("total"));
            }
        }
    }

    /**
     * Method chain call
     *
     * @param key   
     * @param value 
     * @return 
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * success
     *
     * @return 
     */
    public static AjaxResult success() {
        return AjaxResult.success("success");
    }

    /**
     * success
     *
     * @return 
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("success", data);
    }

    public static AjaxResult goSdkPageSuccess(HashMap<String, Object> data) {
        return new AjaxResult(Type.SUCCESS, "success", data);
    }

    /**
     * success
     *
     * @param msg 
     * @return 
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /**
     * success
     *
     * @param msg  
     * @param data 
     * @return 
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(Type.SUCCESS, msg, data);
    }

    /**
     * warn
     *
     * @param msg
     * @return
     */
    public static AjaxResult warn(String msg) {
        return AjaxResult.warn(msg, null);
    }

    /**
     * warn
     *
     * @param msg
     * @param data 
     * @return
     */
    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(Type.WARN, msg, data);
    }

    /**
     * error
     *
     * @return
     */
    public static AjaxResult error() {
        return AjaxResult.error("operation failed");
    }

    /**
     * error
     *
     * @param msg
     * @return
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /**
     * error
     *
     * @param msg
     * @param data 
     * @return
     */
    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(Type.ERROR, msg, data);
    }

    /**
     * error
     *
     * @param msg
     * @return
     */
    public static AjaxResult noAuth(String msg) {
        return new AjaxResult(Type.NO_AUTH, msg, null);
    }
}
