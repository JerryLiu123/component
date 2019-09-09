package cn.com.lgh.cache;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;


@Service
//@ConditionalOnClass(value = {RedisAutoConfig.class})
public class RedisOperation implements ICacheOperation {
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
	/**
	 * 设置新的值，并将原来的值返回
	 * @param key key
	 * @param value value
	 * @param liveTime 超时时间
	 * @return
	 */
	@Override
	public Object getSet(String key, String value, long liveTime) {
		Object a = redisTemplate.opsForValue().getAndSet(key, value);
		if(liveTime > 0) {
			redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
		}
		return a;
	}
	
	/**
	 * 删除Key为这些的数据
	 * @param keys
	 * @throws Exception
	 */
	@Override
	public void delForValue(String... keys) {
		Set<String> set = new HashSet<String>();
		for(String key : keys) {
			set.add(key);
		}
		redisTemplate.delete(set);
	}
	
	/**
	 * 设置值，并添加超时时间，
	 * @param key
	 * @param value
	 * @param liveTime 超时时间
	 * @throws Exception
	 */
	@Override
	public void set(String key, Object value, long liveTime) throws Exception {
		if(liveTime > 0) {
			redisTemplate.opsForValue().set(key, value, liveTime, TimeUnit.SECONDS);
		}else {
			throw new CacheException("存活时间必须大于0!!!");
		}
	}
	
	/**
	 * 重新设置Key的超时时间
	 * @param key
	 * @param timeout
	 * @return
	 */
	@Override
	public boolean resetExpireTime(String key, long timeout) {
		return redisTemplate.boundValueOps(key).expire(timeout, TimeUnit.SECONDS); 
	}
	
	/**
	 * 设置值，并设置偏移量
	 * @param key
	 * @param value
	 * @param offset
	 * @throws Exception
	 */
	@Override
	public void setByOffset(String key, Object value, long offset) throws Exception {
		if(offset > 0) {
			redisTemplate.opsForValue().set(key, value, offset);
		}else {
			throw new CacheException("偏移量必须大于0!!!");
		}
		
	}
	
	/**
	 * 设置值，如果key不存在的话则替换内容并返回true，key存在返回false
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Boolean setValueForExist(String key, String value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	/**
	 * 设置值
	 * @param key
	 * @param value
	 */
	@Override
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 获得 key的内容
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 出队操作，从队列左边取出值
	 * @param key
	 * @return
	 */
	@Override
	public Object dequeue(String key) {
		return redisTemplate.boundListOps(key).leftPop();
	}
	/**
	 * 入队操作，从队列右边插入值
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Long enqueue(String key, Object... value) {
		return redisTemplate.boundListOps(key).rightPushAll(value);
	}
	
	/**
	 * 获得队列大小
	 * @param key
	 * @return
	 */
	@Override
	public Long getQueueSize(String key) {
		return redisTemplate.boundListOps(key).size();
	}

	/**
	 * 插入ZSet集合，并设置其分数
	 * @param key
	 * @param value 值
	 * @param score 分数
	 * @return
	 */
	@Override
	public boolean setZSet(String key, String value, Long score) {
		return redisTemplate.boundZSetOps(key).add(value, score);
	}
	
	/**
	 * 获得指定value的分数值
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Double getZSetScore(String key, String value) {
		return redisTemplate.boundZSetOps(key).score(value);
	}
	
	/**
	 * 对ZSet指定value的分进行递增，并返回递增后的值
	 * @param key
	 * @param value
	 * @param increment
	 * @return
	 */
	@Override
	public Double incrementZSetScore(String key, String value, Long increment) {
		return redisTemplate.boundZSetOps(key).incrementScore(value, increment);
	}
	
	/**
	 * 获得指定value的键值对
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public Cursor<TypedTuple<Object>> getZSetByValue(String key, String value){
		return redisTemplate.boundZSetOps(key).scan(ScanOptions.scanOptions().match(value).build());
	}
	
	/**
	 * 获得ZSet集合所有的值和分数
	 * @param key
	 * @return
	 */
	@Override
	public Cursor<TypedTuple<Object>> getZSetAll(String key) {
		return redisTemplate.boundZSetOps(key).scan(ScanOptions.NONE); 
	}
	
	/**
	 * 获得ZSet指定分数区间的所有值
	 * @param key
	 * @param start 
	 * @param end
	 * @return
	 */
	@Override
	public Set<Object> getZSetByRange(String key, long start, long end) {
		return redisTemplate.boundZSetOps(key).range(start, end);
	}
	
	/**
	 * 获得ZSet集合中所有的value值
	 * @param key
	 * @return
	 */
	@Override
	public Set<Object> getZSetAllValue(String key) {
		return redisTemplate.boundZSetOps(key).range(0, -1);
	}
	
	/**
	 * 获得ZSet指定分数区间的所有值的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	@Override
	public Long countZSetByRange(String key, double min, double max) {
		return redisTemplate.boundZSetOps(key).count(min, max);
	}
	
	/**
	 * 获得集合中所有元素的个数
	 * @param key
	 * @return
	 */
	@Override
	public Long countZSet(String key) {
		return redisTemplate.boundZSetOps(key).zCard();
	}


	@Override
	public void setHashOps(String key, Object value) {
		this.setHashOps("hashops", key, value);
	}

	@Override
	public void setHashOps(String mapName, String key, Object value) {
		redisTemplate.boundHashOps(mapName).put(key, value);
	}

	@Override
	public void setHashOps(String mapName, Map<Object, Object> values) {
		redisTemplate.boundHashOps(mapName).putAll(values);
	}

	@Override
	public Object getZHashOps(String key) {
		return this.getZHashOps("hashops", key);
	}


	@Override
	public Object getZHashOps(String mapName, String key) {
		return redisTemplate.boundHashOps(mapName).get(key);
	}


	@Override
	public Object getkeys(String pattern) {
		 return redisTemplate.keys(pattern);
	}
	
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	@Override
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}


	@Override
	public String flushDB() {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
	}

	@Override
	public long dbSize() {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
	}

	@Override
	public String ping() {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
	}

	@Override
	public <T> T execute(RedisCallback<T> redisCallback) {
		return redisTemplate.execute(redisCallback);
	}
	
//    public List<Object> pipeline(List<PipelineExecutor> pipelineExecutors) {
//        Pipeline pipeline = jedis.pipelined();
//        for (PipelineExecutor executor : pipelineExecutors)
//            executor.load(pipeline);
//        return pipeline.syncAndReturnAll();
//    }
}
