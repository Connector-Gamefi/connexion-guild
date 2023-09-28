package com.connexion.cps.controller.common;

import com.connexion.cps.common.constans.Constants;

/**
 * Generic protocol response object
 * When returning to the client, serialize it into json format
 */
public class ResponseView {
    public int code;
    public String msg;

    public ResponseView failure(String reason) {
        return failure(Constants.RespCode.FAILURE, reason);
    }

    public ResponseView failure(int code, String reason) {
        this.code = code;
        this.msg = reason;

        return this;
    }

    public ResponseView success(){
        this.code = Constants.RespCode.SUCCESS;
        this.msg = "operation success";
        return this;
    }

    public static ResponseView buildFailure(String reason){

        ResponseView v = new ResponseView();
        v.failure(Constants.RespCode.FAILURE, reason);
        return v;

    }

    public static ResponseView buildSuccess(){
        ResponseView v = new ResponseView();
        v.code = Constants.RespCode.SUCCESS;
        v.msg = "operation success";
        return v;
    }
}
