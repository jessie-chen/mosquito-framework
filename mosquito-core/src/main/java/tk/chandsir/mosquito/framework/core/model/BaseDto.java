package tk.chandsir.mosquito.framework.core.model;

import lombok.Data;
import tk.chandsir.mosquito.framework.core.utils.JsonUtil;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseDto implements Serializable {

	private static final long serialVersionUID = -9191310105031822857L;

	/**
	 * 主键ID
	 */
	protected Long id;

	/**
	 * 创建时间
	 */
	protected Date createTime;

	/**
	 * 修改时间
	 */
	protected Date updateTime;

	/**
	 * 是否删除：0：否， 1：是
	 */
	protected Integer deleted = 0;


    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
