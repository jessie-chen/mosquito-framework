package org.mosquito.framework.jdbc.constant;

/**
 * . 是否删除标识常量
 * 
 * @author chenl
 * 
 */
public enum DeletedFlag {
	// 不删除
	DELETEDFLAG_NO(0, "不删除"),
	// 删除
	DELETEDFLAG_YES(1, "删除");

	private Integer value;

	private String name;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * DeletedFlag构造方法
	 * 
	 * @param value
	 * @param name
	 * 
	 */
	DeletedFlag(Integer value, String name) {
		this.setValue(value);
		this.setName(name);
	}
}
