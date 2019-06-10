package com.lgh.sys.manage.server;

import java.util.List;

import com.lgh.sys.manage.model.Role;

public interface RoleServer {

	/**
	 * 
	 * @Title: selectRoleByUri
	 * @Description: 根据请求地址获得uri
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 2:17:01 PM
	 * @param uri
	 * @return
	 */
	public List<Role> selectRoleByUri(String uri);
	
	/**
	 * 
	 * @Title: selectRoleByUserId
	 * @Description: 根据用户ID获得用户角色
	 * @Author lizhiting
	 * @DateTime Jun 4, 2019 2:47:53 PM
	 * @param userId
	 * @return
	 */
	public List<Role> selectRoleByUserId(String userId);
}
