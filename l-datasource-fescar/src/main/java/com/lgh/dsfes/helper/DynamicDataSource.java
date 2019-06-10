package com.lgh.dsfes.helper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.stat.DruidDataSourceStatManager;



/**
 * 
 * @ClassName: DynamicDataSource.java
 * @Description: 用来动态创建数据源
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月8日 上午9:12:04 
 *
 * Modification History:
 * Date         Author          Version            Description<br>
 *---------------------------------------------------------*<br>
 * 2018年8月8日     xiaoming          v1.0.0               修改原因<br>
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
	
	private Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    //保存动态创建的数据源
    private Map<Object, Object> targetDataSource = new ConcurrentHashMap<>();
    //默认数据源
    private Object dynamicDefaultTargetDataSource; 
    
    private DynamicDataSource() {
		super();
	}
    
    public static DynamicDataSource getInstance() {
    	return Singleton.INSTANCE.getInstance();
    }
    
    private static enum Singleton {
    	INSTANCE;
    	private DynamicDataSource singleton;
    	private Singleton() {
    		singleton = new DynamicDataSource();
    	}
        public DynamicDataSource getInstance(){
            return singleton;
        }
    }
	
    /**
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()  
     * @Function: DynamicDataSource.java
     * @Description: 重写使用那个数据源的方法
     *
     * @return 数据源的 key
     *
     * @version: v1.0.0
     * @author: xiaoming
     * @date: 2018年8月8日 上午9:24:01 
     *
     * Modification History:<br>
     * Date         Author          Version            Description <br>
     *------------------------------------------------------------*<br>
     * 2018年8月8日     xiaoming           v1.0.0               修改原因 <br>
     */
	@Override
	protected Object determineCurrentLookupKey() {
		String datasource = DBContextHolder.getDataSource();
		if(datasource == null || StringUtils.isBlank(datasource)) {
			logger.info("----使用默认数据源----");
		}else {
			logger.info("----使用数据源----"+datasource);
		}
		return datasource;
	}
	
	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);
		this.targetDataSource = targetDataSources;
		super.afterPropertiesSet();// 必须添加该句，否则新添加数据源无法识别到
	} 
	
	/**
	 * 
	 * @Function: DynamicDataSource.java
	 * @Description: 根据key 删除数据源
	 *
	 * @param key
	 * @return
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月8日 上午9:32:37 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description <br>
	 *------------------------------------------------------------*<br>
	 * 2018年8月8日     xiaoming           v1.0.0               修改原因 <br>
	 */
	public boolean delDatasources(String key) {
		Map<Object, Object> dynamicTargetDataSources2 = this.targetDataSource;
		if (dynamicTargetDataSources2.containsKey(key)) {
			Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
			for (DruidDataSource l : druidDataSourceInstances) {
				if (key.equals(l.getName())) {
					dynamicTargetDataSources2.remove(key);
					DruidDataSourceStatManager.removeDataSource(l);
					setTargetDataSources(dynamicTargetDataSources2);// 将map赋值给父类的TargetDataSources
					super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}	
	
	/**
	 * 
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#setDefaultTargetDataSource(java.lang.Object)  
	 * @Function: DynamicDataSource.java
	 * @Description: 设置默认的数据源
	 *
	 * @param defaultTargetDataSource
	 *
	 * @version: v1.0.0
	 * @author: xiaoming
	 * @date: 2018年8月8日 上午9:33:55 
	 *
	 * Modification History:<br>
	 * Date         Author          Version            Description <br>
	 *------------------------------------------------------------*<br>
	 * 2018年8月8日     xiaoming           v1.0.0               修改原因 <br>
	 */
	@Override
	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
	}
	

	public Map<Object, Object> getTargetDataSource() {
		return targetDataSource;
	}

	public void setTargetDataSource(Map<Object, Object> targetDataSource) {
		this.targetDataSource = targetDataSource;
	}

	public Object getDynamicDefaultTargetDataSource() {
		return dynamicDefaultTargetDataSource;
	}

	public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
		this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
	}
}
