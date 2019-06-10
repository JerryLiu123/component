package com.lgh.sys.manage.server.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lgh.sys.manage.bean.auto.Role;
import com.lgh.sys.manage.bean.auto.UserDetail;
import com.lgh.sys.manage.model.Userinfo;
import com.lgh.sys.manage.server.RoleServer;
import com.lgh.sys.manage.server.UserInfoServer;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	private static Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	

//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserInfoServer userInfoServer;
	
	@Autowired
	private RoleServer roleServer;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOG.debug("-----获得用户名为:{}-----", username);
		Userinfo info = userInfoServer.selectByLoginName(username);
		if(null == info) {
			throw new UsernameNotFoundException(username);
		}
		
		Set<Role> authorities = new HashSet<>();
		List<com.lgh.sys.manage.model.Role> roles = roleServer.selectRoleByUserId(info.getId());
		roles.stream().forEach(obj -> {
			authorities.add(new Role(obj.getRoleId(), obj.getRoleName()));
		});
		UserDetail user = new UserDetail(info.getId(), username, info.getPassword(), authorities, new Date());
		return user;
	}
	
	@PostConstruct
	public void init() {
		LOG.info("-----初始化UserDetailServiceImpl-----");
	}

}
