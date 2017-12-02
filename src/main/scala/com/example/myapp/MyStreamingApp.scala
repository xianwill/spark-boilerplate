package com.example.myapp

import org.apache.spark.sql.SparkSession
import DataFrameHelper._
import com.typesafe.scalalogging.Logger

object MyStreamingApp {
  def run(spark: SparkSession, args: Array[String]): Unit = {
    import spark.implicits._

    val conf = new MyStreamingAppConf(args)

    spark.stop()
  }
}
