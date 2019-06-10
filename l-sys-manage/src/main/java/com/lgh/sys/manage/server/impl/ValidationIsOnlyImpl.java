package com.lgh.sys.manage.server.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgh.sys.manage.server.TokenOperation;
import com.lgh.sys.manage.server.ValidationIsOnly;

/**
 * 
 * @ClassName: ValidationIsOnlyImpl
 * @Description: 默认的判断是否唯一登陆方法
 * @Author lizhiting
 * @DateTime May 28, 2019 10:16:11 AM
 */
@Service
public class ValidationIsOnlyImpl implements ValidationIsOnly {

	@Autowired
	private TokenOperation tokenOperation;
	
	@Override
	public boolean validation(String id, String token) {
		if(StringUtils.isBlank(token) || StringUtils.isBlank(id)) {
			return true;
		}
		if(!(token.equals(tokenOperation.getToken(id)))) {
			return true;
		}
		return false;
	}

}
