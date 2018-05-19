package org.mosquito.framework.jdbc.mybatis;

import lombok.Data;

/** .
 * order by语句块定义实体
 * 
 * @author chenl
 * 
 */
@Data
public class OrderByClause {

	private String filed;

	private OrderDirection orderDirection;

	public OrderByClause(String filed, OrderDirection orderDirection) {
		this.filed = filed;
		this.orderDirection = orderDirection;
	}

}
