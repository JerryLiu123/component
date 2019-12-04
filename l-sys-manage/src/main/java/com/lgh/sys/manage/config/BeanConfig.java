package com.lgh.sys.manage.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lgh.sys.manage.helper.MD5PasswordEncoder;
import com.lgh.sys.manage.server.RoleServer;
import com.lgh.sys.manage.server.TokenOperation;
import com.lgh.sys.manage.server.UserInfoServer;
import com.lgh.sys.manage.server.ValidationIsOnly;
import com.lgh.sys.manage.server.WrongPasswordPost;
import com.lgh.sys.manage.server.impl.InMemoryTokenOperationImpl;
import com.lgh.sys.manage.server.impl.InMemoryWrongPasswordPostImpl;
import com.lgh.sys.manage.server.impl.RoleServerImpl;
import com.lgh.sys.manage.server.impl.UserInfoServerImpl;
import com.lgh.sys.manage.server.impl.ValidationIsOnlyImpl;

/**
 * 
 * @ClassName: BeanConfig
 * @Description: 初始化默认的Bean，如果在引用的方法中实现了此类中的接口，并且注入的话 那个对应的默认的Bean则不会注入
 * @Author lizhiting
 * @DateTime May 29, 2019 1:47:39 PM
 */
@Configuration
public class BeanConfig {
	
	private Logger log = LoggerFactory.getLogger(BeanConfig.class);

	
	@Bean
	public UserInfoServer userInfoServer() {
		return new UserInfoServerImpl();
	}
	
	@Bean
	@ConditionalOnMissingBean
	public RoleServer roleServer() {
		return new RoleServerImpl();
	}
	
	/**
	 * 
	 * @Title: passwordEncoder
	 * @Description: 注入默认的密码加密解析器
	 * @Author lizhiting
	 * @DateTime May 29, 2019 1:47:18 PM
	 * @return
	 */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
    	if(log.isDebugEnabled()) {
    		log.debug("/-----初始化默认的 密码加密器 -----/");
    	}
        return new MD5PasswordEncoder();
    }
    
    //这个也会影响分布式
    //我在这里记录了token，和用了session有啥区别，，那我为啥要用JWT？？？？？？？
    //这样记录token 
    //用于
    //1. 是否登陆
    //2. 同一用户只能登陆一次
    //3. 登陆人数限制
    /**
     * 
     * @Title: tokenOperation
     * @Description:内存Token管理器，如果要分布式的话请 实现接口 TokenOperation然后注入自己的分布式的Bean
     * @Author lizhiting
     * @DateTime May 29, 2019 1:48:31 PM
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public TokenOperation tokenOperation() {
    	if(log.isDebugEnabled()) {
    		log.debug("/-----初始化默认的 内存token管理器-----/");
    	}
    	return new InMemoryTokenOperationImpl();
    }
    
    //这里判断唯一登陆不回影响分布式，因为用于判断的token 是通过 TokenOperation获得的
    /**
     * 
     * @Title: validationIsOnly
     * @Description: 注入默认的 是否唯一登陆的方法
     * @Author lizhiting
     * @DateTime May 29, 2019 1:49:17 PM
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ValidationIsOnly validationIsOnly() {
    	if(log.isDebugEnabled()) {
    		log.debug("/-----初始化默认的 判断唯一登陆方法 -----/");
    	}
    	return new ValidationIsOnlyImpl();
    }
    
    //这个会影响分布式
    //这里是保存密码错误的次数，用户锁定用户，或者不让他登陆
    /**
     * 
     * @Title: wrongPasswordPost
     * @Description: 初始化 内存密码错误次数方法，如果要分布式的话 请实现接口WrongPasswordPost 并注入自己的Bean
     * @Author lizhiting
     * @DateTime May 29, 2019 1:49:46 PM
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public WrongPasswordPost wrongPasswordPost() {
    	if(log.isDebugEnabled()) {
    		log.debug("/-----初始化默认的 密码错误处理 -----/");
    	}
    	return new InMemoryWrongPasswordPostImpl();
    }
}
