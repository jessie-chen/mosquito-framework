package tk.chandsir.mosquito.framework.jdbc.mybatis;

import lombok.Data;

/** .
 * 通用Mapper基本表字段定义实体
 * 
 * @author chenl
 * 
 */
@Data
public class CommonTableColumn {

	/**
	 * 数据库字段名
	 */
	private String column;

	/**
	 * 对应该类字段
	 */
	private String property;

	/**
	 * 数据库类型
	 */
	private String jdbcType;

}
