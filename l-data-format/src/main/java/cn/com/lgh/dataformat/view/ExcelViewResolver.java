package cn.com.lgh.dataformat.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

/**
 * 
 * 
 * @ClassName: XlsViewResolver.java
 * @Description: 该类的功能描述
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:49:48 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
public class ExcelViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
    	//return new XlsView();
    	//return new EasyPoiXlsView();
    	return new ExcelView();
    }

}
