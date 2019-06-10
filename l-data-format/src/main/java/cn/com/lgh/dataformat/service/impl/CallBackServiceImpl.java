package cn.com.lgh.dataformat.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.session.Session;
import org.springframework.session.SessionRepository;

import cn.com.lgh.dataformat.DataFormatException;
import cn.com.lgh.dataformat.service.ICallBackService;
import cn.com.lgh.dataformat.view.TaskViewResolver;

public class CallBackServiceImpl implements ICallBackService {

/*	@Resource(name = "sessionRepository")
	private SessionRepository<ExpiringSession> sessionRepository;

	@Override
	public Map<String, Object> getCallbackValue(String id) {
		Map<String, Object> data = new HashMap<>();
		ExpiringSession session = sessionRepository.getSession(id);// 根据sessionid 获取 session 同而实现共享
		if (session == null) {
			throw new DataFormatException("没有找到对应的数据!!!");
		}
		Object o = session.getAttribute("res_data");
		data.put("data", o);
		if (session.getAttribute("list_page") != null) {
			data.put("page", session.getAttribute("list_page"));
		}
		TaskViewResolver.taskValue.remove(id);
		return data;
	}*/
	@Resource(name = "sessionRepository")
	private SessionRepository<Session> sessionRepository;

	@Override
	public Map<String, Object> getCallbackValue(String id) {
		Map<String, Object> data = new HashMap<>();
		Session session = sessionRepository.findById(id);// 根据sessionid 获取 session 同而实现共享
		if (session == null) {
			throw new DataFormatException("没有找到对应的数据!!!");
		}
		Object o = session.getAttribute("res_data");
		data.put("data", o);
		if (session.getAttribute("list_page") != null) {
			data.put("page", session.getAttribute("list_page"));
		}
		TaskViewResolver.taskValue.remove(id);
		return data;
	}	

}
