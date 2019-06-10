package com.lgh.sys.manage.bean.auto;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @ClassName: Role
 * @Description: 自定义角色对象
 * @Author lizhiting
 * @DateTime May 16, 2019 1:50:19 PM
 */
public class Role implements GrantedAuthority{
    private String id;//角色ID
    private String name;//角色名称
    
	@Override
	public String getAuthority() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Role) {
			return name.equals(((Role) obj).name);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role() {
	}

	public Role(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
