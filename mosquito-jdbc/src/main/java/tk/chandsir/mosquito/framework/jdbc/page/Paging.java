package tk.chandsir.mosquito.framework.jdbc.page;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * . 分页返回信息封装实体
 * 
 * @param <T>
 * @author chenl
 * 
 */
@Data
public class Paging<T> implements Serializable {

	private static final long serialVersionUID = 668682736357018158L;

	/**
	 * PageInfo对象实例
	 */
	private PageInfo page;

	/**
	 * 结果集
	 */
	private List<T> list;

}
