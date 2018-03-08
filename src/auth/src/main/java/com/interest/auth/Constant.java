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

    // 用户已报名
    int RETCODE_USER_ALREADY_JOIN = 20004;

    // 非法输入
    int RET_CODE_INPUT_ILLEGAL = 10000;

    // 资金类型：0 充值 1: 支出
    int FINANCE_TYPE_RECHARGE = 0;
    int FINANCE_TYPE_EXPENDITURE = 1;

    // 系统错误
    int RETCODE_SYSTEM_ERROR = 10001;

    int ROLE_TYPE_ADMIN = 0;
    int ROLE_TYPE_NORMAL = 1;

    enum ACTIVITY_STATE {ONGOING, END};
}


