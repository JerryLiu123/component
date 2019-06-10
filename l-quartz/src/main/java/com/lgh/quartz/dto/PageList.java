package com.lgh.quartz.dto;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> {

	
    private int page;   //当前页
    private int totalRows;   //总行数
    private int pages;    //总页数
    private List<T> list = new ArrayList<T>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        if(list==null){
            list=new ArrayList<T>();
        }
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
    
}
