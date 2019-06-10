package com.lgh.sys.manage.server.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgh.sys.manage.bean.auto.User;
import com.lgh.sys.manage.config.SysConstant;
import com.lgh.sys.manage.server.WrongPasswordPost;

/**
 * 
 * @ClassName: InMemoryWrongPasswordPostImpl
 * @Description: 默认的单机 密码错误次数校验实现
 * @Author lizhiting
 * @DateTime May 28, 2019 10:12:27 AM
 */
@Service
public class InMemoryWrongPasswordPostImpl implements WrongPasswordPost {
	
	Map<String, Map<String, Long>> errMap = null;
	
	@Autowired
	private SysConstant sysConstant;
	
	@Override
	public void add(User user) {
		String id = user.getName();
		if(errMap.containsKey(id)) {
			Long time = errMap.get(id).get(TIME_KEY);
			//如果上次密码错误的时间间隔大于规定的时间间隔，则重制密码错误次数
			if((System.currentTimeMillis() - time) > (sysConstant.getPwdErrTimeInterval().longValue() * 1000)) {
				errMap.remove(id);
				addNew(id);
			}else {
				Long num = errMap.get(id).get(NUM_KEY) + 1;
				Map<String, Long> value = errMap.get(id);
				value.put(TIME_KEY, System.currentTimeMillis());
				value.put(NUM_KEY, num);
			}
		}else {
			addNew(id);
		}

	}

	@Override
	public Map<String, Long> get(String id) {
		return errMap.get(id);
	}
	
	@Override
	public boolean verify(String id) {
		if(sysConstant.getPwdErrNum().longValue()<= 0) {
			return false;
		}
		if(errMap.containsKey(id)) {
			Long time = errMap.get(id).get(TIME_KEY);
			if((System.currentTimeMillis() - time) <= (sysConstant.getPwdErrTimeInterval().longValue() * 1000)) {
				Long num = errMap.get(id).get(NUM_KEY);
				if(num >= sysConstant.getPwdErrNum().longValue()) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void remove(String id) {
		errMap.remove(id);
	}
	
	private void addNew(String id) {
		Map<String, Long> value = new HashMap<String, Long>();
		value.put(TIME_KEY, System.currentTimeMillis());
		value.put(NUM_KEY, 1L);
		errMap.put(id, value);
	}
	
	@PostConstruct
	public void init() {
		errMap = new ConcurrentHashMap<>();
	}

}
