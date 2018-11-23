package com.tc.website.webapi.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据
 * @author unicorn
 */
@Data
public class Page<T> implements Serializable {

	/**
	 * 页数据列表
	 */
	private List<T> rows;

	/**
	 * 数据总条数
	 */
	private int total;

	/**
	 * 当前页号
	 */
	private int pageNumber;

	/**
	 * 每页条数
	 */
	private int pageSize;

	/**
	 * 总页数
	 */
	private int pageTotal;

	public Page() {

	}

	public Page(List<T> rows, int total, int pageNumber, int pageSize) {
		super();
		this.rows = rows;
		this.total = total;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.pageTotal = (int) Math.ceil(this.total * 1.0 / this.pageSize);
	}

}
