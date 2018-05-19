package tk.chandsir.mosquito.framework.core.model

import java.util.Date

import tk.chandsir.mosquito.framework.core.utils.JsonUtil

import scala.beans.BeanProperty

class BaseDto extends Serializable {

  @BeanProperty var id: java.lang.Long = _
  @BeanProperty var createTime: Date = _
  @BeanProperty var updateTime: Date = _
  @BeanProperty var deleted: java.lang.Integer = _

  override def toString: String = JsonUtil.toJson(this)
}
