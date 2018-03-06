package com.interest.auth.dao;

import com.interest.auth.daobean.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类描述：
 *
 * @author
 */
public interface UserRepository extends CrudRepository<User, Long>
{
    //String findUsernameByUsermark="SELECT username FROM user where user.usermark= :usermark";
    /**
     *  根据用户名找用户信息
     */
    User findByUsername(String username);

    @Transactional
    Integer deleteByUsername(String username);

    //@Query(value = findUsernameByUsermark, nativeQuery = true)
    //String getUsernameByUsermark(@Param("usermark") String usermark);
}
