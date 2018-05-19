package org.mosquito.framework.core.service

import java.util.Date

import org.mosquito.framework.core.model.BaseModel
import org.mosquito.framework.core.repo.CrudMapper

class CrudServiceImpl[T <: BaseModel](val mapper: CrudMapper[T]) extends CrudService[T] {
  override def find(id: Int): T = mapper.selectByPrimaryKey(id)

  override def save(t: T): Int = {
    if (t.id == null || t.id == 0) {
      val now = new Date()
      t.createTime = now
      t.updateTime = now
      mapper.insert(t)
    } else {
      if (t.updateTime == null) {
        t.updateTime = new Date()
      }
      mapper.updateByPrimaryKey(t)
    }
  }

  override def updateSelective(t: T): Int = mapper.updateByPrimaryKeySelective(t)

  override def delete(id: Integer): Int = mapper.deleteByPrimaryKey(id)
}
