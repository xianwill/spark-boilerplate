package com.example.myapp

class PathHelper(val basePath: String, val prefix: String) {
  val myappRows = s"$basePath/spark/myapp/$prefix/rows"
}