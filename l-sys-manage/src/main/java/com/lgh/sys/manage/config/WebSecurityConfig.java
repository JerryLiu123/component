package com.lgh.sys.manage.config;

import com.lgh.sys.manage.helper.filter.permission.UrlFilterInvocationSecurityMetadataSource;
import com.lgh.sys.manage.server.RoleServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lgh.sys.manage.controller.AuthController;
import com.lgh.sys.manage.helper.JwtAuthenticationEntryPoint;
import com.lgh.sys.manage.helper.RestAuthenticationAccessDeniedHandler;
import com.lgh.sys.manage.helper.filter.JwtAuthenticationTokenFilter;
import com.lgh.sys.manage.helper.filter.NumberPasswordErrorsFilter;
import com.lgh.sys.manage.helper.filter.OnTheNumberOfFilter;
import com.lgh.sys.manage.helper.filter.permission.UrlAccessDecisionManager;
import com.lgh.sys.manage.server.impl.AuthServiceImpl;
import com.lgh.sys.manage.server.impl.UserDetailServiceImpl;
import com.lgh.sys.manage.util.JwtUtils;

import java.util.Collection;

/**
 * 
 * @ClassName: WebSecurityConfig
 * @Description: security 配置
 * @Author lizhiting
 * @DateTime May 16, 2019 9:56:14 AM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SysConstant.class)
@Import({JwtUtils.class, 
		JwtAuthenticationEntryPoint.class, 
		JwtAuthenticationTokenFilter.class, 
		UserDetailServiceImpl.class, 
		AuthServiceImpl.class, 
		RestAuthenticationAccessDeniedHandler.class,
		AuthController.class,
		UrlAccessDecisionManager.class,
		BeanConfig.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
    private AccessDeniedHandler accessDeniedHandler;

	@Autowired
    private UserDetailsService userDetailService;

	@Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;
	
	@Autowired
	private SysConstant constant;
	
	@Autowired
	private UrlAccessDecisionManager urlAccessDecisionManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleServer roleServer;
	
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    	authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	constant.getMatcher().add(constant.getLoginUrl());
    	String[] strings = constant.getMatcher().stream().
    						filter(string -> !string.isEmpty()).
    						map(obj -> obj.replaceAll(" ", "")).
    						toArray(String[]::new);
        httpSecurity
		        // 由于使用的是JWT，不需要csrf
		        .csrf().disable()
		        .formLogin().disable()
		        //禁用 logoutFilter 因为用的JWT禁用了session，也没有退出之后需要返回的页面等
		        .logout().disable()
		        //禁用requestCache 因为是接口操作，没有什登陆之后需要恢复的请求
		        .requestCache().disable()
        		//权限不足时异常拦截
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                //认证失败时异常拦截
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 对于获取token的rest api要允许匿名访问,
                //这个匿名访问，也就是不需要拦截的地址 看了一下源码是在 FilterInvocationSecurityMetadataSource 的实现类中如果不拦截的话就代表
                //所有人都可以访问，，然后就这个方法的实现累 返回个 null 就行了
                //也就是说登陆是一个方面，但是是否可以访问（授权）是另一个方面和登陆是不一样的
                .authorizeRequests()
                .antMatchers(strings).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                //添加自定义的权限拦截
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {

					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						object.setAccessDecisionManager(urlAccessDecisionManager);
						object.setSecurityMetadataSource(new UrlFilterInvocationSecurityMetadataSource(roleServer,
								object.getSecurityMetadataSource()));
						return object;
					}
				});
        
        //是否开启https
        if(constant.isSSL()) {
        	constant.getSslMatcher().add(constant.getLoginUrl());
        	String[] sslstrings = constant.getSslMatcher().stream().
					filter(string -> !string.isEmpty()).
					map(obj -> obj.replaceAll(" ", "")).
					toArray(String[]::new);
        	httpSecurity.requiresChannel().antMatchers(sslstrings).requiresSecure();
        }
        
        httpSecurity.headers().cacheControl();
        
        //前置拦截，当前登陆人数校验
        httpSecurity.addFilterBefore(onTheNumberOfFilter(), UsernamePasswordAuthenticationFilter.class);
        
    	//前置拦截，校验 密码错误次数
    	httpSecurity.addFilterBefore(numberPasswordErrorsFilter(), UsernamePasswordAuthenticationFilter.class);
        
        // 添加JWT token 校验
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        //添加权限校验
        //httpSecurity.addFilterBefore(dynamicallyUrlInterceptor(), FilterSecurityInterceptor.class);
        
    }
    

    @Override
    public void configure(WebSecurity web) {
    	String[] strings = constant.getWebMatcher().stream().
							filter(string -> !string.isEmpty()).
							map(obj -> obj.replaceAll(" ", "")).
    						toArray(String[]::new);
    	//不拦截的静态资源
        web
	        .ignoring()
	        .antMatchers(strings);
    }
    
    //注入密码错误次数拦截
    @Bean
    public NumberPasswordErrorsFilter numberPasswordErrorsFilter() {
    	return new NumberPasswordErrorsFilter();
    }
    
    @Bean
    public OnTheNumberOfFilter onTheNumberOfFilter() {
    	return new OnTheNumberOfFilter();
    }
    
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setHideUserNotFoundExceptions(false);
       provider.setUserDetailsService(this.userDetailService);
       provider.setPasswordEncoder(passwordEncoder);
       return provider;
	}
	
//    @Bean
//    public FilterSecurityInterceptor dynamicallyUrlInterceptor(){
//    	FilterSecurityInterceptor interceptor = new FilterSecurityInterceptor();
//        interceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
//
////        //配置RoleVoter决策
////        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
////        decisionVoters.add(new RoleVoter());
//        //设置认证决策管理器
//        interceptor.setAccessDecisionManager(urlAccessDecisionManager);
//        return interceptor;
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

/*
 * SecurityContextPersistenceFilter                                   -> doFilter
 * HeaderWriterFilter                                                 -> doFilterInternal
 * 		(放入一些基础的头信息)
 * LogoutFilter                                                       -> doFilter
 * 		(判断是否为登出)
 * JwtAuthenticationTokenFilter(UsernamePasswordAuthenticationFilter) -> doFilterInternal   
 * 		(先获取用户名和密码，并将其封装成UsernamePasswordToken，然后调用AuthenticationManager进行验证) 
 * 		DaoAuthenticationProvider先调用UserDetailsService 的loadUserByUsername()方法获取UserDetails，获取后再与
 * 		UsernamePasswordAuthenticationFilter获取的username和password进行比较；如果认证通过后会将该 UserDetails 赋给认
 * 		证通过的 Authentication的principal，然后再把该 Authentication 存入到 SecurityContext 中。默认情况下，在认证成功后
 * 		ProviderManager也将清除返回的Authentication中的凭证信息。
 * 
 * RequestCacheAwareFilter                                            -> doFilter
 * SecurityContextHolderAwareRequestFilter                            -> doFilter
 * AnonymousAuthenticationFilter                                      -> doFilter
 * SessionManagementFilter                                            -> doFilter
 * ExceptionTranslationFilter                                         -> doFilter
 * FilterSecurityInterceptor                                          -> doFilter
 * JwtAuthenticationTokenFilter(UsernamePasswordAuthenticationFilter) -> doFilter
 * 自定义Controller
 * UserDetailServiceImpl                                              -> loadUserByUsername
 * 自定义密码验证
 * FilterSecurityInterceptor                                          -> super.finallyInvocation
 * FilterSecurityInterceptor                                          -> super.afterInvocation
 * 
 * 
 * */