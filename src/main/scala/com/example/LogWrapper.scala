package com.example

import org.slf4j.LoggerFactory

object LogWrapper extends Serializable {
  @transient lazy val logger = LoggerFactory.getLogger(getClass.getName)
}