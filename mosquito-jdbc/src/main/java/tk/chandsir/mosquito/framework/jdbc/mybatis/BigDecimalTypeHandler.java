package tk.chandsir.mosquito.framework.jdbc.mybatis;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import tk.chandsir.mosquito.framework.core.utils.ObjectUtils;


/** 
 * 自定义BigDecimal的类型处理器
 * 
 * @author chenl
 * 
 */
@MappedTypes({BigDecimal.class})  
@MappedJdbcTypes(value = {JdbcType.REAL, JdbcType.DECIMAL, JdbcType.NUMERIC})
public class BigDecimalTypeHandler extends BaseTypeHandler<BigDecimal> {
	
	@Override
    public void setNonNullParameter(PreparedStatement ps, int i, BigDecimal parameter, JdbcType jdbcType)
        throws SQLException {
        ps.setBigDecimal(i, parameter);
	}
	
    @Override
    public BigDecimal getNullableResult(ResultSet rs, String columnName)
        throws SQLException {
	    BigDecimal value = rs.getBigDecimal(columnName);
	    if (validateDefaultZero(value)) {
		    return new BigDecimal(0.00);
	    }
        return rs.getBigDecimal(columnName);
    }

    @Override
    public BigDecimal getNullableResult(ResultSet rs, int columnIndex)
        throws SQLException {
	    BigDecimal value = rs.getBigDecimal(columnIndex);
	    if (validateDefaultZero(value)) {
		    return new BigDecimal(0.00);
	    }
        return rs.getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getNullableResult(CallableStatement cs, int columnIndex)
        throws SQLException {
	    BigDecimal value = cs.getBigDecimal(columnIndex);
	    if (validateDefaultZero(value)) {
		    return new BigDecimal(0.00);
	    }
        return cs.getBigDecimal(columnIndex);
    }
    
	private boolean validateDefaultZero(BigDecimal value) {
		return !ObjectUtils.isNullOrEmpty(value) && 1 == value.compareTo(new BigDecimal(-0.000001)) && -1 == value.compareTo(new BigDecimal(0.000001));
	}

}

