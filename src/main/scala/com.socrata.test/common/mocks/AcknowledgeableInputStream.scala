package com.socrata.test.common.mocks

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.charset.{Charset, StandardCharsets}

import com.socrata.http.common.util.Acknowledgeable

/** Will return user provided data to its consumers. */
class AcknowledgeableInputStream(payload: Array[Byte])
    extends InputStream
    with Acknowledgeable {
  def this(payload: String, charset: Charset) = this(payload.getBytes(charset))
  def this(payload: String) = this(payload, StandardCharsets.UTF_8)
  def this() = this(Array.empty[Byte])

  val underlying = new ByteArrayInputStream(payload)

  override def acknowledge(): Unit = ()
  override def read(): Int = underlying.read
}

object AcknowledgeableInputStream {
  def apply(payload: Array[Byte]): AcknowledgeableInputStream = new AcknowledgeableInputStream(payload)
  def apply(payload: String, charset: Charset): AcknowledgeableInputStream = apply(payload.getBytes(charset))
  def apply(payload: String): AcknowledgeableInputStream = apply(payload, StandardCharsets.UTF_8)
  def apply(): AcknowledgeableInputStream = apply(Array.empty[Byte])
}
