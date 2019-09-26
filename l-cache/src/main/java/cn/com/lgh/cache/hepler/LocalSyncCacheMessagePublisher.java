package cn.com.lgh.cache.hepler;

import cn.com.lgh.cache.pubsub.Dispatcher;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.support.AbstractMessagePublisher;
import com.alicp.jetcache.support.CacheMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class LocalSyncCacheMessagePublisher extends AbstractMessagePublisher {

    @Autowired
    private Dispatcher dispatcher;

    /**
    * @Title: publish
    * @Description: key操作后执行
    * @param area
    * @param cacheName
    * @param cacheMessage 中有个type：1：CachePutEvent
     *                               3：CacheRemoveEvent
     *                               2：CachePutAllEvent
     *                               4：CacheRemoveAllEvent
    * @Return: void
    * @Author: lizhiting
    * @Date: 2019-09-26 10:45
     * 这个是在源码 DefaultCacheMonitorManager 对象的 addCacheUpdateMonitor 方法中调用，如果spring上下文中有 CacheMessagePublisher
     * 的实现类的话，addCacheUpdateMonitor将会封装一个CacheMonitor（监听）加入到cache的监听链中，这个监听会关注 新增和删除方法，
     * 这个监听的调用，现在看到的是在 CacheContext 的 buildCache 中添加对于这个cache的监听
    */
    @Override
    public void publish(String area, String cacheName, CacheMessage cacheMessage) {
        //发布通知，说明这个缓存有变化
        Map<String, Object> value = new HashMap<>(3);
        value.put("area", area);
        value.put("cacheName", cacheName);
        value.put("keys", cacheMessage.getKeys());
        dispatcher.send(JSONObject.toJSONString(value));
//        for(Object o : cacheMessage.getKeys()){
//
//            dispatcher.send(cacheName+"."+o);
//        }

//        System.err.println("area:"+area+"cacheName:"+cacheName+"--------------");
    }
}
