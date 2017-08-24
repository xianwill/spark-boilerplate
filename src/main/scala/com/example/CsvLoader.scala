package com.example

import org.apache.commons.csv.{CSVFormat, CSVParser}
import java.nio.charset.Charset
import scala.collection.JavaConversions._

trait CsvLoader {
  def loadCsv(resourcePath: String) = 
    CSVParser.
      parse(getClass.getResource(resourcePath), Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader()).
      getRecords
}