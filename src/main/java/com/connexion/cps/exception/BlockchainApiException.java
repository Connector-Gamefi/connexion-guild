package com.connexion.cps.exception;

/**
 * block exception
 */
public class BlockchainApiException extends RuntimeException{

    public BlockchainApiException(String msg) {
        super(msg);
    }

    public BlockchainApiException(String format, Object...params) {
        super(String.format(format, params));
    }

}
