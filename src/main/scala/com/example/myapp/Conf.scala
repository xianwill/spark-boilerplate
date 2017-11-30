package com.example.myapp

import org.rogach.scallop._
import java.time.Instant

object ConfHelper {
  def defaultRunGroup() = Some(Instant.now().toString().split("T")(0))
}

class MyAppConf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val basePath = opt[String](required=true)
  val runGroup = opt[String](required=true, default=ConfHelper.defaultRunGroup())

  verify()
}

