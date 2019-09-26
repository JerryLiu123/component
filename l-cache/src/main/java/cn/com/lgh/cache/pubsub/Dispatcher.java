package cn.com.lgh.cache.pubsub;

import cn.com.lgh.cache.config.RedisListenerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;

public class Dispatcher {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public boolean send(String message){
        return Optional.ofNullable(message).map(obj ->{
            stringRedisTemplate.convertAndSend(RedisListenerConfig.TOPIC_NAME, obj);
            return true;
        }).orElse(false);
    }
}
