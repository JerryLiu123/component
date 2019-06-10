package com.lgh.sys.manage.helper.filter.permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.model.Role;
import com.lgh.sys.manage.server.RoleServer;

/**
 * 
 * @ClassName: UrlFilterInvocationSecurityMetadataSource
 * @Description: 根据 url 获得地址对应的角色
 * @Author lizhiting
 * @DateTime May 20, 2019 2:31:23 PM
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	@Autowired
	private SysConstant sysConstant;
	
	@Autowired
	private RoleServer roleServer;

	/**
	 * 根据url获得url 对应的所有角色,
	 * 这里是每一次都从数据库中取，正在想办法提高效率，
	 * 返回null的话不会走后面的角色比对
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //从DB中获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequest().getRequestURI();
        // 不需要验证的地址
        if(sysConstant.getMatcher().contains(requestUrl)) {
        	return null;
        }
        
        List<Role> roles = roleServer.selectRoleByUri(requestUrl);
        if(roles != null && !roles.isEmpty()) {
        	ArrayList<ConfigAttribute> configs = new ArrayList<>();
        	roles.stream()
        		.map(j -> j.getRoleName())
        		.filter(s -> StringUtils.isNotBlank(s))
        		.forEach(b -> {
        			configs.add(new SecurityConfig(b));
        		});
        	return configs;
        }
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
