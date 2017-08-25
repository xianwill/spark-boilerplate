package com

import org.apache.spark.sql.SparkSession

package object example {  
  def createSparkSession() = SparkSession.
    builder.
    appName(getClass.getName).
    master(sys.env.getOrElse("SPARK_MASTER", "")).
    config("spark.speculation", false).
    config("spark.hadoop.mapreduce.fileoutputcommitter.algorithm.version", 2).
    config("fs.s3a.fast.upload", true).
    config("spark.cassandra.connection.host", sys.env.getOrElse("CASSANDRA_HOST", "localhost")).
    config("spark.cassandra.auth.username", sys.env.getOrElse("CASSANDRA_USERNAME", "")).
    config("spark.cassandra.auth.password", sys.env.getOrElse("CASSANDRA_PASSWORD", "")).    
    getOrCreate
}
