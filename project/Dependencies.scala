import sbt._

object Dependencies {
  lazy val sparkVersion = "2.2.0"

  lazy val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion % Provided
  lazy val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion % Provided
  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.25"
  lazy val log4jOverSlf4j = "org.slf4j" % "log4j-over-slf4j" % "1.7.25"
  lazy val logbackCore = "ch.qos.logback" % "logback-core" % "1.1.7"
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.1.7"
  lazy val scallop = "org.rogach" %% "scallop" % "2.1.1"
  lazy val cassConnector = "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.1"
  lazy val awsSdk = "com.amazonaws" % "aws-java-sdk" % "1.7.4" % Provided
  lazy val hadoopAws = "org.apache.hadoop" % "hadoop-aws" % "2.7.3" % Provided
  lazy val json4sNative = "org.json4s" %% "json4s-native" % "3.5.3"
  lazy val commonsCsv = "org.apache.commons" % "commons-csv" % "1.4"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test
}
