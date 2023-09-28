package com.connexion.cps.utils.poi;

/**
 * ExcelData format processing adapter
 * 
 * 
 */
public interface ExcelHandlerAdapter
{
    /**
     * format
     * 
     * @param value
     * @param args
     *
     * @return object
     */
    Object format(Object value, String[] args);
}
