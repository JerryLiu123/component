package com.lgh.sys.manage.helper.filter.permission;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;

/**
 * 
 * @ClassName: UrlAccessDecisionManager
 * @Description: 进行角色对比
 * @Author lizhiting
 * @DateTime May 20, 2019 2:37:06 PM
 */
@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {

	/**
	 * configAttributes :是实现接口FilterInvocationSecurityMetadataSource 的方法getAttributes 返回的数据
	 * 比对角色
	 * 源码中就是在这个方法里面判读的如果是 AnonymousAuthenticationToken 则抛出未登陆异常，如果在这里不抛出的话也就代表着这个资源在不登陆的情况下也可以访问
	 * 也就是说是否登陆异常 是对权限来说的
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

        //authorities:当前用户所有的角色列表
		//configAttributes：这个地址允许的角色列表
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for(ConfigAttribute o : configAttributes) {
			if("permitAll".equals(o.toString())){
				return;
			}
			for(GrantedAuthority r : authorities) {
				if(o.getAttribute()!=null && o.getAttribute().equals(r.getAuthority())) {
					return;
				}
			}
		}
		
		throw new AccessDeniedException("您没有权限访问该地址");

	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
