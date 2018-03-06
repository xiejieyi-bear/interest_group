package com.interest.auth.service;

import com.interest.auth.Constant;
import com.interest.auth.bean.ResultBean;
import com.interest.auth.dao.UserRepository;
import com.interest.auth.daobean.User;
import com.interest.auth.util.HGException;
import com.interest.auth.util.Security;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *
 * @author
 */
@Component
public class UserService implements  IUserService
{
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String name) throws HGException
    {
        if (name == null || name.isEmpty())
        {
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "name is empty");
        }
        //TODO 是否需要对name进行格式化操作
        User user = userRepository.findByUsername(name);
        if (user == null)
        {
            throw new HGException(Constant.RET_CODE_USER_PWD_ERROR, "can not find user");
        }
        return user;
    }

    public void validateAuthenticate(String pwd, User user) throws HGException{
        if (pwd == null || pwd.isEmpty())
        {
            throw new HGException(Constant.RET_CODE_INPUT_ILLEGAL, "pwd is empty");
        }

        String salt = user.getSalt();
        String pwdInDb = user.getEncrypt();
        String inpupEncrypt = Security.encrypt(pwd, salt);

        logger.info("inpupEncrypt =" + inpupEncrypt);
        if(inpupEncrypt==null || inpupEncrypt.isEmpty() || !inpupEncrypt.equals(pwdInDb))
        {
            throw new HGException(Constant.RET_CODE_USER_PWD_ERROR,"password is error");
        }
        //logger.info("user =" + name +" validate success.");
        return ;
    }
}
