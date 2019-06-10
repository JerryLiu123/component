package com.lgh.sys.manage.server;

import java.util.Map;

import com.lgh.sys.manage.bean.auto.User;

/**
 * 
 * @ClassName: ErrorLogin
 * @Description: 密码错误后置方法
 * @Author lizhiting
 * @DateTime May 21, 2019 2:18:08 PM
 */
public interface WrongPasswordPost {
	
	public static final String TIME_KEY = "time";
	public static final String NUM_KEY = "num";

	/**
	 * 
	 * @Title: operation
	 * @Description: 密码错误次数加一
	 * @Author lizhiting
	 * @DateTime May 21, 2019 2:22:46 PM
	 * @param user
	 */
	public void add(User user);
	
	
	/**
	 * 
	 * @Title: get
	 * @Description: 获得密码错误次数
	 * @Author lizhiting
	 * @DateTime May 21, 2019 2:27:55 PM
	 * @param id
	 */
	public Map<String, Long> get(String id);
	
	
	/**
	 * 
	 * @Title: verify
	 * @Description: 校验密码错误次数，是否达到限制
	 * @Author lizhiting
	 * @DateTime May 21, 2019 2:44:26 PM
	 * @param id 
	 * @return true：到达限制
	 * 		   false：未达到限制
	 */
	public boolean verify(String id);
	
	/**
	 * 
	 * @Title: remove
	 * @Description: 删除密码错误次数
	 * @Author lizhiting
	 * @DateTime May 21, 2019 2:49:15 PM
	 * @param id
	 */
	public void remove(String id);
}
