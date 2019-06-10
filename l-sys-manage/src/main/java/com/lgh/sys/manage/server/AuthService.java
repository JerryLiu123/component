package com.lgh.sys.manage.server;

import com.lgh.sys.manage.bean.auto.ResponseUserToken;
import com.lgh.sys.manage.bean.auto.User;
import com.lgh.sys.manage.bean.auto.UserDetail;

/**
 * 
 * @ClassName: AuthService
 * @Description: 认证操作
 * @Author lizhiting
 * @DateTime May 16, 2019 10:14:53 AM
 */
public interface AuthService {

    /**
     * 登陆，登陆成功后会发布成功时事件 LoginSuccessEvent
     * @param username
     * @param password
     * @return
     */
    public ResponseUserToken login(User user);

    /**
     * 登出
     * @param token
     */
    void logout(String token);

    /**
     * 刷新Token
     * @param oldToken
     * @return
     */
    public ResponseUserToken refresh(String oldToken);

    /**
     * 根据Token获取用户信息
     * @param token
     * @return
     */
    public UserDetail getUserByToken(String token);
}
