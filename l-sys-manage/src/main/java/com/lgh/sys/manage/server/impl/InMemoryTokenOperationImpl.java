package com.lgh.sys.manage.server.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lgh.sys.manage.server.TokenOperation;

/**
 * 
 * @ClassName: InMemoryTokenOperationImpl
 * @Description: 默认的单机 token 操作实现
 * @Author lizhiting
 * @DateTime May 28, 2019 10:12:52 AM
 */
@Service
public class InMemoryTokenOperationImpl implements TokenOperation {
	
	Map<String, String> tokenMap = null;
	
	@Override
	public void putToken(String id, String token) {
		tokenMap.put(id, token);

	}

	@Override
	public void deleteToken(String id) {
		 tokenMap.remove(id);

	}

	@Override
	public boolean containToken(String id, String token) {
        if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(token) && tokenMap.containsKey(id)) {
            return true;
        }
        return false;
	}
	

	@Override
	public String getToken(String id) {
		return tokenMap.get(id);
	}
	
	@Override
	public int getTokenSize() {
		return tokenMap.size();
	}
	
	@PostConstruct
	public void init() {
		tokenMap = new ConcurrentHashMap<>();
	}

}
