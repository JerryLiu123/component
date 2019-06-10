package cn.com.lgh.dataformat.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;


public class ExcelUtil {

	/**
	 * list      导出列表
	 * title     标题
	 * sheetName 页名
	 * pojoClass 实体类（例：User.class）
	 * fileName  导出文件名，需带有扩展名（.xlsx/.xls）
	 * isCreateHeader 是否创建表头
	 * addIndex  是否添加序号
	 * 
	 * */
	
	public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
			boolean isCreateHeader, HttpServletResponse response) {
		ExportParams exportParams = new ExportParams(title, sheetName);
		exportParams.setCreateHeadRows(isCreateHeader);
		defaultExport(list, pojoClass, fileName, response, exportParams);

	}

	public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
			HttpServletResponse response) {
		defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
	}

	public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
		defaultExport(list, fileName, response);
	}

	private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
			ExportParams exportParams) {
		
		if(fileName.endsWith(".xls")) {
			exportParams.setType(ExcelType.HSSF);
		}else if(fileName.endsWith(".xlsx")) {
			exportParams.setType(ExcelType.XSSF);
		}
		
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
		if (workbook != null);
		downLoadExcel(fileName, response, workbook);
		
	}

	private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
		Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.XSSF);
		if (workbook != null)
			;
		downLoadExcel(fileName, response, workbook);
	}

	/**
	 * titleRows      表格标题行数,默认0
	 * headerRows     表头行数,默认1
	 * */
	public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		List<T> list = null;
		try {
			list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
		} catch (NoSuchElementException e) {
			System.out.println("模板不能为空");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

	public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
			Class<T> pojoClass) {
		if (file == null) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		List<T> list = null;
		try {
			list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
		} catch (NoSuchElementException e) {
			System.out.println("excel文件不能为空");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
}
