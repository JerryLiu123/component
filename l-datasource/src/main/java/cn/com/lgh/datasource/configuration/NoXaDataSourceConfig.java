package cn.com.lgh.datasource.configuration;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import cn.com.lgh.datasource.DataSourceException;
import cn.com.lgh.datasource.DynamicDataSource;
import cn.com.lgh.datasource.util.DataSourceUtil;

@ConditionalOnProperty(prefix="engine", name="isXa", havingValue="false", matchIfMissing=true)
public class NoXaDataSourceConfig {

	private static Logger logger = LoggerFactory.getLogger(NoXaDataSourceConfig.class);
	@Autowired
	private ApplicationContext applicationContext;
	/**
	 * @Function: DBConfig.java
	 * @Description: 初始化数据源
	 *
	 * @return DynamicDataSource
	 * @throws SQLException
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月7日 上午11:54:07 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description <br>
	 *------------------------------------------------------------*<br>
	 * 2018年8月7日     xiaoming           v1.0.0               修改原因 <br>
	 */
	@Bean(value="dataSource")
	@Primary
	public DynamicDataSource dataSource(LDataSourceProperties properties) throws Exception{
		DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();
		Map<Object,Object> map = new HashMap<>();
		DataSource defaultDataSource = null;
		for(Map.Entry<String, Map<String, String>> entry : properties.getDatasource().entrySet()) {
			DataSource dataSource = null;
			dataSource = this.getDruidDataSource(entry.getKey(), entry.getValue());
			if(dataSource != null) {
				map.put(entry.getKey(), dataSource);
				defaultDataSource = dataSource;
			}
		}
		
		if(map.containsKey("system")) {
			defaultDataSource = (DataSource) map.get("system");
		}
		
		dynamicDataSource.setTargetDataSources(map);
		dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
		
		try {
			Map<Object, Object> data = dynamicDataSource.getTargetDataSource();
			if(data != null) {
				DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
				for(Map.Entry<Object, Object> entry : data.entrySet()) {
					if(!applicationContext.containsBean(entry.getKey()+"")) {
						logger.info("添加DataSource到Spring上下文,Bean Name: "+entry.getKey());
						//applicationContext.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(entry.getValue(), entry.getKey()+"");
						beanFactory.registerSingleton(entry.getKey()+"", entry.getValue());			
					}
				}
			}
		} catch (Exception e) {
			logger.error("动态注入Data Source失败!!!!", e);
			throw new DataSourceException(e);
		}
		
		return dynamicDataSource;
	}
	
	private DataSource getDruidDataSource(String name, Map<String, String> config) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("-----初始化非XA数据源-----");
		}
	    DruidDataSource dataSource = new DruidDataSource();
	    dataSource.setName(name);
	    dataSource.setDriverClassName(DataSourceUtil.getDriverClassName(config.getOrDefault("dbType", "")));
	    dataSource.setDbType(config.getOrDefault("dbType", ""));
	    dataSource.setDefaultAutoCommit(false);
	    dataSource.setUrl(DataSourceUtil.getUrl(config.getOrDefault("dbType", ""), config.getOrDefault("ip", ""), config.getOrDefault("port", ""), config.getOrDefault("database", "")));
	    dataSource.setUsername(config.getOrDefault("username", "root"));
	    dataSource.setPassword(config.getOrDefault("password", "root"));
	    dataSource.setInitialSize(Integer.parseInt(config.getOrDefault("minPoolSize", "10")));
	    dataSource.setMinIdle(Integer.parseInt(config.getOrDefault("minPoolSize", "10")));
	    dataSource.setMaxActive(Integer.parseInt(config.getOrDefault("maxPoolSize", "30")));
	    dataSource.setMaxWait(Integer.parseInt(config.getOrDefault("maxWaitTime", "60000")));//获取连接等待的最大时间单位：毫秒
	    dataSource.setTestOnBorrow(false);//在从池中取出链接时是否检查,设置为false，以加快取出速度
	    dataSource.setTestOnReturn(false);
	    dataSource.setTestWhileIdle(true);//设置定时检查链接可用性
	    dataSource.setTimeBetweenEvictionRunsMillis(200000);//检查链接可用性间隔
	    dataSource.setValidationQuery(config.getOrDefault("testQuery", "SELECT 1 from dual"));
	    dataSource.setValidationQueryTimeout(2000);
	    dataSource.setRemoveAbandoned(true);
	    dataSource.setRemoveAbandonedTimeout(300);
	    dataSource.setPoolPreparedStatements(true);//是否缓存 PreparedStatements
	    dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(config.getOrDefault("maxPoolSize", "30")));
	    dataSource.setMaxWait(Integer.parseInt(config.getOrDefault("maxWaitTime", "60000")));
	    try {
	    	dataSource.setFilters("stat,wall");
		} catch (Exception e) {
			throw new Exception("设置 数据源拦截失败!!!", e);
		}

		return dataSource;		
	}
}
