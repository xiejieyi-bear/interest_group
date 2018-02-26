package com.interest.auth.bean;

/**
 * 类描述：返回结果
 *
 * @author
 */

public class ResultBean {

    private final int retcode;
    private final Object data;

    public ResultBean(int retcode, Object data) {
        this.retcode = retcode;
        this.data = data;
    }

    public long getRetcode() {
        return retcode;
    }

    public Object getData() {
        return data;
    }

}
