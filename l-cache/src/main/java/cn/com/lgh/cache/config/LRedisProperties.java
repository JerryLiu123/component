package cn.com.lgh.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


/**
* @Description: redis 配置项
* @Author: lizhiting
* @Date: 2019-09-25 14:22
*/
@ConfigurationProperties(prefix="jetcache.remote.default")
public class LRedisProperties {

    public static final String SENTINEL_PREFIX = "redis-sentinel://";
    public static final String BASE_CLUSTER_PREFIX = "redis://";

    /**地址信息**/
    private List<String> uri;


    /**连接池信息**/
    private Map<String,Integer> poolConfig;

    /**模式 sentinel哨兵 base 单机 cluster 集群 **/
    private String pattern;

    /**超时时间**/
    private Integer timeout;


    public List<String> getUri() {
        return uri;
    }

    public void setUri(List<String> uri) {
        this.uri = uri;
    }

    public Map<String, Integer> getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(Map<String, Integer> poolConfig) {
        this.poolConfig = poolConfig;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
