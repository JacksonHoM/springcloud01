package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.User;

public interface UserService {

    User getUser(Integer userId);
    /** 增加用户积分*/
    void addScore(Integer userId, Integer score);
}
