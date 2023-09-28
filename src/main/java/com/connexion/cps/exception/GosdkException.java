package com.connexion.cps.exception;

/**
 * param invalid exception
 */
public class GosdkException extends RuntimeException{

    public GosdkException(String msg) {
        super(msg);
    }

    public GosdkException(String format, Object...params) {
        super(String.format(format, params));
    }

}
