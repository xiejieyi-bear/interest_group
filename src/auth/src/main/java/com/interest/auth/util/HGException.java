package com.interest.auth.util;

/**
 * 类描述：
 *
 * @author
 */
public class HGException extends Exception
{
    private Integer retCode;

    public HGException(Integer retCode, String message){
        super(message);
        this.retCode = retCode;
    }

    @Override
    public String toString()
    {
        return super.toString() + " retCode = " + String.valueOf(retCode);
    }

    public Integer getRetCode()
    {
        return retCode;
    }

}
