package org.mosquito.framework.jdbc.page;

import java.io.Serializable;

import lombok.Data;

/**
 * . Page封装实体
 * 
 * @author chenl
 * 
 */
@Data
public class PageInfo implements Serializable {

	private static final long serialVersionUID = -3172395473947353430L;

	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 总页数
	 */
	private int pageCount;

	/**
	 * 总记录数
	 */
	private long recordCount;

	/**
	 * 每页的记录数
	 */
	private int pageSize;

}
