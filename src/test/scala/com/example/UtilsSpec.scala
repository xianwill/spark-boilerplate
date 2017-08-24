package com.example

import org.scalatest.{FlatSpec,Matchers}

class UtilsSpec extends FlatSpec with Matchers {
  "UtilsSpec" should "say hello to me" in {
    Utils sayHello() should be(s"hello ${sys.env("USER")}")
  }

  it should "say goodbye to me" in {
    Utils sayGoodbye() should be(s"goodbye ${sys.env("USER")}")
  }
}
