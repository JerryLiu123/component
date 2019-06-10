package cn.com.lgh.dataformat.bean;

public class ExcelBasisBean extends BaseBean {

	private String fileName;//文件名 带后缀名的啊 xls或xlsx
	private String title;//标题
	private String sheetName;//页名
	private Integer isCreateHeader;//是否创建表头 0 不创建 1 创建
	private Integer isAddIndex;//是否添加序号  0 不创建 1 创建
	
	
	public ExcelBasisBean() {
		super();
		setFileName(System.currentTimeMillis()+".xlsx");
		setTitle(null);
		setSheetName("sheet1");
		setIsCreateHeader(1);
		setIsAddIndex(0);
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public Integer getIsCreateHeader() {
		return isCreateHeader;
	}
	public void setIsCreateHeader(Integer isCreateHeader) {
		this.isCreateHeader = isCreateHeader;
	}
	public Integer getIsAddIndex() {
		return isAddIndex;
	}
	public void setIsAddIndex(Integer isAddIndex) {
		this.isAddIndex = isAddIndex;
	}
	
	
}
