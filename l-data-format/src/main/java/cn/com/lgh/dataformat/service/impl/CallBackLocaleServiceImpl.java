package cn.com.lgh.dataformat.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.com.lgh.dataformat.DataFormatException;
import cn.com.lgh.dataformat.service.ICallBackService;
import cn.com.lgh.dataformat.view.TaskViewResolver;

public class CallBackLocaleServiceImpl implements ICallBackService {

	@Override
	public Map<String, Object> getCallbackValue(String id) {
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> model = TaskViewResolver.taskValue.get(id);
		if(model == null || !model.containsKey("res_data")) {
			throw new DataFormatException("没有找到对应的数据!!!");
		}
		data.put("data", model.get("res_data"));
		if(model.containsKey("list_page")) {
			data.put("page", model.get("list_page"));
		}
		TaskViewResolver.taskValue.remove(id);
		return data;
	}

}
