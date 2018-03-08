package com.interest.auth.dao;

import com.interest.auth.daobean.UserFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 类描述：场地管理Repository
 *
 * @author
 */
public interface UserFinanceRepository extends JpaRepository<UserFinance, Long>
{
    String CALC_RECHARGE_TOTAL="SELECT SUM(amount) FROM  user_finance where user_finance.type=0 and " +
            "user_finance.username=:username";
    String CALC_EXPENDITURE_TOTAL="SELECT SUM(amount) FROM  user_finance where user_finance.type=0 and " +
            "user_finance.username=:username";

    String CALC_BALANCE="SELECT SUM(amount) FROM  user_finance where  user_finance.username=:username";

    Iterable<UserFinance> findAllByUsernameAndType(String username,Integer type);

    @Query(value = CALC_RECHARGE_TOTAL, nativeQuery = true)
    Integer getREChargeTotalByUsername(@Param("username") String usename);

    @Query(value = CALC_EXPENDITURE_TOTAL, nativeQuery = true)
    Integer getExpenditureTotalByUsername(@Param("username") String usename);

    @Query(value = CALC_BALANCE, nativeQuery = true)
    Integer getBalanceByUsername(@Param("username") String usename);


}
