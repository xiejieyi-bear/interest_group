package com.interest.auth.service;

import com.interest.auth.daobean.User;
import com.interest.auth.util.HGException;

/**
 * 类描述：
 *
 * @author
 */
public interface IUserService
{
    User getUserByUsername(String name) throws HGException;
    void validateAuthenticate(String pwd,User user) throws HGException;
}
