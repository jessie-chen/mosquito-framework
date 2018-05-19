package org.mosquito.framework.jdbc.mybatis;

/** .
 * order by顺序定义实体
 * 
 * @author chenl
 * 
 */
public enum OrderDirection {
	// 升序
	ASC(0),
	// 降序
	DESC(1);

	private int code;

	OrderDirection(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.valueOf(code);
	}
}
