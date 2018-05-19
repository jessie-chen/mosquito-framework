package org.mosquito.framework.jdbc.mybatis;

import lombok.Data;

/** .
 * Criteria定义实体
 * 
 * @author chenl
 * 
 */
@Data
public class Criteria {

	private String filed;

	private CriteriaCondition condition;

	private Object value1;

	private Object value2;

	public Criteria(String filed, CriteriaCondition condition, Object value1, Object value2) {
		this.filed = filed;
		this.condition = condition;
		this.value1 = value1;
		this.value2 = value2;
	}

}
