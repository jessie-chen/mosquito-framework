package org.mosquito.framework.core.model

import scala.beans.BeanProperty

/**
  * BaseQuery
  *
  * @author 01372461
  */
class BaseQuery extends Serializable {
  /**
    * 排序字段
    */
  @BeanProperty var order: String = _

  /**
    * 排序方式
    */
  @BeanProperty var sort: String = _

  /**
    * 开始页（分页查询时，用于redis缓存的Key中）
    */
  @BeanProperty var start: Integer = _

  /**
    * 每页的行数（分页查询时，用于redis缓存的Key中）
    */
  @BeanProperty var pageSize: Integer = _

}
