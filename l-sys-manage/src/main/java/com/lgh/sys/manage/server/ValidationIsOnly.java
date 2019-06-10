package com.lgh.sys.manage.server;

public interface ValidationIsOnly {

	/**
	 * 
	 * @Title: validation
	 * @Description: 判断当前用户的token是否已经被顶掉了
	 * @Author lizhiting
	 * @DateTime May 17, 2019 9:44:29 AM
	 * @param id
	 * @param token
	 * @return true：被顶掉（已有别人在别的机器登陆）
	 * 			false：没有被顶掉（没有别人用此用户登陆）
	 */
	public boolean validation(String id, String token);
}
