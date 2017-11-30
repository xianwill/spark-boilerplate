package com.example.myapp

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import DataFrameHelper._

object MyApp {
  def run(spark: SparkSession, args: Array[String]): Unit = {
    import spark.implicits._

    val conf = new MyAppConf(args)
    val pathHelper = new PathHelper(conf.basePath(), conf.runGroup())

    LogWrapper.logger.info(s"running with output path ${pathHelper.myappRows}")

    val df1 = MyResourceLoader.loadMyCsv().toDS
    val df2 = MyResourceLoader.loadMyJson().toDS

    val df = df1.unionAll(df2)

    df.foreach(o => {
      LogWrapper.logger.info(s"MyType instance: ${o.id} - ${o.name}")
    })

    df.toDF.writeToSingleCsv(pathHelper.myappRows)
  }
}
