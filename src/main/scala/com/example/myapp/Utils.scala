package com.example.myapp

object Utils {
  def sayHello() = s"hello ${sys.env("USER")}"
  def sayGoodbye() = s"goodbye ${sys.env("USER")}"
}