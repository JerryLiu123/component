package com.lgh.sys.manage.helper;

import org.springframework.context.ApplicationEvent;

import com.lgh.sys.manage.bean.auto.ResponseUserToken;


/**
 * 
 * @ClassName: LoginSuccessEvent
 * @Description: 定义登陆成功事件
 * @Author lizhiting
 * @DateTime May 21, 2019 5:08:50 PM
 */
public class LoginSuccessEvent extends ApplicationEvent {
	
	/**
	  * @Fields serialVersionUID : 
	  */
	
	private static final long serialVersionUID = -1512819154767157971L;

	public LoginSuccessEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
	private ResponseUserToken responseUserToken;

	public LoginSuccessEvent(Object source, ResponseUserToken responseUserToken) {
		super(source);
		this.responseUserToken = responseUserToken;
	}

	public ResponseUserToken getResponseUserToken() {
		return responseUserToken;
	}

	public void setResponseUserToken(ResponseUserToken responseUserToken) {
		this.responseUserToken = responseUserToken;
	}
	
}
