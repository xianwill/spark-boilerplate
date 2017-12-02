package com.example.myapp

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode
import DataFrameHelper._
import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra._
import org.elasticsearch.spark.sql._
import com.typesafe.scalalogging.Logger

object MyApp {
  def run(spark: SparkSession, args: Array[String]): Unit = {
    import spark.implicits._

    val conf = new MyAppConf(args)
    val pathHelper = new PathHelper(conf.basePath(), conf.runGroup())

    val driverLogger = Logger("MyApp")

    driverLogger.info(s"running with output path ${pathHelper.myappRows}")

    val ds1 = MyResourceLoader.loadMyCsv().toDS
    val ds2 = MyResourceLoader.loadMyJson().toDS

    val ds = ds1.unionAll(ds2)

    @transient lazy val workerLogger = Logger("MyAppWorker")

    driverLogger.info("logging rows")

    ds.foreach(o => {
      workerLogger.info(s"MyType instance: ${o.id} - ${o.name}")
    })

    driverLogger.info("writing to csv")

    ds.toDF.writeToSingleCsv(pathHelper.myappRows)

    driverLogger.info("writing to elasticsearch")

    val esIndexConfig = MyResourceLoader.loadMyIndexConfig()

    ElasticSearchHelper.forceIndex("myapp", esIndexConfig)

    ds.saveToEs(s"myapp/thing")

    driverLogger.info("writing to cassandra")

    ds.write.cassandraFormat("things", "myapp").mode(SaveMode.Overwrite).save()

    val s3Bucket = getS3Bucket()

    if (s3Bucket != "") {
        driverLogger.info("downloading from s3")

        val s3Message = S3Helper.getObjectText(s3Bucket, "spark/myapp/hello.txt")
        driverLogger.info(s3Message)
    } else {
        driverLogger.info("S3_BUCKET env var is not set")
    }

    driverLogger.info(s"job finished")
    spark.stop()
  }
}
