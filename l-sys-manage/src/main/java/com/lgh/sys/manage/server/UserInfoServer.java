package com.lgh.sys.manage.server;

import com.lgh.sys.manage.model.Userinfo;

public interface UserInfoServer {

	/**
	 * 
	 * @Title: selectByLoginName
	 * @Description: 根据登陆名查询用户
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 1:56:48 PM
	 * @param userName
	 * @return
	 */
	public Userinfo selectByLoginName(String userName);
	
	/**
	 * 
	 * @Title: lockUser
	 * @Description: 锁定用户
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 2:14:14 PM
	 * @param userID
	 * @return
	 */
	public boolean lockUser(String userID);
	
	/**
	 * 
	 * @Title: checkUpdatePwd
	 * @Description: 校验密码更新时间
	 * @Author lizhiting
	 * @DateTime Jun 10, 2019 1:58:12 PM
	 * @param info
	 * @return
	 */
	public boolean checkUpdatePwd(Userinfo info);
}
