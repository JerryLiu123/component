package cn.com.lgh.dataformat.view;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class TaskViewResolver implements ViewResolver {
	
	public static Map<String, Map<String, Object>> taskValue = new ConcurrentHashMap<String, Map<String, Object>>();
	
	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return new TaskView();
	}

}
