package com.lgh.sys.manage.bean.auto;

/**
 * 
 * @ClassName: ResponseUserToken
 * @Description: 认证后的返回信息
 * @Author lizhiting
 * @DateTime May 28, 2019 9:39:22 AM
 */
public class ResponseUserToken {
    private String token;
    private UserDetail userDetail;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	public ResponseUserToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResponseUserToken(String token, UserDetail userDetail) {
		super();
		this.token = token;
		this.userDetail = userDetail;
	}
    
}
