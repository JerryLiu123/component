package cn.com.lgh.dataformat.configuration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.session.SessionRepository;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import cn.com.lgh.dataformat.DataInterceptor;
import cn.com.lgh.dataformat.ExceptionDispose;
import cn.com.lgh.dataformat.controller.CallBackController;
import cn.com.lgh.dataformat.helper.HttpServletRequestReplacedFilter;
import cn.com.lgh.dataformat.service.ICallBackService;
import cn.com.lgh.dataformat.service.impl.CallBackLocaleServiceImpl;
import cn.com.lgh.dataformat.service.impl.CallBackServiceImpl;
import cn.com.lgh.dataformat.view.JsonViewResolver;
import cn.com.lgh.dataformat.view.TaskViewResolver;
import cn.com.lgh.dataformat.view.ExcelViewResolver;


/**
 * @ClassName: WebMvcConfig.java
 * @Description: MVC 配置
 * 这个版本去掉了 html解析的配置，因为很多项目会用自己的
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:58:03 
 *
 * Modification History:<br>
 * Date         Author          Version            Description<br>
 *---------------------------------------------------------*<br>
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = DataFormatViewAutoConfiguration.class)
public class DataFormatViewAutoConfiguration implements WebMvcConfigurer {
	
	private Logger logger = LoggerFactory.getLogger(DataFormatViewAutoConfiguration.class);

//	@Value("${dataformat.page.suffix:def}")
//	private String suffix;//页面资源前缀
//	
//	@Value("${dataformat.view.default:html}")
//	private String defView;//默认的view解析器
	
//	@Value("${dataformat.page.location}")
//	private String location;//页面转发地址
	
//	@Value("${dataformat.page.htmlview.htmlCache:false}")
//	private boolean htmlCache;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired(required=false)
	private SessionRepository sessionRepository;

	
	
	/**
	 * 设置json转换器
	 * @return MappingJackson2HttpMessageConverter
	 */
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();
		httpMessageConverter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
			add(MediaType.valueOf("application/json;charset=UTF-8"));
			add(MediaType.valueOf("text/html;charset=UTF-8"));
		}});
		return httpMessageConverter;
	}
	
	/**
	 * 设置String 转换器
	 * @return
	 */
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter();
		converter.setSupportedMediaTypes(new ArrayList<MediaType>() {{
			add(MediaType.valueOf("text/plain;charset=UTF-8"));
		}});
		return converter;
	}
	
	/**
	 *  重写消息转换器链
	 *  如果转换器链顺序错乱的话可以重写方法extendMessageConverters()，extendMessageConverters中加入转换器之前执行converters.clear();
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		logger.info("-----重写消息转换器链-----");
		//super.configureMessageConverters(converters);
		converters.add(0, new ByteArrayHttpMessageConverter());//byte类型转换器
		converters.add(1, stringHttpMessageConverter());//String 类型转换器
		converters.add(2, mappingJackson2HttpMessageConverter());//json类型转换器
	}
	
	@Bean
    public FilterRegistrationBean httpServletRequestReplacedRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestReplacedFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("httpServletRequestReplacedFilter");
        registration.setOrder(1);
        return registration;
    }
	
	@Bean
    public ICallBackService callBackService() {
		if(sessionRepository == null) {
        	logger.info("初始化 本地 Callback Service 方法....");
        	return new CallBackLocaleServiceImpl();
		}else {
        	logger.info("初始化 分布式 Callback Service 方法....");
        	return new CallBackServiceImpl();
		}
    }
    
    @Bean
    public CallBackController callBackController() {
    	logger.info("初始化 callback Controller 方法....");
    	return new CallBackController();
    }
    
    @Bean
    public ExceptionDispose exceptionDispose() {
    	logger.info("初始化统一Controller异常处理");
    	return new ExceptionDispose();
    }
	
	/**
	 * 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry)  
	 * @Function: WebMvcConfig.java
	 * @Description: 配置 数据格式化拦截器
	 *
	 * @param registry
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月7日 上午11:58:17 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description <br>
	 *------------------------------------------------------------*<br>
	 * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
	 */
    @Override  
    public void addInterceptors(InterceptorRegistry registry) {  
        registry.addInterceptor(new DataInterceptor())
        		.addPathPatterns("/**");
        logger.info("----------数据格式化拦截器配置结束----------");
    }  
	
    /**
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#configureContentNegotiation(org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer)  
     * @Function: WebMvcConfig.java
     * @Description: 设置默认View解析为json
     *
     * @param configurer
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月7日 上午11:58:38 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    	WebMvcConfigurer.super.configureContentNegotiation(configurer);
	    configurer.favorPathExtension(true)
			.defaultContentType(MediaType.APPLICATION_JSON_UTF8)//默认解析器使用为json
			.mediaType("excel", MediaType.parseMediaType("application/excel"))//自定义后缀
			.mediaType("task", MediaType.parseMediaType("application/task"));//自定义后缀
			
	    logger.info("----------默认 View 解析器和添加自定义解析后缀配置结束----------");
    }
    
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
    	configurer.setUseSuffixPatternMatch(true);
    	configurer.setUseTrailingSlashMatch(true);
    	WebMvcConfigurer.super.configurePathMatch(configurer);
    }
	
    /**
     * 
     * @Function: WebMvcConfig.java
     * @Description: 注入各种View 解析器
     *
     * @param manager
     * @return
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月7日 上午11:58:51 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
     */
    @Bean(name="contentNegotiatingViewResolver")
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
    	
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        //保存所有的view解析器
        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
        resolvers.add(jsonViewResolver());//json解析器
        resolvers.add(xlsViewResolver());//xls解析器
        resolvers.add(taskViewResolver());//task view解析器
//        resolvers.add(htmlViewResolver());//html view解析器
        
        resolver.setViewResolvers(resolvers);
        resolver.afterPropertiesSet();
        logger.info("----------解析器链配置结束----------");
        return resolver;
    }
    
    //task 后缀解析
    @Bean
    public TaskViewResolver taskViewResolver() {
    	return new TaskViewResolver();
    }
    
    //json 视图解析器
    @Bean
    public JsonViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }
    //xls视图解析器
    @Bean
    public ExcelViewResolver xlsViewResolver() {
        return new ExcelViewResolver();
    }
    
    //html解析器
//    @Bean
//    public ThymeleafViewResolver htmlViewResolver() {
//		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//		resolver.setTemplateEngine(templateEngine());
//		resolver.setCharacterEncoding("UTF-8");
//		return resolver;
//    }
//    
//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();   
//		templateResolver.setCharacterEncoding("UTF-8");
//		templateResolver.setApplicationContext(applicationContext);
//		if("def".equals(suffix)) {
//			templateResolver.setPrefix("classpath:/templates/");
//		}else {
//			templateResolver.setPrefix(suffix);
//		}
//		templateResolver.setSuffix(".html");
//		templateResolver.setTemplateMode("HTML5");
//		templateResolver.setCacheable(htmlCache);
//		logger.info("----------html前缀:"+suffix+"-----是否缓存:"+htmlCache+"----------");
//		return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//		SpringTemplateEngine templateEngine = new SpringTemplateEngine();      
//		templateEngine.setTemplateResolver(templateResolver());
//		return templateEngine;
//    }
}
