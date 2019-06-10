package com.lgh.sys.manage.server.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgh.sys.manage.model.Userinfo;
import com.lgh.sys.manage.server.UserInfoServer;

@Service
public class UserInfoServerImpl implements UserInfoServer {
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	@Transactional
	@Override
	public Userinfo selectByLoginName(String userName) {
		String sql = "SELECT id, create_time, creator_id, error_num, login_name, `password`, remark, state, update_time, updater_id "
				+ "FROM userinfo WHERE state='1' AND login_name = :name";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("name", userName);
		try {
			return jdbcTemplate.queryForObject(sql, parameters, new BeanPropertyRowMapper<Userinfo>(Userinfo.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}

	@Transactional
	@Override
	public boolean lockUser(String userID) {
        String sqlUp = "UPDATE userinfo SET state = '2' WHERE `id` = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", userID);
        jdbcTemplate.update(sqlUp, parameters);
		return true;
	}

}
