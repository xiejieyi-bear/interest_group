package com.interest.auth.dao;

import com.interest.auth.daobean.Court;
import com.interest.auth.daobean.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类描述：场地管理Repository
 *
 * @author
 */
public interface CourtRepository extends CrudRepository<Court, Long>
{

}
