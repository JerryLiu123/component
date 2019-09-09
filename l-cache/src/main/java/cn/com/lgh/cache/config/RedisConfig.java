package cn.com.lgh.cache.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.autoconfigure.LettuceFactory;
import com.alicp.jetcache.autoconfigure.RedisLettuceAutoConfiguration;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.lgh.cache.CacheException;
import cn.com.lgh.cache.ICacheOperation;
import cn.com.lgh.cache.RedisOperation;
import io.lettuce.core.RedisClient;

@Configuration
//@EnableMethodCache(basePackages="${jetcache.remote.basepackges}")
@EnableCreateCacheAnnotation
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=60*30)//分布式session
@ConfigurationProperties(prefix="jetcache.remote.default")//配置文件前缀
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    private final String sentinelPrefix = "redis-sentinel://";
    private final String baseAndclusterPrefix = "redis://";
    
    private String sentinelMasterId = null;//sentinel模式主节点
    private Integer timeout;//超时时间
    private String password;//redis密码
    private String pattern;//模式 sentinel哨兵 base 单机 cluster 集群
    //private Map<String,Integer> poolConfig;//连接池信息
    private Integer databaseNumber = 0;//用哪个库
    private List<String> uri;//地址信息
    
    @Bean(name = "defaultClient")
    @DependsOn(RedisLettuceAutoConfiguration.AUTO_INIT_BEAN_NAME)
    public LettuceFactory defaultClient() {
    	logger.info("获得LettuceFactory");
        return new LettuceFactory("remote.default", RedisClient.class);
    }
    

    /**
     * RedisTemplate配置
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory) {
    	logger.info("初始化RedisTemplate");
    	RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    	template.setConnectionFactory(factory);
		setMySerializer(template);
		return template;
    }
    
	@SuppressWarnings("unchecked")
	private void setMySerializer(RedisTemplate template) {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setKeySerializer(template.getStringSerializer());//设置key的序列化方式为String
		template.setValueSerializer(jackson2JsonRedisSerializer);//设置value的序列化方法,转换为json
	}
	
	@Bean(name="redisOperation")
	@ConditionalOnMissingBean
	public ICacheOperation redisOperation() {
		return new RedisOperation();
	}
    
	/**
	 * jedis 连接工厂
	 * @return
	 * @throws Exception 
	 */
	@Bean
	public LettuceConnectionFactory jedisConnectionFactory() throws Exception {
		this.uri = this.formatUrl(this.uri);
		logger.info("---redis 类型:"+pattern+"---");
	    logger.info("---redis 超时时间:"+timeout+"---");
	    logger.info("---redis 地址:"+uri+"---");
	    logger.info("---redis 所用库:"+databaseNumber+"---");
	    logger.info("---redis 密码:"+password+"---");

	    LettuceConnectionFactory factory = null;
	    LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
	    lettuceClientConfigurationBuilder.commandTimeout(Duration.ofMillis((timeout != null && timeout > 0) ? timeout : 60 * 1000));
	    
	    if("sentinel".equals(pattern)) {//哨兵
	    	factory = new LettuceConnectionFactory(this.sentinelConfiguration(), getPoolConfig());
	    }else if("cluster".equals(pattern)) {//集群
	    	factory = new LettuceConnectionFactory(this.clusterConfiguration(), getPoolConfig());
	    }else if("base".equals(pattern)){//单机
	        factory = new LettuceConnectionFactory(this.standaloneConfiguration(), getPoolConfig());
	    }
	    return factory;
	}


	/**
	 * 获取缓存连接池
	 *
	 * @return
	 */
	@Bean
	public LettucePoolingClientConfiguration getPoolConfig() {
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(50);
		config.setMaxWaitMillis(5000);
		config.setMaxIdle(3000);
		config.setMinIdle(1000);
		LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
				.poolConfig(config)
				.commandTimeout(Duration.ofMillis(timeout))
				.shutdownTimeout(Duration.ofMillis(3000))
				.build();
		return pool;
	}
	
	/**
	 * 配置集群连接属性
	 * @return
	 * @throws Exception 
	 */
	private RedisClusterConfiguration clusterConfiguration() throws Exception {
	    RedisClusterConfiguration configuration = new RedisClusterConfiguration();
	    configuration.setMaxRedirects(200);
	    for(String s : uri) {
	        configuration.clusterNode(s.split(":")[0], Integer.valueOf(s.split(":")[1]));
	    }
	    if(password != null && !"null".equals(password) && password.replaceAll(" ", "").length() > 0) {
	    	configuration.setPassword(RedisPassword.of(password));
	    }
	    return configuration;
	}
	
	/**
	 * 配置哨兵连接属性
	 * @return
	 * @throws Exception 
	 */
	private RedisSentinelConfiguration sentinelConfiguration() throws Exception {
		RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
		if(sentinelMasterId == null || "null".equals(sentinelMasterId) || sentinelMasterId.replaceAll(" ", "").length()<=0) {
			throw new CacheException("配置信息错误!!!");
		}
		configuration.setMaster(sentinelMasterId);
		logger.info("哨兵配置为---Master:"+sentinelMasterId+"----uri:"+JSONArray.toJSONString(uri));
	    for(String s : uri) {
	        configuration.sentinel(s.split(":")[0], Integer.valueOf(s.split(":")[1]));
	    }
	    if(password != null && !"null".equals(password) && password.replaceAll(" ", "").length() > 0) {
	    	configuration.setPassword(RedisPassword.of(password));
	    }
		return configuration;
	}
	/**
	 * 
	 * @Function: RedisConfig.java
	 * @Description: 配置redis 单机连接属性
	 *
	 * @return
	 * @throws Exception
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年10月31日 下午2:41:31 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description<br>
	 *---------------------------------------------------------*<br>
	 * 2018年10月31日     xiaoming           v1.0.0               修改原因<br>
	 */
	private RedisStandaloneConfiguration standaloneConfiguration() throws Exception {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		if(uri.size() != 1) {throw new CacheException("redis配置信息错误");}
	    for(String s : uri) {
	    	configuration.setHostName(s.split(":")[0]);
	    	configuration.setPort(Integer.valueOf(s.split(":")[1]));
	    }
	    if(password != null && !"null".equals(password) && password.replaceAll(" ", "").length() > 0) {
	    	configuration.setPassword(RedisPassword.of(password));
	    }
	    configuration.setDatabase(databaseNumber);
	    return configuration;
	}
	
	private List<String> formatUrl(List<String> uri){
    	/*格式化url*/
    	List<String> newUri = new ArrayList<String>();
    	for(String u : uri) {
    		if(u.startsWith(sentinelPrefix)) {//去除哨兵前缀
    			u = u.split(sentinelPrefix)[1];
    		}else if(u.startsWith(baseAndclusterPrefix)) {//去除 单机或集群前缀
    			u = u.split(baseAndclusterPrefix)[1];
    		}
    		if(u.indexOf("@") >= 0) {//获得密码
    			password = u.split("@")[0];
    			u = u.split("@")[1];
    		}
    		if(u.indexOf("?") >= 0){//获得哨兵主节点
    			String[] us = u.split("\\?");
    			if(us.length == 2) {
    				if(us[0].endsWith("/")) {
    					u = us[0].substring(0, us[0].length()-1);
    				}else {
    					u = us[0];
    				}
    				this.sentinelMasterId = us[1].split("=")[1];
    			}
    		}
    		if(u.indexOf("/") >= 0) {//获得用的库
        		if(u.endsWith("/")) {//没有指定库
        			databaseNumber = 0;
        			u = u.substring(0, u.length()-1);
        		}else {
        			databaseNumber = Integer.valueOf(u.split("/")[1]);
        			u = u.substring(0, u.length()-2);
        		}
        		
    		}
    		newUri.add(u);
    	}
    	logger.info("Redis URI:"+JSONArray.toJSONString(newUri)+"----sentinelMasterId:"+this.sentinelMasterId);
    	return newUri;
	}
	  
    
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public Map<String, Integer> getPoolConfig() {
//		return poolConfig;
//	}
//	public void setPoolConfig(Map<String, Integer> poolConfig) {
//		this.poolConfig = poolConfig;
//	}
	public List<String> getUri() {
		return uri;
	}
	public void setUri(List<String> uri) {
        this.uri = uri;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
}
