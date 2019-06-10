package com.lgh.quartz.config;

import java.util.Properties;

import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.lgh.quartz.controller.JobController;
import com.lgh.quartz.helper.InitQuartzJob;
import com.lgh.quartz.helper.JobOperationHelper;
import com.lgh.quartz.server.impl.JobLogServerImpl;
import com.lgh.quartz.server.impl.JobServerImpl;

@Configuration
@Import({
	JobServerImpl.class,
	JobLogServerImpl.class
})
@EnableConfigurationProperties(LQuartzProperties.class)
public class QuartzConfig {
	
	@Autowired
	private LQuartzProperties quartzProperties;
	
	@Bean
	public StdSchedulerFactory factory() throws Exception {
		return new StdSchedulerFactory(quartzProperties());
	}
	
	@Bean
	public JobOperationHelper jobOperationHelper() {
		return new JobOperationHelper();
	}

	@Bean
	public InitQuartzJob initQuartzJob() {
		return new InitQuartzJob();
	}
	
	@Bean
	public JobController jobController() {
		return new JobController();
	}
	
	
	
    /** 
     * 设置quartz属性 
     * @throws Exception 
     */  
    public Properties quartzProperties() throws Exception {  
        Properties prop = new Properties();  
        //主配置
        prop.put("org.quartz.scheduler.instanceName", quartzProperties.getShreadInstanceName());  
        prop.put("org.quartz.scheduler.instanceId", quartzProperties.getShreadInstanceid());  
        prop.put("org.quartz.scheduler.skipUpdateCheck", quartzProperties.getShreadSkipUpdateCheck());  
        prop.put("org.quartz.scheduler.makeSchedulerThreadDaemon", quartzProperties.getShreadGuardian());
        prop.put("org.quartz.scheduler.threadsInheritContextClassLoaderOfInitializer", quartzProperties.getShreadInherit());
        prop.put("org.quartz.scheduler.jobFactory.class", quartzProperties.getShreadJobFactory());  
        
        //线程配置
        prop.put("org.quartz.threadPool.class", quartzProperties.getThreadClass());  
        prop.put("org.quartz.threadPool.threadCount", quartzProperties.getThreadNum());  
        prop.put("org.quartz.threadPool.threadPriority", quartzProperties.getThreadPriority());
        prop.put("org.quartz.threadPool.makeThreadsDaemons", quartzProperties.getThreadGuardian());
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", quartzProperties.getThreadInherit());
        prop.put("org.quartz.threadPool.threadNamePrefix", quartzProperties.getThreadNamePrefix());
        
        //调度器配置
        //判断是否为内存执行期
        if(quartzProperties.isRAM()) {
        	prop.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        }else {
            if("default".equals(quartzProperties.getDataSourcePassword())) {
            	throw new Exception("quartz 数据库密码不能为空");
            }
            if("default".equals(quartzProperties.getDataSourceUser())) {
            	throw new Exception("quartz 数据库用户名不能为空");
            }
            if("default".equals(quartzProperties.getDataSourceURL())) {
            	throw new Exception("quartz 数据库链接地址不能为空");
            }
            
            
            prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");  
            prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");  
            prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");  
            prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");  
            prop.put("org.quartz.jobStore.isClustered", "true");  
            prop.put("org.quartz.dataSource.quartzDataSource.driver", quartzProperties.getDataSourceDrive());  
            prop.put("org.quartz.dataSource.quartzDataSource.URL", quartzProperties.getDataSourceURL());  
            prop.put("org.quartz.dataSource.quartzDataSource.user", quartzProperties.getDataSourceUser());  
            prop.put("org.quartz.dataSource.quartzDataSource.password",  quartzProperties.getDataSourcePassword());  
            prop.put("org.quartz.dataSource.quartzDataSource.maxConnections",quartzProperties.getDataSourceMaxConnections()); 
//            prop.put("org.quartz.dataSource.quartzDataSource.URL", "jdbc:mysql://localhost:3306/sys_manage?useUnicode=true&characterEncoding=UTF-8");  
//            prop.put("org.quartz.dataSource.quartzDataSource.user", "root");  
//            prop.put("org.quartz.dataSource.quartzDataSource.password", "123456");  
//            prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10"); 
        }
        prop.put("org.quartz.jobStore.misfireThreshold", quartzProperties.getJobStoreMisfireThreshold());
        
        return prop;  
    } 
}
