package com.lgh.sys.manage.helper.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;import com.lgh.sys.manage.bean.ResultCode;
import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.helper.http.HttpHelper;
import com.lgh.sys.manage.server.TokenOperation;

/**
 * 
 * @ClassName: OnTheNumberOfFilter
 * @Description: 登陆人数限制
 * @Author lizhiting
 * @DateTime May 30, 2019 9:30:42 AM
 */
@Component
public class OnTheNumberOfFilter extends OncePerRequestFilter {
	
    @Autowired
    private SysConstant sysConstant;
    
    @Autowired
    private TokenOperation tokenOperation;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		if(sysConstant.getLoginUrl().equals(url)) {
			if(request.getMethod().equals("POST")) {
				if(sysConstant.getRestrictUserNum().longValue() > 0) {
					if(tokenOperation.getTokenSize() >= sysConstant.getRestrictUserNum().intValue()) {
						HttpHelper.write(response, "当前登陆人数超过限制，请稍后重试", ResultCode.UNAUTHORIZED);
				        return;
					}
				}
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
