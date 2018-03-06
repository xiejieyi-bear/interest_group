package com.interest.auth;

public interface Constant
{
    int SUCCESS = 0;

    //用户名或者密码错误
    int RET_CODE_USER_PWD_ERROR = 20001;

    // 数据库中无记录
    int RETCODE_NO_RECORD = 20002;

    //用户未加入活动
    int RETCODE_USR_NO_JOIN_ACTIVITY = 20003;

    // 非法输入
    int RET_CODE_INPUT_ILLEGAL = 10000;



    // 系统错误
    int RETCODE_SYSTEM_ERROR = 10001;

    int ROLE_TYPE_ADMIN = 0;
    int ROLE_TYPE_NORMAL = 1;

    enum ACTIVITY_STATE {ONGOING, END};
}


