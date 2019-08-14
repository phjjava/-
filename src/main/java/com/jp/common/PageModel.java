package com.jp.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

public class PageModel<T> implements Serializable {

	private Integer pageNo = 1;// 当前页
	private Integer pageSize = 10;// 一页显示条数
	private String sortOrder;
	private String sortType;
	private PageInfo<T> pageInfo;//分页对象
	private Map<Integer, String> pages;//
	
	private List<?> list;// 当前页数据

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public PageInfo<T> getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo<T> pageInfo) {
		this.pageInfo = pageInfo;
		init();
	}
	
	public Map<Integer, String> getPages() {
		return pages;
	}

	public void setPages(Map<Integer, String> pages) {
		this.pages = pages;
	}

	private void init() {
		
		pages = new LinkedHashMap<>();
		//小于10页
		if (pageInfo.getPages() > 0 && pageInfo.getPages() <= 10) { 
			for (int i = 1; i < 11; i++) {
				pages.put(i, i + "");
				if (i == pageInfo.getPages())
					break;
			}
		} else if (pageInfo.getPages() > 10) {
			if (pageNo > pageInfo.getPages() - 7) { 
				// 最后10页显示情况
				for (int i = (int) (pageInfo.getPages() - 9); i <= pageInfo.getPages(); i++) {
					if (i == pageInfo.getPages() - 9)
						pages.put(1, 1 + "...");
					else
						pages.put(i, i + "");
				}
			} else if(pageNo > 5 && pageNo < pageInfo.getPages() - 5) {
				//中间10页
				for (int i = pageNo-3; i < pageNo+7; i++) {
					if (i == pageNo-3)
						pages.put(1, 1 + "...");
					else if(i == pageNo+6)
						pages.put((int)pageInfo.getPages(), "..." + pageInfo.getPages());
					else
						pages.put(i, i + "");
				}
			} else {
				//开始10页显示情况
				for (int i = 1; i < 11; i++) {
					if (i == 10)
						pages.put((int)pageInfo.getPages(), "..." + pageInfo.getPages());
					else
						pages.put(i, i + "");
				}
			}
		}
	}
	

}