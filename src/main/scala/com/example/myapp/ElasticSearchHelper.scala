package com.example.myapp

import org.apache.commons.httpclient._
import org.apache.http.client.methods._
import org.apache.http.client.ResponseHandler
import org.apache.http.impl.client.{ DefaultHttpClient, HttpClients }
import org.apache.http.{HttpResponse,HttpEntity}
import org.apache.http.entity.StringEntity

object ElasticSearchHelper {
  def forceIndex(indexName: String, indexConfig: String) = {
    val hostName = sys.env.getOrElse("ELASTICSEARCH_HOST", "0.0.0.0")
    val indexUrl = s"http://$hostName:9200/$indexName"

    httpForcePut(indexUrl, indexConfig)
  }

  def httpForcePut(resourceUrl: String, stringValue: String) = {
    val client = HttpClients.createDefault()

    try {
      val checkExistenceReq = new HttpHead(resourceUrl)
      val checkExistenceRes = client.execute(checkExistenceReq)
      val checkExistenceStat = checkExistenceRes.getStatusLine().getStatusCode()

      if (checkExistenceStat == 200) {
        val deleteReq = new HttpDelete(resourceUrl)
        client.execute(deleteReq)
      }

      val params = new StringEntity(stringValue, "UTF-8")
      params.setContentType("application/json")
      val putReq = new HttpPut(resourceUrl)
      putReq.addHeader("content-type", "application/json")
      putReq.setEntity(params)
      val putRes = client.execute(putReq)
      val putStatus = putRes.getStatusLine().getStatusCode()

      putStatus >= 200 && putStatus < 300
    } finally {
      client.close()
    }
  }
}
