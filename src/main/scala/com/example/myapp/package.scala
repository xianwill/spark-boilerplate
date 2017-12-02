package com.example

import org.apache.spark.sql.SparkSession

package object myapp {
  def createSparkSession() = SparkSession.
    builder.
    appName(getClass.getName).
    master(sys.env.getOrElse("SPARK_MASTER", "")).
    config("spark.speculation", false).

    config("spark.hadoop.mapreduce.fileoutputcommitter.algorithm.version", 2).
    config("fs.s3a.fast.upload", true).

    config("spark.cassandra.connection.host", sys.env.getOrElse("CASSANDRA_HOST", "0.0.0.0")).
    config("spark.cassandra.auth.username", sys.env.getOrElse("CASSANDRA_USERNAME", "")).
    config("spark.cassandra.auth.password", sys.env.getOrElse("CASSANDRA_PASSWORD", "")).
    config("spark.cassandra.output.metrics", false).
    config("spark.cassandra.output.concurrent.writes", "1").
    config("spark.cassandra.output.throughput_mb_per_sec", "100").
    config("spark.cassandra.output.consistency.level", "LOCAL_QUORUM").

    config("es.nodes", sys.env.getOrElse("ELASTICSEARCH_HOST", "0.0.0.0")).
    config("es.port", "9200").

    getOrCreate()

  def getS3Bucket() = sys.env.getOrElse("S3_BUCKET", "")
}
