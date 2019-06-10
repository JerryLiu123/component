package com.lgh.sys.manage.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.lgh.sys.manage.model.Role;
import com.lgh.sys.manage.server.RoleServer;

@Service
public class RoleServerImpl implements RoleServer {
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Role> selectRoleByUri(String uri) {
		String sql = "		SELECT " + 
				"			r.role_name " + 
				"		FROM " + 
				"			menuinfo m " + 
				"			LEFT JOIN role_menu rm ON m.menu_id = rm.menu_id " + 
				"			LEFT JOIN role r ON r.role_id = rm.role_id " + 
				"		WHERE m.request_url = :uri";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("uri", uri);
		return jdbcTemplate.query(sql, parameters, new BeanPropertyRowMapper<Role>(Role.class));
	}

	@Override
	public List<Role> selectRoleByUserId(String userId) {
		String sqlRole = "SELECT " + 
				"			r.role_id, " + 
				"			r.role_name " + 
				"		FROM " + 
				"			role r " + 
				"			JOIN role_user ru ON ru.role_id = r.role_id  " + 
				"		WHERE " + 
				"			ru.user_id = :userId";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
		return jdbcTemplate.query(sqlRole, parameters, new BeanPropertyRowMapper<Role>(Role.class));
	}

}
