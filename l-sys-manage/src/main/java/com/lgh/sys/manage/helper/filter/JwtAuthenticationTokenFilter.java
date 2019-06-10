package com.lgh.sys.manage.helper.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lgh.sys.manage.bean.auto.UserDetail;
import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.server.TokenOperation;
import com.lgh.sys.manage.server.ValidationIsOnly;
import com.lgh.sys.manage.util.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * @ClassName: JwtAuthenticationTokenFilter
 * @Description: token 校验
 * @Author lizhiting
 * @DateTime May 16, 2019 9:46:23 AM
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	private static Logger log = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

	@Autowired
	private ValidationIsOnly validationIsOnly;

    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private TokenOperation tokenOperation;
    
	@Autowired
	private SysConstant constant;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    	if(log.isDebugEnabled()) {
    		log.debug("-----进行token校验-----");
    	}
        String auth_token = request.getHeader(constant.getTokenHeader());
        if (StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(constant.getAuthTokenStart())) {
            auth_token = auth_token.substring(constant.getAuthTokenStart().length());
        } else {
            // 不按规范,不允许通过验证
            auth_token = null;
        }
        String username = jwtUtils.getUsernameFromToken(auth_token);
        if(log.isDebugEnabled()) {
        	logger.debug(String.format("Checking authentication for userDetail %s.", username));
        }
        //判断是否只允许一个用户登陆,在这里做感觉不太好，应该用那个 Session那个拦截，，但是我又把session禁用了，，，
        if(constant.getOnly() && StringUtils.isNotBlank(auth_token)) {
        	if(validationIsOnly.validation(username, auth_token)) {
        		auth_token = null;
        	}
        }
        
        if (StringUtils.isNotBlank(auth_token) && 
        		StringUtils.isNotBlank(username) && 
        		tokenOperation.containToken(username, auth_token) && 
        		SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetail userDetail = jwtUtils.getUserFromToken(auth_token);
            if (userDetail != null && jwtUtils.validateToken(request, auth_token, userDetail)) {
            	request.setAttribute("userDetail", userDetail);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                if(log.isDebugEnabled()) {
                	logger.debug(String.format("Authenticated userDetail %s, setting security context", username));
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
