package com.example.myapp

import org.scalatest.{FlatSpec,Matchers}
import org.apache.spark.sql.SparkSession

class MyAppIntegrationSpec extends FlatSpec with Matchers with SparkContextSetup {
  "MyApp" should "do what it does" in withSparkSession { (spark: SparkSession) => {
      MyApp.run(spark, Array("--base-path", s"${sys.env.getOrElse("DATA_ROOT", "/tmp/data")}", "--run-group", "2017-08-24"))

      // yeah, i'm not actually asserting anything

      1 should be(1)
    }
  }
}
