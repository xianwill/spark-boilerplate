package com.example

import org.apache.spark.sql.SparkSession

trait SparkContextSetup {
  def withSparkSession(testMethod: (SparkSession) => Any) {
    val spark = SparkSession.
        builder.
        appName(getClass.getName).
        master(sys.env.getOrElse("SPARK_MASTER", "local")).
        config("spark.speculation", false).
        config("spark.hadoop.mapreduce.fileoutputcommitter.algorithm.version", 2).
        config("fs.s3a.fast.upload", true).
        config("spark.cassandra.connection.host", sys.env.getOrElse("CASSANDRA_HOST", "localhost")).
        config("spark.cassandra.auth.username", sys.env.getOrElse("CASSANDRA_USERNAME", "")).
        config("spark.cassandra.auth.password", sys.env.getOrElse("CASSANDRA_PASSWORD", "")).    
        getOrCreate
    try {
      testMethod(spark)
    }
    finally spark.stop()
  }
}