package com.example

object Utils {
  def sayHello() = s"hello ${sys.env("USER")}"
  def sayGoodbye() = s"goodbye ${sys.env("USER")}"
}