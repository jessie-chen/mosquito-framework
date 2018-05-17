package tk.chandsir.mosquito.framework.jdbc.mybatis;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/** .
 * 通用Mapper基本表结构定义实体
 * 
 * @author chenl
 * 
 */
@Data
public class CommonTable {

	/**
	 * 数据库表名
	 */
	private String table;

	/**
	 * 表对应类
	 */
	private String classType;

	/**
	 * 主键
	 */
	private CommonTableColumn idColumn;

	/**
	 * 除主键外的其他字段
	 */
	private List<CommonTableColumn> columnList;

	/**
	 * 返回的基础Map
	 */
	private String baseResultMap;

	/**
	 * 主键生成方式
	 */
	private KeyGenMode keyGenMode;

	/**
	 * 基础字段ID
	 */
	private String baseColumnsId;

	/**
	 * 基础字段
	 */
	private String baseColumns;

	/**
	 * 命名空间
	 */
	private String namespace;

	public List<CommonTableColumn> getAllColumnList() {
		List<CommonTableColumn> allColumnList = new ArrayList<CommonTableColumn>();
		allColumnList.add(idColumn);
		allColumnList.addAll(columnList);
		return allColumnList;
	}

}
