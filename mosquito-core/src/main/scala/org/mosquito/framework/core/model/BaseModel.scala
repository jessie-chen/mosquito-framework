package org.mosquito.framework.core.model

import java.util.Date

import scala.beans.BeanProperty

class BaseModel extends Serializable {

  @BeanProperty var id: java.lang.Long = _
  @BeanProperty var createTime: Date = _
  @BeanProperty var updateTime: Date = _
  @BeanProperty var creator: String = _
  @BeanProperty var modifier: String = _
  @BeanProperty var deleted: java.lang.Integer = _

}
