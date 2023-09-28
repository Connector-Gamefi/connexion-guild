package com.connexion.cps.exception;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
