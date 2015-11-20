package com.socrata.test.mocks

import java.io.InputStream
import java.nio.charset.{Charset, StandardCharsets}
import javax.activation.MimeType

import com.rojoma.json.v3.ast.JValue
import com.rojoma.simplearm.v2.ResourceScope

import com.socrata.http.client.Response
import com.socrata.http.common.util.Acknowledgeable
import com.socrata.http.server.responses._

import StaticResponse._

class StaticResponse(val message: String,
                     val resultCode: Int,
                     rawHeaders: Map[String, Array[String]],
                     val ct: String) extends Response {
  val resourceScope = rs
  val charset: Charset = StandardCharsets.UTF_8
  val streamCreated = true

  override def headers(name: String): Array[String] = rawHeaders(name)
  override val headerNames = rawHeaders.keys.toSet
  override val contentType = Some(new MimeType(ct))
  override def inputStream(maxBetween: Long): InputStream with Acknowledgeable =
    new AcknowledgeableInputStream(message)
}

object StaticResponse {
  private val rs = new ResourceScope()

  def apply(message: String,
            resultCode: Int = OK.statusCode,
            headers: Map[String, Array[String]] = Map.empty,
            contentType: String = "application/json"): StaticResponse =
    new StaticResponse(message, resultCode, headers, contentType)

  def apply(message: JValue): StaticResponse = apply(message.toString)
}
