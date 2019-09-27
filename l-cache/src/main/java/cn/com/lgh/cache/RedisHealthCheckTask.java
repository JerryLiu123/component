package cn.com.lgh.cache;

import cn.com.lgh.cache.config.RedisListenerConfig;
import cn.com.lgh.cache.pubsub.Dispatcher;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
* @Description: 监听Spring 启动，在启动后增加维持redis队列链接的任务
* @Author: lizhiting
* @Date: 2019-09-27 16:02
*/
public class RedisHealthCheckTask implements ApplicationListener<ApplicationReadyEvent> {

    public static final String CHECK_MESSAGE = "health";

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Dispatcher stringRedisTemplate = event.getApplicationContext().getBean(Dispatcher.class);

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("redis-health-check-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(() -> {
            stringRedisTemplate.send(CHECK_MESSAGE);
            System.out.println("task  run:"+ new Date());
        }, 3, 300, TimeUnit.SECONDS);

    }
}
