package tk.chandsir.mosquito.framework.core.repo

trait CrudMapper[T] {

  def insert(record: T): Int

  def insertSelective(record: T): Int

  def selectByPrimaryKey(id: Integer): T

  def updateByPrimaryKeySelective(record: T): Int

  def updateByPrimaryKey(record: T): Int

  def deleteByPrimaryKey(id: Integer): Int
}
