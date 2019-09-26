package cn.com.lgh.cache;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LCacheApplicationTests {

	@CreateCache(expire = 100, name = "my.test.cache", cacheType = CacheType.BOTH, localExpire = 1000)
	private Cache<Long, String> testCache;

	@Test
	public void contextLoads() {
		testCache.put(111L, "这是测试数据！@#¥%……&");
		System.err.println("缓存中数据:"+testCache.get(111L));
		System.err.println(testCache.remove(111L));
	}

}
