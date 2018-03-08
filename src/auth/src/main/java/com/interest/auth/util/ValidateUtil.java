package com.interest.auth.util;

import com.interest.auth.Constant;

import java.util.regex.Pattern;

/**
 * 类描述：
 *
 * @author
 */
public class ValidateUtil
{
    public static boolean isNum(String plain)
    {
        if (plain == null || plain.isEmpty())
        {
            return false;
        }
        return Pattern.matches("\\d+", plain);
    }

    public static void validateStringNull(String plain, String key) throws HGException
    {
        if (plain == null || plain.isEmpty())
        {
            if(key == null){
                key = "input";
            }
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, key + " is empty");
        }
    }
}
