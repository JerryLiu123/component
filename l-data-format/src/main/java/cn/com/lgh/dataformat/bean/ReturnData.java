package cn.com.lgh.dataformat.bean;


/**
 * @ClassName: ReturnData.java
 * @Description: 返回数据的实体，此项目只会对改返回实体感兴趣，返回别的实体将不在页面展示
 *
 * @version: v1.0.0
 * @author: xiaoming
 * @date: 2018年8月7日 上午11:33:20 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年8月7日     xiaoming          v1.0.0               修改原因
 */
public class ReturnData<T> extends BaseBean {

	private static final long serialVersionUID = 1L;
	
	private int code;
	private String message;
	private T resData;
	
	private XPage page;
	
	private ExcelBasisBean excelBasis;
	public ReturnData() {
	}
	
	public ReturnData<T> req200(T a){
		return this.req(200, "请求成功", a, null);
	}
	
	public ReturnData<T> req500(T a){
		return this.req(500, "请求失败", a, null);
	}
	
	public ReturnData<T> req200(T a, XPage page){
		return this.req(200, "请求成功", a, page);
	}
	
	public ReturnData<T> req500(T a, XPage page){
		return this.req(500, "请求失败", a, page);
	}
	
	public ReturnData<T> req(int code, String msg, T data, XPage page){
		return this.getRequest(code, msg, data, page);
	}
	
	public ReturnData<T> getRequest(int code, String mes, T a, XPage page){
		this.setCode(code);
		this.setMessage(mes);
		this.setResData(a);
		this.setPage(page);
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResData() {
		return resData;
	}

	public void setResData(T resData) {
		this.resData = resData;
	}

	public XPage getPage() {
		return page;
	}

	public void setPage(XPage page) {
		this.page = page;
	}

	public ExcelBasisBean getExcelBasis() {
		return excelBasis;
	}

	public void setExcelBasis(ExcelBasisBean excelBasis) {
		this.excelBasis = excelBasis;
	}
}
