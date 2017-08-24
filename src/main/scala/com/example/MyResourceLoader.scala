package com.example

import scala.collection.JavaConversions._
import org.json4s._
import org.json4s.native.JsonMethods._

object MyResourceLoader extends CsvLoader with JsonLoader {
  implicit val formats = DefaultFormats

  def loadMyCsv() = 
    loadCsv(s"/myapp.csv").map(r => MyType(r.get("id"), r.get("name"))).toSeq

  def loadMyJson() = 
    loadJson(s"/myapp.json").map(_.extract[MyType]).toSeq
}