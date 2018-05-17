package tk.chandsir.mosquito.framework.jdbc.mybatis;

/** .
 * 无效的Mapper XML异常封装类
 * 
 * @author chenl
 * 
 */
public class InvalidXmlMapperException extends RuntimeException {

	private static final long serialVersionUID = -3846556576046399727L;

	public InvalidXmlMapperException(String message) {
		super(message);
	}

	public InvalidXmlMapperException(String message, Throwable cause) {
		super(message, cause);
	}

}
