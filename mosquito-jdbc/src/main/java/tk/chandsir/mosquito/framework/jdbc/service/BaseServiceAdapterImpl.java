package tk.chandsir.mosquito.framework.jdbc.service;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import tk.chandsir.mosquito.framework.core.exception.AppException;
import tk.chandsir.mosquito.framework.core.model.BaseDto;
import tk.chandsir.mosquito.framework.core.model.BaseModel;
import tk.chandsir.mosquito.framework.core.model.BaseQuery;
import tk.chandsir.mosquito.framework.core.utils.ObjectUtils;
import tk.chandsir.mosquito.framework.jdbc.constant.DeletedFlag;
import tk.chandsir.mosquito.framework.jdbc.mapper.BaseMapper;
import tk.chandsir.mosquito.framework.jdbc.page.Paging;
import tk.chandsir.mosquito.framework.jdbc.page.PagingUtil;


/**
 * . Service基础接口适配类
 * 
 * @param <T>
 * @param <K>
 * @param <M>
 * @author chenl
 * 
 */
public class BaseServiceAdapterImpl<T extends BaseDto, K extends BaseModel, M extends BaseMapper<K>>
		  implements IBaseService<T, K> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceAdapterImpl.class);

	@SuppressWarnings("unchecked")
	Class<T> dtoClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
			  .getActualTypeArguments()[0];

	@SuppressWarnings("unchecked")
	Class<K> modelClass = (Class<K>) ((ParameterizedType) this.getClass().getGenericSuperclass())
			  .getActualTypeArguments()[1];

	@Autowired
	protected M mapper;

	@Autowired
	protected MessageSource messageSource;

	@Override
	public Long insert(T dto) throws AppException {
		Long result = 0L;
		try {
			K model = newGenericlInstance(modelClass);
			setDefault(dto, true);
			ObjectUtils.fastCopy(dto, model);
			mapper.insert(model);
			result = model.getId();
		} catch (Exception e) {
			LOGGER.error("insert异常", e);
			throw new AppException(e.getMessage());
		}
		return result;
	}

	@Override
	public int insertBatch(List<T> dtoList) throws AppException {
		int result = 0;
		List<K> list = null;
		try {
			if (!ObjectUtils.isNullOrEmpty(dtoList)) {
				list = new ArrayList<K>();
				for (T data : dtoList) {
					K model = newGenericlInstance(modelClass);
					setDefault(data, true);
					ObjectUtils.fastCopy(data, model);
					list.add(model);
				}
			}
			result = mapper.insertBatch(list);
		} catch (Exception e) {
			LOGGER.error("insertBatch异常", e);
			throw new AppException(e.getMessage());
		}
		return result;
	}

	@Override
	public int update(T dto) throws AppException {
		int result = 0;
		if (!ObjectUtils.isNullOrEmpty(dto)) {
			try {
				K model = newGenericlInstance(modelClass);
				setDefault(dto, false);
				ObjectUtils.fastCopy(dto, model);
				result = mapper.update(model);
			} catch (Exception e) {
				LOGGER.error("update异常", e);
				throw new AppException(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public int updateSelective(T dto) throws AppException {
		int result = 0;
		if (!ObjectUtils.isNullOrEmpty(dto)) {
			try {
				K model = newGenericlInstance(modelClass);
				setDefault(dto, false);
				ObjectUtils.fastCopy(dto, model);
				result = mapper.updateSelective(model);
			} catch (Exception e) {
				LOGGER.error("updateSelective异常", e);
				throw new AppException(e.getMessage());
			}
		}
		return result;
	}

	@Override
	public int deletePhysically(List<Long> idList) throws AppException {
		int result = 0;
		try {
			result = mapper.deletePhysically(idList);
		} catch (Exception e) {
			LOGGER.error("deletePhysically异常", e);
			throw new AppException(e.getMessage());
		}
		return result;
	}

	@Override
	public T get(Long id) throws AppException {
		T result = null;
		try {
			K model = mapper.get(id);
			if (!ObjectUtils.isNullOrEmpty(model)) {
				result = newGenericlInstance(dtoClass);
				ObjectUtils.fastCopy(model, result);
			}
		} catch (Exception e) {
			LOGGER.error("get异常", e);
			throw new AppException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<T> getByIds(List<Long> idList) throws AppException {
		List<T> result = new ArrayList<T>();
		try {
			List<K> list = mapper.getByIds(idList);
			if (!ObjectUtils.isNullOrEmpty(list)) {
				for (K model : list) {
					T dto = newGenericlInstance(dtoClass);
					ObjectUtils.fastCopy(model, dto);
					result.add(dto);
				}
			}
		} catch (Exception e) {
			LOGGER.error("getByIds异常", e);
			throw new AppException(e.getMessage());
		}
		return result;
	}

	@Override
	public List<T> selectAll(T dto) throws AppException {
		List<T> result = new ArrayList<T>();
		try {
			K model = null;
			if (!ObjectUtils.isNullOrEmpty(dto)) {
				model = newGenericlInstance(modelClass);
				ObjectUtils.fastCopy(dto, model);
			}
			List<K> list = mapper.selectAll(model);
			if (!ObjectUtils.isNullOrEmpty(list)) {
				for (K m : list) {
					T d = newGenericlInstance(dtoClass);
					ObjectUtils.fastCopy(m, d);
					result.add(d);
				}
			}
		} catch (Exception e) {
			LOGGER.error("selectAll异常", e);
			throw new AppException(e.getMessage());
		}
		return result;
	}
	
	@Override
	public Long selectCount(T dto) throws AppException {
		Long count = 0L;
		try {
			K model = null;
			if (!ObjectUtils.isNullOrEmpty(dto)) {
				model = newGenericlInstance(modelClass);
				ObjectUtils.fastCopy(dto, model);
			}
			count = mapper.selectCount(model);
		} catch (Exception e) {
			LOGGER.error("selectCount异常", e);
			throw new AppException(e.getMessage());
		}
		return count;
	}

	@Override
	public Paging<T> search(BaseQuery query) throws AppException {
		Paging<T> result = new Paging<T>();
		try {
			PageHelper.startPage(query.getStart(), query.getPageSize());
			Page<K> page = mapper.search(query);
			Page<T> pageDto = new Page<T>(query.getStart(), query.getPageSize());
			ObjectUtils.fastCopy(page, pageDto);
			List<K> list = page.getResult();
			if (!ObjectUtils.isNullOrEmpty(list)) {
				for (K model : list) {
					T dto = newGenericlInstance(dtoClass);
					ObjectUtils.fastCopy(model, dto);
					pageDto.add(dto);
				}
			}
			result = PagingUtil.encapsulatePageResult(pageDto);
		} catch (Exception e) {
			LOGGER.error("search异常", e);
			throw new AppException(e.getMessage());
		}
		return result;
	}

	/**
	 * 获取泛型实例
	 * 
	 * @param clazz
	 * @return G
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private <G> G newGenericlInstance(Class<G> clazz) throws InstantiationException, IllegalAccessException {
		G g = clazz.newInstance();
		return g;
	}

	/**
	 * 设置添加公用参数
	 *
	 * @param data
	 */
	private void setDefault(T data, boolean isNew) {
		if (data instanceof BaseDto) {
			BaseDto dto = (BaseDto) data;
			String userId = null;
			/*
			UserInfo userInfo =  ContextHolder.get().getUser();
			if (!ObjectUtils.isNullOrEmpty(userInfo)) {
				userId = userInfo.getUserId();
			}
			*/
			if (isNew) {
				if (ObjectUtils.isNullOrEmpty(dto.getCreateTime())) {
					dto.setCreateTime(new Timestamp(System.currentTimeMillis()));
				}
				/*
				if (!ObjectUtils.isNullOrEmpty(userId)) {
					dto.setCreatedBy(userId);
				} else {
					dto.setCreatedBy("-1");
				}
				*/
			}
			if (ObjectUtils.isNullOrEmpty(dto.getUpdateTime())) {
				dto.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			}
			/*
			if (!ObjectUtils.isNullOrEmpty(userId)) {
				dto.setUpdatedBy(userId);
			} else {
				dto.setUpdatedBy("-1");
			}
			*/
			if (ObjectUtils.isNullOrEmpty(dto.getDeleted())) {
				dto.setDeleted(DeletedFlag.DELETEDFLAG_NO.getValue());
			}
		}
	}

	/**
	 * 获取国际化信息
	 * 
	 * @param messageKey
	 *            messages资源文件里的Key
	 * @return String messages资源文件里相应Key对应的值
	 */
	protected String getMessage(String messageKey) {
		return messageSource.getMessage(messageKey, null, getLocale());
	}

	/**
	 * 获取国际化信息
	 * 
	 * @param messageKey
	 *            messages资源文件里的Key
	 * @param args
	 *            messages资源文件里的Key对应值的参数
	 * @return String messages资源文件里相应Key对应的值
	 */
	protected String getMessage(String messageKey, Object[] args) {
		return messageSource.getMessage(messageKey, args, getLocale());
	}

	/**
	 * 获取Locale
	 * 
	 * @return Locale
	 */
	private Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}

}
