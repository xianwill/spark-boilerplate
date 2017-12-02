package com.example.myapp

import scala.io.Source

trait StringLoader {
  def loadString(resourcePath: String): String =
    Source.fromInputStream(getClass.getResourceAsStream(resourcePath)).mkString
}
