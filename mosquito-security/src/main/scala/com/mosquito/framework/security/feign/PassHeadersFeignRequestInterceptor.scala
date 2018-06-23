package com.mosquito.framework.security.feign

import java.util

import com.typesafe.scalalogging.Logger
import feign.{RequestInterceptor, RequestTemplate}
import org.springframework.web.context.request.{RequestContextHolder, ServletRequestAttributes}


class PassHeadersFeignRequestInterceptor extends RequestInterceptor {

  val log: Logger = Logger[PassHeadersFeignRequestInterceptor]

  override def apply(template: RequestTemplate): Unit = {
    log.debug("PassHeadersFeignRequestInterceptor")
    val attributes = RequestContextHolder.getRequestAttributes
    attributes match {
      case attr: ServletRequestAttributes =>
        val req = attr.getRequest
        val headerNames: util.Enumeration[String] = req.getHeaderNames
        while (headerNames.hasMoreElements) {
          val name = headerNames.nextElement()
          val headers: util.Enumeration[String] = req.getHeaders(name)
          template.header(name, EnumIterable[String](headers))
          log.debug(s"$name: $headers")
        }
      case _ =>
    }
  }
}

class IteratorAdapter[T](enumeration: util.Enumeration[T]) extends util.Iterator[T] {
  override def hasNext: Boolean = enumeration.hasMoreElements
  override def next(): T = enumeration.nextElement()
}

class EnumIterable[T](enumeration: util.Enumeration[T]) extends java.lang.Iterable[T] {
  override def iterator(): util.Iterator[T] = new IteratorAdapter[T](enumeration)
}

object EnumIterable {
  def apply[T](enumeration: util.Enumeration[T]): EnumIterable[T] = new EnumIterable(enumeration)
}