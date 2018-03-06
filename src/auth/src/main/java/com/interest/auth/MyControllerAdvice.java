package com.interest.auth;

import com.interest.auth.bean.ResultBean;
import com.interest.auth.util.HGException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：定义全局的异常处理类
 *
 * @author
 */
@ControllerAdvice
public class MyControllerAdvice
{
    protected Log logger = LogFactory.getLog(getClass());

    //拦截@Validated校验抛出的异常
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean errorHandler(MethodArgumentNotValidException ex) {
        logger.error("validate basic input error ="  + ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResultBean(Constant.RET_CODE_INPUT_ILLEGAL,null);
    }

    /**
     * 拦截捕捉自定义异常 HGException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = HGException.class)
    public ResultBean myErrorHandler(HGException ex) {
        Integer retCode = ex.getRetCode();
        logger.error("validate error, reason=" + ex.getMessage());
        return new ResultBean(retCode,null);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultBean errorHandler(Exception ex) {
        logger.error("validate error " ,ex);
        return new ResultBean(Constant.RETCODE_SYSTEM_ERROR,null);
    }


}
