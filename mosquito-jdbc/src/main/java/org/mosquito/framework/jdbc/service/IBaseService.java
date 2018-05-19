package org.mosquito.framework.jdbc.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import tk.chandsir.mosquito.framework.core.exception.AppException;
import tk.chandsir.mosquito.framework.core.model.BaseDto;
import tk.chandsir.mosquito.framework.core.model.BaseModel;
import tk.chandsir.mosquito.framework.core.model.BaseQuery;
import org.mosquito.framework.jdbc.page.Paging;

/**
 * . Service基础接口
 * 
 * @param <T>
 *            .
 * @param <K>
 *            .
 * @author chenl
 * 
 */
@Transactional(rollbackFor = AppException.class)
public interface IBaseService<T extends BaseDto, K extends BaseModel> {

	/**
	 * 插入数据
	 *
	 * 如果主键是基于DB的方式，数据插入成功后，主键值会自动填充到输入对象中
	 *
	 * @param dto
	 *            数据
	 * @return 返回插入数据所生成的ID
	 * @throws AppException
	 *             应用服务异常
	 */
	Long insert(T dto) throws AppException;

	/**
	 * 批量插入数据
	 * 
	 * @param dtoList
	 *            数据
	 * @return 返回操作记录数
	 * @throws AppException
	 *             应用服务异常
	 */
	int insertBatch(List<T> dtoList) throws AppException;

	/**
	 * 更新数据 主键为更新条件，其他为数据
	 * 
	 * @param dto
	 *            数据
	 * @return 更新结果行数
	 * @throws AppException
	 *             应用服务异常
	 */
	int update(T dto) throws AppException;

	/**
	 * 更新数据，忽略空字段 主键为更新条件，其他非null字段为数据
	 * 
	 * @param dto
	 *            数据
	 * @return 更新结果行数
	 * @throws AppException
	 *             应用服务异常
	 */
	int updateSelective(T dto) throws AppException;

	/**
	 * 通过主键物理删除记录
	 * 
	 * @param idList
	 *            主键
	 * @return 删除行数
	 * @throws AppException
	 *             应用服务异常
	 */
	int deletePhysically(List<Long> idList) throws AppException;

	/**
	 * 通过主键获取数据
	 * 
	 * @param id
	 *            主键
	 * @return 一行数据
	 * @throws AppException
	 *             应用服务异常
	 */
	T get(Long id) throws AppException;

	/**
	 * 通过主键获取数据
	 * 
	 * @param idList
	 *            主键
	 * @return List 如果无数据时，返回是长度为0的List对象
	 * @throws AppException
	 *             应用服务异常
	 */
	List<T> getByIds(List<Long> idList) throws AppException;

	/**
	 * 通过Model获取数据
	 * 
	 * @param dto
	 *            数据，非null字段都做为条件查询
	 * @return List 如果无数据时，返回是长度为0的List对象
	 * @throws AppException
	 *             应用服务异常
	 */
	List<T> selectAll(T dto) throws AppException;
	
	/**
	 * 通过Model获取数据记录数量
	 * 
	 * @param dto
	 *            数据，非null字段都做为条件查询
	 * @return Long 记录数量
	 * @throws AppException
	 *             应用服务异常
	 */
	Long selectCount(T dto) throws AppException;

	/**
	 * 通过query对象获取分页数据
	 * 
	 * @param query
	 *            数据
	 * @return 分页数据
	 * @throws AppException
	 *             应用服务异常
	 */
	Paging<T> search(BaseQuery query) throws AppException;

}