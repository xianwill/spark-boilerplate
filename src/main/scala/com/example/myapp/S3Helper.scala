package com.example.myapp

import scala.io.Source
import scala.annotation.tailrec
import scala.collection.JavaConversions._
import java.io.{ByteArrayInputStream, InputStream}
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ListObjectsRequest, ObjectMetadata}

object S3Helper {
  def getBucketAndKeyFromPath(url: String) : (String, String) = {
    val bucketAndPrefix = url.split("://").takeRight(1).head
    val arr = bucketAndPrefix.split("/")

    (arr.head, arr.tail.mkString("/"))
  }

  def getObjectText(bucket: String, key: String) = {
    val s3 = new AmazonS3Client()
    val f = s3.getObject(bucket, key).getObjectContent

    Source.fromInputStream(f).mkString
  }

  def putObjectText(bucket: String, key: String, content: String): Unit = {
    val s3 = new AmazonS3Client
    val bytes = content.getBytes
    val is = new ByteArrayInputStream(bytes)
    val om = new ObjectMetadata

    om.setContentLength(bytes.length.toLong)
    s3.putObject(bucket, key, is, om)
    is.close()
  }

  def deleteObject(bucket: String, prefix: String) : Unit = {
    val s3 = new AmazonS3Client
    s3.deleteObject(bucket, prefix)
  }

  def listObjectsByPrefix(bucket: String, prefix: String): Seq[String] = {
    val s3 = new AmazonS3Client

    fetchkeys(s3, bucket, prefix, Seq())
  }

  @tailrec
  private def fetchkeys(
      s3: AmazonS3Client,
      bucket: String,
      prefix: String,
      keys: Seq[String],
      marker: Option[String] = None): Seq[String] = {
    val r = new ListObjectsRequest

    r.setBucketName(bucket)
    r.setPrefix(prefix)
    r.setMaxKeys(1000)

    marker.foreach(r.setMarker)

    val ol = s3.listObjects(r)
    val ks = ol.getObjectSummaries.map(_.getKey)

    if (!ol.isTruncated) keys ++ ks
    else fetchkeys(s3, bucket, prefix, keys ++ ks, Some(ol.getNextMarker))
  }  
}
