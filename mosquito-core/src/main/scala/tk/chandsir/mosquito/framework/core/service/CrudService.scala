package tk.chandsir.mosquito.framework.core.service

trait CrudService[T] {

  def find(id: Int): T

  def save(t: T): Int

  def updateSelective(t: T): Int

  def delete(id: Integer): Int

}
