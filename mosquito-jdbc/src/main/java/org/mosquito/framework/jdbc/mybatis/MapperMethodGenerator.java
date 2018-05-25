package org.mosquito.framework.jdbc.mybatis;

import org.mosquito.framework.core.utils.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.List;

/**
 * . 通用Mapper单表操作方法生成器
 * 
 * @author chenl
 * 
 */
public class MapperMethodGenerator {

	private static final String INSERT = "insert";

	// private static final String INSERT_SELECTIVE = "insertSelective";

	private static final String INSERT_BATCH = "insertBatch";

	private static final String UPDATE = "update";

	private static final String UPDATE_SELECTIVE = "updateSelective";

	private static final String DELETE_PHYSICALLY = "deletePhysically";

	// private static final String DISABLE = "disable";
	//
	// private static final String ENABLE = "enable";

	private static final String GET = "get";

	private static final String GET_BY_IDS = "getByIds";

	private static final String SELECT_ALL = "selectAll";
	
	private static final String SELECT_COUNT = "selectCount";

	private static final String SEARCH = "search";

	private static final String CREATION_DATE = "create_time";

	private static final String CREATED_BY = "creator";

	private static final String UPDATION_DATE = "update_time";

	private static final String UPDATED_BY = "modifier";

	private static final String DELETED = "deleted";

	private static final String ID = "id";

	public static void process(Writer writer, CommonTable table, String idProertyClassType,
			  HashSet<String> existMethodSet, KeyGenMode keyGenMode) throws IOException {

		if (table.getBaseColumns() == null) {
			writer.write(addBaseColumnsSQL(table));
		}

		// if (!existMethodSet.contains(INSERT)) {
		// writer.write(addInsertAndReturnId(table, idProertyClassType, keyGenMode));
		// }

		if (!existMethodSet.contains(INSERT)) {
			writer.write(addInsertSelectiveAndReturnId(table, idProertyClassType, keyGenMode));
		}

		if (!existMethodSet.contains(INSERT_BATCH)) {
			writer.write(addInsertBatch(table, idProertyClassType, keyGenMode));
		}

		if (!existMethodSet.contains(UPDATE)) {
			writer.write(addUpdateByPrimaryKey(table, idProertyClassType));
		}

		if (!existMethodSet.contains(UPDATE_SELECTIVE)) {
			writer.write(addUpdateByPrimaryKeySelective(table, idProertyClassType));
		}

		if (!existMethodSet.contains(DELETE_PHYSICALLY)) {
			writer.write(addDeleteByPrimaryKey(table, idProertyClassType));
		}

		// if (!existMethodSet.contains(DISABLE)) {
		// writer.write(addDisableByPrimaryKey(table, idProertyClassType));
		// }
		//
		// if (!existMethodSet.contains(ENABLE)) {
		// writer.write(addEnableByPrimaryKey(table, idProertyClassType));
		// }

		if (!existMethodSet.contains(GET)) {
			writer.write(addSelectByPrimaryKey(table, idProertyClassType));
		}

		if (!existMethodSet.contains(GET_BY_IDS)) {
			writer.write(addSelectByPrimaryKeys(table, idProertyClassType));
		}

		if (!existMethodSet.contains(SELECT_ALL)) {
			writer.write(addSelectByModel(table, idProertyClassType));
		}
		
		if (!existMethodSet.contains(SELECT_COUNT)) {
			writer.write(addSelectCountByModel(table, idProertyClassType));
		}

		if (!existMethodSet.contains(SEARCH)) {
			writer.write(addSearch(table, idProertyClassType));
		}

	}

