package com.connexion.cps.exception;

/**
 * invalid exception
 */
public class GameApiException extends RuntimeException{

    public GameApiException(String msg) {
        super(msg);
    }

    public GameApiException(String format, Object...params) {
        super(String.format(format, params));
    }

}
