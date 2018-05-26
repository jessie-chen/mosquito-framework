package org.mosquito.framework.jdbc.mybatis;

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
	 * 基础字段  <sql id="BaseColumns" />
	 */
	private String baseColumns;

	/**
	 * 命名空间
	 */
	private String namespace;


    // 别名 alias

    /**
     * 表的别名. <Alias/>
     */
    private String alias;

    /**
     * 别名字段.  <sql id="AliasColumns" />
     */
    private String aliasColumns;

    private String aliasColumnsId;

    /**
     * 别名结果集  <resultMap id="AliasResultMap" />
     * @return
     */
    private String aliasResultMap;

    private String aliasResultMapId;

	public List<CommonTableColumn> getAllColumnList() {
		List<CommonTableColumn> allColumnList = new ArrayList<CommonTableColumn>();
		allColumnList.add(idColumn);
		allColumnList.addAll(columnList);
		return allColumnList;
	}

}