	private static String addBaseColumnsSQL(CommonTable table) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<sql id=\"" + table.getBaseColumnsId() + "\" >\n");
		sb.append(getColumns(table));
		sb.append("\n</sql>\n");
		return sb.toString();
	}

	// private static String addInsertAndReturnId(CommonTable table, String
	// idProertyClassType, KeyGenMode keyGenMode) {
	// StringBuilder sb = new StringBuilder();
	// sb.append(getInsertHeader(table, idProertyClassType, "insert",
	// table.getClassType(), keyGenMode));
	// sb.append("\n insert into " + table.getTable());
	// sb.append("(" + getColumns(table) + ")");
	// sb.append("\n values " + getValues(table.getAllColumnList()));
	// sb.append("\n</insert>\n");
	// return sb.toString();
	// }

	private static String addInsertSelectiveAndReturnId(CommonTable table, String idProertyClassType,
			  KeyGenMode keyGenMode) {
		StringBuilder sb = new StringBuilder();
		// sb.append(getInsertHeader(table, idProertyClassType, "insertSelective",
		// table.getClassType(), keyGenMode));
		sb.append(getInsertHeader(table, idProertyClassType, "insert", table.getClassType(), keyGenMode));
		sb.append("\n insert into " + table.getTable());
		sb.append(getIfColumns(table));
		sb.append("\n    values " + getIfValues(table.getAllColumnList()));
		sb.append("\n</insert>\n");
		return sb.toString();
	}

	private static String addInsertBatch(CommonTable table, String idProertyClassType, KeyGenMode keyGenMode) {
		StringBuilder sb = new StringBuilder();
		sb.append(getInsertHeader(table, idProertyClassType, "insertBatch", "java.util.List", keyGenMode));
		sb.append("\n insert into " + table.getTable());
		sb.append("(" + getColumns(table) + ")");
		sb.append("\n    values " + getBatchValues(table.getAllColumnList()));
		sb.append("\n</insert>\n");
		return sb.toString();
	}

	private static String addUpdateByPrimaryKey(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n <update id=\"update\" parameterType=\"" + table.getClassType() + "\" >");
		sb.append("\n update " + table.getTable());
		sb.append(getConditions4Update(table.getColumnList(), false));
		sb.append("\n where " + getKeyCondition(table.getIdColumn()));
		sb.append("\n</update>\n");
		return sb.toString();
	}

	private static String addUpdateByPrimaryKeySelective(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n <update id=\"updateSelective\" parameterType=\"" + table.getClassType() + "\" >");
		sb.append("\n update " + table.getTable());
		sb.append(getConditions4Update(table.getColumnList(), true));
		sb.append("\n where " + getKeyCondition(table.getIdColumn()));
		sb.append("\n</update>\n");
		return sb.toString();
	}

	private static String addDeleteByPrimaryKey(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<delete id=\"deletePhysically\"  parameterType=\"" + idProertyClassType + "\" >");
		sb.append("\n delete  from " + table.getTable());
		sb.append("\n    where " + table.getIdColumn().getColumn() + " in ");
		sb.append("\n    <foreach collection=\"list\" item=\"id\" open=\"(\" separator=\",\" close=\")\">");
		sb.append("\n        #{id}");
		sb.append("\n    </foreach>");
		sb.append("\n</delete>\n");
		return sb.toString();
	}

	// private static String addDisableByPrimaryKey(CommonTable table, String
	// idProertyClassType) {
	// StringBuilder sb = new StringBuilder();
	// sb.append("\n<update id=\"disable\" parameterType=\"" + idProertyClassType +
	// "\" >");
	// sb.append("\n update " + table.getTable() + " set enabled_flag = 0");
	// sb.append("\n where " + table.getIdColumn().getColumn() + " in ");
	// sb.append("\n <foreach collection=\"list\" item=\"id\" open=\"(\"
	// separator=\",\" close=\")\">");
	// sb.append("\n #{id}");
	// sb.append("\n </foreach>");
	// sb.append("\n</update>\n");
	// return sb.toString();
	// }
	//
	// private static String addEnableByPrimaryKey(CommonTable table, String
	// idProertyClassType) {
	// StringBuilder sb = new StringBuilder();
	// sb.append("\n<update id=\"enable\" parameterType=\"" + idProertyClassType +
	// "\" >");
	// sb.append("\n update " + table.getTable() + " set enabled_flag = 1");
	// sb.append("\n where " + table.getIdColumn().getColumn() + " in ");
	// sb.append("\n <foreach collection=\"list\" item=\"id\" open=\"(\"
	// separator=\",\" close=\")\">");
	// sb.append("\n #{id}");
	// sb.append("\n </foreach>");
	// sb.append("\n</update>\n");
	// return sb.toString();
	// }

	private static String addSelectByPrimaryKey(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<select id=\"get\" resultMap=\"" + table.getBaseResultMap() + "\" parameterType=\""
				  + idProertyClassType + "\" >");
		sb.append("\n select ");
		sb.append(getColumns(table));
		sb.append("\n    from " + table.getTable());
		sb.append("\n    where " + getKeyCondition(table.getIdColumn()));
		sb.append("\n</select>\n");
		return sb.toString();
	}

	private static String addSelectByPrimaryKeys(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<select id=\"getByIds\" resultMap=\"" + table.getBaseResultMap() + "\" parameterType=\""
				  + idProertyClassType + "\" >");
		sb.append("\n select ");
		sb.append(getColumns(table));
		sb.append("\n    from " + table.getTable());
		sb.append("\n    where " + table.getIdColumn().getColumn() + " in ");
		sb.append("\n    <foreach collection=\"list\" item=\"id\" open=\"(\" separator=\",\" close=\")\">");
		sb.append("\n        #{id}");
		sb.append("\n    </foreach>");
		sb.append("\n</select>\n");
		return sb.toString();
	}

	private static String addSearch(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<select id=\"search\" resultMap=\"" + table.getBaseResultMap() + "\" parameterType=\""
				  + table.getClassType() + "\" >");
		sb.append("\n select ");
		sb.append(getColumns(table));
		sb.append("\n    from " + table.getTable());
		sb.append(getConditions4QueryWithoutDefault(table.getAllColumnList(), null, null));
		sb.append("\n</select>\n");
		return sb.toString();
	}

	private static String addSelectByModel(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<select id=\"selectAll\" resultMap=\"" + table.getBaseResultMap() + "\" parameterType=\""
				  + table.getClassType() + "\" >");
		sb.append("\n select ");
		sb.append(getColumns(table));
		sb.append("\n    from " + table.getTable());
		sb.append(getConditions4Query(table.getAllColumnList(), null, null));
		sb.append("\n</select>\n");
		return sb.toString();
	}
	
	private static String addSelectCountByModel(CommonTable table, String idProertyClassType) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<select id=\"selectCount\" resultType=\"java.lang.Long\" parameterType=\""
				  + table.getClassType() + "\" >");
		sb.append("\n select count(1)");
		sb.append("\n    from " + table.getTable());
		sb.append(getConditions4Query(table.getAllColumnList(), null, null));
		sb.append("\n</select>\n");
		return sb.toString();
	}

	private static String getInsertHeader(CommonTable table, String idProertyClassType, String method,
			  String parameterType, KeyGenMode globalKeyGenMode) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<insert id=\"" + method + "\" parameterType=\"" + parameterType + "\" ");
		KeyGenMode keyGenMode = table.getKeyGenMode();
		if (keyGenMode == null) {
			keyGenMode = globalKeyGenMode;
		}
		if (keyGenMode != null) {
			if (KeyGenMode.CUSTOM == keyGenMode) {
				sb.append(">");
			} else {
				sb.append("useGeneratedKeys=\"true\" keyProperty=\"" + table.getIdColumn().getProperty() + "\">");
				if (KeyGenMode.DB_UUID == keyGenMode) {
					sb.append("<selectKey keyProperty=\"" + table.getIdColumn().getProperty() + "\" resultType=\""
							  + idProertyClassType + "\" order=\"BEFORE\">");
					sb.append(StringUtils.defaultString(keyGenMode.getValue(),
							  "select replace(uuid(),'-','') from dual"));
					sb.append("</selectKey>");
				} else if (KeyGenMode.MYCAT == keyGenMode) {
					sb.append("<selectKey keyProperty=\"" + table.getIdColumn().getProperty() + "\" resultType=\""
							  + idProertyClassType + "\" order=\"BEFORE\">");
					sb.append(StringUtils.defaultString(keyGenMode.getValue(),
							  "select next value for MYCATSEQ_" + table.getTable().toUpperCase()));
					sb.append("</selectKey>");
				} else if (KeyGenMode.IDENTITY == keyGenMode) {
					// xxx
					sb.append("");
				} else if (KeyGenMode.UUID == keyGenMode) {
					sb.append("<bind name=\"" + table.getIdColumn().getProperty() + "\" value='"
							  + StringUtils.defaultString(keyGenMode.getValue(),
									"@java.util.UUID@randomUUID().toString().replace(\"-\", \"\")")
							  + "' />");
				} else if (KeyGenMode.CUSTOM == keyGenMode) {
					// xxx
					sb.append("");
				}
			}
		}
		return sb.toString();
	}

	private static String getColumns(CommonTable table) {
		StringBuilder sb = new StringBuilder();
		for (CommonTableColumn tableColumn : table.getAllColumnList()) {
			sb.append(tableColumn.getColumn());
			sb.append(",");
		}
		if (sb.indexOf(",") != -1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	private static String getIfColumns(CommonTable table) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n   <trim prefix=\"(\" suffix=\")\"   suffixOverrides=\",\" >");

		for (CommonTableColumn tableColumn : table.getAllColumnList()) {
			sb.append("\n    <if test=\"" + tableColumn.getProperty() + " != null\" >");
			sb.append(tableColumn.getColumn());
			sb.append(",");
			sb.append("\n    </if>");
		}
		sb.append("\n   </trim>");
		return sb.toString();
	}

	private static String getKeyCondition(CommonTableColumn idColumn) {
		return idColumn.getColumn() + " = #{" + idColumn.getProperty() + ",jdbcType=" + idColumn.getJdbcType() + "}";
	}

	private static String getConditions4Update(List<CommonTableColumn> columnList, boolean needif) {
		String prefix = "SET";
		String stuffix = ",";

		StringBuilder sb = new StringBuilder();
		sb.append("\n   <trim prefix=\"" + prefix + "\"  suffixOverrides=\"" + stuffix + "\" >");
		for (CommonTableColumn column : columnList) {
			if (isDefault(column)) {
				sb.append("\n    <if test=\"" + column.getProperty() + " != null\" >");
				sb.append("\n   	  " + column.getColumn() + " = #{" + column.getProperty() + ",jdbcType="
						  + column.getJdbcType() + "}" + stuffix);
				sb.append("\n    </if>");
			} else {
				if (needif) {
					sb.append("\n    <if test=\"" + column.getProperty() + " != null\" >");
				}
				sb.append("\n   	  " + column.getColumn() + " = #{" + column.getProperty() + ",jdbcType="
						  + column.getJdbcType() + "}" + stuffix);
				if (needif) {
					sb.append("\n    </if>");
				}
			}
		}
		sb.append("\n   </trim>");
		return sb.toString();
	}

	private static boolean isDefault(CommonTableColumn column) {
		if (CREATION_DATE.equals(column.getColumn())) {
			return true;
		}

		if (CREATED_BY.equals(column.getColumn())) {
			return true;
		}

		if (UPDATION_DATE.equals(column.getColumn())) {
			return true;
		}

		if (UPDATED_BY.equals(column.getColumn())) {
			return true;
		}

		if (DELETED.equals(column.getColumn())) {
			return true;
		}

		if (ID.equals(column.getColumn())) {
			return true;
		}
		return false;

	}

	private static String getConditions4Query(List<CommonTableColumn> columnList, String prefixSQL, String var) {
		String prefix = "WHERE";
		String prefixOverrides = "AND | OR";

		if (var == null) {
			var = "";
		}
		var = var.trim();

		StringBuilder sb = new StringBuilder();
		sb.append("\n   <trim prefix=\"" + prefix + "\"  prefixOverrides=\"" + prefixOverrides + "\" >");
		if (prefixSQL != null) {
			sb.append("\n   	  " + prefixSQL);
		}
		for (CommonTableColumn column : columnList) {
			sb.append("\n    <if test=\"" + var + column.getProperty() + " != null\" >");
			sb.append("\n   	  AND " + column.getColumn() + " = #{" + var + column.getProperty() + ", jdbcType="
					  + column.getJdbcType() + "}");
			sb.append("\n    </if>");
		}
		sb.append("\n   </trim>");
		return sb.toString();
	}
	
	private static String getConditions4QueryWithoutDefault(List<CommonTableColumn> columnList, String prefixSQL, String var) {
		String prefix = "WHERE";
		String prefixOverrides = "AND | OR";

		if (var == null) {
			var = "";
		}
		var = var.trim();

		StringBuilder sb = new StringBuilder();
		sb.append("\n   <trim prefix=\"" + prefix + "\"  prefixOverrides=\"" + prefixOverrides + "\" >");
		if (prefixSQL != null) {
			sb.append("\n   	  " + prefixSQL);
		}
		for (CommonTableColumn column : columnList) {
			if (!isDefault(column)) {
				sb.append("\n    <if test=\"" + var + column.getProperty() + " != null\" >");
				sb.append("\n   	  AND " + column.getColumn() + " = #{" + var + column.getProperty() + ", jdbcType="
						  + column.getJdbcType() + "}");
				sb.append("\n    </if>");
			}
		}
		sb.append("\n   </trim>");
		return sb.toString();
	}

	// private static String getValues(List<CommonTableColumn> columnList) {
	// StringBuilder sb = new StringBuilder();
	// sb.append("(");
	// for (CommonTableColumn column : columnList) {
	// sb.append("#{" + column.getProperty() + ", jdbcType=" + column.getJdbcType()
	// + "},");
	// }
	// if (sb.indexOf(CommonConstants.DELIMITER_COMMA) != -1) {
	// sb.deleteCharAt(sb.length() - 1);
	// }
	// sb.append(")");
	// return sb.toString();
	// }

	private static String getIfValues(List<CommonTableColumn> columnList) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n   <trim prefix=\"(\" suffix=\")\"  suffixOverrides=\",\" >");
		for (CommonTableColumn column : columnList) {
			sb.append("\n    <if test=\"" + column.getProperty() + " != null\" >");
			sb.append("#{" + column.getProperty() + ", jdbcType=" + column.getJdbcType() + "},");
			sb.append("\n    </if>");
		}
		sb.append("\n   </trim>");
		return sb.toString();
	}

	private static String getBatchValues(List<CommonTableColumn> columnList) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n <foreach item=\"model\" index=\"index\" collection=\"list\" separator=\",\" >");
		sb.append("\n   <trim prefix=\"(\"  suffix=\")\" suffixOverrides=\",\" >");
		for (CommonTableColumn column : columnList) {
			sb.append("\n    <choose>");
			sb.append("\n    <when test=\"" + "model." + column.getProperty() + " != null\" >");
			sb.append("#{model." + column.getProperty() + ",jdbcType=" + column.getJdbcType() + "},");
			sb.append("\n    </when>");
			sb.append("\n    <otherwise>");
			sb.append("default,");
			sb.append("\n    </otherwise>");
			sb.append("\n    </choose>");
		}
		sb.append("\n   </trim>");
		sb.append("\n </foreach>");
		return sb.toString();
	}

}
