package org.mosquito.framework.jdbc.mapper;

import com.github.pagehelper.Page;
import org.mosquito.framework.core.model.BaseQuery;
import org.mosquito.framework.core.model.Identity;

import java.util.List;


/**
 * . Mapper基类.
 * 
 * @param <K>
 *            .
 * @author chenl
 * 
 */
public interface BaseMapper<K extends Identity> {

	/**
	 * 默认表名
	 */
	String TABLE_NAME_DEFAULT = "default";

	/**
	 * 插入数据
	 * 
	 * @param data
	 *            数据
	 * @return 返回操作记录数
	 */
	int insert(K data);

	/**
	 * 批量插入数据
	 * 
	 * @param datas
	 *            数据
	 * @return 返回操作记录数
	 */
	int insertBatch(List<K> datas);

	/**
	 * 更新数据 主键为更新条件，其他为数据
	 * 
	 * @param data
	 *            数据
	 * @return 更新结果行数
	 */
	int update(K data);

	/**
	 * 更新数据，忽略空字段 主键为更新条件，其他非空字段为数据
	 * 
	 * @param data
	 *            数据
	 * @return 更新结果行数
	 */
	int updateSelective(K data);

	/**
	 * 通过主键物理删除记录
	 * 
	 * @param ids
	 *            主键List
	 * @return 删除行数
	 */
	int deletePhysically(List<Long> ids);

	/**
	 * 通过主键获取数据
	 * 
	 * @param id
	 *            主键
	 * @return 一行数据
	 */
	K get(Long id);

	/**
	 * 通过主键获取数据
	 * 
	 * @param ids
	 *            主键List
	 * @return List 如果无数据时，返回是长度为0的List对象
	 */
	List<K> getByIds(List<Long> ids);

	/**
	 * 通过Model获取数据
	 * 
	 * @param data
	 *            Model数据，非空字段都做为条件查询
	 * @return 数据列表
	 */
	List<K> selectAll(K data);
	
	/**
	 * 通过Model获取数据记录数量
	 * 
	 * @param data
	 *            Model数据，非空字段都做为条件查询
	 * @return 记录数量
	 */
	Long selectCount(K data);

	/**
	 * 通过pagination对象进行相关参数查询，获取分页数据
	 * 
	 * @param data
	 *            数据
	 * @return 数据分页列表
	 */
	Page<K> search(BaseQuery data);

}
