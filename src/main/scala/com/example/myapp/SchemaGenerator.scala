package com.example.myapp

import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType

object SchemaGenerator {
  def generateMyTypeSchema(): StructType = ScalaReflection.schemaFor[MyType].dataType.asInstanceOf[StructType]  
}