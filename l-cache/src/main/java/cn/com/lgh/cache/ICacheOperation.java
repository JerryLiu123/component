package cn.com.lgh.cache;

import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public interface ICacheOperation {
	/**
	 * 设置新的值，并将原来的值返回
	 * @param key key
	 * @param value value
	 * @param liveTime 超时时间
	 * @return
	 */
	public Object getSet(String key, String value, long liveTime);
	
	/**
	 * 删除Key为这些的数据
	 * @param keys
	 * @throws Exception
	 */
	public void delForValue(String... keys);
	
	/**
	 * 设置值，并添加超时时间，
	 * @param key
	 * @param value
	 * @param liveTime 超时时间
	 * @throws Exception
	 */
	public void set(String key, Object value, long liveTime) throws Exception ;
	
	/**
	 * 重新设置Key的超时时间
	 * @param key
	 * @param timeout
	 * @return
	 */
	public boolean resetExpireTime(String key, long timeout);
	
	/**
	 * 设置值，并设置偏移量
	 * @param key
	 * @param value
	 * @param offset
	 * @throws Exception
	 */
	public void setByOffset(String key, Object value, long offset) throws Exception;
	
	/**
	 * 设置值，如果key不存在的话则替换内容并返回true，key存在返回false
	 * @param key
	 * @param value
	 * @return
	 */
	public Boolean setValueForExist(String key, String value);

	/**
	 * 设置值
	 * @param key
	 * @param value
	 */
	public void set(String key, Object value);

	/**
	 * 获得 key的内容
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 出队操作，从队列左边取出值
	 * @param key
	 * @return
	 */
	public Object dequeue(String key);
	/**
	 * 入队操作，从队列右边插入值
	 * @param key
	 * @param value
	 * @return
	 */
	public Long enqueue(String key, Object... value);
	
	/**
	 * 获得队列大小
	 * @param key
	 * @return
	 */
	public Long getQueueSize(String key);

	/**
	 * 插入ZSet集合，并设置其分数
	 * @param key
	 * @param value 值
	 * @param score 分数
	 * @return
	 */
	public boolean setZSet(String key, String value, Long score);
	
	/**
	 * 获得指定value的分数值
	 * @param key
	 * @param value
	 * @return
	 */
	public Double getZSetScore(String key, String value);
	
	/**
	 * 对ZSet指定value的分进行递增，并返回递增后的值
	 * @param key
	 * @param value
	 * @param increment
	 * @return
	 */
	public Double incrementZSetScore(String key, String value, Long increment);
	
	/**
	 * 获得指定value的键值对
	 * @param key
	 * @param value
	 * @return
	 */
	public Cursor<TypedTuple<Object>> getZSetByValue(String key, String value);
	
	/**
	 * 获得ZSet集合所有的值和分数
	 * @param key
	 * @return
	 */
	public Cursor<TypedTuple<Object>> getZSetAll(String key);
	
	/**
	 * 获得ZSet指定分数区间的所有值
	 * @param key
	 * @param start 
	 * @param end
	 * @return
	 */
	public Set<Object> getZSetByRange(String key, long start, long end);
	
	/**
	 * 获得ZSet集合中所有的value值
	 * @param key
	 * @return
	 */
	public Set<Object> getZSetAllValue(String key);
	
	/**
	 * 获得ZSet指定分数区间的所有值的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Long countZSetByRange(String key, double min, double max);
	
	/**
	 * 获得集合中所有元素的个数
	 * @param key
	 * @return
	 */
	public Long countZSet(String key);
	
	/**
	 * @Function: ICacheOperation.java
	 * @Description: 插入 hash 数据 使用默认的 mapName
	 *
	 * @param key
	 * @param value
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月29日 下午5:33:37 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description<br>
	 *---------------------------------------------------------*<br>
	 * 2018年8月29日     xiaoming           v1.0.0               修改原因<br>
	 */
	public void setHashOps(String key, Object value);
	
	/**
	 * 
	 * @Function: ICacheOperation.java
	 * @Description: 插入 hash 数据
	 *
	 * @param mapName
	 * @param key
	 * @param value
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月29日 下午5:35:17 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description<br>
	 *---------------------------------------------------------*<br>
	 * 2018年8月29日     xiaoming           v1.0.0               修改原因<br>
	 */
	public void setHashOps(String mapName, String key, Object value);
	
	public void setHashOps(String mapName, Map<Object, Object> values);
	
	public Object getZHashOps(String key);
	
	public Object getZHashOps(String mapName, String key);

	public Object getkeys(String pattern);
	
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(final String key);

	/**
	 * 
	 * @Function: ICacheOperation.java
	 * @Description: 清空库
	 *
	 * @return
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月29日 下午5:36:20 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description<br>
	 *---------------------------------------------------------*<br>
	 * 2018年8月29日     xiaoming           v1.0.0               修改原因<br>
	 */
	public String flushDB();

	/**
	 * 
	 * @Function: ICacheOperation.java
	 * @Description: 获得现在DB的大小
	 *
	 * @return
	 * @throws：异常描述
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月29日 下午5:36:29 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description<br>
	 *---------------------------------------------------------*<br>
	 * 2018年8月29日     xiaoming           v1.0.0               修改原因<br>
	 */
	public long dbSize();

	public String ping();
	
	public <T> T execute(RedisCallback<T> redisCallback);
}
