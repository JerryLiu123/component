package com.lgh.sys.manage.helper.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSONObject;
import com.lgh.sys.manage.bean.ResultCode;
import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.helper.http.HttpHelper;
import com.lgh.sys.manage.helper.http.RequestReaderHttpServletRequestWrapper;
import com.lgh.sys.manage.model.Userinfo;
import com.lgh.sys.manage.server.UserInfoServer;
import com.lgh.sys.manage.server.WrongPasswordPost;

/**
 * 
 * @ClassName: NumberPasswordErrorsFilter
 * @Description: 密码错误次数校验
 * @Author lizhiting
 * @DateTime May 21, 2019 3:05:08 PM
 */
@Component
public class NumberPasswordErrorsFilter extends OncePerRequestFilter {
	
	private Logger log = LoggerFactory.getLogger(NumberPasswordErrorsFilter.class);
	
    @Autowired
    private WrongPasswordPost wrongPasswordPost;
    
    @Autowired
    private SysConstant sysConstant;

    @Autowired
    private UserInfoServer userInfoServer;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		if(sysConstant.getLoginUrl().equals(url)) {
			if(request.getMethod().equals("POST")) {
				// 防止流读取一次后就没有了, 所以需要将流继续写出去
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				HttpServletRequest requestWrapper = new RequestReaderHttpServletRequestWrapper(httpServletRequest);
				
		        JSONObject jsonObject = JSONObject.parseObject(HttpHelper.getBodyString(requestWrapper));
				if(jsonObject != null) {
					String name = jsonObject.getString("name");
					if(log.isDebugEnabled()) {
						log.debug("-----进行密码错误次数校验:{}-----", name);
					}
					
					if(!(StringUtils.isEmpty(name)) && wrongPasswordPost.verify(name)) {
				        String body = null;
						//如果锁定用户
						if(sysConstant.isPwdErrLock()) {
					        body = "账户已被锁定，请链接管理员解除锁定";
							Userinfo us = userInfoServer.selectByLoginName(name);
					        if(us != null && !"2".equals(us.getState())) {
						        userInfoServer.lockUser(us.getId());
					        }
					        
						}else {
							Long passTime = sysConstant.getPwdErrTimeInterval() - ((System.currentTimeMillis() - wrongPasswordPost.get(name).get(WrongPasswordPost.TIME_KEY))/1000);
					        body = String.format("密码错误次数超出限制，请于%s秒后重试", passTime);
						}
						HttpHelper.write(response, body, ResultCode.PWD_ERR_OVERT);
				        return;
					}
				}
				filterChain.doFilter(requestWrapper, response);
				
			}
		}else {
			filterChain.doFilter(request, response);
		}
	}

}
