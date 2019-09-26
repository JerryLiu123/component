package cn.com.lgh.cache.pubsub;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.MultiLevelCache;
import com.alicp.jetcache.anno.method.CacheHandler;
import com.alicp.jetcache.anno.support.ConfigProvider;
import com.alicp.jetcache.embedded.AbstractEmbeddedCache;
import com.alicp.jetcache.embedded.LinkedHashMapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Map;
import java.util.Optional;

/**
* @Description: redis 队列监听
* @Author: lizhiting
* @Date: 2019-09-26 11:12
*/
public class Consumer implements MessageListener {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ConfigProvider configProvider;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserialize = valueSerializer.deserialize(message.getBody());
        //System.err.println("收到的mq消息" + deserialize);
        Map<String, Object> data = JSONObject.parseObject(deserialize, new TypeReference<Map>(){});
        try {
            Cache cache = configProvider.getCacheManager().getCache(String.valueOf(data.get("area")), String.valueOf(data.get("cacheName")));
            //做本地缓存的清理
            Optional.ofNullable(cache)
                    .filter(obj -> obj instanceof CacheHandler.CacheHandlerRefreshCache)
                    .map(obj -> ((CacheHandler.CacheHandlerRefreshCache) obj).getTargetCache())
                    .filter(obj -> obj instanceof MultiLevelCache)
                    .map(obj -> (MultiLevelCache) obj)
                    .ifPresent(obj ->{
                        for(Cache c : obj.caches()){
                            if(c instanceof AbstractEmbeddedCache){
                                JSONArray keys = (JSONArray) data.get("keys");
                                for(Object key : keys){
                                    ((AbstractEmbeddedCache) c).remove(key);
                                }
                            }
                        }
                    });
        }catch (IllegalArgumentException e){

        }


    }
}
