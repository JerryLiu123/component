package com.lgh.sys.manage.helper.filter.permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.lgh.sys.manage.model.Role;
import com.lgh.sys.manage.server.RoleServer;
import org.springframework.util.AntPathMatcher;

/**
 * 
 * @ClassName: UrlFilterInvocationSecurityMetadataSource
 * @Description: 根据 url 获得地址对应的角色
 * @Author lizhiting
 * @DateTime May 20, 2019 2:31:23 PM
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {




	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**权限数据库查询**/
	private RoleServer roleServer;

	/**系统默认的 MetadataSource**/
	private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

	public UrlFilterInvocationSecurityMetadataSource(RoleServer roleServer, FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource) {
		this.roleServer = roleServer;
		this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
	}

	/**
	 * 根据url获得url 对应的所有角色,
	 * 这里是每一次都从数据库中取，正在想办法提高效率，
	 * 返回null的话不会走后面的角色比对
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //从DB中获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequest().getRequestURI();

		Collection<ConfigAttribute> configs = new ArrayList<>();
        List<Role> roles = roleServer.selectRoleByUri(requestUrl);
        if(roles != null && !roles.isEmpty()) {
        	roles.stream()
        		.map(j -> j.getRoleName())
        		.filter(s -> StringUtils.isNotBlank(s))
        		.forEach(b -> {
        			configs.add(new SecurityConfig(b));
        		});
        }

        //如果数据库里没有当前地址的权限信息，则使用系统配置的角色进行校验
		return Optional.ofNullable(configs)
				.filter(obj -> obj!= null && !obj.isEmpty())
				.orElseGet(() -> filterInvocationSecurityMetadataSource.getAttributes(o));
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
