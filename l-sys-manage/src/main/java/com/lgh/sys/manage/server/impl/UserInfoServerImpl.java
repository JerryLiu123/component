package com.lgh.sys.manage.server.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.model.Userinfo;
import com.lgh.sys.manage.server.UserInfoServer;

@Service
public class UserInfoServerImpl implements UserInfoServer {
	
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
	@Autowired
	private SysConstant sysConstant;

	@Transactional
	@Override
	public Userinfo selectByLoginName(String userName) {
		String sql = "SELECT id, create_time, creator_id, error_num, login_name, `password`, remark, state, update_time, updater_id, update_pwd_time "
				+ "FROM sys_login_info WHERE login_name = :name";
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
        String sqlUp = "UPDATE sys_login_info SET state = '2' WHERE `id` = :id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", userID);
        jdbcTemplate.update(sqlUp, parameters);
		return true;
	}

	@Override
	public boolean checkUpdatePwd(Userinfo info) {
		if(sysConstant.isUpdatePassword()) {
			if(info == null || info.getUpdatePwdTime() == null) {
				return false;
			}
			//获得时间
			long day = sysConstant.getUpdatePasswordDay();
			LocalDate to = LocalDate.now();
			LocalDate from = LocalDateTime.ofInstant(info.getUpdatePwdTime().toInstant(), ZoneId.systemDefault()).toLocalDate();
			int days = (int) ChronoUnit.DAYS.between(from, to);
			if(days >= day) {
				return false;
			}
		}
		return true;
	}

}
