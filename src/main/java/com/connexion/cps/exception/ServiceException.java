package com.connexion.cps.exception;

/**
 * business exception
 * 
 * 
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * error msg
     */
    private String message;

    /**
     * Error details, internal debugging errors
     *
     *
     */
    private String detailMessage;

    /**
     * contructor
     */
    public ServiceException()
    {
    }

    public ServiceException(String message)
    {
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }

    public String getMessage()
    {
        return message;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}