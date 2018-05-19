package org.mosquito.framework.core.utils

import java.util.Date

import org.scalatest.{FlatSpec, Matchers}

/**
  * JsonTest
  *
  * @author 01372461
  */
class JsonTest extends FlatSpec with Matchers  {

  case class Foo(name: String, age: Int, createTime: Date)

  "Json format" should "format data correctly" in {
    println(JsonUtil.toJson(Foo("foo", 18, new Date())))
  }
}
