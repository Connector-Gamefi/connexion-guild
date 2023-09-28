package com.connexion.cps.exception;

/**
 * invalid param exception
 */
public class InvalidParamException extends RuntimeException{

    public InvalidParamException(String msg) {
        super(msg);
    }

    public InvalidParamException(String format, Object...params) {
        super(String.format(format, params));
    }

}
