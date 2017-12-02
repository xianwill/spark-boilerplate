import sbt._

object Dependencies {
  lazy val sparkVersion = "2.2.0"

  lazy val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion % Provided
  lazy val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % Provided
  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
  lazy val scallop = "org.rogach" %% "scallop" % "2.1.1"
  lazy val cassConnector = "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.1"
  lazy val elasticSearchSpark = "org.elasticsearch" %% "elasticsearch-spark-20" % "6.0.0"
  lazy val awsSdk = "com.amazonaws" % "aws-java-sdk" % "1.7.4" % Provided
  lazy val hadoopAws = "org.apache.hadoop" % "hadoop-aws" % "2.7.3" % Provided
  lazy val json4sNative = "org.json4s" %% "json4s-native" % "3.5.3"
  lazy val commonsCsv = "org.apache.commons" % "commons-csv" % "1.4"
  lazy val httpclient = "org.apache.httpcomponents" % "httpclient" % "4.5.3"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
}
