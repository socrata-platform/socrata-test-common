package com.socrata.testcommon.mocks

import java.io.InputStream
import java.nio.charset.{Charset, StandardCharsets}
import javax.activation.MimeType

import com.rojoma.json.v3.ast.JValue
import com.rojoma.simplearm.v2.ResourceScope

import com.socrata.http.client.Response
import com.socrata.http.common.util.Acknowledgeable
import com.socrata.http.server.responses._

import StaticResponse._

/** Represents a response from a `HttpClient`, especially useful when combined
  * with `StaticHttpClient`.
  *
  * Instances of this class should be created via function application on the
  * companion `object`.
  *
  * There are a variety of useful overloads provided, however more
  * customization points may be needed.
  *
  * Don't hesitate to submit a pull-request if you find any that are useful to
  * your project.
  */
class StaticResponse protected (val input: InputStream with Acknowledgeable,
                                val resultCode: Int,
                                rawHeaders: Map[String, Array[String]],
                                val ct: String) extends Response {
  protected def this(payload: Array[Byte],
                     resultCode: Int,
                     rawHeaders: Map[String, Array[String]],
                     ct: String) =
    this(util.AcknowledgeableInputStream(payload), resultCode, rawHeaders, ct)

  protected def this(payload: String,
                     resultCode: Int,
                     rawHeaders: Map[String, Array[String]],
                     ct: String) =
    this(util.AcknowledgeableInputStream(payload), resultCode, rawHeaders, ct)

  val resourceScope = rs
  val charset: Charset = StandardCharsets.UTF_8
  val streamCreated = true

  override def headers(name: String): Array[String] = rawHeaders(name)
  override val headerNames = rawHeaders.keys.toSet
  override val contentType = Some(new MimeType(ct))
  override def inputStream(maxBetween: Long): InputStream with Acknowledgeable = input
}

object StaticResponse {
  private val rs = new ResourceScope()

  def apply(payload: Array[Byte],
            statusCode: Int,
            headers: Map[String, Array[String]]): StaticResponse = new StaticResponse(
    util.AcknowledgeableInputStream(payload), statusCode, headers, "application/octet-stream")
  def apply(payload: Array[Byte]): StaticResponse = apply(payload, OK.statusCode, Map.empty)

  def apply(message: String,
            statusCode: Int,
            headers: Map[String, Array[String]],
            contentType: String): StaticResponse = new StaticResponse(
    util.AcknowledgeableInputStream(message), statusCode, headers, contentType)

  def apply(message: String, statusCode: Int): StaticResponse =
    apply(message, statusCode, Map.empty, "application/json")

  def apply(message: String): StaticResponse = apply(message, OK.statusCode, Map.empty, "application/json")

  def apply(message: JValue): StaticResponse = apply(message.toString)
}
