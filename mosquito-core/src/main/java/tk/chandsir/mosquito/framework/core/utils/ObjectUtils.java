package tk.chandsir.mosquito.framework.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 对象操作工具类
 * 
 * @author chenl
 * 
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);
	private static final String ERROR_STRING = "对象属性复制异常:";

	private ObjectUtils() {
	}

	/**
	 * 判断对象是否为空
	 * <p>
	 * 如果为String，则判断 null 和 "" <br />
	 * 如果为集合与MAP,则断null 和 集合中无元素 <br />
	 * 如果为数组，则判断null 和 length=0 <br />
	 * </p>
	 * 
	 * <pre>
	 * isNullOrEmpty(null) true
	 * isNullOrEmpty("") true
	 * isNullOrEmpty(list) list=null 或者 list.size() =0 返回true
	 * isNullOrEmpty(map)  map=null 或者 map.size() =0 返回true
	 * isNullOrEmpty(array)  array=null 或者 array.length =0 返回true
	 * </pre>
	 * 
	 * @param obj
	 *            待判断对象
	 * @return ture or false
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null
			    || (obj instanceof String && "".equals(String.valueOf(obj).trim()))
			    || (obj instanceof Collection && ((Collection) obj).isEmpty()) 
			    || (obj instanceof Map && ((Map) obj).isEmpty()) 
			    || (obj instanceof Object[] && ((Object[]) obj).length == 0) 
			    || (obj instanceof Boolean && !((Boolean) obj))) {
			return true;
		}
		return false;
	}

	/**
	 * 将对象转换为字节数组
	 * 
	 * @param obj
	 *            待转换对象
	 * @return 对象的字节数组
	 */
	public static byte[] toByteArray(Object obj) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			LOGGER.error("对象序列化字节数组异常", e);
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 将字节数组转换为对象
	 * 
	 * @param bytes
	 *            字节数组
	 * @return 对象
	 */
	public static Object toObject(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			LOGGER.error("字节数组反序列化为对象异常", e);
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 将srcObj对象的属性值拷贝到targetObj对象相应的属性上
	 *
	 * @param srcObj
	 *            源对象
	 * @param targetObj
	 *            目标对象
	 */
	public static void fastCopy(Object srcObj, Object targetObj) {
		try {
			BeanUtils.copyProperties(srcObj, targetObj);
		} catch (Exception e) {
			LOGGER.info(ERROR_STRING, e);
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Description 对象快速copy并创建返回目标对象(返回目标对象)
	 * 
	 * @param srcObj
	 * @param targetClass
	 * @return
	 * @author: 01372630 Create at: 2018年1月30日 上午11:46:16
	 * @param <T>
	 */

	public static <T> T fastCopyAndNew(Object srcObj, Class<T> targetClass) throws IllegalArgumentException {
		if (srcObj == null) {
			return null;
		}
		try {
			T targetObject = targetClass.newInstance();
			BeanUtils.copyProperties(srcObj, targetObject);
			return targetObject;
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(ERROR_STRING, e);
			}
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Description 快速复制源list属性值并创建返回目标list
	 * 
	 * @param srcObj
	 * @param targetClass
	 * @return 返回目标List<K>
	 * @throws IllegalArgumentException
	 * @author: 01372630 Create at: 2018年1月30日 下午2:10:48
	 */

	public static <K, T> List<K> fastCopyAndNewList(List<T> srcObj, Class<K> targetClass) throws IllegalArgumentException {
		if (srcObj == null || srcObj.isEmpty()) {
			return null;
		}
		try {
			List<K> targetList = new ArrayList<K>();
			for (Object object : srcObj) {
				K targetObject = fastCopyAndNew(object, targetClass);
				targetList.add(targetObject);
			}
			return targetList;
		} catch (Exception e) {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(ERROR_STRING, e);
			}
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Description List快速copy
	 * 
	 * @param srcList
	 *            源List
	 * @param targetList
	 *            目标list
	 * @param targetClazz
	 *            目标list
	 * @author: 01372630 Create at: 2018年1月30日 上午11:43:47
	 */

	public static <T, K> void fastCopyList(List<T> srcList, List<K> targetList, Class<K> targetClazz)
			throws IllegalArgumentException {
		if (srcList == null || targetList == null || targetClazz == null) {
			return;
		}
		for (T t : srcList) {
			K k = null;
			try {
				k = targetClazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error("获取泛型实例异常", e);
				throw new IllegalArgumentException(e);
			}
			ObjectUtils.fastCopy(t, k);
			targetList.add(k);
		}
	}

}
