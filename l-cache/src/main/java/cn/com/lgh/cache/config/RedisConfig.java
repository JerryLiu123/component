package cn.com.lgh.cache.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

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


/**
* @Description: redis 配置类
* @Author: lizhiting
* @Date: 2019-09-25 14:20
*/
@Configuration
@EnableConfigurationProperties(LRedisProperties.class)
public class RedisConfig {
    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

	/**sentinel模式主节点**/
    private String sentinelMasterId = null;
	/**redis密码**/
    private String password;
	/** 用哪个库 **/
    private Integer databaseNumber = 0;
	/** 地址信息 **/
    private List<String> uri;


	@Autowired
	private LRedisProperties lRedisProperties;
    
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
	@ConditionalOnMissingBean
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
		//设置key的序列化方式为String
		template.setKeySerializer(template.getStringSerializer());
		//设置value的序列化方法,转换为json
		template.setValueSerializer(jackson2JsonRedisSerializer);
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
	@ConditionalOnMissingBean
	public LettuceConnectionFactory jedisConnectionFactory(LettucePoolingClientConfiguration pools) throws Exception {
		this.uri = this.formatUrl(lRedisProperties.getUri());
		logger.info("---redis 类型:"+lRedisProperties.getPattern()+"---");
	    logger.info("---redis 超时时间:"+lRedisProperties.getTimeout()+"---");
	    logger.info("---redis 地址:"+uri+"---");
	    logger.info("---redis 所用库:"+databaseNumber+"---");
	    logger.info("---redis 密码:"+password+"---");

	    LettuceConnectionFactory factory = null;

		Optional.ofNullable(lRedisProperties.getPattern()).orElseThrow(() -> new CacheException("请指定链接类型（sentinel:哨兵 cluster:集群 base:单机）"));
	    switch (lRedisProperties.getPattern()){
			case "sentinel":
				factory = new LettuceConnectionFactory(this.sentinelConfiguration(), pools);
				break;
			case "cluster":
				factory = new LettuceConnectionFactory(this.clusterConfiguration(), pools);
				break;
			case "base":
				factory = new LettuceConnectionFactory(this.standaloneConfiguration(), pools);
				break;
			default:
				throw new CacheException("请指定链接类型（sentinel:哨兵 cluster:集群 base:单机）");

		}
	    return factory;
	}


	/**
	 * 获取缓存连接池
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public LettucePoolingClientConfiguration getPoolConfig() {
		Map<String, Integer> pools = lRedisProperties.getPoolConfig();
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(Optional.ofNullable(pools.get("maxTotal")).orElse(50));
		config.setMaxWaitMillis(5000);
		config.setMaxIdle(Optional.ofNullable(pools.get("maxIdle")).orElse(30));
		config.setMinIdle(Optional.ofNullable(pools.get("minIdle")).orElse(5));
		LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
				.poolConfig(config)
				.commandTimeout(Duration.ofMillis((lRedisProperties.getTimeout() != null && lRedisProperties.getTimeout() > 0) ? lRedisProperties.getTimeout() : 60 * 1000))
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
		return Optional.ofNullable(uri).map(obj -> {
			RedisClusterConfiguration configuration = new RedisClusterConfiguration();
			configuration.setMaxRedirects(200);
			uri.stream().forEach(s -> {
				configuration.clusterNode(s.split(":")[0], Integer.valueOf(s.split(":")[1]));
			});
			Optional.ofNullable(password).ifPresent(c -> {
				configuration.setPassword(RedisPassword.of(password));
			});
			return configuration;
		}).orElseThrow(() -> new CacheException("配置信息错误!!!"));

	}
	
	/**
	 * 配置哨兵连接属性
	 * @return
	 * @throws Exception 
	 */
	private RedisSentinelConfiguration sentinelConfiguration() throws Exception {
		return Optional.ofNullable(sentinelMasterId).map(obj -> {
			RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
			configuration.setMaster(obj);
			uri.stream().forEach(s -> {
				configuration.sentinel(s.split(":")[0], Integer.valueOf(s.split(":")[1]));
			});
			Optional.ofNullable(password).ifPresent(c -> {
				configuration.setPassword(RedisPassword.of(password));
			});
			return configuration;
		}).orElseThrow(() -> new CacheException("配置信息错误!!!"));
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
		configuration.setHostName(uri.get(0).split(":")[0]);
		configuration.setPort(Integer.valueOf(uri.get(0).split(":")[1]));
		Optional.ofNullable(password).ifPresent(c -> {
			configuration.setPassword(RedisPassword.of(password));
		});
	    configuration.setDatabase(databaseNumber);
	    return configuration;
	}

	
	private List<String> formatUrl(List<String> uri){
    	/*格式化url*/
    	List<String> newUri = new ArrayList<String>();
    	for(String u : uri) {
			//去除哨兵前缀
    		if(u.startsWith(LRedisProperties.SENTINEL_PREFIX)) {
    			u = u.split(LRedisProperties.SENTINEL_PREFIX)[1];
				//去除 单机或集群前缀
    		}else if(u.startsWith(LRedisProperties.BASE_CLUSTER_PREFIX)) {
    			u = u.split(LRedisProperties.BASE_CLUSTER_PREFIX)[1];
    		}
			//获得密码
    		if(u.indexOf("@") >= 0) {
    			password = u.split("@")[0];
    			u = u.split("@")[1];
    		}
			//获得哨兵主节点
    		if(u.indexOf("?") >= 0){
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
			//获得用的库
    		if(u.indexOf("/") >= 0) {
				//没有指定库
        		if(u.endsWith("/")) {
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
	  

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getUri() {
		return uri;
	}
	public void setUri(List<String> uri) {
        this.uri = uri;
	}
	
	
}
