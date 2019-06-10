package cn.com.lgh.dataformat.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import cn.com.lgh.dataformat.DataFormatException;
import cn.com.lgh.dataformat.bean.ExcelBasisBean;
import cn.com.lgh.dataformat.util.ExcelUtil;

/**
 * 
 * Copyright: Copyright (c) 2018 LanRu-Caifu
 * 
 * @ClassName: ExcelView.java
 * @Description: 导出excel view 解析 这里没有用 AbstractXlsxView 和AbstractXlsView是因为这个都是只能导出一种，而且还好出问题
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年10月29日 下午2:59:18 
 *
 * Modification History:<br>
 * Date         Author          Version            Description<br>
 *---------------------------------------------------------*<br>
 * 2018年10月29日     xiaoming           v1.0.0               修改原因<br>
 */
public class ExcelView extends AbstractView{
	
	public ExcelView() {
		super();
		setContentType("application/excel");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		    if(!model.containsKey("res_data") || !model.containsKey("excelBasis")) {
		    	throw new DataFormatException("导出excel异常，未找到要导出的数据");
		    }
		    
		    Object datas = model.get("res_data");
		    Object ob = model.get("excelBasis");
		    if(datas == null || ob == null) {
		    	throw new DataFormatException("导出excel异常，未找到要导出的数据");
		    }
		    
		    if((!(ob instanceof ExcelBasisBean)) || (!(datas instanceof List))) {
		    	throw new DataFormatException("导出excel异常，数据格式错误");
		    }
		    ExcelBasisBean excelBasis = (ExcelBasisBean) ob;
		    List datasList = (List) datas;
	    	if(datasList == null || datasList.isEmpty()) {
	    		throw new DataFormatException("导出excel异常，导出的数据为空!!!");
	    	}
	    	ExcelUtil.exportExcel(datasList, excelBasis.getTitle(), excelBasis.getSheetName(), 
	    			datasList.get(0).getClass(), excelBasis.getFileName(), 
	    			excelBasis.getIsCreateHeader() == 0 ? false:true, response);
		    
		    
		}catch (Exception e) {
			logger.error("导出 excel 失败", e);
			throw new DataFormatException("导出excel异常", e);
		}
	}

}
