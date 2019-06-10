package com.lgh.dsfes.configuation;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.lgh.dsfes.helper.note.DynamicDataSourceAspect;


/**
 * 
 * Copyright: Copyright (c) 2018 LanRu-Caifu
 * 
 * @ClassName: DBAutoAutoConfiguration.java
 * @Description: dataSource 自动配置主要是用于多数据源切换
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年10月24日 上午9:45:21 
 *
 * Modification History:<br>
 * Date         Author          Version            Description<br>
 *---------------------------------------------------------*<br>
 * 2018年10月24日     xiaoming           v1.0.0               修改原因<br>
 */
@Configuration
@Import(value= {DataSourceConfig.class, DynamicDataSourceAspect.class})
@EnableConfigurationProperties(LDataSourceProperties.class)
@ConditionalOnClass({ DataSource.class, EmbeddedDatabaseType.class })
@AutoConfigureBefore(value={DataSourceAutoConfiguration.class})
public class DBAutoAutoConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(DBAutoAutoConfiguration.class);
    /**
     * 注册一个StatViewServlet
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet(LDataSourceProperties properties) {
    	logger.info("-----初始化 druid StatViewServlet-----");
    	ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), properties.getDruidurl());
 
        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow",properties.getDruidallow());
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny",properties.getDruiddeny());
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername",properties.getDruiduser());
        servletRegistrationBean.addInitParameter("loginPassword",properties.getDruidpass());
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");// 禁用HTML页面上的“Reset All”功能
        return servletRegistrationBean;
    }
 
    /**
     * 注册一个：filterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
 
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.setName("druidWebStatFilter");
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
