package tk.chandsir.mosquito.framework.jdbc.mybatis;

/** .
 * Criteria条件封装类
 * 
 * @author chenl
 * 
 */
public enum CriteriaCondition {
	// 是否为空
	IsNull(0),
	// 是否不为空
	IsNotNull(1),
	// 等于
	EqualTo(2),
	// 不等于
	NotEqualTo(3),
	// 大于
	GreaterThan(4),
	// 大于等于
	GreaterThanOrEqualTo(5),
	// 小于
	LessThan(6),
	// 小于等于
	LessThanOrEqualTo(7),
	// like
	Like(8),
	// not like
	NotLike(9),
	// in
	In(10),
	// not in
	NotIn(11),
	// between
	Between(12),
	// not between
	NotBetween(13);

	private int code;
    /**
     *  .
     * Description TODO(这里用一句话描述这个方法的作用)
     * @param code
     * @author: 01372634    
     * Create at: 2018年1月8日 下午2:03:17
     */
	CriteriaCondition(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return String.valueOf(code);
	}
}
