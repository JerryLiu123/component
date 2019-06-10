package com.lgh.sys.manage.bean.auto;

/**
 * 
 * @ClassName: User
 * @Description: 用户登陆信息
 * @Author lizhiting
 * @DateTime May 28, 2019 9:39:53 AM
 */
public class User {
	/*用户名*/
    private String name;
    /*密码*/
    private String password;
    /*是否记住密码*/
    private String isRemember;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User() {
		super();
	}
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}
	public String getIsRemember() {
		return isRemember;
	}
	public void setIsRemember(String isRemember) {
		this.isRemember = isRemember;
	}
    
    
}
