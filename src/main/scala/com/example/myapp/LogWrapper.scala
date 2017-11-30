package com.example.myapp

import org.slf4j.LoggerFactory

object LogWrapper extends Serializable {
  @transient lazy val logger = LoggerFactory.getLogger(getClass.getName)
}