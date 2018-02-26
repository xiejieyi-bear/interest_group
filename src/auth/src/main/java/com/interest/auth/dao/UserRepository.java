package com.interest.auth.dao;

import com.interest.auth.daobean.User;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;

/**
 * 类描述：
 *
 * @author
 */
public interface UserRepository extends CrudRepository<User, Long>
{
    /**
     *  根据用户名找用户信息
     */
    User findByUsername(String var1);
}