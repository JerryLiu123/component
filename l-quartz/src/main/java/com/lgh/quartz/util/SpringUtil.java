package com.lgh.quartz.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringUtil.java
 * @Description: spring 在普通类中获得 spring注入的Bean工具类
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:41:27 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(SpringUtil.class);
    private static ApplicationContext applicationContext;
    private static ConfigurableApplicationContext configurableContext;
    private static BeanDefinitionRegistry beanDefinitionRegistry;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        //logger.info("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+SpringUtil.applicationContext+"========");   
    }

    /**
     * @Function: SpringUtil.java
     * @Description: 获得applicationContext
     *
     * @return
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月7日 上午11:42:13 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
     */
    public synchronized static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    public synchronized static ConfigurableApplicationContext getConfigurableApplicationContext() {
    	if(configurableContext == null ) {
    		configurableContext = (ConfigurableApplicationContext)getApplicationContext();
    	}
    	return configurableContext;
    }
    
    public synchronized static BeanDefinitionRegistry getBeanDefinitionRegistry() {
    	if(beanDefinitionRegistry == null) {
    		beanDefinitionRegistry = (DefaultListableBeanFactory) configurableContext.getBeanFactory();
    	}
    	return beanDefinitionRegistry;
    }
    

    /**
     * @Function: SpringUtil.java
     * @Description: 移除bean
     *
     * @param beanId
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月16日 下午5:35:11 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月16日     xiaoming           v1.0.0               修改原因 <br>
     */
    public static void unregisterBean(String beanId){
    	getBeanDefinitionRegistry().removeBeanDefinition(beanId);
    }

    /**
     * 
     * @Function: SpringUtil.java
     * @Description: 通过Bean name 获取 Bean.
     *
     * @param name
     * @return 
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月7日 上午11:42:25 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * 
     * @Function: SpringUtil.java
     * @Description: 通过class获取Bean.
     *
     * @param clazz
     * @return
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月7日 上午11:42:56 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 
     * @Function: SpringUtil.java
     * @Description: 通过name,以及Clazz返回指定的Bean
     *
     * @param name bean名称
     * @param clazz bean 类型
     * @return
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月7日 上午11:43:07 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
     */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
    
    /**
     * 
     * @Function: SpringUtil.java
     * @Description: 通过Class获得Bean
     *
     * @param clazz bean类型
     * @return 返回一个map key为beanName value为Bean
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月7日 上午11:43:34 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
     */
    public static <T> Map<String, T> getBeans(Class<T> clazz){
        return getApplicationContext().getBeansOfType(clazz);
    }
    
    
    /**
     * 
     * @Function: SpringUtil.java
     * @Description: 获得spring中所有注入的bean
     *
     * @return
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月16日 下午5:11:40 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月16日     xiaoming           v1.0.0               修改原因 <br>
     */
    public static List<Map<String, Object>> getAllBeans(){
        String[] beans = SpringUtil.getApplicationContext()  
                .getBeanDefinitionNames();  
        List<Map<String, Object>> beansList = new ArrayList<>();
        for (String beanName : beans) {  
            Class<?> beanType = SpringUtil.getApplicationContext().getType(beanName);
            Map<String, Object> beansMap = new HashMap<>();
            beansMap.put("beanName", beanName);
            beansMap.put("beanType", beanType);
            beansMap.put("banPackage", beanType.getPackage());
            beansMap.put("bean", SpringUtil.getApplicationContext().getBean(beanName));
            beansList.add(beansMap);
        }
        
        return beansList;
    }
    
}
