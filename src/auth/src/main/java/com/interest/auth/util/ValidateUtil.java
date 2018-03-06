package com.interest.auth.util;

import java.util.regex.Pattern;

/**
 * 类描述：
 *
 * @author
 */
public class ValidateUtil
{
    public static boolean isNum(String plain){
        if(plain == null || plain.isEmpty()){
            return false;
        }
        return Pattern.matches("\\d+",plain);
    }
}
