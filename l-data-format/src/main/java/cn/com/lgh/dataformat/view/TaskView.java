package cn.com.lgh.dataformat.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @ClassName: TaskView.java
 * @Description: task View 解析
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月13日 上午11:34:39 
 *
 * Modification History:
 * Date         Author          Version            Description<br>
 *---------------------------------------------------------*<br>
 * 2018年8月13日     xiaoming          v1.0.0               修改原因<br>
 */
public class TaskView extends AbstractView {
	private Logger logger = LoggerFactory.getLogger(TaskView.class);
	
	public TaskView() {
		super();
		setContentType("application/task");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("将Collentor的返回值存入 缓存，等待提取-id:"+request.getAttribute("backID")+"----sessionId:"+request.getSession().getId());
		//通过sessionID获得session
		request.getSession().setAttribute("res_data", model.get("res_data"));//放入数据
		if(model.containsKey("list_page")) {
			request.getSession().setAttribute("list_page", model.get("list_page"));//放入分页信息
		}
		//放入本地一份，当没有redis 的时候就从本地取
		TaskViewResolver.taskValue.put(request.getAttribute("backID")+"", model);
	}

}
