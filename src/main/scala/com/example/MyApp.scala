package com.example

import org.apache.spark.sql.SaveMode
import DataFrameHelper._

object MyApp {
  def main(args: Array[String]): Unit = {
    val spark = createSparkSession()

    import spark.implicits._

    val conf = new MyAppConf(args)
    val pathHelper = new PathHelper(conf.basePath(), conf.runGroup())

    LogWrapper.logger.info(s"running with master ${sys.env("SPARK_MASTER")} output path ${pathHelper.myappRows}")

    val df1 = MyResourceLoader.loadMyCsv().toDS
    val df2 = MyResourceLoader.loadMyJson().toDS

    val df = df1.unionAll(df2)

    df.foreach(o => {
      LogWrapper.logger.info(s"MyType instance: ${o.id} - ${o.name}")
    })
    
    df.toDF.writeToSingleCsv(pathHelper.myappRows)
  }
}
