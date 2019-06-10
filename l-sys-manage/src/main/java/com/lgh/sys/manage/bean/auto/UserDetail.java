package com.lgh.sys.manage.bean.auto;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @ClassName: UserDetail
 * @Description: 登陆后存储用户详细
 * @Author lizhiting
 * @DateTime May 28, 2019 9:40:46 AM
 */
public class UserDetail extends User {
	
	private static final long serialVersionUID = 1L;

	public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public UserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	

	public UserDetail(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}
	
	public UserDetail(String id, String username, String password, Collection<? extends GrantedAuthority> authorities, Date lastTokenDate) {
		super(username, password, authorities);
		this.id = id;
		this.lastTokenDate = lastTokenDate;
	}

	private String id;

	private Date lastTokenDate;


	public Date getLastTokenDate() {
		return lastTokenDate;
	}

	public void setLastTokenDate(Date lastTokenDate) {
		this.lastTokenDate = lastTokenDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
