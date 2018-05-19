package org.mosquito.framework.jdbc.page;

import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.chandsir.mosquito.framework.core.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * . 封装分页返回信息工具类，将返回来的Page分页对象封装为WukongPage对象
 * 
 * @author chenl
 * 
 */
public class PagingUtil {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PagingUtil.class);

	/**
	 * 封装分页返回信息
	 * 
	 * @param result
	 * @return wukongPage<T>
	 */
	public static <T> Paging<T> encapsulatePageResult(Page<T> result) {
		Paging<T> wukongPage = null;
		try {
			if (null != result) {
				wukongPage = new Paging<T>();
				wukongPage.setList(result.getResult());
				PageInfo pageInfo = new PageInfo();
				pageInfo.setCurrentPage(result.getPageNum());
				pageInfo.setPageSize(result.getPageSize());
				pageInfo.setRecordCount(result.getTotal());
				pageInfo.setPageCount(result.getPages());
				wukongPage.setPage(pageInfo);
			}
			return wukongPage;
		} catch (Exception e) {
			LOGGER.error("封装分页返回信息出现系统异常", e);
			throw new IllegalStateException("封装分页返回信息出现系统异常");
		}
	}
	
	public static <T, K> void wukongPageCopy(Paging<T> src, Paging<K> target, Class<K> targetClazz) {
		List<K> voList = new ArrayList<K>();
		ObjectUtils.fastCopyList(src.getList(), voList, targetClazz);
		ObjectUtils.fastCopy(src, target);
		target.setList(voList);
	}

}
