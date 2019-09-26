package cn.com.lgh.cache.config;


import cn.com.lgh.cache.hepler.LocalSyncCacheMessagePublisher;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration;
import com.alicp.jetcache.support.CacheMessagePublisher;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableCreateCacheAnnotation
@Configuration
@AutoConfigureBefore(JetCacheAutoConfiguration.class)// 需在JetCacheAutoConfiguration前将SpringConfigProvider进行注册
@Import(value= {RedisConfig.class, RedisListenerConfig.class})
public class CacheAutoConfig {

    @Bean
    public CacheMessagePublisher defaultCacheMonitorManager(){
        return new LocalSyncCacheMessagePublisher();
    }


}
