package com.interest.auth.dao;

import com.interest.auth.daobean.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

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
    User findByUsername(String username);

    @Transactional
    Integer deleteByUsermark(String usermark);

}
