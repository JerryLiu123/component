package com.lgh.sys.manage.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lgh.sys.manage.bean.ResultCode;
import com.lgh.sys.manage.bean.ResultJson;
import com.lgh.sys.manage.bean.auto.ResponseUserToken;
import com.lgh.sys.manage.bean.auto.User;
import com.lgh.sys.manage.bean.auto.UserDetail;
import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.exception.CustomException;
import com.lgh.sys.manage.helper.LoginSuccessEvent;
import com.lgh.sys.manage.helper.MD5PasswordEncoder;
import com.lgh.sys.manage.server.AuthService;
import com.lgh.sys.manage.server.TokenOperation;
import com.lgh.sys.manage.server.WrongPasswordPost;
import com.lgh.sys.manage.util.JwtUtils;

/**
 * 
 * @ClassName: AuthServiceImpl
 * @Description: 认证操作
 * @Author lizhiting
 * @DateTime May 16, 2019 4:01:06 PM
 */
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
    private UserDetailsService userDetailsService;
	@Autowired
    private JwtUtils jwtTokenUtil;
    @Autowired
    private SysConstant sysConstant;
    
    @Autowired //获取Spring容器
    private ApplicationContext context;
	
    @Autowired
    private TokenOperation tokenOperation;
    
    @Autowired
    private WrongPasswordPost wrongPasswordPost;
	
	@Autowired
	private MD5PasswordEncoder encoder;
	
    @Override
    public ResponseUserToken login(User user) {
        //用户验证
        final Authentication authentication = authenticate(user);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String token = null;
        //如果保存密码的话则保存7天
        if("1".equals(user.getIsRemember())) {
        	token = jwtTokenUtil.generateAccessToken(userDetail, sysConstant.getRememberPasswordExpiration());
        }else {
        	token = jwtTokenUtil.generateAccessToken(userDetail);
        }
        //存储token
        tokenOperation.putToken(user.getName(), token);
        ResponseUserToken to = new ResponseUserToken(token, userDetail);
        
        //后置操作
        //删除密码错误次数
        wrongPasswordPost.remove(user.getName());
        //登陆成功后事件发布,在这发布事件，，是因为没找到security登陆成功后的，后置方法
		context.publishEvent(new LoginSuccessEvent(this,to));
        return to;

    }

    @Override
    public void logout(String token) {
        token = token.substring(sysConstant.getAuthTokenStart().length());
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        tokenOperation.deleteToken(userName);
    }

    @Override
    public ResponseUserToken refresh(String oldToken) {
        String token = oldToken.substring(sysConstant.getAuthTokenStart().length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetail userDetail = (UserDetail) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, userDetail.getLastTokenDate())){
            token =  jwtTokenUtil.refreshToken(token);
            return new ResponseUserToken(token, userDetail);
        }
        return null;
    }

    @Override
    public UserDetail getUserByToken(String token) {
        token = token.substring(sysConstant.getAuthTokenStart().length());
        return jwtTokenUtil.getUserFromToken(token);
    }

    private Authentication authenticate(User user) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        } catch (DisabledException | UsernameNotFoundException | BadCredentialsException e) {
        	if(e instanceof BadCredentialsException) {
        		//密码错误次数加一
        		wrongPasswordPost.add(user);
        		throw new CustomException("用户名密码错误");
        	}
        	if(e instanceof UsernameNotFoundException) {
        		throw new CustomException("用户未找到");
        	}
            throw new CustomException(e.getMessage());
        }
    }
}
