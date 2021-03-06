package cn.com.lgh.cache.pubsub;

import cn.com.lgh.cache.RedisHealthCheckTask;
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
import java.util.concurrent.atomic.AtomicBoolean;

/**
* @Description: redis 队列监听
* @Author: lizhiting
* @Date: 2019-09-26 11:12
*/
public class Consumer implements MessageListener {

    public static AtomicBoolean isUpdate = new AtomicBoolean(true);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ConfigProvider configProvider;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        if(!isUpdate.get()){
            isUpdate.set(true);
            return;
        }
        /*这里还有队列消费步完成，短线重连等等问题没有解决*/
        RedisSerializer<String> valueSerializer = redisTemplate.getStringSerializer();
        String deserialize = valueSerializer.deserialize(message.getBody());
        if(deserialize == null || RedisHealthCheckTask.CHECK_MESSAGE.equals(deserialize)){
            return;
        }

        Map<String, Object> data = JSONObject.parseObject(deserialize, new TypeReference<Map>(){});
        try {
            /*
            * 这里是源码在CacheContext中的,在__createOrGetCache中在初始化cache后会将 cache 放到SimpleCacheManager中，
            * 这个 SimpleCacheManager 是ConfigProvider 中获得出来的,现在看到的，，，，
            */
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
                                    System.err.println("清空一级缓存数据:"+key);
                                    ((AbstractEmbeddedCache) c).remove(key);
                                }
                            }
                        }
                    });
        }catch (IllegalArgumentException e){

        }


    }
}
