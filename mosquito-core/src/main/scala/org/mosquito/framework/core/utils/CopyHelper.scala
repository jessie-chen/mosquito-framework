package org.mosquito.framework.core.utils

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.BeanUtils
import java.util

trait CopyHelper {

  val log: Logger = LoggerFactory.getLogger(classOf[CopyHelper])

  def fastCopy(src: Any, dest: Any) = {
    try {
      BeanUtils.copyProperties(src, dest)
    } catch {
      case e: Exception => {
        log.error("fastCopy error", e)
        throw new IllegalArgumentException(e)
      }
    }
  }

  def fastCopyAndNew[T](src: Any, clazz: Class[T]): T = {
    if (src == null) null
    try {
      var dest: T = clazz.newInstance()
      fastCopy(src, dest)
      dest
    } catch {
      case e: Exception => {
        log.error("fastCopy error", e)
        throw new IllegalArgumentException(e)
      }
    }
  }

  def fastCopyList[S, T](srcList: util.List[S], destList: util.List[T], destClass: Class[T]): Unit = {
    if (srcList == null || destList == null || destClass == null) {
      return
    }
    try {
      srcList.forEach(e => {
        val dest = destClass.newInstance()
        fastCopy(e, dest)
        destList.add(dest)
      })
    } catch {
      case e: Exception => {
        log.error("fastCopy error", e)
        throw new IllegalArgumentException(e)
      }
    }
  }

  def fastCopyAndNewList[S, T](srcList: util.List[S], destClass: Class[T]): util.List[T] = {
    if (srcList == null) return null
    if (srcList.isEmpty) return new util.ArrayList[T]()

    try {
      val destList: util.List[T] = new util.ArrayList[T](srcList.size())
      srcList.forEach(e => destList.add(fastCopyAndNew(e, destClass)))
      destList
    } catch {
      case e: Exception => {
        log.error("fastCopy error", e)
        throw new IllegalArgumentException(e)
      }
    }

  }

}
