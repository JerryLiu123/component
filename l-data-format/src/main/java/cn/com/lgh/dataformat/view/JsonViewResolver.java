package cn.com.lgh.dataformat.view;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 
 * 
 * @ClassName: JsonViewResolver.java
 * @Description: json view 解析器，用的是MappingJackson2JsonView
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:47:54 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
public class JsonViewResolver implements ViewResolver {
	private Logger logger = LoggerFactory.getLogger(JsonViewResolver.class);
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Set<String> keys = new HashSet<String>();
        keys.add("res_ip");
        keys.add("res_state");
        keys.add("res_msg");
        keys.add("res_data");
        keys.add("res_time");
        keys.add("res_url");
        keys.add("list_page");
        keys.add("res_err");
        view.setModelKeys(keys);//指定解析那个model
        view.setPrettyPrint(true);
        return view;
    }

}
