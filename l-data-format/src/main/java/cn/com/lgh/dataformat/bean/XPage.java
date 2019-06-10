package cn.com.lgh.dataformat.bean;



import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by xwtt on 15/4/19.
 * 拷贝自 zwfw 框架
 * 修改: lgh
 */
public class XPage implements Serializable{
	private Logger logger = LoggerFactory.getLogger(XPage.class);
    static final int DEFAULT_PAGE_SIZE = 10;
    static final int FIRST_PAGE_NUMBER = 1;

    /**当前页数 从1开始**/
    private int pageNumber = FIRST_PAGE_NUMBER;
    /**每页多少条**/
    private int pageSize = DEFAULT_PAGE_SIZE;
    /**总页数**/
    private int pageCount;
    /**查询总记录数**/
    private long recordCount;
    /** 起始行**/
    private int startRow;
    /**当前记录数**/
    private int nowSize;
    /**末行**/
    private int endRow;
    /**是否未最后一页**/
    private boolean last;
    /**是否为第一页**/
    private boolean first;
    
    public XPage(){

    }
    public XPage(int recordCount) {
        this.setRecordCount(recordCount);
    }

    public XPage(int currentPage, int recordCount) {
        this.setRecordCount(recordCount);
        if (currentPage > this.getPageCount()) {
            this.setPageNumber(this.getPageCount());
        } else {
            this.setPageNumber(currentPage);
        }
        calculateStartAndEndRow();
    }

    public XPage resetPageCount() {
        pageCount = -1;
        return this;
    }

    public int getPageCount() {
        if (pageCount < 0)
            pageCount = (int) Math.ceil((double) recordCount / pageSize);
        return pageCount;
    }

    public XPage setRecordCount(long recordCount) {
        this.recordCount = recordCount > 0 ? recordCount : 0;
        this.pageCount = (int) Math.ceil((double) recordCount / pageSize);
        return this;
    }

    public int getOffset() {
        return pageSize * (pageNumber - 1);
    }

    public boolean isFirst() {
        return pageNumber == 1;
    }

    public boolean isLast() {
        if (pageCount == 0)
            return true;
        return pageNumber == pageCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public XPage setPageNumber(int pn) {
        pageNumber = pn > FIRST_PAGE_NUMBER ? pn : FIRST_PAGE_NUMBER;
        calculateStartAndEndRow();
        return this;
    }
    
    public int getStartRow() {
		return startRow;
	}
	public XPage setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}
	public int getEndRow() {
		return endRow;
	}
	public XPage setEndRow(int endRow) {
		this.endRow = endRow;
		return this;
	}
	public XPage setPageSize(int pageSize) {
        this.pageSize = (pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE);
        calculateStartAndEndRow();
        return resetPageCount();
    }
	
    /**
     * 计算起止行号
     */
    private void calculateStartAndEndRow() {
        this.startRow = this.pageNumber > 0 ? (this.pageNumber - 1) * this.pageSize : 0;
        this.endRow = this.startRow + this.pageSize * (this.pageNumber > 0 ? 1 : 0);
    }

    public String toString() {
        return String.format("size: %d, total: %d, page: %d/%d", pageSize,
                recordCount, pageNumber, this.getPageCount());
    }
	public int getNowSize() {
		return nowSize;
	}
	public void setNowSize(int nowSize) {
		this.nowSize = nowSize;
	}
}
