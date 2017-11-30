package com.example.myapp

import org.apache.spark.sql.SaveMode
import DataFrameHelper._

object Driver {
  def main(args: Array[String]): Unit = {
    val spark = createSparkSession()

    MyApp.run(spark, args)
  }
}
