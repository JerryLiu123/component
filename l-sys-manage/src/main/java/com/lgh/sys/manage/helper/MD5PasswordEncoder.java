package com.lgh.sys.manage.helper;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lgh.sys.manage.util.MD5Util;

/**
 * 
 * @ClassName: MD5PasswordEncoder
 * @Description: 密码加密和密码比对
 * @Author lizhiting
 * @DateTime May 28, 2019 10:11:18 AM
 */
@Configuration
public class MD5PasswordEncoder implements PasswordEncoder {
	
	private static Logger LOG = LoggerFactory.getLogger(MD5PasswordEncoder.class);

	@Override
	public String encode(CharSequence rawPassword) {
		try {
			if(LOG.isDebugEnabled()) {
				LOG.debug("执行MD5加密");
			}
			String md5 = MD5Util.md5(rawPassword.toString());
			return md5;
		} catch (Exception e) {
			// TODO: handle exception
			return rawPassword.toString();
		}
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			if(LOG.isDebugEnabled()) {
				LOG.debug("执行密码比对");
			}
			return MD5Util.verify(rawPassword.toString(), encodedPassword);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	
	@PostConstruct
	public void init() {
		LOG.info("-----初始化MD5PasswordEncoder-----");
	}
	
//	public static void main(String[] args) {
//		System.err.println(new MD5PasswordEncoder().encode("123456"));
//	}

}
