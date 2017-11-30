package com.example.myapp

import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.{DataFrame,Column}
import org.apache.spark.sql.functions._

object DataFrameHelper {
  implicit def applyHelper(dataFrame: DataFrame) = new DataFrameHelper(dataFrame)
}

class DataFrameHelper(df: DataFrame) {
  def rollup(dimensions: Seq[String], aggregateSpec: Seq[(String, String)]) = {
    val dimCols = dimensions.map(d => col(d))
    val aggregates = aggregateSpec.map { case(colName, alias) => {
      sum(colName).alias(alias)
    }}
    val grouped = df.groupBy(dimCols: _*)

    if (aggregates.length > 1)
      grouped.agg(aggregates.head, aggregates.tail: _*)
    else
      grouped.agg(aggregates.head)
  }

  def withJoins(dfs: Seq[DataFrame], joinCols: Seq[Seq[String]]) = {
    var joined = df

    dfs.zip(joinCols).foreach { case(dfn, joinCol) => {
      joined = joined.join(dfn, joinCol, "left_outer")
    }}

    joined
  }

  def writeUniqueRowsToCsv(path: String, colNames: String*) = {
    val cols = colNames.map(c => col(c))
    val uniqueDf = df.select(cols: _*).distinct()

    uniqueDf.coalesce(1).
    write.option("header", "true").
      mode(SaveMode.Overwrite).csv(path)

    df
  }

  def writePartitionedRowsToCsv(path: String, partitionColNames: String*) = {
    val cols = partitionColNames.map(c => col(c))

    df.
      repartition(cols: _*).
      write.
      option("header", "true").
      partitionBy(partitionColNames: _*).
      mode(SaveMode.Overwrite).
      csv(path)

    df
  }

  def writeToSingleCsv(path: String) = {
    df.
      coalesce(1).
      write.
      option("header", "true").
      mode(SaveMode.Overwrite).
      csv(path)
  }

  def writeToSingleJson(path: String) = {
    df.
      coalesce(1).
      write.
      option("header", "true").
      mode(SaveMode.Overwrite).
      json(path)
  }
}