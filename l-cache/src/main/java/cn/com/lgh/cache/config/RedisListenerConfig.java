package cn.com.lgh.cache.config;

import cn.com.lgh.cache.pubsub.Consumer;
import cn.com.lgh.cache.pubsub.Dispatcher;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@AutoConfigureBefore(RedisConfig.class)
@Configuration
public class RedisListenerConfig {

    public static String TOPIC_NAME = "jetcache.cache.local";


    @Bean
    public Consumer consumer(){
        return new Consumer();
    }

    @Bean
    public Dispatcher dispatcher(){
        return new Dispatcher();
    }

    @Bean
    public MessageListenerAdapter getMessageListenerAdapter(Consumer receiver) {
        //当没有继承MessageListener时需要写方法名字
        return new MessageListenerAdapter(receiver);
    }

    /**
     * 创建消息监听容器
     *
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic(TOPIC_NAME));
        return redisMessageListenerContainer;
    }
}
