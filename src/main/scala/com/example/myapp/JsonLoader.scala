package com.example.myapp

import scala.io.Source
import scala.collection.JavaConversions._
import org.json4s._
import org.json4s.native.JsonMethods._

trait JsonLoader {
  def loadJson(resourcePath: String) =
    Source.fromInputStream(getClass.getResourceAsStream(resourcePath)).getLines.map(l => parse(l))
}