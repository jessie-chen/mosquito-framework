package tk.chandsir.mosquito.framework.jdbc.mybatis;

import org.apache.commons.lang3.StringUtils;

/** .
 * 主键生成方式
 * 
 * @author chenl
 * 
 */
public enum KeyGenMode {
	/** 用户自定义 */
	CUSTOM("CUSTOM"),
	/** 数据库ID自增 */
	IDENTITY("IDENTITY"),
	/** UUID */
	UUID("UUID"),
	/** UUID */
	DB_UUID("DB_UUID"),
	/** MYCAT 序列自增 */
	MYCAT("MYCAT");

	private String code;

	private String value;

	KeyGenMode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return code;
	}

	public static KeyGenMode parse(String code) {
		KeyGenMode result = null;
		code = StringUtils.trim(code);
		for (KeyGenMode keyGenMode : values()) {
			if (keyGenMode.getCode().equals(code)) {
				result = keyGenMode;
			}
		}
		if (result == null) {
			result = KeyGenMode.IDENTITY;
		}
		return result;
	}
}
