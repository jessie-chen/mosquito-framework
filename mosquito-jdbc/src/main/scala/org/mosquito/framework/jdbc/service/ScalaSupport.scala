package org.mosquito.framework.jdbc.service

import org.mosquito.framework.core.model.{BaseDto, BaseModel}
import collection.JavaConverters._

/**
  * Scala 返回类型支持.
  * 增加带"s"后缀的方法,用于直接返回scala的集合类型.而不是java的集合类型.
  *
  * @tparam T
  * @tparam M
  */
trait ScalaSupport[T <: BaseDto, M <: BaseModel] { this: IBaseService[T, M]  =>

  def getByIdss(idList: java.util.List[java.lang.Long]): Seq[T] =  getByIds(idList).asScala

  def selectAlls(dto: T): Seq[T] = selectAll(dto).asScala

}
