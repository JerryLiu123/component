package com.lgh.sentinel;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;

/**
 * 
 * @ClassName: IPAddrRequestOriginParser
 * @Description: sentinel 中用来从request中获得当前请求者的表示，<br>
 * 				 获得之后用来做请求权限拦截
 * @Author lizhiting
 * @DateTime Apr 11, 2019 9:15:22 AM
 */
public class IPAddrRequestOriginParser implements RequestOriginParser {

	@Override
	public String parseOrigin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		// 这里只是简单的获得请求的IP
		return request.getRemoteAddr();
	}

}
